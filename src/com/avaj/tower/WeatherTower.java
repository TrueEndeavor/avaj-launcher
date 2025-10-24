package com.avaj.tower;

import com.avaj.util.Coordinates;
import com.avaj.weather.WeatherProvider;

/*
 * WeatherTower extends Tower to add weather-specific functionality.
 * Aircraft can query the weather at their coordinates, and when weather changes,
 * all registered aircraft get notified (via the Observer pattern).
 */
public class WeatherTower extends Tower
{
	/*
	 * Get current weather at given coordinates. Delegates to the WeatherProvider
	 * singleton which generates weather based on position.
	 */
	public String getWeather(Coordinates p_coordinates)
	{
		return WeatherProvider.getInstance().getCurrentWeather(p_coordinates);
	}

	/*
	 * Trigger a weather change. This kicks off the Observer pattern - notifies
	 * all registered aircraft so they can react to the new conditions.
	 */
	public void changeWeather()
	{
		this.conditionChanged(); // notify all observers
	}
}
