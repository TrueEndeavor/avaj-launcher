package com.avaj.tower;

import java.util.List;
import java.util.ArrayList;

import com.avaj.aircraft.Flyable;
import com.avaj.aircraft.Aircraft;
import com.avaj.util.Logger;

/*
 * Tower is the subject in the Observer pattern. It keeps track of all registered
 * flyables and notifies them when conditions change.
 *
 * When an aircraft lands (height 0), it unregisters itself from the tower.
 * We use a snapshot of the observers list when notifying to avoid concurrent
 * modification issues (aircraft unregistering mid-notification).
 */
public class Tower
{
	private final List<Flyable> observers = new ArrayList<Flyable>();

	/*
	 * Register a new flyable with the tower. Once registered, it'll get notified
	 * every time conditions change. We log registration messages per the spec.
	 */
	public void register(Flyable p_flyable) {
		if (p_flyable != null && !observers.contains(p_flyable))
		{
			observers.add(p_flyable);
			Aircraft a = (Aircraft) p_flyable;
			Logger.log("Tower says: " +
				a.getClass().getSimpleName() + "#" + a.getName() + "(" + a.getId() + ") registered to weather tower.");
		}
	}

	/*
	 * Remove a flyable from the tower (usually because it landed).
	 * No more weather updates for this one!
	 */
	public void unregister(Flyable p_flyable) {
		observers.remove(p_flyable);
		Aircraft a = (Aircraft) p_flyable;
		Logger.log("Tower says: " +
			a.getClass().getSimpleName() + "#" + a.getName() + "(" + a.getId() + ") unregistered from weather tower.");
	}

	/*
	 * Notify all registered observers that conditions changed. Uses a snapshot
	 * of the observers list so if someone unregisters during the loop (e.g., lands),
	 * we don't get a ConcurrentModificationException.
	 */
	protected void conditionChanged()
	{
		// Make a copy to iterate safely
		final List<Flyable> snapshot = new ArrayList<Flyable>(observers);

		for (java.util.Iterator<Flyable> it = snapshot.iterator(); it.hasNext(); )
		{
			Flyable f = it.next();
			f.updateConditions();
		}
	}
}
