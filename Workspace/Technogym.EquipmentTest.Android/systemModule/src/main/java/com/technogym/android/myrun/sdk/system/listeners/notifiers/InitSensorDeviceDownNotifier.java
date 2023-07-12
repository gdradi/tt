package com.technogym.android.myrun.sdk.system.listeners.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

/**
 * This is the {@link Notifier} related to "sensor down" notifications.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public final class InitSensorDeviceDownNotifier extends Notifier<ISystemListener> {

	private final Integer mValue;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling the {@code super} constructor and initializing the class properties.
	 * 
	 * @param value
	 *            : the value to notify
	 * */
	protected InitSensorDeviceDownNotifier(final Integer value) {
		super();

		this.mValue = value;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param version
	 *            : the parameter required by class constructor
	 * */
	public static InitSensorDeviceDownNotifier create(final Integer value) {
		return new InitSensorDeviceDownNotifier(value);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final ISystemListener listener) {
		listener.onValueFromSensorDown(this.mValue);
	}

	// }

}
