package com.avaj.weather;

import com.avaj.util.Coordinates;
import java.lang.Math;

/*
 * WeatherProvider - generates weather based on coordinates (Singleton)
 * Weather determined by location: longitude + latitude + height.
 */
public final class WeatherProvider
{
	private static final WeatherProvider s_instance = new WeatherProvider();
	private static final String[] WEATHER = {"SUN", "RAIN", "FOG", "SNOW"};

	private WeatherProvider()
	{
	}

	public static WeatherProvider getInstance()
	{
		return s_instance;
	}

	public String getCurrentWeather(Coordinates p_coordinates)
	{
		
		int seed = Math.abs(p_coordinates.getLongitude() + p_coordinates.getLatitude() + p_coordinates.getHeight());
		return WEATHER[seed % WEATHER.length];
	}
}
