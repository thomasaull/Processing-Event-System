package de.thomasaull.event;

import java.lang.reflect.Method;

public class EventListener
{
	public String type;
	public Method callback;
	public String callbackString;
	public Object callbackClass;
	public Class eventClass;
	public Boolean trash = false;
	
	public EventListener(String _type, Object _callbackClass, Method _callback, Class _eventClass)
	{
		type = _type;
		callbackClass = _callbackClass;
		callback = _callback;
		//callbackString = _callbackString;
		eventClass = _eventClass;
	}
}
