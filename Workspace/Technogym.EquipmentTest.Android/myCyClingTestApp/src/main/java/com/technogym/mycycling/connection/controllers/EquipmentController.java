package com.technogym.mycycling.connection.controllers;

import com.technogym.mycycling.connection.listeners.EquipmentConnectionListener;
import com.technogym.mycycling.connection.models.EquipmentConnectionState;

import java.util.ArrayList;
import java.util.List;

public abstract class EquipmentController implements IEquipmentController {

    protected final List<EquipmentConnectionListener> mConnectionListeners;
    protected EquipmentConnectionState mState;

    // { Construction

    protected EquipmentController(final EquipmentConnectionState initialState) {
        super();

        this.mConnectionListeners = new ArrayList<EquipmentConnectionListener>();
        this.mState = initialState;
    }

    // }

    // { IEquipmentController implementation

    @Override
    public void registerEquipmentConnectionListener(final EquipmentConnectionListener listener) {
        if (!this.mConnectionListeners.contains(listener)) {
            this.mConnectionListeners.add(listener);
        }
    }

    @Override
    public void unregisterEquipmentConnectionListener(final EquipmentConnectionListener listener) {
        if (this.mConnectionListeners.contains(listener)) {
            this.mConnectionListeners.remove(listener);
        }
    }

    @Override
    public EquipmentConnectionState getConnectionState() {
        return this.mState;
    }

    @Override
    public void setConnectionState(final EquipmentConnectionState state) {
        this.mState = state;
    }

    @Override
    public List<EquipmentConnectionListener> getRegisteredListeners() {
        return this.mConnectionListeners;
    }

    // }
}
