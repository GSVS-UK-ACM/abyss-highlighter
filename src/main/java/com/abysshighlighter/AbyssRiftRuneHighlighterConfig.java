package com.abysshighlighter;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup(AbyssRiftRuneHighlighterConfig.CONFIG_GROUP)
public interface AbyssRiftRuneHighlighterConfig extends Config
{
	String CONFIG_GROUP = "abyss-rift-rune-highlighter";

	@ConfigItem(
		keyName = "showRuneNames",
		name = "Show rune names",
		description = "Shows each rune name beneath its icon",
		position = 0
	)
	default boolean showRuneNames()
	{
		return true;
	}

	@Range(min = 20, max = 64)
	@ConfigItem(
		keyName = "iconSize",
		name = "Icon size",
		description = "Size of rune icons above the rifts",
		position = 1
	)
	default int iconSize()
	{
		return 32;
	}

	@Range(min = 80, max = 300)
	@ConfigItem(
		keyName = "heightOffset",
		name = "Height offset",
		description = "Vertical position of rune icons above the rifts",
		position = 2
	)
	default int heightOffset()
	{
		return 170;
	}

	@ConfigItem(
		keyName = "nameColor",
		name = "Name colour",
		description = "Colour used for rune names",
		position = 3
	)
	default Color nameColor()
	{
		return Color.WHITE;
	}
}

