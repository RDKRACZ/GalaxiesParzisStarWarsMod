package com.parzivail.datagen.tarkin;

import com.parzivail.datagen.tarkin.config.PswgTarkin;
import com.parzivail.datagen.tarkin.config.TcwTarkin;
import com.parzivail.tarkin.api.TarkinItem;
import com.parzivail.tarkin.api.TarkinLang;
import com.parzivail.util.Lumberjack;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * T.A.R.K.I.N. - Text Asset Record-Keeping, Integration, and Normalization
 */
public class Tarkin
{
	public static final Lumberjack LOG = new Lumberjack("TARKIN");

	public static void main() throws Exception
	{
		AssetGenerator.setOutputRoot(Path.of(System.getenv("TARKIN_OUT_DIR")));

		List<BuiltAsset> assets = new ArrayList<>();

		var tarkinModid = System.getProperty("tarkin", "");

		switch (tarkinModid)
		{
			case "pswg":
				PswgTarkin.build(assets);
				break;
			case "pswg_addon_clonewars":
				TcwTarkin.build(assets);
				break;
		}

		BuiltAsset.nukeRecipeDir();
		BuiltAsset.nukeBlockstateDir();
		BuiltAsset.nukeBlockModelJsons();
		BuiltAsset.nukeItemModelJsons();
		BuiltAsset.nukeBlockLootTables();
		BuiltAsset.nukeTags();

		for (var asset : assets)
		{
			LOG.log("Wrote %s", asset.getFilename());
			asset.write();
		}

		LOG.log("Wrote %s assets", assets.size());

		// Synchronize the keys of the en_us locale
		BuiltAsset.mergeLanguageKeys(new Identifier(tarkinModid, LanguageProvider.OUTPUT_LOCALE), new Identifier(tarkinModid, LanguageProvider.TARGET_LOCALE));
		LOG.log("Merged language keys");

		LOG.log("Done");
	}

	public static void generateLangFromConfigAnnotations(LanguageBuilder autoconfigOption, List<BuiltAsset> assets, Class<?> config)
	{
		var subclasses = Arrays.asList(config.getDeclaredClasses());

		for (var field : config.getDeclaredFields())
		{
			var fieldLang = autoconfigOption.dot(field.getName());
			fieldLang.build(assets);

			if (field.isAnnotationPresent(ConfigEntry.Gui.Tooltip.class))
			{
				String defaultValue = null;

				var commentAnnotation = field.getAnnotation(Comment.class);
				if (commentAnnotation != null)
					defaultValue = commentAnnotation.value();

				fieldLang.dot("@Tooltip").build(assets, defaultValue);
			}

			if (subclasses.contains(field.getType()))
			{
				var subclassLang = autoconfigOption.dot(field.getName());
				generateLangFromConfigAnnotations(subclassLang, assets, field.getType());
			}
		}
	}

	public static <T, TA extends Annotation> void consumeFields(Class<TA> annotationClazz, Class<?> rootClazz, Class<T> registryType, BiConsumer<T, TA> consumer)
	{
		for (var field : rootClazz.getFields())
		{
			var annotation = field.getAnnotation(annotationClazz);
			if (!Modifier.isStatic(field.getModifiers()) || annotation == null || !registryType.isAssignableFrom(field.getType()))
				continue;

			try
			{
				consumer.accept((T)field.get(null), annotation);
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}

		for (var clazz : rootClazz.getClasses())
			consumeFields(annotationClazz, clazz, registryType, consumer);
	}

	public static void registerLangFields(Class<?> rootClazz, LanguageBuilder languageBuilder, List<BuiltAsset> assets)
	{
		consumeFields(TarkinLang.class, rootClazz, String.class, (s, a) -> languageBuilder.entry(s).build(assets));
	}

	public static void registerItemFields(Class<?> rootClazz, List<BuiltAsset> assets)
	{
		consumeFields(TarkinItem.class, rootClazz, Item.class, (item, a) -> {
			var gen = new ItemGenerator(item);

			switch (a.lang())
			{
				case Item -> gen.lang(LanguageProvider::item);
			}

			switch (a.model())
			{
				case Empty -> gen.model(ModelFile::empty);
				case Item -> gen.model(ModelFile::item);
				case SpawnEgg -> gen.model(ModelFile::spawn_egg);
				case HandheldItem -> gen.model(ModelFile::handheld_item);
			}

			gen.build(assets);
		});
	}
}
