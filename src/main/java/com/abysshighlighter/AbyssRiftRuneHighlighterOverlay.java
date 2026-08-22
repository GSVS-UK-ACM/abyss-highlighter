package com.abysshighlighter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.DecorativeObject;
import net.runelite.api.Perspective;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.api.WorldView;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

class AbyssRiftRuneHighlighterOverlay extends Overlay
{
	static final int ABYSS_REGION_ID = 12107;
	private static final int LABEL_GAP = 3;

	private final Client client;
	private final ItemManager itemManager;
	private final AbyssRiftRuneHighlighterPlugin plugin;
	private final AbyssRiftRuneHighlighterConfig config;
	private final Map<Integer, BufferedImage> runeImages = new HashMap<>();
	private Font cachedBaseFont;
	private Font cachedLabelFont;
	private int cachedFontSize = -1;

	@Inject
	AbyssRiftRuneHighlighterOverlay(
		Client client,
		ItemManager itemManager,
		AbyssRiftRuneHighlighterPlugin plugin,
		AbyssRiftRuneHighlighterConfig config)
	{
		this.client = client;
		this.itemManager = itemManager;
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_SCENE);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		Player localPlayer = client.getLocalPlayer();
		if (localPlayer == null || localPlayer.getWorldLocation().getRegionID() != ABYSS_REGION_ID)
		{
			return null;
		}

		boolean showNames = config.showRuneNames();
		int iconSize = config.iconSize();
		int heightOffset = config.heightOffset();
		Color nameColor = showNames ? config.nameColor() : null;
		Font originalFont = graphics.getFont();
		if (showNames)
		{
			graphics.setFont(getLabelFont(originalFont, config.fontSize()));
		}

		try
		{
			for (DecorativeObject object : plugin.getTrackedAbyssRifts())
			{
				if (isInActiveWorldView(object))
				{
					renderRune(graphics, object, iconSize, heightOffset, showNames, nameColor);
				}
			}
		}
		finally
		{
			if (showNames)
			{
				graphics.setFont(originalFont);
			}
		}
		return null;
	}

	private void renderRune(
		Graphics2D graphics,
		DecorativeObject object,
		int iconSize,
		int heightOffset,
		boolean showNames,
		Color nameColor)
	{
		AbyssRiftRune rift = AbyssRiftRune.fromObjectId(object.getId());
		if (rift == null)
		{
			return;
		}

		BufferedImage image = runeImages.computeIfAbsent(
			rift.getRuneItemId(),
			itemManager::getImage
		);
		if (image == null)
		{
			return;
		}

		Point originalLocation = Perspective.getCanvasImageLocation(
			client,
			object.getLocalLocation(),
			image,
			heightOffset
		);
		if (originalLocation == null)
		{
			return;
		}

		int centerX = originalLocation.getX() + image.getWidth() / 2;
		int centerY = originalLocation.getY() + image.getHeight() / 2;
		int iconX = centerX - iconSize / 2;
		int iconY = centerY - iconSize / 2;
		graphics.drawImage(image, iconX, iconY, iconSize, iconSize, null);

		if (showNames)
		{
			renderName(graphics, rift.getDisplayName(), centerX, iconY + iconSize, nameColor);
		}
	}

	private void renderName(Graphics2D graphics, String name, int centerX, int iconBottom, Color nameColor)
	{
		int textX = centerX - graphics.getFontMetrics().stringWidth(name) / 2;
		int textY = iconBottom + LABEL_GAP + graphics.getFontMetrics().getAscent();
		OverlayUtil.renderTextLocation(
			graphics,
			new Point(textX, textY),
			name,
			nameColor
		);
	}

	private Font getLabelFont(Font baseFont, int fontSize)
	{
		if (cachedLabelFont == null || cachedFontSize != fontSize || !baseFont.equals(cachedBaseFont))
		{
			cachedBaseFont = baseFont;
			cachedFontSize = fontSize;
			cachedLabelFont = baseFont.deriveFont(Font.BOLD, (float) fontSize);
		}
		return cachedLabelFont;
	}

	void clearCaches()
	{
		runeImages.clear();
		cachedBaseFont = null;
		cachedLabelFont = null;
		cachedFontSize = -1;
	}

	private boolean isInActiveWorldView(DecorativeObject object)
	{
		WorldView activeWorldView = client.getTopLevelWorldView();
		Player localPlayer = client.getLocalPlayer();
		if (localPlayer != null && localPlayer.getWorldView() != null)
		{
			activeWorldView = localPlayer.getWorldView();
		}

		return activeWorldView != null
			&& object.getWorldView() != null
			&& object.getWorldView().getId() == activeWorldView.getId()
			&& object.getPlane() == activeWorldView.getPlane();
	}
}
