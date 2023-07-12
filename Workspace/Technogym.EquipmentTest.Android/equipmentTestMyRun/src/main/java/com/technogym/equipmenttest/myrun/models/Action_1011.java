package com.technogym.equipmenttest.myrun.models;

import android.content.Context;

import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.myrun.proxies.SystemProxy;

import java.util.UUID;

import it.spot.android.logger.domain.Logger;

public class Action_1011 extends BaseMyRunAction {

    private boolean positiveResultObtained = false;
    private String guid;
    private final ISystemProxy mSystemProxy;

    // { Construction

    protected Action_1011(final Context context) {
        super(context);
        // we suppose it's already been instantiated, so no need to pass IEquipmentController
        this.mSystemProxy = SystemProxy.getInstance(null);
        mSystemProxy.registerForNotification(this);
        guid = UUID.randomUUID().toString();
    }

    public static Action_1011 create(final Context context) {
        return new Action_1011(context);
    }

    // }

    // { Action abstract method implementation

    @Override
    public void execute(String data) {
        Logger.getInstance().logDebug(
                getClass().getSimpleName(), "["+guid+"][Action_1011] execute");
        executeCommand("BT");
    }

    @Override
    public boolean executeCommand(final String commandID) {
        Logger.getInstance().logDebug(
                getClass().getSimpleName(), "["+guid+"][Action_1011] executeCommand "+commandID);
        if(commandID.equals("BT")){
            try {
                Logger.getInstance().logDebug(
                        getClass().getSimpleName(), "["+guid+"][Action_1011] Sending BT Command");
                this.mSystemProxy.DetectBluetooth();
            } catch (Exception ex) {
                ex.printStackTrace();
                this.mListener.onActionCompleted(false);
            }
        }
        return true;
    }

    // }

    // { ISystemListener implementation

    @Override
    public void onBluetoothRetrieved(String response) {
        Logger.getInstance().logDebug(
                getClass().getSimpleName(), "["+guid+"][Action_1011] onBluetoothRetrieved: response = "+response);
        boolean result = response.contains("OK");
        if(result){
            Logger.getInstance().logDebug(
                    getClass().getSimpleName(), "["+guid+"][Action_1011] Positive result yet obtained: "+positiveResultObtained);
            if(!positiveResultObtained){
                positiveResultObtained = true;
                this.mSystemProxy.deregisterForNotification(this);
                Logger.getInstance().logDebug(
                        getClass().getSimpleName(), "["+guid+"][Action_1010] calling onActionUpdate(true)");
                this.mListener.onActionUpdate("true"+";"+guid);
            }
        }

    }

    @Override
    public void stop() {
        this.mSystemProxy.deregisterForNotification(this);
        super.stop();
    }

}
