package sim;

public class Baloon extends Aircraft
{
	public Baloon(long p_id, String p_name, Coordinates p_coordinates)
	{
		super(p_id, p_name, p_coordinates);
	}

	public void updateConditions()
	{
		String weatherType = weatherTower.getWeather(coordinates);

		switch (weatherType)
		{
			// ◦ SUN - Longitude increases with 2, Height increases with 4
			case "SUN":
				moveAndLog(+2, 0, +4, "warm sunny day, drifts a bit east, rising up");
				break;
			// ◦ RAIN - Height decreases with 5
			case "RAIN":
				moveAndLog(0, 0, -5, "drenched in rain, sinking");
				break;
			// ◦ FOG - Height decreases with 3
			case "FOG":
				moveAndLog(0, 0, -3, "thick fog, slowly sinking");
				break;
			// ◦ SNOW - Height decreases with 15
			case "SNOW":
				moveAndLog(0, 0, -15, "plummeting in snow, loosing altitude fast");
				break;
			default:
				break;
		}
	}
}
