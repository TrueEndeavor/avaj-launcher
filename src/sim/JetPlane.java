package sim;

public class JetPlane extends Aircraft
{
	public JetPlane(long p_id, String p_name, Coordinates p_coordinates)
	{
		super(p_id, p_name, p_coordinates);
	}

	public void updateConditions()
	{
		String weatherType = weatherTower.getWeather(coordinates);

		switch (weatherType)
		{
			// ◦ SUN - Latitude increases with 10, Height increases with 2
			case "SUN":
				moveAndLog(0, 10, +2, "flies north in good sun, climbs a bit");
				break;
			// ◦ RAIN - Latitude increases with 5
			case "RAIN":
				moveAndLog(0, 5,  0, "pushes north, slower in rain, no climb");
				break;
			// ◦ FOG - Latitude increases with 1
			case "FOG":
				moveAndLog(0, 1,  0, "creeps north carefully through fog");
				break;
			// ◦ SNOW - Height decreases with 7
			case "SNOW":
				moveAndLog(0, 0, -7, "tries to hold position in snow, loosing altitude");
				break;
			default:
				break;
		}
	}
}
