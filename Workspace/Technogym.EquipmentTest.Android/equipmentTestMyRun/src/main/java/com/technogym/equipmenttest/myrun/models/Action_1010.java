package com.technogym.equipmenttest.myrun.models;

import android.content.Context;

import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.myrun.proxies.SystemProxy;

import java.util.UUID;

import it.spot.android.logger.domain.Logger;

public class Action_1010 extends BaseMyRunAction {

    private final ISystemProxy mSystemProxy;
    private boolean positiveResultObtained = false;
    private String guid;



    // { Construction

    protected Action_1010(final Context context) {
        super(context);
        // we suppose it's already been instantiated, so no need to pass IEquipmentController
        this.mSystemProxy = SystemProxy.getInstance(null);
        mSystemProxy.registerForNotification(this);
        guid = UUID.randomUUID().toString();
    }

    public static Action_1010 create(final Context context) {
        return new Action_1010(context);
    }

    // }

    // { Action abstract method implementation

    @Override
    public void execute(String data) {
        Logger.getInstance().logDebug(
                getClass().getSimpleName(), "["+guid+"][Action_1010] execute");
        executeCommand("NFC");
    }

    @Override
    public boolean executeCommand(final String commandID) {
        Logger.getInstance().logDebug(
                getClass().getSimpleName(), "["+guid+"][Action_1010] executeCommand "+commandID);
        if(commandID.equals("NFC")){
            try {
                Logger.getInstance().logDebug(
                        getClass().getSimpleName(), "["+guid+"][Action_1010] Sending NFC Command");
                this.mSystemProxy.VerifyNFC();
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
    public void onNFCRetrieved(String response) {

        Logger.getInstance().logDebug(
                getClass().getSimpleName(), "["+guid+"][Action_1010] onNFCRetrieved: response = "+response);
        boolean result = response.contains("OK");
        if(result){
            Logger.getInstance().logDebug(
                    getClass().getSimpleName(), "["+guid+"][Action_1010] Positive result yet obtained: "+positiveResultObtained);
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
