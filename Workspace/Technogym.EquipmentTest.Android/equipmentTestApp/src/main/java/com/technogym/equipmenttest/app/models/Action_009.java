package com.technogym.equipmenttest.app.models;

import org.json.JSONArray;

import android.content.Context;

import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.android.myrun.sdk.system.proxies.SystemProxy;
import com.technogym.equipmenttest.library.models.Action;

/**
 * This class has to configure on the equipment the user presence detection. Generally, it's called twice during a test
 * with two different parameters. It's an automatic action.
 * 
 * @author Federico Foschini
 * @version 2.0
 * */
public class Action_009 extends BaseAction {

	private final ISystemProxy mSystemProxy;

	// { Construction

	protected Action_009(final Context context) {
		super(context);
		// we suppose it's already been instantiated, so no need to pass IEquipmentController
		this.mSystemProxy = SystemProxy.getInstance(null);
	}

	public static Action_009 create(final Context context) {
		return new Action_009(context);
	}

	// }

	// { Action abstract method implementation

	@Override
	public void execute(final String data) {
		boolean result = false;

		try {
			final JSONArray params = new JSONArray(data);
			final int value = params.getInt(0);
			this.mSystemProxy.setUserDetection(value);
			result = true;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		this.mListener.onActionCompleted(result);
	}

	// }

}
