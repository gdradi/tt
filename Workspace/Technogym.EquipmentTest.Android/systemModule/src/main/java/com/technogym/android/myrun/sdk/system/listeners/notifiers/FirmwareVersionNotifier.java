package com.technogym.android.myrun.sdk.system.listeners.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

/**
 * This is the {@link INotifier} related to "firmware version" notifications.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public final class FirmwareVersionNotifier extends Notifier<ISystemListener> {

	private final String mVersion;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param version
	 *            : the firmware version received from the equipment
	 * */
	protected FirmwareVersionNotifier(final String version) {
		super();

		this.mVersion = version;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param version
	 *            : the parameter required by class constructor
	 * */
	public static FirmwareVersionNotifier create(final String version) {
		return new FirmwareVersionNotifier(version);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final ISystemListener listener) {
		listener.onFirmwareVersionRetrieved(this.mVersion);
	}

	// }

}
