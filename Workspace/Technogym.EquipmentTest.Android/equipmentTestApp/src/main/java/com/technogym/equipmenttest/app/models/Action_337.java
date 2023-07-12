package com.technogym.equipmenttest.app.models;

import it.spot.android.logger.domain.Logger;
import android.content.Context;

import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.android.myrun.sdk.system.proxies.SystemProxy;
import com.technogym.equipmenttest.library.models.Action;

/**
 * This class initializes the "device-down" sensors:
 * <ul>
 * <li>sends the proper command</li>
 * <li>checks the response correctness</li>
 * </ul>
 * It's an automatic action.
 * 
 * @author Federico Foschini
 * @version 2.0
 * */
public class Action_337 extends BaseAction {

	private static final String CALIBRATE_SENSOR_DOWN = "calibrate_sensor_down";
	private final ISystemProxy mSystemProxy;

	// { Construction

	protected Action_337(final Context context) {
		super(context);
		// we suppose it's already been instantiated, so no need to pass IEquipmentController
		this.mSystemProxy = SystemProxy.getInstance(null);
		this.mSystemProxy.registerForNotification(this);
	}

	public static Action_337 create(final Context context) {
		return new Action_337(context);
	}

	// }

	// { Action abstract method implementation


	@Override
	public boolean executeCommand(String commandID) {

		if (commandID.equals(CALIBRATE_SENSOR_DOWN)) {
			Logger.getInstance().logDebug(getClass().getSimpleName(), 
					"executeCommand: CALIBRATE SENSOR DOWN");

			try {
				this.mSystemProxy.initSensorDeviceDown();
			} catch (Exception ex) {
				ex.printStackTrace();
				this.mListener.onActionCompleted(false);
			}
		} else {
			return super.executeCommand(commandID);
		}

		return true;
	}

	@Override
	public void stop() {
		this.mSystemProxy.deregisterForNotification(this);
		super.stop();
	}

	// }

	// { ISystemListener implementation

	@Override
	public void onValueFromSensorDown(final Integer value) {
		Logger.getInstance().logDebug(getClass().getSimpleName(), 
				"onValueFromSensorDown retrieved value: " + String.valueOf(value));

		this.mSystemProxy.deregisterForNotification(this);
		this.mListener.onActionUpdate(String.valueOf(value));
	}


	// }

}
