package com.abysshighlighter;

import com.google.inject.Provides;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Inject;
import net.runelite.api.DecorativeObject;
import net.runelite.api.GameState;
import net.runelite.api.events.DecorativeObjectDespawned;
import net.runelite.api.events.DecorativeObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "Abyss Rift Rune Highlighter",
	description = "Shows the matching rune icon and name above every rift in the Abyss",
	tags = {"abyss", "rift", "rune", "runecraft", "runecrafting", "highlight", "overlay"}
)
public class AbyssRiftRuneHighlighterPlugin extends Plugin
{
	private final Set<DecorativeObject> trackedAbyssRifts = new HashSet<>();

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private AbyssRiftRuneHighlighterOverlay overlay;

	@Provides
	AbyssRiftRuneHighlighterConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AbyssRiftRuneHighlighterConfig.class);
	}

	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
		trackedAbyssRifts.clear();
	}

	@Subscribe
	public void onDecorativeObjectSpawned(DecorativeObjectSpawned event)
	{
		DecorativeObject object = event.getDecorativeObject();
		if (AbyssRiftRune.fromObjectId(object.getId()) != null)
		{
			trackedAbyssRifts.add(object);
		}
	}

	@Subscribe
	public void onDecorativeObjectDespawned(DecorativeObjectDespawned event)
	{
		trackedAbyssRifts.remove(event.getDecorativeObject());
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		GameState state = event.getGameState();
		if (state == GameState.LOADING || state == GameState.HOPPING || state == GameState.LOGIN_SCREEN)
		{
			trackedAbyssRifts.clear();
		}
	}

	Set<DecorativeObject> getTrackedAbyssRifts()
	{
		return Collections.unmodifiableSet(trackedAbyssRifts);
	}
}

