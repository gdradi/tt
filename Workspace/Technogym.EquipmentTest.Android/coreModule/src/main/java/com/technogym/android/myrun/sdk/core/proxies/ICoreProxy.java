package com.technogym.android.myrun.sdk.core.proxies;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.communication.proxies.IEquipmentProxy;
import com.technogym.android.myrun.sdk.core.listeners.ICoreListener;
import com.technogym.android.myrun.sdk.core.routines.KeepAliveRoutine.IKeepAliveExpiredListener;
import com.technogym.android.myrun.sdk.status.proxies.IStatusProxy;

/**
 * It's the interface for an {@link IEquipmentProxy} which manages the core functionalities to communicate with the
 * equipment.<br/>
 * <br/>
 * It resolves the generic {@code TListener} with an appropriate {@link ICoreListener}, so that external components can
 * register themselves for core notification events.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public interface ICoreProxy extends IEquipmentProxy<ICoreListener>, IKeepAliveExpiredListener {

	/**
	 * This method sends to the equipment the command for entering remote mode. It's necessary for starting a training
	 * activity.
	 * */
	public void enterRemoteMode() throws WriteNotAllowedException;

	/**
	 * This method sends the command for exiting remote mode and brings the equipment back to standard behavior.
	 * */
	public void exitRemoteMode() throws WriteNotAllowedException;

	/**
	 * This method sends the command for keeping alive the connection with the equipment. It's necessary after entering
	 * remote mode to keep the dialog between application and equipment up.
	 * 
	 * @see {@link ICoreProxy#initializeKeepAliveRoutine(IStatusProxy)}
	 * @see {@link ICoreProxy#enterRemoteMode()}
	 * */
	public void sendKeepAlive() throws WriteNotAllowedException;

	/**
	 * Method to disable the keep alive on the myrun
	 */
	public void disableKeepAlive() throws WriteNotAllowedException;
	
	/**
	 * This method allows to check if the equipment is in remote mode or not.
	 * 
	 * @return {@code true} if remote mode is enabled, {@code false} otherwise.
	 * */
	public boolean isRemoteModeEnabled();

}
