package com.technogym.android.myrun.sdk.communication.proxies;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.communication.commands.ICommand;
import com.technogym.android.myrun.sdk.communication.listeners.IListener;
import com.technogym.android.myrun.sdk.communication.notificationRules.INotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.connection.controllers.EquipmentController;
import com.technogym.android.myrun.sdk.connection.controllers.IEquipmentController;
import com.technogym.android.myrun.sdk.connection.listeners.EquipmentConnectionListener;

/**
 * It's the basic implementation of {@link IEquipmentProxy} interface.<br/>
 * It's generic in TListener because it delegates to subclasses the subscription to a certain type of events.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public abstract class EquipmentProxy<TListener extends IListener> implements IEquipmentProxy<TListener>,
		EquipmentConnectionListener {

	private final List<TListener> mListeners;
	private final IEquipmentController mConnectionController;
	private final HashMap<String, List<INotificationRule<TListener>>> mNotificationRules;

	private boolean mInitialized;

	// { Construction

	/**
	 * The class constructor. It initializes the internal properties and registers itself to the given
	 * {@link IEquipmentController}.
	 * 
	 * @param connectionController
	 *            : the controller which centralizes the connection with the equipment
	 * */
	protected EquipmentProxy(final IEquipmentController connectionController) {
		super();

		this.mConnectionController = connectionController;
		this.mConnectionController.registerEquipmentConnectionListener(this);
		this.mNotificationRules = new HashMap<String, List<INotificationRule<TListener>>>();
		this.mListeners = new ArrayList<TListener>();

		this.mInitialized = false;
	}

	// }

	// { IEquipmentProxy implementation

	@Override
	public abstract void initializeNotificationRules();

	@Override
	public void registerForNotification(final TListener listener) {
		if (!this.mListeners.contains(listener)) {
			this.mListeners.add(listener);
		}
	}

	@Override
	public void deregisterForNotification(final TListener listener) {
		if (this.mListeners.contains(listener)) {
			this.mListeners.remove(listener);
		}
	}

	@Override
	public final void sendCommand(final ICommand command) throws WriteNotAllowedException {
		this.mConnectionController.sendMessage(command.execute());
	}

	// }

	// { Private and protected methods

	/**
	 * This method tells if the {@link EquipmentProxy} has already been initialized or not.
	 * 
	 * @return {@code true} if it has already been initialized, {@code false} otherwise
	 * */
	protected final boolean getInitializationState() {
		return this.mInitialized;
	}

	/**
	 * This method sets the initialization state of the {@link EquipmentProxy}.
	 * 
	 * @param initialized
	 *            : a {@code boolean} for the initialization state
	 * */
	protected final void setInitializationState(final boolean initialized) {
		this.mInitialized = initialized;
	}

	/**
	 * This method adds an {@link INotificationRule}: this means that the instance of the {@link EquipmentProxy} will be
	 * able to manage another family / type of messages sent by the equipment itself.
	 * 
	 * @param notificationRule
	 *            : the rule to add
	 * */
	protected final void addNotificationRule(final INotificationRule<TListener> notificationRule) {
		if (!this.mNotificationRules.containsKey(notificationRule.getIdentifier())) {
			this.mNotificationRules
					.put(notificationRule.getIdentifier(), new ArrayList<INotificationRule<TListener>>());
		}
		// Aggiungo solo se non esiste già
		List<String> classNames = new ArrayList<>();
		for (INotificationRule<TListener> l : this.mNotificationRules.get(notificationRule.getIdentifier())) {
			classNames.add(l.getClass().getSimpleName());
		}
		boolean sameInstanceCondition = this.mNotificationRules.get(notificationRule.getIdentifier()).contains(notificationRule);
		boolean sameClassCondition = classNames.contains(notificationRule.getClass().getSimpleName());
		if (sameClassCondition || sameInstanceCondition) {
			Log.e("GDRADI", "NotificationRule "+notificationRule.getClass().getSimpleName()+" registration skipped. sameInstanceCondition="+sameInstanceCondition+", sameClassCondition="+sameClassCondition);
			return;
		}
		this.mNotificationRules.get(notificationRule.getIdentifier()).add(notificationRule);
	}

	/**
	 * This method sends a message to the equipment through the {@link EquipmentController}.<br/>
	 * It's protected because it would not be safe: this method can be called only internally.
	 * 
	 * @param message
	 *            : the message to send
	 * */
	protected final void sendMessage(final String message) throws WriteNotAllowedException {
		this.mConnectionController.sendMessage(message);
	}

	/**
	 * This method handles the reception of a message from the equipment.<br/>
	 * In the details, it checks and parses the parameters of the message and then identifies the related
	 * {@link INotificationRule}s subscribed to the proxy, so that the {@link INotifier}s can be properly notified.
	 * 
	 * @param message
	 *            : the message received
	 * */
	protected final void messageReceived(final String message) {

		final String parameters = this.getUnparsedParametersFromMessage(message);
		final String identifier = this.getRuleIdentifierFromMessage(message);

		this.notifyByRuleIdentifier(identifier, parameters);
	}

	/**
	 * This method iterates over the {@link INotificationRule}s registered and notifies every {@link INotifier} bound to
	 * those.
	 * 
	 * @param identifier
	 *            : the string which identifies the rule
	 * @param params
	 *            : the unparsed parameters
	 * */
	protected void notifyByRuleIdentifier(final String identifier, final String params) {
		final List<INotificationRule<TListener>> notificationRules = this.mNotificationRules.get(identifier);
		if (notificationRules != null) {
			for (INotificationRule<TListener> notificationRule : notificationRules) {
				if (notificationRule != null) {
					Iterator<INotifier<TListener>> notifiers = notificationRule.getNotifiers(params);
					while (notifiers.hasNext()) {
						final INotifier<TListener> notifier = notifiers.next();
						for (TListener listener : this.mListeners) {
							notifier.notify(listener);
						}
					}
				}
			}
		}
	}

	/**
	 * This method composes the {@link INotificationRule} identifier parsing the proper parts of the received message
	 * and returns it.<br/>
	 * That's a base implementation, it can be overridden without any kind of problems.
	 * 
	 * @param message
	 *            : the received message
	 * 
	 * @return the rule identifier
	 * */
	protected String getRuleIdentifierFromMessage(final String message) {
		// MYRUN 2020 - aggiunto il try catch perchè alcuni comandi del MYRUN2020
		// non rispondono con source e code, ma solo con source
		try {
			final String source = message.substring(0, 3);
			final String code = message.substring(4, 7);
			return String.format("%s_%s", source, code);
		} catch (Exception e) {
			return message;
		}

	}

	/**
	 * This method gets the optional parameters from the received message.<br/>
	 * This is a base implementation, it can be overridden without any kind of problems.
	 * 
	 * @param message
	 *            : the received message
	 * 
	 * @return the unparsed parameters
	 * */
	protected String getUnparsedParametersFromMessage(final String message) {
		if (message.length() > 7) {
			return message.substring(8);
		}
		return "";
	}

	/**
	 * This method simply returns the list of TListeners registered to the proxy.
	 * 
	 * @return the list of TListeners
	 * */
	protected List<TListener> getListeners() {
		return this.mListeners;
	}

	// }

	// { EquipmentConnectionListener implementation

	@Override
	public final void onMessageReceived(String message) {
		messageReceived(message);
	}

	@Override
	public void onConnectionTerminated() {
		// INF: Empty
	}

	@Override
	public void onConnectionInterrupted() {
		// INF: Empty
	}

	@Override
	public void onConnectionFailed() {
		// INF: Empty
	}

	@Override
	public void onConnectionEstablished() {
		// INF: Empty
	}

	// }

}
