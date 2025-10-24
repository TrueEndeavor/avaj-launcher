package com.avaj.util;

/*
 * Coordinates represents a 3D position: longitude (east-west), latitude (north-south),
 * and height (altitude). It's immutable - once created, coordinates don't change.
 * Aircraft create new Coordinates objects when they move.
 *
 * Height is always kept in the range [0, 100] - aircraft can't fly underground
 * or into space. The moveAndLog method in Aircraft handles the clamping.
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
