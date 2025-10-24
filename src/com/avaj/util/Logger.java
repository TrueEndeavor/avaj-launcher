package com.avaj.util;

import java.io.*;

/*
 * Simple logger that writes everything to a file. Static methods make it easy to use
 * from anywhere - just call Logger.log() and you're good.
 *
 * The important bits:
 * - init() opens the file (creates/overwrites it)
 * - log() writes a line to the file
 * - close() flushes and closes everything
 *
 * Resource leak protection: we're extra careful when creating the writer chain
 * (FileWriter -> BufferedWriter -> PrintWriter) to make sure nothing leaks if
 * something goes wrong mid-construction.
 */
public final class Logger
{
	private static PrintWriter s_out;

	private Logger()
	{ }

	/*
	 * Opens (or reopens) the log file. If there was an old file open, we close it first.
	 *
	 * The tricky part here is the nested writer construction. We build the chain
	 * step by step (FileWriter, then BufferedWriter, then PrintWriter) so if anything
	 * fails, we can clean up properly. Otherwise we'd leak file handles.
	 *
	 * Why three wrappers? Each does something useful:
	 * - FileWriter: actually writes to the file
	 * - BufferedWriter: buffers writes for performance (don't hit disk every line)
	 * - PrintWriter: gives us nice methods like println() with automatic flushing
	 */
	public static void init(String p_filename)
	{
		// Close old writer safely before creating new one
		PrintWriter oldWriter = s_out;
		s_out = null; // Clear reference first to prevent using half-closed writer

		if (oldWriter != null)
		{
			try
			{
				oldWriter.close();
			}
			catch (Exception ignore)
			{
				// Ignore errors when closing old writer
			}
		}

		// Create new writer - construct outer to inner to prevent leaks
		FileWriter fw = null;
		BufferedWriter bw = null;
		try
		{
			fw = new FileWriter(p_filename, false);
			bw = new BufferedWriter(fw);
			s_out = new PrintWriter(bw);
		}
		catch (IOException e)
		{
			// Clean up any partially created resources
			if (bw != null)
			{
				try { bw.close(); } catch (Exception ignore) {}
			}
			else if (fw != null)
			{
				try { fw.close(); } catch (Exception ignore) {}
			}

			// Per spec: print errors to stdout and stop.
			System.out.println("Error: cannot open log file: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/*
	 * Writes a line to the log file. If the logger isn't initialized yet,
	 * we fall back to stdout so you at least see something during testing.
	 */
	public static void log(String p_line)
	{
		if (s_out != null)
		{
			s_out.println(p_line);
		}
		else
		{
			// Fallback so tests still show something before init()
			System.out.println(p_line);
		}
	}

	/*
	 * Flushes and closes the log file. Always call this at the end so everything
	 * gets written to disk. The flush() ensures buffered content is written before close().
	 */
	public static void close()
	{
		if (s_out != null)
		{
			s_out.flush();
			s_out.close();
			s_out = null;
		}
	}
}
