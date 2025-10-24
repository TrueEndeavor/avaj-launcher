package com.avaj.aircraft;

import com.avaj.tower.WeatherTower;

/*
 * Base class for anything that can fly. This is the observer side of the Observer pattern -
 * flyables register with the WeatherTower and get notified when weather changes.
 *
 * Each concrete aircraft (Baloon, Helicopter, JetPlane) implements updateConditions()
 * to react differently to weather changes.
 */
public abstract class Flyable
{
	protected WeatherTower weatherTower;

	// Each aircraft type reacts differently to weather - implement in subclasses
	public abstract void updateConditions();

	/*
	 * Hook this flyable up to the tower. Once registered, we'll get notified
	 * every time weather changes via updateConditions(). Classic Observer pattern.
	 */
	public void registerTower(WeatherTower p_tower)
	{
		this.weatherTower = p_tower;
		if (p_tower != null)
		{
			p_tower.register(this);
		}
	}
}
