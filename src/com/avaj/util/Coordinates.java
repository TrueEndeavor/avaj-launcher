package com.avaj.util;

/*
 * Coordinates stores longitude, latitude, and height.
 * When aircraft moves, it creates a new Coordinates object.
 * Old object is automatically cleaned up by Java garbage collector.
 */
public final class Coordinates
{
	public final int longitude;
	public final int latitude;
	public final int height;

	public Coordinates(int p_longitude, int p_latitude, int p_height)
	{
		this.longitude = p_longitude;
		this.latitude  = p_latitude;
		this.height    = p_height;
	}

	public int getLongitude()
	{
		return longitude;
	}

	public int getLatitude()
	{
		return latitude;
	}

	public int getHeight()
	{
		return height;
	}
}
