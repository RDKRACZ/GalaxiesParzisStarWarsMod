package com.parzivail.pswg.item.lightsaber.data;

import net.minecraft.util.Identifier;

public record LightsaberDescriptor(Identifier id, String ownerName, Identifier hiltModel, float bladeHue, float bladeSaturation, float bladeValue)
{
}
