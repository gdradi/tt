package com.technogym.equipmenttest.appbleuart.models;

import android.content.Context;
import com.technogym.android.myrun.sdk.system.commands.EnableAllSegments;
import com.technogym.android.myrun.sdk.system.commands.EnableDefaultSegments;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.appbleuart.proxies.SystemProxy;
import it.spot.android.logger.domain.Logger;

public class Action_1016 extends BaseBleUartAction {
    private final ISystemProxy mSystemProxy;

    // { Construction

    protected Action_1016(final Context context) {
        super(context);
        this.mSystemProxy = SystemProxy.getInstance(null);
    }

    public static Action_1016 create(final Context context) {
        return new Action_1016(context);
    }

    // }

    // { Action abstract method implementation

    @Override
    public void execute(String data) {
        try {
            Logger.getInstance().logDebug(getClass().getSimpleName(), "[Action 1016] EXECUTE: InputData=" + data);

            if ("ENABLE_ALL".equals(data)) {
                Logger.getInstance().logDebug(getClass().getSimpleName(), "[Action 1016] Sending ENABLE_ALL");
                this.mSystemProxy.sendCommand(EnableAllSegments.create()); // Comando SDISP_FULL_ON
            }

            else if ("ENABLE_DEFAULT".equals(data)) {
                Logger.getInstance().logDebug(getClass().getSimpleName(), "[Action 1016] Sending ENABLE_DEFAULT");
                this.mSystemProxy.sendCommand(EnableDefaultSegments.create()); // Comando SDISP_NORMAL
            }

            else {
                Logger.getInstance().logDebug(getClass().getSimpleName(), "[Action 1016] INVALID COMMAND: "+data);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // }

}
