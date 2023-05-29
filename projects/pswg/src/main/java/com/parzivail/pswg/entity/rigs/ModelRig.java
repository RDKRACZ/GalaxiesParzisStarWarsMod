package com.parzivail.pswg.entity.rigs;

import com.parzivail.p3d.P3dModel;
import com.parzivail.util.math.MathUtil;
import com.parzivail.util.math.Transform;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public abstract class ModelRig<T>
{
	private final P3dModel RIG;

	protected ModelRig(Identifier path)
	{
		RIG = P3dModel.tryLoad(path, false);
	}

	public Matrix4f getTransform(T target, Quaternionf orientation, String socketName, float tickDelta)
	{
		var mat = new Matrix4f();

		mat.rotate(orientation);

		RIG.transformToSocket(mat, socketName, target, tickDelta, this::getPartTransformation);

		return mat;
	}

	public Vec3d getWorldPosition(Transform stack, T target, Quaternionf orientation, String socketName, float tickDelta)
	{
		stack.save();
		stack.multiply(getTransform(target, orientation, socketName, tickDelta));

		var entry = stack.value();
		var parent = entry.getModel();
		var vec = MathUtil.transform(Vec3d.ZERO, parent);
		stack.restore();

		return vec;
	}

	public abstract Matrix4f getPartTransformation(T target, String part, float tickDelta);
}
