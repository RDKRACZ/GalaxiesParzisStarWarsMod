package com.parzivail.swg.render.entity;

import com.parzivail.swg.ship.BasicFlightModel;
import com.parzivail.util.ui.gltk.EnableCap;
import com.parzivail.util.ui.gltk.GL;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by colby on 12/26/2017.
 */
public abstract class RenderBasicFlightModel extends Render
{
	private final float verticalCenteringOffset;

	public RenderBasicFlightModel(float verticalCenteringOffset)
	{
		this.verticalCenteringOffset = verticalCenteringOffset;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float unknown, float partialTicks)
	{
		if (!(entity instanceof BasicFlightModel))
			return;

		BasicFlightModel ship = (BasicFlightModel)entity;
		GL.Disable(EnableCap.CullFace);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL.PushMatrix();
		GL.Translate(x, y, z);
		GL.Rotate(-90, 1, 0, 0);

		GL.Translate(0, 0, verticalCenteringOffset);
		float dYaw = MathHelper.wrapAngleTo180_float(ship.orientation.getYaw() - ship.previousOrientation.getYaw());
		float dPitch = MathHelper.wrapAngleTo180_float(ship.orientation.getPitch() - ship.previousOrientation.getPitch());
		float dRoll = MathHelper.wrapAngleTo180_float(ship.orientation.getRoll() - ship.previousOrientation.getRoll());
		GL11.glRotatef(180F - ship.previousOrientation.getYaw() - dYaw * partialTicks, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(ship.previousOrientation.getPitch() + dPitch * partialTicks + 180, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(-(ship.previousOrientation.getRoll() + dRoll * partialTicks) + 180, 1.0F, 0.0F, 0.0F);
		GL.Translate(0, 0, -verticalCenteringOffset);

		doRender(ship, partialTicks);

		GL.PopMatrix();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL.Enable(EnableCap.CullFace);
	}

	public abstract void doRender(BasicFlightModel ship, float partialTicks);

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return null;
	}
}
