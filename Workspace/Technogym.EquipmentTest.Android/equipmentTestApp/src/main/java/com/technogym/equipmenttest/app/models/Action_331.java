package com.technogym.equipmenttest.app.models;

import it.spot.android.logger.domain.Logger;
import android.content.Context;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.android.myrun.sdk.system.proxies.SystemProxy;
import com.technogym.equipmenttest.library.models.Action;

/**
 * This class makes the equipment go in standby.<br/>
 * It sends the command to standby the MyRun equipment. A warning message is needed withing the UI.<br/>
 * It's an automatic action.
 * 
 * @author Federico Foschini
 * @version 2.0
 * */
public class Action_331 extends BaseAction {

	private static final String FORCE_SUSPENSION_EQUIPMENT = "force_suspension";

	private final ISystemProxy mSystemProxy;

	// { Construction

	protected Action_331(final Context context) {
		super(context);
		// we suppose it's already been instantiated, so no need to pass IEquipmentController
		this.mSystemProxy = SystemProxy.getInstance(null);
	}

	public static Action_331 create(final Context context) {
		return new Action_331(context);
	}

	// }

	// { Action abstract method implementation

	@Override
	public void execute(final String data) {
		// INF: Empty
	}

	@Override
	public void stop() {
		mSystemProxy.deregisterForNotification(this);
		super.stop();
	}

	@Override
	public boolean executeCommand(final String commandID) {

		if (commandID.equals(FORCE_SUSPENSION_EQUIPMENT)) {
			try {

				//Logger.getInstance().logDebug(getClass().getSimpleName(), "executeCommand: DUMP IO SENSORS");
				Logger.getInstance().logDebug(getClass().getSimpleName(), "Get status to check sensors");

				mSystemProxy.registerForNotification(this);
				mSystemProxy.getStatus();
				//mSystemProxy.dumpIOSensorsValues();

			} catch (WriteNotAllowedException ex) {
				Logger.getInstance().logDebug(getClass().getSimpleName(),
						"BT Error executing command to get the status of the equipment");

				ex.printStackTrace();
				this.mListener.onActionCompleted(false);
			}
		} else {
			return super.executeCommand(commandID);
		}
		return true;
	}

	// }

	// { ISystemListener implementation


	@Override
	public void onWakeupSensorRetrieved(final Integer wakeupSensorValue) {

//		Logger.getInstance().logDebug("ACTION_331", "The value of the wakeup sensor is: " + wakeupSensorValue);
//
//		mSystemProxy.deregisterForNotification(this);
//		boolean result = false;
//		if (wakeupSensorValue.intValue() == 1) {
//			try {
//				Logger.getInstance().logDebug(getClass().getSimpleName(),
//						"ACTION 331 - executeCommand: FORCE SUSPENSION");
//
//				mSystemProxy.forceSuspension();
//				result = true;
//			} catch (WriteNotAllowedException ex) {
//				ex.printStackTrace();
//			}
//		}
//		mListener.onActionCompleted(result);
	}

	@Override
	public void onStatusRetreived(String status) {

		Logger.getInstance().logDebug(getClass().getSimpleName(),
				"onStatusRetreived: [" + status + "]");

		// prelievo dello status della macchina
		String[] statusVals = status.split(",");
		String qsstatusString = statusVals[12];
		int qsstatusVal = Integer.parseInt(qsstatusString);

		Logger.getInstance().logDebug(getClass().getSimpleName(),
				"onStatusRetreived - QSStatus: [" + qsstatusVal + "]");

		/*
			**********************************************************
			* If the current QS Status is == [QSWaitForStartRC = 2]
			* The force_suspension command can be executed
			* and the action will be completed successfully
			*
			* If the current QS Status is != [QSWaitForStartRC = 2]
			* The force_suspension command can't be executed
			* and the action will be completed unsuccessfully
			**********************************************************
		 */
		boolean result = qsstatusVal == 2;
		mSystemProxy.deregisterForNotification(this);

		// send the force_suspension command
		try {
			Logger.getInstance().logDebug(getClass().getSimpleName(),
					"execute the command [" + FORCE_SUSPENSION_EQUIPMENT + "]");
			mSystemProxy.forceSuspension();

		} catch (WriteNotAllowedException ex) {
			ex.printStackTrace();
			result = false;
		}
		mListener.onActionCompleted(result);
	}

}
