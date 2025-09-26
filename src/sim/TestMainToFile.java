package sim;

public class TestMainToFile
{
	public static void main(String[] args)
	{
		Logger.init("simulation.txt");   // start logging to file
		try
		{
			WeatherTower tower = new WeatherTower();

			Flyable b1 = new Baloon(1L, "B1", new Coordinates(5, 15, 6));
			Flyable j1 = new JetPlane(2L, "J1", new Coordinates(0,  0, 99));
			Flyable h1 = new Helicopter(3L, "H1", new Coordinates(10, 20, 95));

			b1.registerTower(tower);
			j1.registerTower(tower);
			h1.registerTower(tower);

			for (int i = 0; i < 4; i++)
			{
				tower.changeWeather();
			}
		}
		finally
		{
			Logger.close();              // flush & close the file
		}
	}
}
