package com.avaj.weather;

import com.avaj.util.Coordinates;

/*
 * WeatherProvider generates weather for the simulation. 
 * It's a Singleton - only one instance exists, and everyone uses getInstance() to access it
 *
 * For simplicity, we just cycle through the four weather types (SUN -> RAIN -> FOG -> SNOW)
 * in order. 
 */
public final class WeatherProvider
{
	private static final WeatherProvider s_instance = new WeatherProvider();
	private static final String[] WEATHER = {"SUN", "RAIN", "FOG", "SNOW"};
	private int i = 0;

	// Private constructor - only we can create instances (Singleton pattern)
	private WeatherProvider()
	{ }

	// Everyone gets the same instance
	public static WeatherProvider getInstance()
	{
		return s_instance;
	}

	/*
	 * Get current weather for given coordinates. We cycle through weather types
	 * in order: SUN, RAIN, FOG, SNOW, SUN, RAIN...
	 * All aircraft experience the same weather at each cycle.
	 */
	public String getCurrentWeather(Coordinates p_coordinates)
	{
		String weatherType = WEATHER[i % WEATHER.length];
		i++;
		return weatherType;
	}
}
