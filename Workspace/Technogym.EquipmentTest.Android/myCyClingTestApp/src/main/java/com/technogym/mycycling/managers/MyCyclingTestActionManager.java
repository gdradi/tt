package com.technogym.mycycling.managers;

import android.content.Context;

import com.technogym.mycycling.bridges.web.IJavascriptBridge;
import com.technogym.mycycling.connection.controllers.IEquipmentController;
import com.technogym.mycycling.models.actions.Action;
import com.technogym.mycycling.models.actions.custom.Action_633;

import it.spot.android.logger.domain.Logger;

/**
 * This class initializes the "device-up" sensors:
 * <ul>
 * <li>sends the proper command</li>
 * <li>checks the response correctness</li>
 * </ul>
 * It's an automatic action.
 *
 * @author Federico Foschini
 * @version 1.0
 * */
public class MyCyclingTestActionManager extends TestActionManager {

    protected IEquipmentController mEquipController;

    // { Construction

    /**
     * The class constructor: it calls the constructor of the parent base class {@link TestActionManager} and doesn't do
     * anything specific.
     *
     * @param context
     *            : the execution context of the application
     * @param javascriptBridge
     *            : a bridge class needed to communicate with webView content
     * */
    protected MyCyclingTestActionManager(final Context context, final IJavascriptBridge javascriptBridge, final IEquipmentController equipmentController) {
        super(context, javascriptBridge);
        mEquipController = equipmentController;
    }

    /**
     * This is a static method which initializes an instance of the given class. It replaces - in a very trivial way - a
     * factory class and can implement specific behavior to check the correctness of input parameters.
     *
     * @param context
     *            : the execution context of the application
     * @param javascriptBridge
     *            : a bridge class needed to communicate with webView content
     * @param equipmentController
     *            : the controller to interact and manage the equipment
     *
     * @return an instance of {@link MyCyclingTestActionManager} if everything goes fine, {@code null} otherwise.
     * */
    public static MyCyclingTestActionManager create(final Context context, final IJavascriptBridge javascriptBridge, final IEquipmentController equipmentController) {
        return new MyCyclingTestActionManager(context, javascriptBridge, equipmentController);
    }

    // }

    // { ITestActionManager abstract methods implementation

    @Override
    protected Action initializeAction(final int id) {
        switch (id) {
            case 633:
                return Action_633.create(this.mContext, mEquipController);
        }
        return null;
    }

    @Override
    public void execute(final int id, final String data) {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Executing action " + id);
        super.execute(id, data);
    }

    // }
}
