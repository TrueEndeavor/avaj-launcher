package sim;

public class Helicopter extends Aircraft
{
	public Helicopter(long p_id, String p_name, Coordinates p_coordinates)
	{
		super(p_id, p_name, p_coordinates);
	}

	public void updateConditions()
	{
		String weatherType = weatherTower.getWeather(coordinates);

		switch (weatherType)
		{
			// ◦ SUN - Longitude increases with 10, Height increases with 2
			case "SUN":
				moveAndLog(10, 0, +2, "flies east in good sun, climbs a bit");
				break;
			// ◦ RAIN - Longitude increases with 5
			case "RAIN":
				moveAndLog(5, 0, 0, "pushes east, slower in rain, no climb");
				break;
			// ◦ FOG - Longitude increases with 1
			case "FOG":
				moveAndLog(1, 0, 0, "creeps east carefully through fog");
				break;
			// ◦ SNOW - Height decreases with 12
			case "SNOW":
				moveAndLog(0, 0, -12, "can't keep flight in snow, loosing altitude");
				break;
			default:
				break;
		}
	}
}
