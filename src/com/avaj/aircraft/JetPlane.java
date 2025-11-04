package com.avaj.aircraft;

import com.avaj.util.Coordinates;

/*
 * JetPlane - fast and powerful aircraft that flies NORTH (latitude increases)
 *
 * Movement patterns based on weather:
 * SUN  - full throttle north (+10 latitude) with climb (+2 height)
 * RAIN - reduced speed north (+5 latitude), level flight (0 height)
 * FOG  - careful advance north (+1 latitude), level flight (0 height)
 * SNOW - engines struggling, grounded (0 latitude), descent (-7 height)
 */
public class JetPlane extends Aircraft
{
	public JetPlane(long p_id, String p_name, Coordinates p_coordinates)
	{
		super(p_id, p_name, p_coordinates);
	}

	public void updateConditions()
	{
		String weatherType = weatherTower.getWeather(coordinates);

		switch (weatherType)
		{
			case "SUN":
				// Latitude +10 (NORTH), Height +2
				moveAndLog(0, 10, +2, "SUN - Full throttle north, climbing.");
				break;

			case "RAIN":
				// Latitude +5 (NORTH)
				moveAndLog(0, 5, 0, "RAIN - Reduced speed north, level flight.");
				break;

			case "FOG":
				// Latitude +1 (NORTH)
				moveAndLog(0, 1, 0, "FOG - Careful advance north, level flight.");
				break;

			case "SNOW":
				// Height -7
				moveAndLog(0, 0, -7, "SNOW - Engines struggling, descent.");
				break;

			default:
				break;
		}
	}
}
