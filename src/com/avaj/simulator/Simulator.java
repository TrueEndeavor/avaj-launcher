package com.avaj.simulator;

import java.io.*;
import java.util.*;

import com.avaj.aircraft.*;
import com.avaj.factory.AircraftFactory;
import com.avaj.tower.WeatherTower;
import com.avaj.util.Coordinates;
import com.avaj.util.Logger;

/*
 * Aircraft Weather Simulation - mini air traffic control system.
 *
 * Flow:
 * 1. Validate command line arguments (need exactly one: scenario file path)
 * 2. Check that scenario file exists and is readable
 * 3. Initialize Logger and create simulation.txt
 * 4. Read first line to get number of simulation cycles
 * 5. Parse aircraft definitions from remaining lines (TYPE NAME LON LAT HEIGHT)
 * 6. Create aircraft via factory (gets unique IDs automatically)
 * 7. Register all aircraft with the WeatherTower (Observer pattern setup)
 * 8. Run simulation cycles - each cycle changes weather, all aircraft react
 * 9. Close Logger to flush everything to output file
 *
 * Design patterns:
 * - Observer: Tower notifies all aircraft when weather changes
 * - Factory: AircraftFactory creates aircraft from type strings
 * - Singleton: WeatherProvider and AircraftFactory are single instances
 */
public class Simulator
{
	private Simulator()
	{
	}

	/*
	 * Step 1: Validate command line arguments (need exactly one: scenario file path)
	 */
	private static void validateArguments(String[] args) throws SimulationException
	{
		if (args.length != 1)
		{
			System.out.println("Usage: java -cp src com.avaj.simulator.Simulator <scenario file>");
			throw new SimulationException("Invalid arguments: expected 1 argument, got " + args.length);
		}
	}

	/*
	 * Step 2: Check that scenario file exists and is readable
	 */
	private static File validateScenarioFile(String path) throws SimulationException
	{
		File file = new File(path);
		if (!file.isFile() || !file.canRead())
		{
			throw new SimulationException("Cannot read scenario file: " + path);
		}
		return file;
	}

	/*
	 * Steps 4-8: Read scenario, create aircraft, register with tower, and run simulation.
	 * Using try-with-resources - BufferedReader gets closed automatically.
	 */
	private static void runScenario(File scenarioFile) throws SimulationException
	{
		try (BufferedReader reader = new BufferedReader(new FileReader(scenarioFile)))
		{
			// Step 4: Read first line to get number of simulation cycles (must be positive integer)
			int cycles = parseCycles(reader);
			Logger.log("Read cycles = " + cycles);

			AircraftFactory factory = AircraftFactory.getInstance();
			// Step 5: Parse aircraft definitions from remaining lines (TYPE NAME LON LAT HEIGHT)
			// Step 6: Create aircraft via factory (validates format, coordinates, and type) as and when the defintions are parsed
			List<Flyable> flyables = parseAircraftDefinitions(reader, factory);

			WeatherTower tower = new WeatherTower();
			// Step 7: Register all aircraft with the WeatherTower (Observer pattern setup)
			registerAircraft(flyables, tower);

			runSimulation(tower, cycles);
		}
		catch (IOException e)
		{
			throw new SimulationException("I/O error reading scenario file: " + e.getMessage());
		}
	}

	/*
	 * Step 4: Read first line to get number of simulation cycles (must be positive integer)
	 */
	private static int parseCycles(BufferedReader reader) throws SimulationException, IOException
	{
		String line = reader.readLine();
		if (line == null)
		{
			throw new SimulationException("Scenario file is empty");
		}

		line = line.trim();
		try
		{
			int cycles = Integer.parseInt(line);
			if (cycles <= 0)
			{
				throw new SimulationException("First line (cycles) must be a positive integer");
			}
			return cycles;
		}
		catch (NumberFormatException e)
		{
			throw new SimulationException("First line (cycles) is not a valid integer: " + line);
		}
	}

	/*
	 * Step 5: Parse aircraft definitions from remaining lines (TYPE NAME LON LAT HEIGHT)
	 * Skips blank lines and comments (lines starting with #)
	 */
	private static List<Flyable> parseAircraftDefinitions(BufferedReader reader, AircraftFactory factory)
		throws SimulationException, IOException
	{
		List<Flyable> flyables = new ArrayList<Flyable>();
		String line;
		int lineNo = 1;

		while ((line = reader.readLine()) != null)
		{
			lineNo++;
			line = line.trim();

			if (line.isEmpty() || line.startsWith("#"))
			{
				continue;
			}
			// Step 6: Create aircraft via factory (validates format, coordinates, and type)
			Flyable flyable = parseAircraftLine(line, lineNo, factory);
			flyables.add(flyable);
		}

		return flyables;
	}

	/*
	 * Step 6: Create aircraft via factory (validates format, coordinates, and type)
	 * Height gets clamped to [0, 100] range per the spec
	 */
	private static Flyable parseAircraftLine(String line, int lineNo, AircraftFactory factory)
		throws SimulationException
	{
		String[] tokens = line.split("\\s+");
		if (tokens.length != 5)
		{
			throw new SimulationException("Line " + lineNo + " malformed (need 5 tokens): " + line);
		}

		String type = tokens[0];
		String name = tokens[1];

		int longitude, latitude, height;
		try
		{
			longitude = Integer.parseInt(tokens[2]);
			latitude  = Integer.parseInt(tokens[3]);
			height    = Integer.parseInt(tokens[4]);
		}
		catch (NumberFormatException e)
		{
			throw new SimulationException("Line " + lineNo + " has non-integer coordinates: " + line);
		}

		if (longitude < 0 || latitude < 0 || height < 0)
		{
			throw new SimulationException("Line " + lineNo + " has negative coordinates/height: " + line);
		}

		if (height > 100)
		{
			height = 100;
		}
		Coordinates coordinates = new Coordinates(longitude, latitude, height);
		try
		{
			// New aircrafts get created from the factory and launched in the given coordinates
			return factory.newAircraft(type, name, coordinates);
		}
		catch (IllegalArgumentException e)
		{
			throw new SimulationException("Line " + lineNo + " unknown aircraft type: " + type);
		}
	}

	/*
	 * Step 7: Register all aircraft with the WeatherTower (Observer pattern setup)
	 */
	private static void registerAircraft(List<Flyable> flyables, WeatherTower tower)
	{
		for (Flyable flyable : flyables)
		{
			flyable.registerTower(tower);
		}
	}

	/*
	 * Step 8: Run simulation cycles - each cycle changes weather, all aircraft react
	 */
	private static void runSimulation(WeatherTower tower, int cycles)
	{
		for (int i = 0; i < cycles; i++)
		{
			tower.changeWeather();
		}
	}

	/*
	 * BONUS: Custom exception for simulation errors
	 */
	private static class SimulationException extends Exception
	{
		public SimulationException(String message)
		{
			super(message);
		}
	}

	/*
	 * Main Entry point
	 */
	public static void main(String[] args)
	{
		try
		{
			// 1. Validate command line arguments (need exactly one: scenario file path)
			validateArguments(args);
			String scenarioPath = args[0];

			// 2. Check that scenario file exists and is readable
			File scenarioFile = validateScenarioFile(scenarioPath);

			// 3. Initialize Logger and create simulation.txt
			Logger.init("simulation.txt");
			try
			{
				// Steps 4-8: Read scenario, create aircraft, register with tower, and run simulation
				runScenario(scenarioFile);
			}
			finally
			{
				// 9. Close Logger to flush everything to output file
				Logger.close();
			}
		}
		catch (SimulationException e)
		{
			System.out.println("Error: " + e.getMessage());
		}
	}
}
