package com.technogym.equipmenttest.myrun.models;

import android.content.Context;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.myrun.proxies.SystemProxy;

import java.util.UUID;

import it.spot.android.logger.domain.Logger;

public class Action_1021 extends BaseMyRunAction {

    private static final String TAG = Action_003.class.getSimpleName();
    private final ISystemProxy mSystemProxy;
    private final String ExpectedResponse = "GYMKIT DISABLED";
    private boolean responseObtained = false;
    private String guid;


    protected Action_1021(final Context context) {
        super(context);
        Logger.getInstance().logDebug(TAG, "Constructor");
        this.mSystemProxy = SystemProxy.getInstance(null);
        mSystemProxy.registerForNotification(this);
        guid = UUID.randomUUID().toString();
    }

    public static Action_1021 create(final Context context) {
        return new Action_1021(context);
    }



    @Override
    public void execute(String data) {
        Logger.getInstance().logDebug(TAG, "["+guid+"][Action_1021] Executing action");
        executeCommand("DISABLEGYMKIT");
    }

     @Override
     public boolean executeCommand(String commandID){
         Logger.getInstance().logDebug(TAG, "["+guid+"][Action_1021] executeCommand "+commandID);
         if(commandID.equals("DISABLEGYMKIT")){
             try {
                 this.mSystemProxy.disableGymkit();
             } catch (Exception ex) {
                 Logger.getInstance().logDebug(TAG, "["+guid+"][Action_1021] Exception while sending command");
                 ex.printStackTrace();
                 this.mListener.onActionUpdate(false+ ";" + guid);
             }
         }
         return true;
     }

    @Override
    public void onDisableGymkitRetrieved(String status) {
        Logger.getInstance().logDebug(TAG, "["+guid+"][Action_1021] onDisableGymkitRetrieved. Response: "+status);
        if(!responseObtained){
            responseObtained = true;
            boolean result = status.toLowerCase().contains(ExpectedResponse.toLowerCase());
            Logger.getInstance().logDebug(TAG, "["+guid+"][Action_1021] Response is valid: "+result);
            this.mListener.onActionUpdate(result + ";" + guid);
            mSystemProxy.deregisterForNotification(Action_1021.this);
        }else{
            Logger.getInstance().logDebug(TAG, "["+guid+"][Action_1021] Response was yet obtained");
        }
    }

}
