package sim;

public final class AircraftFactory
{
	private static final AircraftFactory s_instance = new AircraftFactory();

    private long nextId = 1L; // generate unique IDs here

	private AircraftFactory()
	{ }

	public static AircraftFactory getInstance()
	{
		return s_instance;
	}

	public Flyable newAircraft(String p_type,
								String p_name,
								int p_longitude,
								int p_latitude,
								int p_height)
	{
		// Create a Flyable by type, with a fresh ID, from raw coordinates
		Coordinates c = new Coordinates(p_longitude, p_latitude, p_height);
		long id = nextId++;

		String t = p_type == null ? "" : p_type.trim().toLowerCase();
		if ("helicopter".equals(t)) {
			return new Helicopter(id, p_name, c);
		} else if ("jetplane".equals(t)) {
			return new JetPlane(id, p_name, c);
		} else if ("baloon".equals(t) || "balloon".equals(t)) { // accept both spellings
			return new Baloon(id, p_name, c);
		}
		throw new IllegalArgumentException("Unknown aircraft type: " + p_type);
	}
}