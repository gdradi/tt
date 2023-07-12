package com.technogym.equipmenttest.appbleuart.models;

import android.content.Context;

import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.appbleuart.proxies.SystemProxy;
import com.technogym.equipmenttest.library.models.Action;

import it.spot.android.logger.domain.Logger;

public class Action_594 extends BaseBleUartAction {

    private final ISystemProxy mSystemProxy;

    /**
     * Base constructor for an {@link Action}.
     *
     * @param context : the application's {@link Context} within it gets executed.
     */
    protected Action_594(Context context) {
        super(context);
        // we suppose it's already been instantiated, so no need to pass IEquipmentController
        this.mSystemProxy = SystemProxy.getInstance(null);
    }

    public static Action_594 create(final Context context) {
        return new Action_594(context);
    }


    @Override
    public void execute(String data) {
        this.mSystemProxy.registerForNotification(this);
    }


    @Override
    public void onGetCardioHRValueResponse(String response) {
        Logger.getInstance().logDebug(
                getClass().getSimpleName(), "[Action 594] onGetCardioHRValueResponse " + response);
        this.mListener.onActionUpdate(response);
    }

    @Override
    public void onStartCardioReceiverResponse(String response) {
        Logger.getInstance().logDebug(
                getClass().getSimpleName(), "[Action 594] onStartCardioReceiverResponse " + response);
        this.mListener.onActionUpdate(response);
    }

    @Override
    public void onStopCardioReceiverResponse(String response) {
        Logger.getInstance().logDebug(
                getClass().getSimpleName(), "[Action 594] onStopCardioReceiverResponse " + response);
        this.mSystemProxy.deregisterForNotification(this);
        this.mListener.onActionUpdate(response);
    }

    @Override
    public void stop() {
        super.stop();
        this.mSystemProxy.deregisterForNotification(this);
    }
}
