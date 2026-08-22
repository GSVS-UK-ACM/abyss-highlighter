package com.abysshighlighter;

import java.awt.Color;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AbyssRiftRuneHighlighterConfigTest
{
	private final AbyssRiftRuneHighlighterConfig config = new AbyssRiftRuneHighlighterConfig() { };

	@Test
	public void usesReadableDefaults()
	{
		assertTrue(config.showRuneNames());
		assertEquals(40, config.iconSize());
		assertEquals(14, config.fontSize());
		assertEquals(210, config.heightOffset());
		assertEquals(Color.WHITE, config.nameColor());
		assertEquals("abyss-rift-rune-highlighter", AbyssRiftRuneHighlighterConfig.CONFIG_GROUP);
	}
}
