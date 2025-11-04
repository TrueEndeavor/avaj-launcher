package com.avaj.util;

import java.io.*;

/*
 * Logger writes simulation output to a file.
 * Usage: init() to open file, log() to write lines, close() to finish.
 */
public final class Logger
{
	private static PrintWriter s_out;

	private Logger()
	{
	}

	public static void init(String p_filename)
	{
		PrintWriter oldWriter = s_out;
		s_out = null;

		if (oldWriter != null)
		{
			try
			{
				oldWriter.close();
			}
			catch (Exception ignore)
			{
			}
		}

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
			if (bw != null)
			{
				try
				{
					bw.close();
				}
				catch (Exception ignore)
				{
				}
			}
			else if (fw != null)
			{
				try
				{
					fw.close();
				}
				catch (Exception ignore)
				{
				}
			}

			System.out.println("Error: cannot open log file: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public static void log(String p_line)
	{
		if (s_out != null)
		{
			s_out.println(p_line);
		}
		else
		{
			System.out.println(p_line);
		}
	}

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
