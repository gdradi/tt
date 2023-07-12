package com.technogym.android.myrun.sdk.communication.events;

/**
 * It represents a common interface for all the type of events that can be fired up during the communication between
 * application and equipment.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public interface INotificationEvent {

	/**
	 * This method gets the sender of the notification event.<br/>
	 * 
	 * @return the generic {@code Object} which represents the sender
	 * */
	public Object getSender();

	/**
	 * This method gets the identifier of the event, so that it can be easily recognized by the listeners.
	 * 
	 * @return the string identifier
	 * */
	public String getIdentifier();

}
