package sim;

public final class AircraftFactory
{

	private static final AircraftFactory s_instance = new AircraftFactory();

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
		return null;
	}
}
