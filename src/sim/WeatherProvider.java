package sim;

public final class WeatherProvider
{
	private static final WeatherProvider s_instance = new WeatherProvider();

	private WeatherProvider()
	{ }

	public static WeatherProvider getInstance()
	{
		return s_instance;
	}

	public String getCurrentWeather(Coordinates p_coordinates)
	{
		return null;
	}
}
