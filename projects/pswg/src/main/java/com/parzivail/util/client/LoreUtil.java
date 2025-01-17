package com.parzivail.util.client;

import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LoreUtil
{
	public static Text getLore(Item item, Object... args)
	{
		var reg = Registry.ITEM.getId(item);
		return Text.translatable(getLoreKey("item", reg), args).formatted(Formatting.GRAY, Formatting.ITALIC);
	}

	public static String getLoreKey(Item item)
	{
		var reg = Registry.ITEM.getId(item);
		return getLoreKey("item", reg);
	}

	public static String getLoreKey(String domain, Identifier registryId)
	{
		return "lore." + domain + "." + registryId.getNamespace() + "." + registryId.getPath();
	}
}
