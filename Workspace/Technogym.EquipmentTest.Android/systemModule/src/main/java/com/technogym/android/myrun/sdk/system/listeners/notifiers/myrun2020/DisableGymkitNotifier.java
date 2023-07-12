package com.technogym.android.myrun.sdk.system.listeners.notifiers.myrun2020;

import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

public class DisableGymkitNotifier extends Notifier<ISystemListener> {
    private final String mStatus;

    // { Construction

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing the class properties.
     *
     * */
    protected DisableGymkitNotifier(final String status) {
        super();
        this.mStatus = status;
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     *
     * @param status
     *            : the parameter required by class constructor
     * */
    public static DisableGymkitNotifier create(final String status) {
        return new DisableGymkitNotifier(status);
    }

    // }

    // { INotifier implementation

    @Override
    public void notify(final ISystemListener listener) {
        listener.onDisableGymkitRetrieved(this.mStatus);
    }

    // }
}
