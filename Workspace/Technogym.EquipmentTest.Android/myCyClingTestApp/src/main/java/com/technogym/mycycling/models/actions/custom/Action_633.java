package com.technogym.mycycling.models.actions.custom;

import android.content.Context;
import android.util.Log;

import com.technogym.mycycling.commands.commands.DumpStateCommand;
import com.technogym.mycycling.commands.commands.SetRestPositionCommand;
import com.technogym.mycycling.commands.factories.CommandFactory;
import com.technogym.mycycling.commands.models.ICommandReply;
import com.technogym.mycycling.commands.replies.DumpStateReply;
import com.technogym.mycycling.commands.replies.SetRestPositionReply;
import com.technogym.mycycling.connection.controllers.IEquipmentController;
import com.technogym.mycycling.connection.listeners.EquipmentConnectionListener;
import com.technogym.mycycling.connection.models.EquipmentConnectionState;
import com.technogym.mycycling.exceptions.ConnectionException;
import com.technogym.mycycling.models.actions.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import it.spot.android.logger.domain.Logger;

/**
 * This class has to check and verify the rest position of the equipment.
 *
 * @author Federico Foschini
 * @version 2.0
 * */
public class Action_633 extends Action implements EquipmentConnectionListener {

    // { Internal fields

    protected int actionExecutionPhase;
    protected IEquipmentController mEquipController;

    // THETAMOT RANGE VALUES
    protected static final String THETAMOT_KEY = "THETAMOT";

    // THETAMOT for Upper Range = 950
    protected static final int THETAMOT_UPPER = 950;
    protected static final int THETAMOT_UPPER_MIN = 947;
    protected static final int THETAMOT_UPPER_MAX = 953;

    // THETAMOT for Lower Range = 400
    protected static final int THETAMOT_LOWER = 400;
    protected static final int THETAMOT_LOWER_MIN = 397;
    protected static final int THETAMOT_LOWER_MAX = 403;

    // }

    // { Construction

    /**
     * Base constructor for an {@link Action}.
     *
     * @param context : the application's {@link Context} within it gets executed.
     */
    protected Action_633(Context context, IEquipmentController equipmentController) {
        super(context);
        mEquipController = equipmentController;
        mEquipController.registerEquipmentConnectionListener(this);
    }

    public static Action_633 create(final Context context, final IEquipmentController equipmentController) {
        return new Action_633(context, equipmentController);
    }

    // }

    // { EquipmentConnectionListener implementation

    @Override
    public void onValueReceived(ICommandReply reply) {
        switch (reply.getName()) {
            case SetRestPositionReply.CMD_NAME:
                sendDumpState();
                break;

            case DumpStateReply.CMD_NAME:
                /*
                 * Al DUMPLK command corrispondono due scenari:
                 * - Se action phase = 0
                 *   l'azione ha completato la prima fase (SCALRESTPOS -> 950) e si deve procedere alla successiva (SCALRESTPOS -> 400)
                 *
                 * - Se action phase = 1
                 *   l'azione ha completato la seconda fase e viene segnalato il completamento con successo
                 *
                 * Gli scenari sopra descritti sono validi in caso in cui il valore del thetamot è nel range determinato
                 * Se non è in tale range, viene reinviato il comando e si attende per un secondo
                 */
                Logger.getInstance().logError(this.getClass().getSimpleName(), "Reply from command: " + reply.getName());
                Log.i(this.getClass().getSimpleName(), "Reply from command: " + reply.getName());
                if(reply instanceof DumpStateReply) {
                    DumpStateReply dumpStateReply = (DumpStateReply)reply;
                    HashMap<String, String> dumpVals = dumpStateReply.getDumpReplyValues();
                    String dumValTarget = dumpVals.get(THETAMOT_KEY);
                    int dotIndex = dumValTarget.indexOf('.');
                    int thetamot = Integer.parseInt(dumValTarget.substring(0, dotIndex));

                    Log.i(this.getClass().getSimpleName(), "thetamot: " + thetamot);
                    Log.i(this.getClass().getSimpleName(), "action execution phase: " + actionExecutionPhase);

                    if((thetamot >= THETAMOT_UPPER_MIN && thetamot <= THETAMOT_UPPER_MAX && actionExecutionPhase == 0) ||
                            thetamot >= THETAMOT_LOWER_MIN && thetamot <= THETAMOT_LOWER_MAX && actionExecutionPhase == 1) {
                        actionExecutionPhase = actionExecutionPhase + 1;
                        if(actionExecutionPhase > 1) {
                            mEquipController.unregisterEquipmentConnectionListener(this);
                            mListener.onActionCompleted(true);
                            return;
                        } else {
                            sendSetRestPos(THETAMOT_LOWER);
                            Logger.getInstance().logError(this.getClass().getSimpleName(), "Sending set rest pos command - Lower Thetamot");
                        }

                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            Log.e(this.getClass().getSimpleName(), "Waiting sleep error: " + e.getMessage());
                        }
                        Logger.getInstance().logError(this.getClass().getSimpleName(), "Sending dump state command");
                        sendDumpState();
                    }
                }
                break;
        }
    }

    @Override
    public void onTargetDeviceFound(String data) { }

    @Override
    public void onConnectionStateChanged(EquipmentConnectionState state) { }

    @Override
    public void onUpgradeDone(Boolean result) { }

    // }

    // { Action abstract method implementation

    @Override
    public void execute(String data) {
        /**
         * If the equipment is not connected, an error will be notified
         */
        if(!mEquipController.isBleConnected()) {
            mListener.onActionCompleted(false);
            return;
        }

        // 1 - Send SCALRESTPOS with value 950
        actionExecutionPhase = 0;
        sendSetRestPos(THETAMOT_UPPER);
        //mEquipController.unregisterEquipmentConnectionListener(this);
    }

    // }

    // { Internal method

    /*
     * Sends the command to require the dump state
     */
    protected void sendDumpState() {
        try {
            mEquipController.sendCommand(CommandFactory.create(DumpStateCommand.CMD_NAME, new ArrayList<String>()));
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getInstance().logError(this.getClass().getSimpleName(), "sendDumpState error: " + e.getMessage());
            mEquipController.unregisterEquipmentConnectionListener(this);
            mListener.onActionCompleted(false);
        }
    }

    /*
     * Sends the command to set the rest position
     */
    protected void sendSetRestPos(int restPosValue) {
        List<String> params = new ArrayList<>();
        params.add("" + restPosValue);
        try {
            mEquipController.sendCommand(CommandFactory.create(SetRestPositionCommand.CMD_NAME, params));
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getInstance().logError(this.getClass().getSimpleName(), "SetRestPositionCommand error: " + e.getMessage());
            mEquipController.unregisterEquipmentConnectionListener(this);
            mListener.onActionCompleted(false);
        }
    }

    // }
}
