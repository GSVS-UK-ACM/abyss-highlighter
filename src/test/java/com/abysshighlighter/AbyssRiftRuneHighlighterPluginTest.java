package com.abysshighlighter;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class AbyssRiftRuneHighlighterPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(AbyssRiftRuneHighlighterPlugin.class);
		RuneLite.main(args);
	}
}
