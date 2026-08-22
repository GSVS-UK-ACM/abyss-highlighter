package com.abysshighlighter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.ObjectID;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AbyssRiftRuneTest
{
	@Test
	public void mapsEveryAbyssRiftToItsMatchingRune()
	{
		assertMapping(ObjectID.ABYSS_EXIT_TO_AIR, ItemID.AIRRUNE, "Air");
		assertMapping(ObjectID.ABYSS_EXIT_TO_BLOOD_PARENT, ItemID.BLOODRUNE, "Blood");
		assertMapping(ObjectID.ABYSS_EXIT_TO_BODY, ItemID.BODYRUNE, "Body");
		assertMapping(ObjectID.ABYSS_EXIT_TO_CHAOS, ItemID.CHAOSRUNE, "Chaos");
		assertMapping(ObjectID.ABYSS_EXIT_TO_COSMIC, ItemID.COSMICRUNE, "Cosmic");
		assertMapping(ObjectID.ABYSS_EXIT_TO_DEATH, ItemID.DEATHRUNE, "Death");
		assertMapping(ObjectID.ABYSS_EXIT_TO_EARTH, ItemID.EARTHRUNE, "Earth");
		assertMapping(ObjectID.ABYSS_EXIT_TO_FIRE, ItemID.FIRERUNE, "Fire");
		assertMapping(ObjectID.ABYSS_EXIT_TO_LAW, ItemID.LAWRUNE, "Law");
		assertMapping(ObjectID.ABYSS_EXIT_TO_MIND, ItemID.MINDRUNE, "Mind");
		assertMapping(ObjectID.ABYSS_EXIT_TO_NATURE, ItemID.NATURERUNE, "Nature");
		assertMapping(ObjectID.ABYSS_EXIT_TO_SOUL, ItemID.SOULRUNE, "Soul");
		assertMapping(ObjectID.ABYSS_EXIT_TO_WATER, ItemID.WATERRUNE, "Water");
	}

	@Test
	public void hasThirteenDistinctObjectAndRuneItemIds()
	{
		assertEquals(13, AbyssRiftRune.values().length);
		Set<Integer> objectIds = Arrays.stream(AbyssRiftRune.values())
			.map(AbyssRiftRune::getObjectId)
			.collect(Collectors.toSet());
		Set<Integer> itemIds = Arrays.stream(AbyssRiftRune.values())
			.map(AbyssRiftRune::getRuneItemId)
			.collect(Collectors.toSet());
		assertEquals(13, objectIds.size());
		assertEquals(13, itemIds.size());
	}

	@Test
	public void ignoresUnrelatedObjects()
	{
		assertNull(AbyssRiftRune.fromObjectId(-1));
	}

	private static void assertMapping(int objectId, int itemId, String displayName)
	{
		AbyssRiftRune rift = AbyssRiftRune.fromObjectId(objectId);
		assertEquals(itemId, rift.getRuneItemId());
		assertEquals(displayName, rift.getDisplayName());
	}
}
