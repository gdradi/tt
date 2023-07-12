package com.technogym.android.myrun.sdk.system.listeners.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

/**
 * This is the {@link INotifier} related to "wakeup sensor value" notifications.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class WakeupSensorValueNotifier extends Notifier<ISystemListener> {

	private final Integer mWakeupSensorValue;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param wakeupSensorValue
	 *            : the value of the wakeup sensor
	 * */
	protected WakeupSensorValueNotifier(final Integer wakeupSensorValue) {
		super();

		this.mWakeupSensorValue = wakeupSensorValue;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param result
	 *            : the parameter required by class constructor
	 * */
	public static WakeupSensorValueNotifier create(final Integer wakeupSensorValue) {
		return new WakeupSensorValueNotifier(wakeupSensorValue);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final ISystemListener listener) {
		listener.onWakeupSensorRetrieved(mWakeupSensorValue);
	}

	// }

}
