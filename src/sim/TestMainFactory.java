package sim;

public class TestMainFactory {
    public static void main(String[] args) {
        Logger.init("simulation.txt");
        try {
            WeatherTower tower = new WeatherTower();
            AircraftFactory f = AircraftFactory.getInstance();

            Flyable b1 = f.newAircraft("Baloon",     "Ba1",  5, 15,  6);
            Flyable j1 = f.newAircraft("JetPlane",   "Je1",  0,  0, 99);
            Flyable h1 = f.newAircraft("Helicopter", "He1", 10, 20, 95);

            b1.registerTower(tower);
            j1.registerTower(tower);
            h1.registerTower(tower);

            for (int i = 0; i < 4; i++) {
                tower.changeWeather();
            }
        } finally {
            Logger.close();
        }
    }
}
