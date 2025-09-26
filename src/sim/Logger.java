package sim;

import java.io.*;

public final class Logger
{
	private static PrintWriter s_out;

	private Logger() 
	{ }

	public static void init(String p_filename)
	{
		try
		{
			if (s_out != null) s_out.close();
			s_out = new PrintWriter(new BufferedWriter(new FileWriter(p_filename, false)));
		}
		catch (IOException e)
		{
			// Per spec: print errors to stdout and stop.
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
			// Fallback so tests still show something before init()
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
