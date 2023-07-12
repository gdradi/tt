package com.technogym.android.myrun.sdk.system.listeners.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

/**
 * This is the {@link INotifier} related to "serial number" notifications.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class GetSerialNumberNotifier extends Notifier<ISystemListener> {

	private final String mSerialNumber;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param serial
	 *            : the serial number received from the equipment
	 * */
	protected GetSerialNumberNotifier(final String serial) {
		super();

		this.mSerialNumber = serial;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param serial
	 *            : the parameter required by class constructor
	 * */
	public static GetSerialNumberNotifier create(final String serial) {
		return new GetSerialNumberNotifier(serial);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final ISystemListener listener) {
		listener.onSerialNumberRetrieved(this.mSerialNumber);
	}

	// }

}
