package com.avaj.aircraft;

import com.avaj.util.Coordinates;
import com.avaj.util.Logger;
import com.avaj.tower.WeatherTower;

/*
 * Base class for all aircraft types. Each aircraft has an ID, name, and position (Coordinates).
 * This class provides common functionality for all aircraft - moving, logging, and landing.
 *
 * The subclasses (Baloon, Helicopter, JetPlane) implement updateConditions() to define
 * how they react to different weather types (SUN, RAIN, FOG, SNOW).
 */
public abstract class Aircraft extends Flyable
{
	protected final long id;          // Unique identifier - assigned by factory
	protected final String name;      // Name of this aircraft
	protected Coordinates coordinates; // Current position (can change during simulation)

	protected Aircraft(long p_id, String p_name, Coordinates p_coordinate)
	{
		this.id = p_id;
		this.name = p_name;
		this.coordinates = p_coordinate;
	}

	// Getters for tower to access protected fields
	public long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	// Helper to create log tags like "Helicopter#H1(42)"
	protected String tag()
	{
		return getClass().getSimpleName() + "#" + name + "(" + id + ")";
	}

	/*
	 * Core movement logic used by all aircraft types. Takes coordinate deltas,
	 * applies them, clamps height to [0, 100], logs a message, and handles landing.
	 *
	 * If height hits 0, the aircraft lands and unregisters from the tower (no more
	 * weather updates for you!).
	 */
	protected void moveAndLog(int dLon, int dLat, int dH, String msg)
	{
		int lon = coordinates.getLongitude() + dLon;
		int lat = coordinates.getLatitude()  + dLat;
		int h   = coordinates.getHeight()    + dH;

		// Clamp height to valid range [0, 100] - can't fly underground or into space
		h = Math.max(0, Math.min(100, h));

		coordinates = new Coordinates(lon, lat, h);

		// Log the aircraft's message about the weather
		if (msg != null && !msg.isEmpty())
		{
			Logger.log(tag() + ": " + msg);
		}

		// If we touched ground (height 0), time to land and unregister
		if (h == 0)
		{
			Logger.log(tag() + " landing.");
			weatherTower.unregister(this);
		}
	}
}
