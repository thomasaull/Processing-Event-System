package de.thomasaull.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import sun.net.www.content.text.plain;

public class EventSystem
{
	static ArrayList<EventListener> eventListeners = new ArrayList();
	static ArrayList<EventListener> eventListenersTrashbin = new ArrayList();
	private static Boolean dispatchInProgress = false;
	
	public EventSystem()
	{
	}
	
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
	
	
	public static void removeEventListener(Class _eventClass, String _type, Object _containingClass, String _method)
	{		
//		System.out.println("dispatch In Progress: " + dispatchInProgress);
				
		for(int i = 0; i < eventListeners.size(); i++)
		{
			EventListener eventListener = eventListeners.get(i);
			if(eventListener.type == _type && eventListener.callbackClass == _containingClass && eventListener.callback.getName() == _method && eventListener.eventClass == _eventClass)
			{
				if(dispatchInProgress)
				{
					eventListenersTrashbin.add(eventListener);
				}
				else
					eventListeners.remove(eventListener);
			}
		}
	}
	
	
	public static void emptyTrashbin()
	{
//		System.out.println("trashbin emptying: " + eventListenersTrashbin.size() + " | " + eventListeners.size());
		eventListeners.removeAll(eventListenersTrashbin);
		eventListenersTrashbin.clear();
//		System.out.println("trashbin emptied: " + eventListenersTrashbin.size() + " | " + eventListeners.size());
	}
	
	
	public static void dispatchEvent(Event _event)
	{
		dispatchEvent(_event.type, _event);
	}
	
	
	public static void dispatchEvent(String _type, Event _event)
	{	
		dispatchInProgress = true;
		
		//System.out.println("ÐÐÐÐÐÐÐÐÐÐÐÐÐÐÐÐÐ");
		//System.out.println("dispatchEvent" + " | " + eventListeners.size());
		
		for(int i = 0; i < eventListeners.size(); i++)
		{
			EventListener eventListener = eventListeners.get(i);
			//System.out.println(eventListener.type);
		}
				
//		System.out.println("size bei dispatch: " + eventListeners.size());
		
		for(int i = 0; i < eventListeners.size(); i++)
		{
			EventListener eventListener = eventListeners.get(i);
			
//			System.out.println(":: " + i + " :: " + eventListener.type);
		
			if(eventListener.type == _type)
			{
				
				try
				{
					/*if(_type == "EXPAND")
						System.out.println("expand!");*/
					
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
		
		dispatchInProgress = false;
		emptyTrashbin();
	}
	
}
