package sim;

import java.io.*;
import java.util.*;

public class Simulator
{
	private Simulator()	{}

	public static void main(String[] args)
	{
		// 1) exactly one argument required
		if (args.length != 1)
		{
			System.out.println("Usage: java -cp bin sim.Simulator <scenario file>");
			return;
		}
		
		// 2) init output file per spec
		Logger.init("simulation.txt");
		try
		{
			// 3) validate the scenario file exists & is readable (no parsing yet)
			String scenarioPath = args[0];
			File f = new File(scenarioPath);
			if (!f.isFile() || !f.canRead()) {
				Logger.log("Error: cannot read scenario file: " + scenarioPath);
				return;
			}

			// 4) placeholder: we’ll parse and simulate in the next step
			// (leaving the file created/cleared so spec is satisfied)
			// Logger.log("Simulation started."); // optional
			// in Simulator.main, after you validated the file exists and Logger.init()
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(f));
				String line = br.readLine();
				if (line == null) {
					Logger.log("Error: scenario file is empty.");
					return;
				}
				line = line.trim();
				// must be a positive integer
				int cycles;
				try {
					cycles = Integer.parseInt(line);
					if (cycles <= 0) {
						Logger.log("Error: first line (cycles) must be a positive integer.");
						return;
					}
				} catch (NumberFormatException e) {
					Logger.log("Error: first line (cycles) is not a valid integer: " + line);
					return;
				}

				// (Parsing of aircraft lines comes later.)
				// For now, just prove we read it:
				Logger.log("Read cycles = " + cycles);

					// --- 2) Parse aircraft lines (definitions only) ---
				List<Flyable> flyables = new ArrayList<Flyable>();
				AircraftFactory factory = AircraftFactory.getInstance();
				WeatherTower tower = new WeatherTower();

				int lineNo = 1; // we already read line 1
				while ((line = br.readLine()) != null) {
					lineNo++;
					line = line.trim();
					if (line.isEmpty() || line.startsWith("#")) continue; // allow blanks/comments

					// Expected: TYPE NAME LONG LAT HEIGHT
					String[] tok = line.split("\\s+");
					if (tok.length != 5) {
						Logger.log("Error: line " + lineNo + " malformed (need 5 tokens): " + line);
						return;
					}
					String type = tok[0];
					String name = tok[1];

					int lon, lat, h;
					try {
						lon = Integer.parseInt(tok[2]);
						lat = Integer.parseInt(tok[3]);
						h   = Integer.parseInt(tok[4]);
					} catch (NumberFormatException e) {
						Logger.log("Error: line " + lineNo + " has non-integer coordinates: " + line);
						return;
					}

					// Optional gentle guards (spec: coordinates positive; height capped to 100)
					if (lon < 0 || lat < 0 || h < 0) {
						Logger.log("Error: line " + lineNo + " has negative coordinates/height: " + line);
						return;
					}
					if (h > 100) h = 100;  // clamp height per project conventions

					try {
						Flyable fb = factory.newAircraft(type, name, lon, lat, h);
						flyables.add(fb);
					} catch (IllegalArgumentException ex) {
						Logger.log("Error: line " + lineNo + " unknown type: " + type);
						return;
					}
				}

				// --- 3) Register all aircraft with the tower (prints spec messages) ---
				for (Flyable fbx : flyables) {
					fbx.registerTower(tower);
				}

				// NOTE: We are intentionally NOT running the simulation loop yet.
				// Next micro-step: run 'cycles' times -> tower.changeWeather();
				// --- 4) RUN the simulation (one line loop) ---
				for (int i = 0; i < cycles; i++) {
					tower.changeWeather();
				}

			} catch (IOException e) {
				Logger.log("Error: cannot read scenario file: " + e.getMessage());
				return;
			} finally {
				try { if (br != null) br.close(); } catch (Exception ignore) {}
				Logger.close();
			}


		}
		finally
		{
			Logger.close(); // ensure simulation.txt is written
		}
	}
}