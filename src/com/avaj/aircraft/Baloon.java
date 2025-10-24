package com.avaj.aircraft;

import com.avaj.util.Coordinates;

/*
 * Baloon - light and affected heavily by weather. Rises nicely in sun,
 * but sinks quickly in bad weather (especially snow!). Only moves east in sunny weather.
 */
public class Baloon extends Aircraft
{
	public Baloon(long p_id, String p_name, Coordinates p_coordinates)
	{
		super(p_id, p_name, p_coordinates);
	}

	// How this baloon reacts to weather changes
	public void updateConditions()
	{
		String weatherType = weatherTower.getWeather(coordinates);

		switch (weatherType)
		{
			case "SUN":    // Best case - warm air lifts us up, drift east
				moveAndLog(+2, 0, +4, "warm sunny day, drifts a bit east, rising up");
				break;
			case "RAIN":   // Rain adds weight - we sink
				moveAndLog(0, 0, -5, "drenched in rain, sinking");
				break;
			case "FOG":    // Fog makes us lose heat - slow sink
				moveAndLog(0, 0, -3, "thick fog, slowly sinking");
				break;
			case "SNOW":   // Snow is heavy and cold - rapid descent!
				moveAndLog(0, 0, -15, "plummeting in snow, loosing altitude fast");
				break;
			default:
				break;
		}
	}
}
