package com.technogym.equipmenttest.appbleuart.models;

import android.content.Context;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.appbleuart.proxies.SystemProxy;

import it.spot.android.logger.domain.Logger;

public class Action_1020 extends BaseBleUartAction {

	private final ISystemProxy mSystemProxy;

	// { Construction

	protected Action_1020(final Context context) {
		super(context);
		// we suppose it's already been instantiated, so no need to pass IEquipmentController
		this.mSystemProxy = SystemProxy.getInstance(null);
		mSystemProxy.registerForNotification(this);
	}

	public static Action_1020 create(final Context context) {
		return new Action_1020(context);
	}

	// }

	// { Action abstract method implementation

	@Override
	public void execute(String data) {
		Logger.getInstance().logDebug(getClass().getSimpleName(), "[execute] data: "+data);
	}


	@Override
	public boolean executeCommand(final String commandID) {
		Logger.getInstance().logDebug(getClass().getSimpleName(), "[executeCommand] commandID: "+commandID);
		if(commandID.equals("BUTTONSTATUS")){
			try {
				mSystemProxy.verifyButtonStatus();
			} catch (WriteNotAllowedException e) {
				e.printStackTrace();
				Logger.getInstance().logDebug(getClass().getSimpleName(), "[executeCommand] exception: "+e.getMessage());
				return false;
			}
		}else{
			Logger.getInstance().logDebug(getClass().getSimpleName(), "[executeCommand] Command not valid");
		}
		return true;
	}


	@Override
	public void onButtonStatusResponseRetrieved(String mStatus) {
		Logger.getInstance().logDebug(getClass().getSimpleName(), "[onButtonStatusResponseRetrieved] Status: "+mStatus);
		this.mListener.onActionUpdate(mStatus);
	}

	// { ISystemListener implementation

	@Override
	public void stop() {
		this.mSystemProxy.deregisterForNotification(this);
		super.stop();
	}

}
