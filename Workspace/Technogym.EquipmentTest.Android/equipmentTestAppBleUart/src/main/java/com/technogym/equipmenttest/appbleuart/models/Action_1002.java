package com.technogym.equipmenttest.appbleuart.models;

import android.content.Context;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.appbleuart.proxies.SystemProxy;
import it.spot.android.logger.domain.Logger;

public class Action_1002 extends BaseBleUartAction {

    private final ISystemProxy mSystemProxy;
    private String mMeasureUnitDescription;
    /**
     * Base constructor for an {@link Action}.
     *
     * @param context : the application's {@link Context} within it gets executed.
     */
    protected Action_1002(Context context) {
        super(context);
        // we suppose it's already been instantiated, so no need to pass IEquipmentController
        this.mSystemProxy = SystemProxy.getInstance(null);
        this.mSystemProxy.registerForNotification(this);
        this.mMeasureUnitDescription = "";
    }

    public static Action_1002 create(final Context context) {
        return new Action_1002(context);
    }


    @Override
    public void execute(String data) {
        try {
            Logger.getInstance().logDebug(
                    getClass().getSimpleName(), "[Action 1002] EXECUTE: InputData=" + data);

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
                getClass().getSimpleName(), "[Action 1002] ON MEASURE UNIT SET : " + result);

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
                getClass().getSimpleName(), "[Action 1002] ON MEASURE UNIT RETREIVED : RetreivedUnitDescription= " +
                        measureUnit + "   PreviousUnitDescription=" + this.mMeasureUnitDescription);
        this.mSystemProxy.deregisterForNotification(this);
        this.mListener.onActionCompleted(true);
    }
}
