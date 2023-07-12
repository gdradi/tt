package com.technogym.equipmenttest.myrun.models;

import android.content.Context;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.core.listeners.ICoreListener;
import com.technogym.android.myrun.sdk.system.commands.myrun2020.MyRunStartCalibrationCommand;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.myrun.proxies.SystemProxy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.spot.android.logger.domain.Logger;

public class Action_245 extends BaseMyRunAction {

    private static final String TAG = Action_245.class.getSimpleName();
    private final ISystemProxy mSystemProxy;


    /**
     * Base constructor for an {@link Action}.
     *
     * @param context : the application's {@link Context} within it gets executed.
     */
    protected Action_245(Context context) {
        super(context);
        mSystemProxy = SystemProxy.getInstance(null);
    }

    public static Action_245 create(final Context context) {
        return new Action_245(context);
    }



    @Override
    public void execute(String data) {
        Logger.getInstance().logDebug(TAG, "Starting action");
        mSystemProxy.registerForNotification(this);
        try {
            mSystemProxy.sendCommand(MyRunStartCalibrationCommand.create());
        } catch (WriteNotAllowedException e) {
            e.printStackTrace();
            mListener.onActionUpdate("Errore durante invio di comando start calibrazione: "+e.getMessage());
        }
    }

    @Override
    public boolean executeCommand(final String commandID) {
        // Non intercetto il command id perchè in questo modo va lanciato sempre quello del sensore induttivo
        try {
            mSystemProxy.getMyRunSensoreInduttivoStatus();
            return true;
        } catch (WriteNotAllowedException e) {
            return false;
        }
    }

    @Override
    public void onSensoreInduttivoRetrieved(String status) {
        JSONObject update = new JSONObject();
        try {
            update.put("type", "SENSORE_INDUTTIVO_RESPONSE");
            update.put("value", status);
            mListener.onActionUpdate(update.toString());
        } catch (JSONException e) {
            mListener.onActionUpdate("Error: "+e.getMessage());
        }
    }

    @Override
    public void onStartCalibrationResponse(String status) {
        JSONObject update = new JSONObject();
        try {
            update.put("type", "START_CALIBRATION_RESPONSE");
            update.put("value", status
                    .replace("ISCAL:", "")
                    .replace("\n", " ")
                    .replace("\r", " ")
            );
            mListener.onActionUpdate(update.toString());
        } catch (JSONException e) {
            mListener.onActionUpdate("Error: "+e.getMessage());
        }
    }
}

