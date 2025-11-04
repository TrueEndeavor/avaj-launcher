package com.avaj.aircraft;

import com.avaj.util.Coordinates;
import com.avaj.util.Logger;
import com.avaj.tower.WeatherTower;

/*
 * Aircraft - base class for all aircraft types
 * Stores ID, name, and coordinates.
 * Subclasses implement updateConditions() for weather reactions.
 */
public abstract class Aircraft extends Flyable
{
	protected final long id;
	protected final String name;
	protected Coordinates coordinates;

	protected Aircraft(long p_id, String p_name, Coordinates p_coordinate)
	{
		this.id = p_id;
		this.name = p_name;
		this.coordinates = p_coordinate;
	}

	public long getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	protected String tag()
	{
		return getClass().getSimpleName() + "#" + name + "(" + id + ")";
	}

	protected void moveAndLog(int dLon, int dLat, int dH, String msg)
	{
		int lon = coordinates.getLongitude() + dLon;
		int lat = coordinates.getLatitude()  + dLat;
		int h   = coordinates.getHeight()    + dH;

		h = Math.max(0, Math.min(100, h));

		coordinates = new Coordinates(lon, lat, h);

		if (msg != null && !msg.isEmpty())
		{
			Logger.log(tag() + ": " + msg);
		}

		if (h == 0)
		{
			Logger.log(tag() + " landing.");
			weatherTower.unregister(this);
		}
	}
}
