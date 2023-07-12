package com.technogym.equipmenttest.appbleuart.models;

import android.content.Context;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.appbleuart.proxies.SystemProxy;

import it.spot.android.logger.domain.Logger;

public class Action_1004 extends BaseBleUartAction {
    private final ISystemProxy mSystemProxy;
    private static final String ExpectedResponse = "Life-Data erased";

    /**
     * Base constructor for an {@link Action}.
     *
     * @param context : the application's {@link Context} within it gets executed.
     */
    protected Action_1004(Context context) {
        super(context);
        this.mSystemProxy = SystemProxy.getInstance(null);
        this.mSystemProxy.registerForNotification(this);
    }

    public static Action_1004 create(final Context context) {
        return new Action_1004(context);
    }


    @Override
    public void execute(String data) {
        Logger.getInstance().logDebug(
                getClass().getSimpleName(), "[Action 1004] EXECUTE THE LIFE TIME RESET");
        try {
            mSystemProxy.resetLifeTime();
        } catch (WriteNotAllowedException e) {
            e.printStackTrace();
            Logger.getInstance().logError(
                    getClass().getSimpleName(),
                    "[ACTION 1004] ERROR DURING EXECUTION OF LIFE TIME RESET: " + e.getLocalizedMessage());
            this.mSystemProxy.deregisterForNotification(this);
            this.mListener.onActionCompleted(false);
        }
    }

    @Override
    public void onLifeTimeResetted(String status) {
        Logger.getInstance().logDebug(
                getClass().getSimpleName(), "[Action 1004] onLifeTimeResetted. Result: "+status);
        Logger.getInstance().logDebug(
                getClass().getSimpleName(), "[Action 1004] ExpectedResponse: " + ExpectedResponse);
        this.mSystemProxy.deregisterForNotification(this);
        this.mListener.onActionCompleted(status.toLowerCase().contains(ExpectedResponse.toLowerCase()));
    }

}
