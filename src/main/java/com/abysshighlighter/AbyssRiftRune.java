package com.abysshighlighter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.ObjectID;

enum AbyssRiftRune
{
	AIR(ObjectID.ABYSS_EXIT_TO_AIR, ItemID.AIRRUNE, "Air"),
	BLOOD(ObjectID.ABYSS_EXIT_TO_BLOOD_PARENT, ItemID.BLOODRUNE, "Blood"),
	BODY(ObjectID.ABYSS_EXIT_TO_BODY, ItemID.BODYRUNE, "Body"),
	CHAOS(ObjectID.ABYSS_EXIT_TO_CHAOS, ItemID.CHAOSRUNE, "Chaos"),
	COSMIC(ObjectID.ABYSS_EXIT_TO_COSMIC, ItemID.COSMICRUNE, "Cosmic"),
	DEATH(ObjectID.ABYSS_EXIT_TO_DEATH, ItemID.DEATHRUNE, "Death"),
	EARTH(ObjectID.ABYSS_EXIT_TO_EARTH, ItemID.EARTHRUNE, "Earth"),
	FIRE(ObjectID.ABYSS_EXIT_TO_FIRE, ItemID.FIRERUNE, "Fire"),
	LAW(ObjectID.ABYSS_EXIT_TO_LAW, ItemID.LAWRUNE, "Law"),
	MIND(ObjectID.ABYSS_EXIT_TO_MIND, ItemID.MINDRUNE, "Mind"),
	NATURE(ObjectID.ABYSS_EXIT_TO_NATURE, ItemID.NATURERUNE, "Nature"),
	SOUL(ObjectID.ABYSS_EXIT_TO_SOUL, ItemID.SOULRUNE, "Soul"),
	WATER(ObjectID.ABYSS_EXIT_TO_WATER, ItemID.WATERRUNE, "Water");

	private static final Map<Integer, AbyssRiftRune> BY_OBJECT_ID;

	static
	{
		Map<Integer, AbyssRiftRune> rifts = new HashMap<>();
		for (AbyssRiftRune rift : values())
		{
			AbyssRiftRune previous = rifts.put(rift.objectId, rift);
			if (previous != null)
			{
				throw new IllegalStateException("Duplicate Abyss rift object mapping");
			}
		}
		BY_OBJECT_ID = Collections.unmodifiableMap(rifts);
	}

	private final int objectId;
	private final int runeItemId;
	private final String displayName;

	AbyssRiftRune(int objectId, int runeItemId, String displayName)
	{
		this.objectId = objectId;
		this.runeItemId = runeItemId;
		this.displayName = displayName;
	}

	int getObjectId()
	{
		return objectId;
	}

	int getRuneItemId()
	{
		return runeItemId;
	}

	String getDisplayName()
	{
		return displayName;
	}

	static AbyssRiftRune fromObjectId(int objectId)
	{
		return BY_OBJECT_ID.get(objectId);
	}
}
