package com.parzivail.swg.block;

import com.parzivail.swg.Resources;
import com.parzivail.swg.registry.BlockRegister;
import com.parzivail.util.block.PBlockPillar;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockEndorLog extends PBlockPillar
{
	public BlockEndorLog()
	{
		super("endorLog");
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg)
	{
		// side icon
		this.field_150167_a = new IIcon[3];

		// top icon
		this.field_150166_b = new IIcon[1];

		this.field_150167_a[0] = reg.registerIcon(Resources.modColon(name + "_sideClean"));
		this.field_150167_a[1] = reg.registerIcon(Resources.modColon(name + "_sideBlend"));
		this.field_150167_a[2] = reg.registerIcon(Resources.modColon(name + "_sideFlora"));

		this.field_150166_b[0] = reg.registerIcon(Resources.modColon(name + "_top"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side)
	{
		int meta = worldIn.getBlockMetadata(x, y, z);
		int k = meta & 12;
		int l = meta & 3;

		if (y <= 0 || meta != 0)
			return k == 0 && (side == 1 || side == 0) ? this.getTopIcon(l) : (k == 4 && (side == 5 || side == 4) ? this.getTopIcon(l) : (k == 8 && (side == 2 || side == 3) ? this.getTopIcon(l) : this.getSideIcon(l)));

		return k == 0 && (side == 1 || side == 0) ? this.getTopIcon(l) : (k == 4 && (side == 5 || side == 4) ? this.getTopIcon(l) : (k == 8 && (side == 2 || side == 3) ? this.getTopIcon(l) : this.getSideIcon(l, worldIn, x, y, z)));
	}

	private IIcon getSideIcon(int icon, IBlockAccess worldIn, int x, int y, int z)
	{
		Block blockBelow = worldIn.getBlock(x, y - 1, z);
		if (blockBelow == BlockRegister.endorLog)
		{
			if (y <= 1)
				return field_150167_a[0];

			Block blockBelowThat = worldIn.getBlock(x, y - 2, z);
			if (blockBelowThat == Blocks.grass || blockBelowThat == BlockRegister.fastGrass)
				return field_150167_a[1];

		}
		if (blockBelow == Blocks.grass || blockBelow == BlockRegister.fastGrass)
			return field_150167_a[2];

		return field_150167_a[0];
	}
}
