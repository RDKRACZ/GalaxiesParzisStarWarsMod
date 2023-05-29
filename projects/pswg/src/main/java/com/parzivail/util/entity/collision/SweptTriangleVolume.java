package com.parzivail.util.entity.collision;

import com.parzivail.util.math.CollisionUtil;
import com.parzivail.util.math.MathUtil;
import com.parzivail.util.math.QuatUtil;
import net.minecraft.util.math.Vec3d;
import org.apache.commons.lang3.mutable.MutableObject;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public record SweptTriangleVolume(Vec3d a, Vec3d b, Vec3d c, double radius) implements ICollisionVolume
{
	@Override
	public ICollisionVolume transform(Quaternionf q)
	{
		return new SweptTriangleVolume(QuatUtil.rotate(a, q), QuatUtil.rotate(b, q), QuatUtil.rotate(c, q), radius);
	}

	@Override
	public ICollisionVolume transform(Matrix4f m)
	{
		return new SweptTriangleVolume(MathUtil.transform(a, m), MathUtil.transform(b, m), MathUtil.transform(c, m), radius);
	}

	@Override
	public boolean resolveCapsuleCollision(CapsuleVolume sourceHitbox, MutableObject<Vec3d> movementContainer)
	{
		var movement = movementContainer.getValue();
		var result = CollisionUtil.closestPointsTriangleSegment(sourceHitbox.start().add(movement), sourceHitbox.end().add(movement), a, b, c);

		var intersectionRay = result.b().subtract(result.a());

		var minDistance = sourceHitbox.radius() + radius;

		// Check if the volume is intersecting
		if (result.squareDistance() > minDistance * minDistance)
			return false;

		var intersectionLength = intersectionRay.length();
		var overlap = intersectionLength - minDistance;

		var impulse = intersectionRay.multiply(overlap / intersectionLength);

		var slideMovement = movement.add(impulse);

		movementContainer.setValue(slideMovement);

		return true;
	}
}
