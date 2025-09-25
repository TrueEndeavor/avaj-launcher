package sim;

import java.util.List;
import java.util.ArrayList;

public class Tower
{
	private final List<Flyable> observers = new ArrayList<Flyable>();

	public void register(Flyable p_flyable) {
        if (p_flyable != null && !observers.contains(p_flyable))
		{
            observers.add(p_flyable);
        }
    }

    public void unregister(Flyable p_flyable) {
        observers.remove(p_flyable);
    }

    protected void conditionChanged()
	{
		// Better take a copy (snapshot) of observers for iteration.
		// The copy will still hold pointers to the actual observers.
		// Such copy avoids concurrent modification if an observer unregisters during iteration
        final List<Flyable> snapshot = new ArrayList<Flyable>(observers);
		
		for (java.util.Iterator<Flyable> it = snapshot.iterator(); it.hasNext(); )
		{
			Flyable f = it.next();
	   		f.updateConditions();
		}
    }
}
