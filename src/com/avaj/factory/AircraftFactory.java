package com.avaj.factory;

import com.avaj.aircraft.*;
import com.avaj.util.Coordinates;

/*
 * AircraftFactory - creates aircraft from type strings (Singleton)
 * Recognizes: helicopter, jetplane, baloon/balloon (case-insensitive).
 * Auto-assigns unique IDs starting from 1.
 */
public final class AircraftFactory
{
	private static final AircraftFactory s_instance = new AircraftFactory();
	private long nextId = 1L;

	private AircraftFactory()
	{
	}

	public static AircraftFactory getInstance()
	{
		return s_instance;
	}

	public Flyable newAircraft(String p_type,
								String p_name,
								int p_longitude,
								int p_latitude,
								int p_height)
	{
		Coordinates c = new Coordinates(p_longitude, p_latitude, p_height);
		long id = nextId++;

		String t;
		if (p_type == null)
		{
			t = "";
		}
		else
		{
			t = p_type.trim().toLowerCase();
		}

		if ("helicopter".equals(t))
		{
			return new Helicopter(id, p_name, c);
		}
		else if ("jetplane".equals(t))
		{
			return new JetPlane(id, p_name, c);
		}
		else if ("baloon".equals(t) || "balloon".equals(t))
		{
			return new Baloon(id, p_name, c);
		}

		throw new IllegalArgumentException("Unknown aircraft type: " + p_type);
	}
}
