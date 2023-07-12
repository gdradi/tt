package com.technogym.android.myrun.sdk.communication.notificationRules;

import java.util.Iterator;

import com.technogym.android.myrun.sdk.communication.listeners.IListener;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;

/**
 * This is the interface for a basic rule which allows to manage certain types of notifications.<br/>
 * The rule is responsible to select only the allowed {@link INotifier}s: for this scope, the notified event is
 * recognized through the specific identifier ({@link #getIdentifier()}) of the rule and the one wrapped into the
 * received message.<br/>
 * <br/>
 * Basically, the implementations of this interface will work as notification dispatchers: they should also act as
 * parsers of the received content, but this capability shouldn't be publicly exposed through the interface.<br/>
 * <br/>
 * It's generic in {@code TListener}.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * 
 * @see INotifier
 * @see Notifier
 * */
public interface INotificationRule<TListener extends IListener> {

	/**
	 * This method gets the identifier of the notifications it can manage.
	 * 
	 * @return the identifier
	 * */
	public String getIdentifier();

	/**
	 * This method gets all the {@link INotifier}s which can be properly notified.
	 * 
	 * @param notifyMessage
	 *            : the content of the notification
	 * */
	public Iterator<INotifier<TListener>> getNotifiers(final String notifyMessage);

}
