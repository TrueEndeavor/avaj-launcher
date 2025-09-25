package sim;

public final class WeatherProvider
{
	private static final WeatherProvider s_instance = new WeatherProvider();
	private static final String[] WEATHER = {"SUN", "RAIN", "FOG", "SNOW"};
    private int i = 0;
	
	private WeatherProvider()
	{ }

	public static WeatherProvider getInstance()
	{
		return s_instance;
	}

	// Simple weather cycle : Rotates between SUN, RAIN, FOG and SNOW regularly
	public String getCurrentWeather(Coordinates p_coordinates)
	{
		String weatherType = WEATHER[i % WEATHER.length];
        i++;
        return weatherType;
	}
}
