package de.thomasaull.event;

import java.lang.reflect.Method;

public class EventListener
{
	public String type;
	public Method callback;
	public Object callbackClass;
	public Class eventClass;
	
	public EventListener(String _type, Object _callbackClass, Method _callback, Class _eventClass)
	{
		type = _type;
		callbackClass = _callbackClass;
		callback = _callback;
		eventClass = _eventClass;
	}
}
