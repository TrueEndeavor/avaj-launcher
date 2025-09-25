package sim;

public class Baloon extends Aircraft
{
	public Baloon(long p_id, String p_name, Coordinates p_coordinates)
	{
		super(p_id, p_name, p_coordinates);
	}

	public void updateConditions()
	{
		System.out.println(
            "Baloon#" + name + "(" + id + ") notified @ [" +
            coordinates.getLongitude() + "," +
            coordinates.getLatitude() + "," +
            coordinates.getHeight() + "]"
        );
	}
}
