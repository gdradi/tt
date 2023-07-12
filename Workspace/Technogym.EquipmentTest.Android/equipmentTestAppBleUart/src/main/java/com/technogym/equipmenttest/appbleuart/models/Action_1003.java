package com.technogym.equipmenttest.appbleuart.models;

import android.content.Context;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.appbleuart.proxies.SystemProxy;

import it.spot.android.logger.domain.Logger;

public class Action_1003 extends BaseBleUartAction {
    private final ISystemProxy mSystemProxy;

    /**
     * Base constructor for an {@link Action}.
     *
     * @param context : the application's {@link Context} within it gets executed.
     */
    protected Action_1003(Context context) {
        super(context);
        this.mSystemProxy = SystemProxy.getInstance(null);
        this.mSystemProxy.registerForNotification(this);
    }

    public static Action_1003 create(final Context context) {
        return new Action_1003(context);
    }


    @Override
    public void execute(String data) {
        Logger.getInstance().logDebug(
                getClass().getSimpleName(), "[Action 1003] EXECUTE THE RESET ERROR LOG");
        try {
            mSystemProxy.resetErrorLog();
        } catch (WriteNotAllowedException e) {
            e.printStackTrace();
            Logger.getInstance().logError(
                    getClass().getSimpleName(),
                    "[ACTION 1003] ERROR DURING EXECUTION OF RESET ERROR LOG: " + e.getLocalizedMessage());
            this.mSystemProxy.deregisterForNotification(this);
            this.mListener.onActionCompleted(false);
        }
    }


    @Override
    public void onResetErrorLog(String result) {
        Logger.getInstance().logDebug(
                getClass().getSimpleName(),
                "[Action 1003] onResetErrorLog result: " + result);

        this.mSystemProxy.deregisterForNotification(this);
        this.mListener.onActionCompleted(result.contains("OK"));
    }
}
