package com.avaj.aircraft;

import com.avaj.util.Coordinates;

/*
 * Helicopter - powered rotorcraft that flies EAST (longitude increases)
 *
 * Movement patterns based on weather:
 * SUN  - full power east (+10 longitude) with climb (+2 height)
 * RAIN - reduced power east (+5 longitude), level flight (0 height)
 * FOG  - careful advance east (+1 longitude), level flight (0 height)
 * SNOW - rotors icing up, grounded (0 longitude), rapid descent (-12 height)
 */
public class Helicopter extends Aircraft
{
	public Helicopter(long p_id, String p_name, Coordinates p_coordinates)
	{
		super(p_id, p_name, p_coordinates);
	}

	public void updateConditions()
	{
		String weatherType = weatherTower.getWeather(coordinates);

		switch (weatherType)
		{
			case "SUN":
				// Longitude +10 (EAST), Height +2
				moveAndLog(10, 0, +2, "SUN - Full power east, climbing.");
				break;

			case "RAIN":
				// Longitude +5 (EAST)
				moveAndLog(5, 0, 0, "RAIN - Reduced power east, level flight.");
				break;

			case "FOG":
				// Longitude +1 (EAST)
				moveAndLog(1, 0, 0, "FOG - Careful advance east, level flight.");
				break;

			case "SNOW":
				// Height -12
				moveAndLog(0, 0, -12, "SNOW - Rotors icing up, rapid descent.");
				break;

			default:
				break;
		}
	}
}
