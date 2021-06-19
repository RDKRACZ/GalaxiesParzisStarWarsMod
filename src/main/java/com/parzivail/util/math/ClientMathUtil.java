package com.parzivail.util.math;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.*;

@Environment(EnvType.CLIENT)
public class ClientMathUtil
{
	public static final Matrix4f MATRIX_IDENTITY = new Matrix4f();

	static
	{
		MATRIX_IDENTITY.loadIdentity();
	}

	public static Vec3f transform(Vec3f v, Matrix4f m)
	{
		Vector4f v4 = new Vector4f(v);
		v4.transform(m);
		return new Vec3f(v4.getX(), v4.getY(), v4.getZ());
	}

	public static Vec3f transform(Vec3f v, Matrix3f m)
	{
		Vec3f v3 = v.copy();
		v3.transform(m);
		return v3;
	}

	public static Quaternion getRotation(Direction direction)
	{
		return switch (direction)
				{
					case DOWN -> new Quaternion(90, 0, 0, true);
					case UP -> new Quaternion(-90, 0, 0, true);
					case NORTH -> new Quaternion(0, 90, 0, true);
					case SOUTH -> new Quaternion(0, -90, 0, true);
					case WEST -> new Quaternion(0, 180, 0, true);
					case EAST -> new Quaternion(0, 0, 0, true);
				};
	}
}
