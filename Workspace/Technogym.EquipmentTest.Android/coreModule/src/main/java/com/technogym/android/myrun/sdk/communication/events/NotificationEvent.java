package com.technogym.android.myrun.sdk.communication.events;

/**
 * The standard implementation of {@link INotificationEvent} interface.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public class NotificationEvent implements INotificationEvent {

	private final Object mSender;
	private final String mIdentifier;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It requires nothing more than a reference to the notification sender and an identification string to achieve the
	 * recognition of the proper listeners.
	 * 
	 * @param sender
	 *            : the generic object representing the sender of the notification
	 * @param identifier
	 *            : the identification string needed for listeners' recognition
	 * */
	protected NotificationEvent(final Object sender, final String identifier) {
		super();

		this.mSender = sender;
		this.mIdentifier = identifier;
	}

	/**
	 * It's a static factory method for class initialization.
	 * 
	 * @param sender
	 *            : the generic object representing the sender of the notification
	 * @param identifier
	 *            : the identification string needed for listeners' recognition
	 * */
	public static INotificationEvent create(final Object sender, final String identifier) {
		return new NotificationEvent(sender, identifier);
	}

	// }

	// { INotificationEvent implementation

	@Override
	public Object getSender() {
		return this.mSender;
	}

	@Override
	public String getIdentifier() {
		return this.mIdentifier;
	}

	// }

}
