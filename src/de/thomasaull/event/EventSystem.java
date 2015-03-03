package de.thomasaull.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import sun.net.www.content.text.plain;

public class EventSystem
{
	static ArrayList<EventListener> eventListeners = new ArrayList();
	static ArrayList<EventListener> eventListenersTrashbin = new ArrayList();
	static ArrayList<EventListener> eventListenersToBeAdded = new ArrayList();
	private static Boolean dispatchInProgress = false;
	
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
			
			if(dispatchInProgress) {
				eventListenersToBeAdded.add(eventListener);
				//System.out.println("dispatch in progress...");
			}
			else
				eventListeners.add(eventListener);
		}
		else
			System.out.println("error adding event to the listenerList");
	}
	
	
	public static void addEventListenersDelayed()
	{
		if(eventListenersToBeAdded.size() == 0)
			return;
		
		/*for(EventListener eventListener : eventListenersToBeAdded)
		{
			eventListeners.add(eventListener);
			//System.out.println("event listener added delayed: " + eventListener.type);
		}*/
		
		eventListeners.addAll(eventListenersToBeAdded);
		eventListenersToBeAdded.clear();
	}
	
	
	public static void removeEventListener(Class _eventClass, String _type, Object _containingClass, String _method)
	{		
//		System.out.println("dispatch In Progress: " + dispatchInProgress);
			
		System.out.println("remove Event Listener: " + _type);
		
		/*for(int i = 0; i < eventListeners.size(); i++)
		{
			EventListener eventListener = eventListeners.get(i);*/
		
		Iterator<EventListener> i = eventListeners.iterator();
		while(i.hasNext()) 
		{
			EventListener eventListener = i.next();
			
			
			Class<?> containingClass = _containingClass.getClass().getEnclosingClass();
			
			if (containingClass == null) 
			{
				containingClass = _containingClass.getClass();
			}

			// DEBUUGING CLASS COMPARISION IF IN THREAD
			/*if(_type.equals("TICK"))
			{
				if(eventListener.type == _type)
				{
					System.out.println(eventListener.type + " | " + _type);
					System.out.println(eventListener.callbackClass.getClass() + " | " + containingClass);
					System.out.println(eventListener.callback.getName() + " | " + _method);
					System.out.println(eventListener.eventClass + " | " + _eventClass);
					
					if(eventListener.type == _type && eventListener.callbackClass.getClass() == containingClass && eventListener.callback.getName() == _method && eventListener.eventClass == _eventClass)
					{
						System.out.println("GEFUNDEN!!!");
					}
					else {
						System.out.println("NICHT GEFUNDEN!!!");
					}
				}
			}*/
			
			
			if(eventListener.type == _type && eventListener.callbackClass.getClass() == containingClass && eventListener.callback.getName() == _method && eventListener.eventClass == _eventClass)
			{				
				/*if(dispatchInProgress)
				{
					//eventListener.trash = true;
					eventListenersTrashbin.add(eventListener);
				}
				else 
				{*/
					//eventListeners.remove(eventListener);
					i.remove();
				//}
			}
		}
	}
	
	
	public static void emptyTrashbin()
	{
		if(eventListenersTrashbin.size() == 0)
			return;
		
//		System.out.println("trashbin emptying: " + eventListenersTrashbin.size() + " | " + eventListeners.size());
				
		if(dispatchInProgress != true)
		{
			eventListeners.removeAll(eventListenersTrashbin);
			eventListenersTrashbin.clear();
		}
		
//		System.out.println("trashbin emptied: " + eventListenersTrashbin.size() + " | " + eventListeners.size());
	}
	
	
	public static void dispatchEvent(Event _event)
	{
		dispatchEvent(_event.type, _event);
	}
	
	
	public static void dispatchEvent(String _type, Event _event)
	{	
		//System.out.println("dispatch" + _type);
		
		dispatchInProgress = true;
		
		//System.out.println("dispatchEvent" + " | " + eventListeners.size());
		
		/*for(int i = 0; i < eventListeners.size(); i++)
		{
			EventListener eventListener = eventListeners.get(i);
			//System.out.println(eventListener.type);
		}*/
				
//		System.out.println("size bei dispatch: " + eventListeners.size());
		
		
		/*List<String> names = ....
		Iterator<String> i = names.iterator();
		while (i.hasNext()) {
		   String s = i.next(); // must be called before you can call i.remove()
		   // Do something
		   i.remove();
		}*/
		
		
		
		//for(int i = 0; i < eventListeners.size(); i++)
		
		
		
		//for(EventListener eventListener : eventListeners)
		//{
			//EventListener eventListener = eventListeners.get(i);
			
//			System.out.println(":: " + i + " :: " + eventListener.type);
		
			//System.out.println(_type + " " + eventListener.type);
		
		
		// cycle through a copy of the acutal Event Listeners to avoid Exceptions:
		ArrayList<EventListener> dispatchEventListeners = (ArrayList<EventListener>) eventListeners.clone();
		
		Iterator<EventListener> i = dispatchEventListeners.iterator();
		while(i.hasNext()) 
		{
			EventListener eventListener = i.next();
			
			//if(eventListener.type == _type)
			if(_type.equals(eventListener.type))
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
		addEventListenersDelayed();
	}
	
}
