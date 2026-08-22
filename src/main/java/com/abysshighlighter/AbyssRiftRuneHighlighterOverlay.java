package com.abysshighlighter;

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

		for (DecorativeObject object : plugin.getTrackedAbyssRifts())
		{
			if (isInActiveWorldView(object))
			{
				renderRune(graphics, object);
			}
		}
		return null;
	}

	private void renderRune(Graphics2D graphics, DecorativeObject object)
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
			config.heightOffset()
		);
		if (originalLocation == null)
		{
			return;
		}

		int iconSize = config.iconSize();
		int centerX = originalLocation.getX() + image.getWidth() / 2;
		int centerY = originalLocation.getY() + image.getHeight() / 2;
		int iconX = centerX - iconSize / 2;
		int iconY = centerY - iconSize / 2;
		graphics.drawImage(image, iconX, iconY, iconSize, iconSize, null);

		if (config.showRuneNames())
		{
			renderName(graphics, rift.getDisplayName(), centerX, iconY + iconSize);
		}
	}

	private void renderName(Graphics2D graphics, String name, int centerX, int iconBottom)
	{
		Font originalFont = graphics.getFont();
		graphics.setFont(originalFont.deriveFont(Font.BOLD));
		int textX = centerX - graphics.getFontMetrics().stringWidth(name) / 2;
		int textY = iconBottom + LABEL_GAP + graphics.getFontMetrics().getAscent();
		OverlayUtil.renderTextLocation(
			graphics,
			new Point(textX, textY),
			name,
			config.nameColor()
		);
		graphics.setFont(originalFont);
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

