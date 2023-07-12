package com.technogym.equipmenttest.appbleuart.models;

import android.content.Context;
import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.appbleuart.proxies.SystemProxy;
import it.spot.android.logger.domain.Logger;

/**
 * This class makes the equipment go in standby.
 * It sends the command to standby the Technogym Cycle equipment. A warning message is needed withing the UI.<br/>
 * It's an automatic action.
 *
 * */

public class Action_1015 extends BaseBleUartAction {
    private static final String FORCE_SUSPENSION_EQUIPMENT = "force_suspension";
    private static final String ExpectedResponse = "SUSPENDING";

    private final ISystemProxy mSystemProxy;

    // { Construction

    protected Action_1015(final Context context) {
        super(context);
        // we suppose it's already been instantiated, so no need to pass IEquipmentController
        this.mSystemProxy = SystemProxy.getInstance(null);
        mSystemProxy.registerForNotification(this);
    }

    @Override
    public void execute(String data) {

    }

    public static Action_1015 create(final Context context) {
        return new Action_1015(context);
    }

    // }

    // { Action abstract method implementation

    @Override
    public boolean executeCommand(final String commandID) {

        if (commandID.equals(FORCE_SUSPENSION_EQUIPMENT)) {
            try {

                //Logger.getInstance().logDebug(getClass().getSimpleName(), "executeCommand: DUMP IO SENSORS");
                //Logger.getInstance().logDebug(getClass().getSimpleName(), "Get status to check sensors");
                //mSystemProxy.forceSuspension();

                mSystemProxy.registerForNotification(this);

                Logger.getInstance().logDebug(getClass().getSimpleName(), "[Action 1015] - executeCommand: "+commandID);
                mSystemProxy.forceSuspension();

                // NON SERVE CHIEDERE LO STATO. SI MANDA DIRETTAMENTE IL COMANDO FORCE SUSPENSION
                //mSystemProxy.getStatus();


                //mSystemProxy.dumpIOSensorsValues();
                //this.mListener.onActionCompleted(true);

            } catch (WriteNotAllowedException ex) {
                Logger.getInstance().logDebug(getClass().getSimpleName(),
                        "[Action 1015] BT Error executing command to get the status of the equipment");

                ex.printStackTrace();
                this.mListener.onActionCompleted(false);
            }
        } else {
            return super.executeCommand(commandID);
        }
        return true;
    }

    @Override
    public void stop() {
        mSystemProxy.deregisterForNotification(this);
        super.stop();
    }

    // }

    // { ISystemListener implementation
    @Override
    public void onStatusRetreived(String status) {

        Logger.getInstance().logDebug(getClass().getSimpleName(),
                "[Action 1015] onStatusRetreived: [" + status + "]");


        // QUESTO METODO NON VIENE PIU' CHIAMATO PERCHE' NON VIENE MANDATO IL COMANDO STATUS

       /*
        // prelievo dello status della macchina
        String[] statusVals = status.split(",");
        String qsstatusString = statusVals[12]; // Per MyRun era statusVals[4]

        Logger.getInstance().logDebug(getClass().getSimpleName(),
                "[Action 1015] onStatusRetreived - QSStatus: [" + qsstatusString + "]");*/

        /*
         **********************************************************
         * If the current QS Status is == [QSWaitForStartRC = 2]
         * The force_suspension command can be executed
         * and the action will be completed successfully
         *
         * If the current QS Status is != [QSWaitForStartRC = 2]
         * The force_suspension command can't be executed
         * and the action will be completed unsuccessfully
         **********************************************************
         */

       /* boolean result = qsstatusString.toLowerCase().contains("qswaitforstart");

        if(result){
            // send the force_suspension command
            try {
                Logger.getInstance().logDebug(getClass().getSimpleName(),
                        "execute the command [" + FORCE_SUSPENSION_EQUIPMENT + "]");
                mSystemProxy.forceSuspension();

            } catch (WriteNotAllowedException ex) {
                ex.printStackTrace();
                mListener.onActionCompleted(false);
            }
        }else{
            mListener.onActionCompleted(false);
        }*/
    }

    @Override
    public void onSuspendCommandResponseRetrieved (String status){
        Logger.getInstance().logDebug(getClass().getSimpleName(),
                "[Action 1015] onSuspendCommandResponseRetrieved: [" + status + "]");

        boolean result = status.toLowerCase().contains(ExpectedResponse.toLowerCase());
        mSystemProxy.deregisterForNotification(this);

        Logger.getInstance().logDebug(getClass().getSimpleName(),
                "[Action 1015] onSuspendCommandResponseRetrieved: response is valid: " + result);

        mListener.onActionCompleted(result);
    }
}
