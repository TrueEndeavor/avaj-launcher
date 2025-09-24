package sim;

public abstract class Aircraft implements Flyable
{
	protected final long id;
	protected final String name;
	protected Coordinates coordinates;
	protected WeatherTower weatherTower;

	protected Aircraft(long p_id, String p_name, Coordinates p_coordinate)
	{
		this.id = p_id;
		this.name = p_name;
		this.coordinates = p_coordinate;
	}

	public void registerTower(WeatherTower p_tower)
	{
		this.weatherTower = p_tower;
		p_tower.register(this);
	}

	public abstract void updateConditions();
}
