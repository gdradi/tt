package com.technogym.android.myrun.sdk.communication.proxies;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.communication.commands.ICommand;
import com.technogym.android.myrun.sdk.communication.listeners.IListener;
import com.technogym.android.myrun.sdk.communication.notificationRules.INotificationRule;

/**
 * This interface defines the standard methods that a class must implement to act as an Equipment proxy.<br/>
 * <br/>
 * It's generic in {@code TListener} because the registration and de-registration of those would be too much restrictive
 * in some case.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public interface IEquipmentProxy<TListener extends IListener> {

	/**
	 * This method defines what are the {@link INotificationRule}s that the {@link IEquipmentProxy} will be able to take
	 * in charge during the communication with the equipment.
	 * */
	public void initializeNotificationRules();

	/**
	 * This method allows to register a listener to the {@link IEquipmentProxy}.<br/>
	 * 
	 * @param listener
	 *            : the {@code TListener} to register
	 * */
	public void registerForNotification(final TListener listener);

	/**
	 * This method allows to de-register a listener from the {@link IEquipmentProxy}.<br/>
	 * 
	 * @param listener
	 *            : the {@code TListener} to de-register
	 * */
	public void deregisterForNotification(final TListener listener);

	/**
	 * This method allows to send a command to the equipment. It's not specified the kind of message and connection
	 * between the equipment and the application because it's all delegated to other components that will have the
	 * knowledge of the technology and of the semantic used.
	 * 
	 * @param command
	 *            : the generic command to send to the equipment
	 * */
	public void sendCommand(final ICommand command) throws WriteNotAllowedException;
}
