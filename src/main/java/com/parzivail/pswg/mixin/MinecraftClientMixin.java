package com.parzivail.pswg.mixin;

import com.parzivail.pswg.Client;
import com.parzivail.pswg.client.texture.remote.RemoteTextureProvider;
import com.parzivail.pswg.client.texture.stacked.StackedTextureProvider;
import com.parzivail.pswg.client.texture.tinted.stacked.TintedTextureProvider;
import com.parzivail.pswg.handler.LeftClickHandler;
import com.parzivail.util.Lumberjack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.ReloadableResourceManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public abstract class MinecraftClientMixin
{
	@Shadow
	@Final
	private TextureManager textureManager;

	@Shadow
	@Final
	private ReloadableResourceManager resourceManager;

	@Shadow
	@Nullable
	public ClientPlayerInteractionManager interactionManager;

	@Shadow
	protected abstract void doAttack();

	@Inject(method = "<init>", at = @At("TAIL"))
	private void initTail(RunArgs args, CallbackInfo ci)
	{
		var remoteAssetDir = args.directories.assetDir.toPath().resolve("pswgRemoteAssets");
		Lumberjack.debug("Remote asset directory: %s", remoteAssetDir.toString());
		Client.remoteTextureProvider = new RemoteTextureProvider(textureManager, "pswg:remote", remoteAssetDir);
		Client.stackedTextureProvider = new StackedTextureProvider(textureManager, "pswg:stacked");
		Client.tintedTextureProvider = new TintedTextureProvider(textureManager, "pswg:tinted");
	}

	@Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;initializeSearchableContainers()V", shift = At.Shift.BEFORE))
	private void initAtSearchable(RunArgs args, CallbackInfo ci)
	{
		// Registering the reloadable resource managers here because this method is
		// only called once and it's before the tail of MinecraftClient's <init>,
		// where the resource manager is reloaded

		Client.ResourceManagers.registerReloadableManagers(resourceManager);
	}

	@Inject(method = "handleInputEvents()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;handleBlockBreaking(Z)V", shift = At.Shift.BEFORE), cancellable = true)
	private void handleInputEvents$handleBlockBreaking(CallbackInfo ci)
	{
		// interactionManager cannot be null here
		assert interactionManager != null;
		LeftClickHandler.handleInputEvents(this::doAttack, interactionManager, ci);
	}

	@Inject(method = "handleInputEvents()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z", shift = At.Shift.BEFORE, ordinal = 0), cancellable = true)
	private void handleInputEvents$isUsingItem(CallbackInfo ci)
	{
		LeftClickHandler.handleIsUsingItemAttack(this::doAttack, ci);
	}

	@Inject(method = "doAttack()V", at = @At("HEAD"), cancellable = true)
	private void doAttack(CallbackInfo ci)
	{
		LeftClickHandler.doAttack(ci);
	}
}
