package de.thomasaull.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Event extends Object
{
	String type;
	Method callback;
	
	public Event()
	{
//		System.out.println("Event superclass constructor");
	}
	
	public Event(String _type)
	{
		type = _type;
	}
}
