package com.technogym.equipmenttest.app.models;

import it.spot.android.logger.domain.Logger;
import android.content.Context;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.android.myrun.sdk.system.proxies.SystemProxy;
import com.technogym.equipmenttest.library.models.Action;

/**
 * This class has to delete history collection of errors.
 * 
 * @author Federico Foschini
 * @version 2.0
 * */
public class Action_007 extends BaseAction {

	private final ISystemProxy mSystemProxy;
	
	// { Construction

	protected Action_007(Context context) {
		super(context);

		this.mSystemProxy = SystemProxy.getInstance(null);
		mSystemProxy.registerForNotification(this);
	}

	public static Action_007 create(final Context context) {
		return new Action_007(context);
	}

	// }

	// { Private methods

	private void doResetError() {
		try {
			mSystemProxy.resetErrorLog();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			Logger.getInstance().logError(
					getClass().getSimpleName(), 
					"ERROR DURING EXECUTION OF RESET ERROR LOG: " + e.getLocalizedMessage());
			this.mSystemProxy.deregisterForNotification(this);
			this.mListener.onActionCompleted(false);
		}
	}
	
//	private void getErrorLog() {
//		try {
//			mSystemProxy.getErrorLog();
//		} catch (WriteNotAllowedException e) {
//			e.printStackTrace();
//			Logger.getInstance().logError(
//					getClass().getSimpleName(), 
//					"ERROR DURING EXECUTION OF GET ERROR LOG: " + e.getLocalizedMessage());
//			this.mSystemProxy.deregisterForNotification(this);
//			this.mListener.onActionCompleted(false);
//		}
//	}
	
	// }

	// { Action abstract method implementation

	@Override
	public void execute(String data) {
		Logger.getInstance().logDebug(
				getClass().getSimpleName(), "EXECUTE THE RESET ERROR LOG");
		doResetError();
	}

	// }

	// { ISystemListener implementation

	@Override
	public void onResetErrorLog(String result) {
		Logger.getInstance().logDebug(
				getClass().getSimpleName(), 
				"onResetErrorLog result: " + result);

		this.mSystemProxy.deregisterForNotification(this);
		this.mListener.onActionCompleted(result.contains("OK"));
		
//		if(!result.contains("OK")) {
//			this.mSystemProxy.deregisterForNotification(this);
//			this.mListener.onActionCompleted(false);
//		} else {
//			Logger.getInstance().logDebug(
//					getClass().getSimpleName(), "EXECUTE THE GET ERROR LOG");
//			getErrorLog();
//		}
	}

	@Override
	public void onErrorLogRetreived(String errorLogs) {
//		Logger.getInstance().logDebug(
//				getClass().getSimpleName(), 
//				"onErrorLogRetreived errorLogs: " + errorLogs);
//		
//		String[] data = errorLogs.split(",");
//		int nErrors = Integer.parseInt(data[0]);
//		
//		this.mSystemProxy.deregisterForNotification(this);
//		
//		this.mListener.onActionCompleted(nErrors == 0);
	}


	// }
}
