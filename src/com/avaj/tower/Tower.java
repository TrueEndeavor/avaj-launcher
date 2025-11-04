package com.avaj.tower;

import java.util.List;
import java.util.ArrayList;

import com.avaj.aircraft.Flyable;
import com.avaj.aircraft.Aircraft;
import com.avaj.util.Logger;

/*
 * Tower - tracks and notifies registered aircraft
 * Registers/unregisters flyables and notifies them of condition changes.
 */
public class Tower
{
	private final List<Flyable> observers = new ArrayList<Flyable>();

	public void register(Flyable p_flyable)
	{
		if (p_flyable != null && !observers.contains(p_flyable))
		{
			observers.add(p_flyable);
			Aircraft a = (Aircraft) p_flyable;
			Logger.log("Tower says: " +
				a.getClass().getSimpleName() + "#" + a.getName() + "(" + a.getId() + ") registered to weather tower.");
		}
	}

	public void unregister(Flyable p_flyable)
	{
		observers.remove(p_flyable);
		Aircraft a = (Aircraft) p_flyable;
		Logger.log("Tower says: " +
			a.getClass().getSimpleName() + "#" + a.getName() + "(" + a.getId() + ") unregistered from weather tower.");
	}

	protected void conditionChanged()
	{
		final List<Flyable> snapshot = new ArrayList<Flyable>(observers);

		for (java.util.Iterator<Flyable> it = snapshot.iterator(); it.hasNext(); )
		{
			Flyable f = it.next();
			f.updateConditions();
		}
	}
}
