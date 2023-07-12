package com.technogym.equipmenttest.appbleuart.models;

import android.content.Context;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.appbleuart.proxies.SystemProxy;

import it.spot.android.logger.domain.Logger;

public class Action_1018 extends BaseBleUartAction {

	private final ISystemProxy mSystemProxy;

	// { Construction

	protected Action_1018(final Context context) {
		super(context);
		// we suppose it's already been instantiated, so no need to pass IEquipmentController
		this.mSystemProxy = SystemProxy.getInstance(null);
		mSystemProxy.registerForNotification(this);
	}

	public static Action_1018 create(final Context context) {
		return new Action_1018(context);
	}

	// }

	// { Action abstract method implementation

	@Override
	public void execute(String data) {
		try {
			Logger.getInstance().logDebug(
					getClass().getSimpleName(), "EXECUTE Action_1018");
			this.mSystemProxy.askBtVersion();
		} catch (Exception ex) {
			ex.printStackTrace();
			this.mListener.onActionCompleted(false);
		}
	}

	// }

	// { ISystemListener implementation

	@Override
	public void stop() {
		this.mSystemProxy.deregisterForNotification(this);
		super.stop();
	}

	@Override
	public void onBtVersionRetrieved(String version) {
		Logger.getInstance().logDebug(
				getClass().getSimpleName(), "onBtVersionRetrieved = " +
						version);
		//this.mSystemProxy.deregisterForNotification(this);
		this.mListener.onActionUpdate(version);
	}
}
