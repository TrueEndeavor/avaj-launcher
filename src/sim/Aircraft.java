package sim;

public abstract class Aircraft extends Flyable
{
	protected final long id;
	protected final String name;
	protected Coordinates coordinates;

	protected Aircraft(long p_id, String p_name, Coordinates p_coordinate)
	{
		this.id = p_id;
		this.name = p_name;
		this.coordinates = p_coordinate;
	}

    protected String tag()
	{
        return getClass().getSimpleName() + "#" + name + "(" + id + ")";
    }

    /** Apply deltas, clamp height, log, and handle landing/unregister. */
    protected void moveAndLog(int dLon, int dLat, int dH, String msg)
	{
        int lon = coordinates.getLongitude() + dLon;
        int lat = coordinates.getLatitude()  + dLat;
        int h   = coordinates.getHeight()    + dH;

		// Force h into the valid range [0, 100]
        h = Math.max(0, Math.min(100, h));

        coordinates = new Coordinates(lon, lat, h);

        if (msg != null && !msg.isEmpty())
		{
            System.out.println(tag() + ": " + msg);
        }

        if (h == 0)
		{
            System.out.println(tag() + " landing.");
            weatherTower.unregister(this);
        }
    }
}
