package carintelligence;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import sun.net.www.content.text.plain;

public class EventSystem
{
	static ArrayList<EventListener> eventListeners = new ArrayList();
	
	public EventSystem()
	{
		
	}
	
	
	/*public static void addEventListener(Class _eventclass, String _type, Object _class, String _method)
	{
		addEventListener(_type, _class, _method);
	}*/
	
	
	public static void addEventListener(Class _eventClass, String _type, Object _containingClass, String _method)
	{
		Method method = null;
		
		try
		{
			method = _containingClass.getClass().getMethod(_method, _eventClass);
			
			/*if(_event == null)
			{
				method = _class.getClass().getMethod(_method);
			}
			else
				method = _class.getClass().getMethod(_method, Event.class);*/
		} 
		catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (NoSuchMethodException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		EventListener eventListener;
		
		if(method != null) 
		{
			eventListener = new EventListener(_type, _containingClass, method, _eventClass);
			eventListeners.add(eventListener);
		}
		else
			System.out.println("error adding event to the listenerList");
	}
	
	
	public static void dispatchEvent(Event _event)
	{
		dispatchEvent(_event.type, _event);
	}
	
	
	public static void dispatchEvent(String _type, Event _event)
	{		
		for(EventListener eventListener : eventListeners)
		{
			if(eventListener.type == _type)
			{				
				try
				{
					eventListener.callback.invoke(eventListener.callbackClass, _event);
				} 
				catch (IllegalArgumentException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (IllegalAccessException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				catch (InvocationTargetException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
