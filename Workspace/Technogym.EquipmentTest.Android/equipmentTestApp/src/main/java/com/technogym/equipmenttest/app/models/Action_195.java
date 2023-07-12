package com.technogym.equipmenttest.app.models;

import android.content.Context;

import com.technogym.android.myrun.sdk.system.commands.EnableAllSegments;
import com.technogym.android.myrun.sdk.system.commands.EnableDefaultSegments;
import com.technogym.android.myrun.sdk.system.commands.GetCalibrationGradientValueMVCommand;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.android.myrun.sdk.system.proxies.SystemProxy;
import com.technogym.equipmenttest.library.models.Action;

import it.spot.android.logger.domain.Logger;

public class Action_195 extends BaseAction {

    private final ISystemProxy mSystemProxy;

	// { Construction

	protected Action_195(final Context context) {
		super(context);
        this.mSystemProxy = SystemProxy.getInstance(null);
	}

	public static Action_195 create(final Context context) {
		return new Action_195(context);
	}

	// }

	// { Action abstract method implementation

	@Override
	public void execute(String data) {
		try {
			Logger.getInstance().logDebug(getClass().getSimpleName(), "EXECUTE: InputData=" + data);
			
			if ("ENABLE_ALL".equals(data)) {
                Logger.getInstance().logDebug(getClass().getSimpleName(), "Sending ENABLE_ALL");
                this.mSystemProxy.sendCommand(EnableAllSegments.create());
            }

            else if ("ENABLE_DEFAULT".equals(data)) {
                Logger.getInstance().logDebug(getClass().getSimpleName(), "Sending ENABLE_DEFAULT");
                this.mSystemProxy.sendCommand(EnableDefaultSegments.create());
            }

            else {
                Logger.getInstance().logDebug(getClass().getSimpleName(), "INVALID COMMAND: "+data);
            }

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// }

}
