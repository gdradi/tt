package com.technogym.equipmenttest.myrun.models;

import android.content.Context;

import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.myrun.proxies.SystemProxy;

import it.spot.android.logger.domain.Logger;

/**
 * This class has to configure on the equipment the unit of measure basing on the 5° number in its item code
 * with two different parameters. It's an automatic action.
 * 
 * @author Federico Foschini
 * @version 2.0
 * */
public class Action_1005 extends BaseMyRunAction {

	private final ISystemProxy mSystemProxy;

	// { Construction

	protected Action_1005(final Context context) {
		super(context);
		// we suppose it's already been instantiated, so no need to pass IEquipmentController
		this.mSystemProxy = SystemProxy.getInstance(null);
		this.mSystemProxy.registerForNotification(this);
	}

	public static Action_1005 create(final Context context) {
		return new Action_1005(context);
	}

	// }

	// { Action abstract method implementation

	@Override
	public void execute(String data) {
		try {
			Logger.getInstance().logDebug(
					getClass().getSimpleName(), "[Action 1005] EXECUTING ACTION 1005:");
			mSystemProxy.testWakeUp();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onTestWakeUpResponseRetrieved(String mStatus) {
		Logger.getInstance().logDebug(
				getClass().getSimpleName(), "[Action 1005] RESPONSE RETRIEVED: "+mStatus);
		this.mSystemProxy.deregisterForNotification(this);
		this.mListener.onActionUpdate(mStatus);
	}

	// }


}
