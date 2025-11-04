package com.avaj.aircraft;

import com.avaj.tower.WeatherTower;

/*
 * Flyable - base class for flying objects
 * Registers with WeatherTower to receive weather updates.
 * Subclasses implement updateConditions() to react to weather.
 */
public abstract class Flyable
{
	protected WeatherTower weatherTower;

	public abstract void updateConditions();

	public void registerTower(WeatherTower p_tower)
	{
		this.weatherTower = p_tower;
		if (p_tower != null)
		{
			p_tower.register(this);
		}
	}
}
