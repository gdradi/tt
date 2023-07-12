package com.technogym.android.myrun.sdk.system.listeners.notifiers.bleUart;

import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

public class DetectBluetoothNotifier extends Notifier<ISystemListener> {
    private final String mStatus;

    // { Construction

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing the class properties.
     *
     * @param status
     *            : the down sensor status retreived from the equipment
     * */
    protected DetectBluetoothNotifier(final String status) {
        super();

        this.mStatus = status;
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     *
     * @param status
     *            : the parameter required by class constructor
     * */
    public static DetectBluetoothNotifier create(final String status) {
        return new DetectBluetoothNotifier(status);
    }

    // }

    // { INotifier implementation

    @Override
    public void notify(final ISystemListener listener) {
        listener.onBluetoothRetrieved(this.mStatus);
    }

    // }
}
