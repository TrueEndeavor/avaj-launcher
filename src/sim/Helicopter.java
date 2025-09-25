package sim;

public class Helicopter extends Aircraft
{
	public Helicopter(long p_id, String p_name, Coordinates p_coordinates)
	{
		super(p_id, p_name, p_coordinates);
	}

	public void updateConditions()
	{
		 System.out.println(
            "Helicopter#" + name + "(" + id + ") notified @ [" +
            coordinates.getLongitude() + "," +
            coordinates.getLatitude() + "," +
            coordinates.getHeight() + "]"
        );
	}
}
