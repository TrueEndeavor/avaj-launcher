package test;

import com.avaj.aircraft.*;
import com.avaj.tower.*;
import com.avaj.weather.*;
import com.avaj.factory.*;
import com.avaj.util.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class TestWeatherTypes {

    // --- Helpers ------------------------------------------------------------

    static class Sample {
        final int id, lon, lat, h, cycle;
        Sample(int id, int lon, int lat, int h, int cycle) {
            this.id = id; this.lon = lon; this.lat = lat; this.h = h; this.cycle = cycle;
        }
    }

    static String labelOf(Aircraft a) {
        return a.getClass().getSimpleName() + "#" + a.name + "(" + a.id + ")";
    }

    static char idChar(long id) {
        // single char marker for the plot: 1..9 then A..Z then '*'
        if (id >= 1 && id <= 9) return (char)('0' + id);
        long k = id - 10;
        if (k >= 0 && k < 26) return (char)('A' + k);
        return '*';
    }

    // --- Main ---------------------------------------------------------------

    public static void main(String[] args) {
        final int cycles = 12;          // tweak as you like
        final int W = 70, H = 22;       // ASCII canvas width/height

        WeatherTower tower = new WeatherTower();

        // Real flyables with real constructors
        Flyable h1 = new Helicopter(1L, "H1", new Coordinates(10, 20, 95));
        Flyable b1 = new Baloon(2L, "B1", new Coordinates(5,  15,  6));
        Flyable j1 = new JetPlane(3L, "J1", new Coordinates(0,   0, 99));

        List<Flyable> all = Arrays.asList(h1, b1, j1);
        for (Flyable f : all) f.registerTower(tower);

        // Record samples (cycle 0 initial + after each change)
        List<Sample> samples = new ArrayList<Sample>();
        Map<Integer, String> legend = new LinkedHashMap<Integer, String>();

        // capture initial (cycle 0)
        capture(all, samples, legend, 0);

        for (int c = 1; c <= cycles; c++) {
            tower.changeWeather();
            capture(all, samples, legend, c);
        }

        // Compute bounds from all samples (X=lon, Y=lat)
        int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
        for (Sample s : samples) {
            if (s.lon < minX) minX = s.lon;
            if (s.lon > maxX) maxX = s.lon;
            if (s.lat < minY) minY = s.lat;
            if (s.lat > maxY) maxY = s.lat;
        }
        int dx = Math.max(1, maxX - minX);
        int dy = Math.max(1, maxY - minY);

        // Build canvas, fill with dots
        char[][] grid = new char[H][W];
        for (int r = 0; r < H; r++) Arrays.fill(grid[r], '.');

        // Plot each sample point with its ID; handle overlaps as '*'
        // We’ll show every cycle point; last cycle points will be overwritten last.
        for (Sample s : samples) {
            int sx = (int) Math.round((s.lon - minX) * 1.0 * (W - 1) / dx);
            int sy = (int) Math.round((s.lat - minY) * 1.0 * (H - 1) / dy);
            sy = (H - 1) - sy; // invert Y for screen row

            char mark = idChar(s.id);
            char prev = grid[sy][sx];
            if (prev == '.' || prev == mark) {
                grid[sy][sx] = mark;
            } else {
                grid[sy][sx] = '*'; // collision
            }
        }

        // Print header with bounds
        System.out.println("ASCII plot: longitude (X) vs latitude (Y)");
        System.out.println("X: [" + minX + " .. " + maxX + "], Y: [" + minY + " .. " + maxY + "]");
        System.out.println();

        // Print grid
        for (int r = 0; r < H; r++) {
            System.out.print('|');
            System.out.print(grid[r]);
            System.out.println('|');
        }

        // Legend
        System.out.println();
        System.out.println("Legend (marker => aircraft):");
        for (Map.Entry<Integer, String> e : legend.entrySet()) {
            System.out.println("  " + idChar(e.getKey()) + " => " + e.getValue());
        }

        // Heights per cycle (so you can verify Z separately from the 2D plot)
        System.out.println();
        System.out.println("Heights by cycle (h only; does not affect 2D distances):");
        printHeightTable(samples);

        tower.unregister(h1);
        tower.unregister(b1);
    }

    // capture helper: sample all aircraft at this cycle
    private static void capture(List<Flyable> all, List<Sample> samples,
                                Map<Integer, String> legend, int cycle) {
        for (Flyable f : all) {
            Aircraft a = (Aircraft) f; // same package -> safe cast & access
            int id = (int) a.id;
            if (!legend.containsKey(id)) legend.put(id, labelOf(a));
            samples.add(new Sample(id,
                                   a.coordinates.getLongitude(),
                                   a.coordinates.getLatitude(),
                                   a.coordinates.getHeight(),
                                   cycle));
        }
    }

    // print height table per id per cycle
    private static void printHeightTable(List<Sample> samples) {
        // order cycles
        SortedSet<Integer> cycles = new TreeSet<Integer>();
        // per id map
        Map<Integer, Map<Integer, Integer>> perId = new LinkedHashMap<Integer, Map<Integer, Integer>>();

        for (Sample s : samples) {
            cycles.add(s.cycle);
            Map<Integer, Integer> m = perId.get(s.id);
            if (m == null) { m = new LinkedHashMap<Integer, Integer>(); perId.put(s.id, m); }
            m.put(s.cycle, s.h);
        }

        // header
        System.out.print("ID \\ cycle |");
        for (int c : cycles) System.out.printf(" %3d", c);
        System.out.println();
        System.out.print("-----------+");
        for (int c : cycles) System.out.print("----");
        System.out.println();

        // rows
        for (Map.Entry<Integer, Map<Integer, Integer>> e : perId.entrySet()) {
            int id = e.getKey();
            Map<Integer, Integer> m = e.getValue();
            System.out.printf("     %3s   |", String.valueOf(idChar(id)));
            for (int c : cycles) {
                Integer h = m.get(c);
                System.out.printf(" %3s", h == null ? "-" : String.valueOf(h));
            }
            System.out.println();
        }
    }
}