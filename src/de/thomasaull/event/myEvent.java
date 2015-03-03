package events;

public class myEvent extends Event
{
	public static String EVENT_TYPE_A = "EVENT_TYPE_A";
	
	public String type;
	public int someData;

	public myEvent(String _type)
	{
		super(_type);
		//type = _type;
	}
}