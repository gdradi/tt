package com.technogym.equipmenttest.app.models;

import it.spot.android.logger.domain.Logger;
import android.content.Context;

import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.android.myrun.sdk.system.proxies.SystemProxy;
import com.technogym.equipmenttest.library.models.Action;

/**
 * This class has to configure on the equipment the user presence detection to 0, so it's disabled. 
 * It's an automatic action.
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public class Action_157 extends BaseAction {

	private final ISystemProxy mSystemProxy;

	// { Construction

	protected Action_157(final Context context) {
		super(context);
		// we suppose it's already been instantiated, so no need to pass IEquipmentController
		this.mSystemProxy = SystemProxy.getInstance(null);
	}

	public static Action_157 create(final Context context) {
		return new Action_157(context);
	}

	// }

	// { Action abstract method implementation

	@Override
	public void execute(final String data) {
		boolean result = false;

		Logger.getInstance().logDebug(
			getClass().getSimpleName(), "EXECUTING ");
		
		try {
			this.mSystemProxy.setUserDetection(0);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Logger.getInstance().logDebug(
			getClass().getSimpleName(), "RESULT " + String.valueOf(result));
		
		this.mListener.onActionCompleted(result);
	}

	// }

}
