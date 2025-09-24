package sim;

public interface Flyable
{
	void updateConditions();
	void registerTower(WeatherTower p_tower);
}