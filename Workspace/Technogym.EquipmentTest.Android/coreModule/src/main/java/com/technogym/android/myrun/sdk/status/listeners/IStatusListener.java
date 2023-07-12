package com.technogym.android.myrun.sdk.status.listeners;

import com.technogym.android.myrun.sdk.communication.enums.MyRunStatusType;
import com.technogym.android.myrun.sdk.communication.listeners.IListener;
import com.technogym.android.myrun.sdk.status.proxies.IStatusProxy;

/**
 * This is the interface that a listener must implement if it needs to be notified by the {@link IStatusProxy}.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public interface IStatusListener extends IListener {

	/**
	 * This method gets called when current status of the equipment is received.
	 * 
	 * @param status
	 *            {@link MyRunStatusType} corresponding to the current status of the equipment.
	 * */
	public void onEquipmentStatusChanged(final MyRunStatusType status);

}
