package events;

public class Usage extends PApplet
{	
	public void setup()
	{
		EventSystem.addEventListener(MyEvent.class, MyEvent.EVENT_TYPE_A, this, "doThis");
	}

	public void draw()
	{
		// dispatch Event without any data
		EventSystem.dispatchEvent(new MyEvent(MyEvent.EVENT_TYPE_A));

		// dispatch Event with data
		MyEvent event = new MyEvent(MyEvent.EVENT_TYPE_A);
		event.someData = 42;
		EventSystem.dispatchEvent(event);

		// listening and dispatching events can happen in different classes,
		// so class A can dispatch an event, listened by class B, or even class C, D, E and F
	}

	public void doThis(MyEvent _event)
	{
		// do something
		// access event data like _event.someData
	}
}