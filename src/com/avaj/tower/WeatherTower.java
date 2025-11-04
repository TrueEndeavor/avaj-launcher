package com.avaj.tower;

import com.avaj.util.Coordinates;
import com.avaj.weather.WeatherProvider;

/*
 * WeatherTower - manages weather and notifies aircraft
 * Gets weather from WeatherProvider and triggers notifications.
 */
public class WeatherTower extends Tower
{
	public String getWeather(Coordinates p_coordinates)
	{
		return WeatherProvider.getInstance().getCurrentWeather(p_coordinates);
	}

	public void changeWeather()
	{
		this.conditionChanged();
	}
}
