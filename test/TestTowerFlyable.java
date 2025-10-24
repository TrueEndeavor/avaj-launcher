package test;

import com.avaj.aircraft.*;
import com.avaj.tower.*;
import com.avaj.weather.*;
import com.avaj.factory.*;
import com.avaj.util.*;

public class TestTowerFlyable
{
    public static void main(String[] args)
    {
        WeatherTower tower = new WeatherTower();
        Flyable f1 = new Helicopter(1L, "H1", new Coordinates(10, 20, 30));
        Flyable f2 = new Baloon(2L, "B1", new Coordinates(5,  15, 40));
        
        f1.registerTower(tower);
        f2.registerTower(tower);

        tower.conditionChanged();  // all observers notified

        // unregister one of the observers
        tower.unregister(f2);
        tower.conditionChanged();  // one less observer to be notified
    }
}
