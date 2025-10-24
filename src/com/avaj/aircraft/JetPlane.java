package com.avaj.aircraft;

import com.avaj.util.Coordinates;

/*
 * JetPlane - fast and powerful, prefers going north (latitude increases).
 * Jets handle most weather well, but even they struggle a bit in snow.
 */
public class JetPlane extends Aircraft
{
	public JetPlane(long p_id, String p_name, Coordinates p_coordinates)
	{
		super(p_id, p_name, p_coordinates);
	}

	// How this jetplane reacts to weather changes
	public void updateConditions()
	{
		String weatherType = weatherTower.getWeather(coordinates);

		switch (weatherType)
		{
			case "SUN":    // Perfect conditions - cruise north at full speed, climb
				moveAndLog(0, 10, +2, "flies north in good sun, climbs a bit");
				break;
			case "RAIN":   // Still go north, but slower and stay at current altitude
				moveAndLog(0, 5,  0, "pushes north, slower in rain, no climb");
				break;
			case "FOG":    // Slow down, careful navigation through low visibility
				moveAndLog(0, 1,  0, "creeps north carefully through fog");
				break;
			case "SNOW":   // Stay in place, snow affects engines - lose altitude
				moveAndLog(0, 0, -7, "tries to hold position in snow, loosing altitude");
				break;
			default:
				break;
		}
	}
}
