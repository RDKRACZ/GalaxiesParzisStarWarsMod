package com.parzivail.errorman;

import com.google.gson.Gson;
import com.parzivail.errorman.model.*;
import com.parzivail.pswg.Resources;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.crash.CrashReport;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class ErrorManager
{
	private static final Gson GSON = new Gson();
	private static final String ROLLBAR_API_ENDPOINT = "https://api.rollbar.com/api/1/item/";
	private static final String ROLLBAR_CLIENT_TOKEN = "d9b378407e67416bad536678502410d1";

	public static void onCrash(CrashReport report)
	{
		// Do not ask to report errors if the user has disabled it
		if (!Resources.CONFIG.get().askToSendCrashReports)
			return;

		// Do not ask to report errors in development environments
		if (FabricLoader.getInstance().isDevelopmentEnvironment())
			return;

		var cause = report.getCause();
		var atFault = false;

		var depth = 0;
		while (cause != null && depth < 10)
		{
			var trace = cause.getStackTrace();

			if (Arrays.stream(trace).map(StackTraceElement::getClassName).anyMatch(s -> s.startsWith("com.parzivail")))
			{
				atFault = true;
				break;
			}

			cause = cause.getCause();

			// Prevent infinite loops for poorly-formed
			// exception objects
			depth++;
		}

		// Do not ask to report errors if PSWG's package
		// isn't directly referenced in the stack trace
		if (!atFault)
			return;

		getConsentToDispatch(report);
	}

	private static void getConsentToDispatch(CrashReport report)
	{
		// Do not report errors without the user's consent
		var message = "PSWG has crashed! Send this crash report to the developers?";
		if (TinyFileDialogs.tinyfd_messageBox("PSWG Error", message, "yesno", "error", true))
			dispatchError(report);
	}

	private static void dispatchError(CrashReport report)
	{
		var modSet = new HashMap<String, String>();

		String pswgVersion = "<unknown>";
		for (var mod : FabricLoader.getInstance().getAllMods())
		{
			var meta = mod.getMetadata();

			if (meta.getId().equals(Resources.MODID))
				pswgVersion = meta.getVersion().getFriendlyString();

			// Exclude Fabric API modules
			var lifecycle = meta.getCustomValue("fabric-api:module-lifecycle");
			if (lifecycle != null)
				continue;

			// Exclude mods without paths
			var paths = mod.getOrigin().getPaths();
			if (paths.isEmpty())
				continue;

			var filename = paths.get(0).getFileName().toString();
			modSet.put(meta.getId(), String.format("%s!%s", meta.getVersion().getFriendlyString(), filename));
		}

		// Do not report errors if the version field is
		// known but unpopulated
		if ("${version}".equals(pswgVersion))
			return;

		var traceStack = new Stack<RollbarTrace>();

		var cause = report.getCause();
		var depth = 0;
		while (cause != null && depth < 10)
		{
			var exc = new RollbarException(cause.getClass().getName(), cause.getMessage());

			var trace = cause.getStackTrace();
			var frames = new RollbarFrame[trace.length];

			for (var i = 0; i < trace.length; i++)
				frames[trace.length - i - 1] = new RollbarFrame(trace[i].getFileName(), trace[i].getClassName(), trace[i].getMethodName(), trace[i].getLineNumber());

			traceStack.push(new RollbarTrace(exc, frames));

			cause = cause.getCause();

			// Prevent infinite loops for poorly-formed
			// exception objects
			depth++;
		}

		var traceChain = new RollbarTrace[traceStack.size()];
		var i = 0;
		while (!traceStack.empty())
			traceChain[i++] = traceStack.pop();

		var request = new RollbarRequest(new RollbarData(
				pswgVersion,
				"client",
				"java",
				new RollbarBody(traceChain),
				new RollbarRequestInfo(modSet)
		));
		var requestJson = GSON.toJson(request);

		try (var httpclient = HttpClients.createDefault())
		{
			HttpPost httppost = new HttpPost(ROLLBAR_API_ENDPOINT);
			httppost.addHeader("X-Rollbar-Access-Token", ROLLBAR_CLIENT_TOKEN);
			httppost.addHeader("Accept", "application/json");
			httppost.setEntity(new StringEntity(requestJson, ContentType.APPLICATION_JSON));

			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();

			if (entity != null)
			{
				try (InputStream instream = entity.getContent())
				{
					var rollbarResponse = GSON.fromJson(new InputStreamReader(instream), RollbarResponse.class);

					var section = report.addElement("PSWG Crash Submission Details");
					section.add("Status", rollbarResponse.err());
					if (rollbarResponse.result() != null)
						section.add("Report ID", rollbarResponse.result().uuid());
					else if (rollbarResponse.message() != null)
						section.add("Error Message", rollbarResponse.message());
				}
			}
		}
		catch (Exception e)
		{
			// Handle no exceptions
		}
	}
}
