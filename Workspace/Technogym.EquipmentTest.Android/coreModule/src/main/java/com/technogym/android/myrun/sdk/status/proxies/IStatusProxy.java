package com.technogym.android.myrun.sdk.status.proxies;

import com.technogym.android.myrun.sdk.communication.enums.MyRunStatusType;
import com.technogym.android.myrun.sdk.communication.proxies.IEquipmentProxy;
import com.technogym.android.myrun.sdk.core.proxies.ICoreProxy;
import com.technogym.android.myrun.sdk.status.listeners.IStatusListener;

/**
 * This is a particular interface which extends {@link IEquipmentProxy} and allows to register for equipment status
 * notifications. It's a core functionality which could be included in {@link ICoreProxy} but the equipment status could
 * be interesting also for components which do not need other core functionalities (instead the opposite is not true).
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public interface IStatusProxy extends IEquipmentProxy<IStatusListener> {

	/**
	 * This method exposes the current status of the equipment.
	 * */
	public MyRunStatusType getEquipmentStatus();

}
