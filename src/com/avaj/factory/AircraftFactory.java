package com.avaj.factory;

import com.avaj.aircraft.*;
import com.avaj.util.Coordinates;

/*
 * AircraftFactory creates aircraft instances based on type strings (Factory pattern).
 * It's also a Singleton - one factory for the whole simulation.
 *
 * The factory handles ID generation automatically - each aircraft gets a unique ID
 * starting from 1 and incrementing. Clients just pass the type, name, and coordinates.
 */
public final class AircraftFactory
{
	private static final AircraftFactory s_instance = new AircraftFactory();

	private long nextId = 1L; // Auto-increment ID for each aircraft created

	// Private constructor - Singleton pattern
	private AircraftFactory()
	{ }

	// Everyone gets the same factory instance
	public static AircraftFactory getInstance()
	{
		return s_instance;
	}

	/*
	 * Create a new aircraft based on type string. Recognizes:
	 * - "helicopter" -> Helicopter
	 * - "jetplane" -> JetPlane
	 * - "baloon" or "balloon" -> Baloon (accepts both spellings)
	 *
	 * Type string is case-insensitive. Automatically assigns a unique ID.
	 */
	public Flyable newAircraft(String p_type,
								String p_name,
								int p_longitude,
								int p_latitude,
								int p_height)
	{
		// Build coordinates and assign next available ID
		Coordinates c = new Coordinates(p_longitude, p_latitude, p_height);
		long id = nextId++;

		// Case-insensitive type matching
		String t = p_type == null ? "" : p_type.trim().toLowerCase();
		if ("helicopter".equals(t)) {
			return new Helicopter(id, p_name, c);
		} else if ("jetplane".equals(t)) {
			return new JetPlane(id, p_name, c);
		} else if ("baloon".equals(t) || "balloon".equals(t)) {
			return new Baloon(id, p_name, c);
		}
		throw new IllegalArgumentException("Unknown aircraft type: " + p_type);
	}
}
