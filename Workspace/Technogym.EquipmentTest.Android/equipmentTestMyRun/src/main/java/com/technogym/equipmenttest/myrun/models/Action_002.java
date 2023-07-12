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
public class Action_002 extends BaseMyRunAction {

	private final ISystemProxy mSystemProxy;
	//private String mMeasureUnit;
	private String mMeasureUnitDescription;
	//private boolean serialAsParam;
	
	// { Construction

	protected Action_002(final Context context) {
		super(context);
		// we suppose it's already been instantiated, so no need to pass IEquipmentController
		this.mSystemProxy = SystemProxy.getInstance(null);
		//this.mMeasureUnit = "";
		this.mMeasureUnitDescription = "";
		//serialAsParam = false;
		mSystemProxy.registerForNotification(this);
	}

	public static Action_002 create(final Context context) {
		return new Action_002(context);
	}

	// }

	// { Action abstract method implementation

	@Override
	public void execute(String data) {
		try {
			Logger.getInstance().logDebug(
					getClass().getSimpleName(), "EXECUTE: InputData=" + data);
			
			int value = -1;
			value = Integer.parseInt("" + ((data.toCharArray())[5]));
			//serialAsParam = true;

			//this.mMeasureUnit = (value == 1 ? "simpunits" : "ssiunits");
			this.mMeasureUnitDescription = (value == 1 ? "IMPERIAL UNITS" : "SI UNITS");
			
			this.mSystemProxy.setMeasureUnit(value);
			
//			if(!data.equals("") && data != null){
//				//final JSONArray params = new JSONArray(data);
//				//String serial = params.getString(0);
//				
//				value = Integer.parseInt("" + ((data.toCharArray())[5]));
//				serialAsParam = true;
//
//				this.mMeasureUnit = (value == 1 ? "simpunits" : "ssiunits");
//				this.mMeasureUnitDescription = (value == 1 ? "IMPERIAL UNITS" : "SI UNITS");
//				
//				this.mSystemProxy.setMeasureUnit(value);
//				this.mListener.onActionCompleted(true);
//			} else {
//				//this.mSystemProxy.askForSerialNumber();
//			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onMeasureUnitSet(Boolean result) {
		Logger.getInstance().logDebug(
				getClass().getSimpleName(), "ON MEASURE UNIT SET : " + result);

		this.mListener.onActionCompleted(true);
//		this.mSystemProxy.deregisterForNotification(this);
//		try {
//			if(result){
//				this.mSystemProxy.askForMeasureUnit();
//			} else {
//				this.mListener.onActionCompleted(false);
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
	}

	@Override
	public void onMeasureUnitRetreived(String measureUnit) {
		Logger.getInstance().logDebug(
				getClass().getSimpleName(), "ON MEASURE UNIT RETREIVED : RetreivedUnitDescription= " +
							measureUnit + "   PreviousUnitDescription=" + this.mMeasureUnitDescription);
		this.mSystemProxy.deregisterForNotification(this);
		this.mListener.onActionCompleted(true);
	}


}
