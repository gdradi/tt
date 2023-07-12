package com.technogym.equipmenttest.appbleuart.models;

import android.content.Context;
import com.technogym.android.myrun.sdk.system.commands.bleUart.ResistanceLevelCommand;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.appbleuart.proxies.SystemProxy;
import it.spot.android.logger.domain.Logger;

public class Action_1007 extends BaseBleUartAction {
    private final ISystemProxy mSystemProxy;

    /**
     * Base constructor for an {@link Action}.
     *
     * @param context : the application's {@link Context} within it gets executed.
     */
    protected Action_1007(Context context) {
        super(context);
        this.mSystemProxy = SystemProxy.getInstance(null);
    }

    public static Action_1007 create(final Context context) {
        return new Action_1007(context);
    }


    @Override
    public void execute(String resistanceLevel) {
        try {
            Logger.getInstance().logDebug(getClass().getSimpleName(), "[Action 1007]  Sending SET_RESISTANCE_LEVEL " + resistanceLevel);
            this.mSystemProxy.sendCommand(ResistanceLevelCommand.create(resistanceLevel)); // Comando SET_RESISTANCE_LEVEL
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }





}


