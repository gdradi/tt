package com.technogym.equipmenttest.appbleuart.models;

import android.content.Context;

import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.appbleuart.proxies.SystemProxy;

import it.spot.android.logger.domain.Logger;

public class Action_1005 extends BaseBleUartAction {

    private final ISystemProxy mSystemProxy;
    /**
     * Base constructor for an {@link Action}.
     *
     * @param context : the application's {@link Context} within it gets executed.
     */
    protected Action_1005(Context context) {
        super(context);
        // we suppose it's already been instantiated, so no need to pass IEquipmentController
        this.mSystemProxy = SystemProxy.getInstance(null);
        this.mSystemProxy.registerForNotification(this);
    }

    public static Action_1005 create(final Context context) {
        return new Action_1005(context);
    }


    @Override
    public void execute(String data) {
        try {
            Logger.getInstance().logDebug(
                    getClass().getSimpleName(), "[Action 1005] EXECUTING ACTION 1005");
            mSystemProxy.testWakeUp();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void onTestWakeUpResponseRetrieved(String mStatus) {
        Logger.getInstance().logDebug(
                getClass().getSimpleName(), "[Action 1005] RESPONSE RETRIEVED: "+mStatus);
        this.mSystemProxy.deregisterForNotification(this);
        this.mListener.onActionUpdate(mStatus);
    }
}
