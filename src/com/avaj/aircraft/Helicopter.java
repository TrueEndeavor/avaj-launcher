package com.avaj.aircraft;

import com.avaj.util.Coordinates;

/*
 * Helicopter - powered flight, so it can move in most weather. Prefers going east
 * (longitude increases). Handles sun and rain well, but snow is tough for the rotors.
 */
public class Helicopter extends Aircraft
{
	public Helicopter(long p_id, String p_name, Coordinates p_coordinates)
	{
		super(p_id, p_name, p_coordinates);
	}

	// How this helicopter reacts to weather changes
	public void updateConditions()
	{
		String weatherType = weatherTower.getWeather(coordinates);

		switch (weatherType)
		{
			case "SUN":    // Best conditions - full speed east, can climb
				moveAndLog(10, 0, +2, "flies east in good sun, climbs a bit");
				break;
			case "RAIN":   // Still move east, but slower and no climbing
				moveAndLog(5, 0, 0, "pushes east, slower in rain, no climb");
				break;
			case "FOG":    // Visibility sucks - creep forward carefully
				moveAndLog(1, 0, 0, "creeps east carefully through fog");
				break;
			case "SNOW":   // Ice on the rotors - can't maintain altitude
				moveAndLog(0, 0, -12, "can't keep flight in snow, loosing altitude");
				break;
			default:
				break;
		}
	}
}
