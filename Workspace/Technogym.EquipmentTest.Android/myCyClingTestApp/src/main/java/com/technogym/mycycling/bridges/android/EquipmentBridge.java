package com.technogym.mycycling.bridges.android;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.technogym.mycycling.managers.ITestActionManager;

public abstract class EquipmentBridge<TActionManager extends ITestActionManager> extends Bridge implements
		IEquipmentBridge<TActionManager> {

	protected final TActionManager mActionManager;

	// { Construction

	protected EquipmentBridge(final Activity activity, final TActionManager actionManager) {
		super(activity);

		this.mActionManager = actionManager;
	}

	// }

	// { IEquipmentBridge implementation

	@Override
	public void clearResources() {
		// INF: Empty
	}

	@Override
	@JavascriptInterface
	public void executeAction(final int id, final String data) {
		this.mActionManager.execute(id, data);
	}

	@JavascriptInterface
	public void executeCommand(final String commandID) {
		this.mActionManager.executeCommand(commandID);
	}

	@Override
	@JavascriptInterface
	public abstract boolean isUnity();

	@Override
	@JavascriptInterface
	public abstract boolean isMyRun();

	@Override
	@JavascriptInterface
	public abstract boolean isMyCycling();

	@Override
	@JavascriptInterface
	public abstract void askForEquipmentSerialNumber();

	@Override
	@JavascriptInterface
	public abstract void setEquipmentSerialNumber(final String serialNumber);

	// }

}
