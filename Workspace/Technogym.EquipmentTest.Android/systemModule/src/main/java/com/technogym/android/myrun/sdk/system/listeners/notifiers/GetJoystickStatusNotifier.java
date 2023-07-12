package com.technogym.android.myrun.sdk.system.listeners.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

/**
 * This is the {@link INotifier} related to "calibration gradient values vs mV" notifications.
 * */
public class GetJoystickStatusNotifier extends Notifier<ISystemListener> {

	private final String mResult;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 *
	 * @param result
	 *            : the calibration gradient values vs mV set
	 * */
	protected GetJoystickStatusNotifier(final String result) {
		super();

		this.mResult = result;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param result
	 *            : the parameter required by class constructor
	 * */
	public static GetJoystickStatusNotifier create(final String result) {
		return new GetJoystickStatusNotifier(result);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final ISystemListener listener) {
		listener.onJoystickStatusRetrieved(this.mResult);
	}

	// }

}