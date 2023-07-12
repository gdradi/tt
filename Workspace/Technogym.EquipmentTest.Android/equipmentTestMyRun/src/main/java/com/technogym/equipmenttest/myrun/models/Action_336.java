package com.technogym.equipmenttest.myrun.models;

import android.content.Context;

import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.myrun.proxies.SystemProxy;

import it.spot.android.logger.domain.Logger;

/**
 * This class initializes the "device-up" sensors:
 * <ul>
 * <li>sends the proper command</li>
 * <li>checks the response correctness</li>
 * </ul>
 * It's an automatic action.
 * 
 * @author Federico Foschini
 * @version 2.0
 * */
public class Action_336 extends BaseMyRunAction {

	private static final String CALIBRATE_SENSOR_UP = "calibrate_sensor_up";
	private final ISystemProxy mSystemProxy;

	// { Construction

	protected Action_336(final Context context) {
		super(context);
		// we suppose it's already been instantiated, so no need to pass IEquipmentController
		this.mSystemProxy = SystemProxy.getInstance(null);
		this.mSystemProxy.registerForNotification(this);
	}

	public static Action_336 create(final Context context) {
		return new Action_336(context);
	}

	// }

	// { Action abstract method implementation

	@Override
	public void execute(final String data) {
		// INF: Empty
	}

	@Override
	public boolean executeCommand(String commandID) {

		if (commandID.equals(CALIBRATE_SENSOR_UP)) {
			Logger.getInstance().logDebug(getClass().getSimpleName(), 
					"executeCommand: CALIBRATE SENSOR UP");

			try {
				this.mSystemProxy.initSensorDeviceUp();
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
	public void onValueFromSensorUp(final Integer value) {
		Logger.getInstance().logDebug(getClass().getSimpleName(), 
				"onValueFromSensorDown retrieved value: " + String.valueOf(value));

		this.mSystemProxy.deregisterForNotification(this);
		this.mListener.onActionUpdate(String.valueOf(value));
	}

	// }

}
