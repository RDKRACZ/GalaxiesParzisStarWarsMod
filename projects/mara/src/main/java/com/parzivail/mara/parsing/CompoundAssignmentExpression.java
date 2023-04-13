package com.parzivail.mara.parsing;

import com.parzivail.mara.lexing.Token;

public class CompoundAssignmentExpression extends Expression
{
	public final Expression left;
	public final Token operator;
	public final Expression right;

	public CompoundAssignmentExpression(Expression left, Token operator, Expression right)
	{
		super(left.firstToken);
		this.left = left;
		this.operator = operator;
		this.right = right;
	}

	@Override
	public String toString()
	{
		return String.format("(%s [%s]= %s)", left, operator.type, right);
	}
}
