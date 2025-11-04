package com.avaj.aircraft;

import com.avaj.util.Coordinates;

/*
 * Baloon - light and heavily affected by weather conditions
 * Drifts EAST (longitude increases) in sunny weather only
 *
 * Movement patterns based on weather:
 * SUN  - warm air lifts up (+4 height), gentle drift east (+2 longitude)
 * RAIN - water weight pulls down (-5 height), no horizontal movement
 * FOG  - cooling air descends slowly (-3 height), no horizontal movement
 * SNOW - ice and snow cause rapid descent (-15 height), no horizontal movement
 */
public class Baloon extends Aircraft
{
	public Baloon(long p_id, String p_name, Coordinates p_coordinates)
	{
		super(p_id, p_name, p_coordinates);
	}

	public void updateConditions()
	{
		String weatherType = weatherTower.getWeather(coordinates);

		switch (weatherType)
		{
			case "SUN":
				// Longitude +2 (EAST), Height +4
				moveAndLog(+2, 0, +4, "SUN - Warm air lifts up, drift east.");
				break;

			case "RAIN":
				// Height -5
				moveAndLog(0, 0, -5, "RAIN - Water weight pulls down.");
				break;

			case "FOG":
				// Height -3
				moveAndLog(0, 0, -3, "FOG - Cooling air, descending slowly.");
				break;

			case "SNOW":
				// Height -15
				moveAndLog(0, 0, -15, "SNOW - Ice and snow, rapid descent.");
				break;

			default:
				break;
		}
	}
}
