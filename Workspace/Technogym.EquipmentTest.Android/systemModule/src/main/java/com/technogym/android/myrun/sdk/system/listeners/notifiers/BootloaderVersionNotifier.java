package com.technogym.android.myrun.sdk.system.listeners.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

/**
 * This is the {@link INotifier} related to "firmware bootloader version" notifications.
 *
 * @author Federico Foschini
 * @version 1.0
 * */
public class BootloaderVersionNotifier extends Notifier<ISystemListener> {

    private final String mVersion;

    // { Construction

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing the class properties.
     *
     * @param version
     *            : the firmware's bootloader version received from the equipment
     * */
    protected BootloaderVersionNotifier(final String version) {
        super();

        this.mVersion = version;
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     *
     * @param version
     *            : the parameter required by class constructor
     * */
    public static BootloaderVersionNotifier create(final String version) {
        return new BootloaderVersionNotifier(version);
    }

    // }

    // { INotifier implementation

    @Override
    public void notify(final ISystemListener listener) {
        listener.onFirmwareBootloaderVersionRetrieved(this.mVersion);
    }

    // }

}
