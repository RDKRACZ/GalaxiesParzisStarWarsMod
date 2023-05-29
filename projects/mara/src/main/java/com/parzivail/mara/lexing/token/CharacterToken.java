package com.parzivail.mara.lexing.token;

import com.parzivail.mara.lexing.TokenType;

public class CharacterToken extends Token
{
	public final char value;

	public CharacterToken(char value, int location)
	{
		super(TokenType.CharacterLiteral, location);
		this.value = value;
	}
}
