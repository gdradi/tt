package com.technogym.equipmenttest.myrun.models;

import android.content.Context;
import android.os.Handler;

import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.myrun.proxies.SystemProxy;
import com.technogym.equipmenttest.library.models.Action;

/**
 * This class makes the equipment calibrate itself.<br/>
 * It's an automatic action.
 * 
 * @author Federico Foschini
 * @version 2.0
 * */
public class Action_328 extends BaseMyRunAction {

	private static final String AUTOCALIBRATE_EQUIPMENT = "autocalibrate";
	private ISystemProxy mSystemProxy;

	// { Construction

	protected Action_328(final Context context) {
		super(context);
		// we suppose it's already been instantiated, so no need to pass IEquipmentController
		this.mSystemProxy = SystemProxy.getInstance(null);
	}

	public static Action_328 create(final Context context) {
		return new Action_328(context);
	}

	// }

	// { Action abstract method implementation

	@Override
	public void execute(final String data) {
		// INF: Empty
	}

	@Override
	public boolean executeCommand(final String commandID) {

		if (commandID.equals(AUTOCALIBRATE_EQUIPMENT)) {
			try {
				this.mSystemProxy.autocalibrate();
			} catch (Exception ex) {
				ex.printStackTrace();
				this.mListener.onActionCompleted(false);
			}

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					mListener.onActionCompleted(true);
				}
			}, 90000);

		} else {
			return super.executeCommand(commandID);
		}

		return true;
	}

	// }
	
}
