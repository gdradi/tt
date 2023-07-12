package com.technogym.android.myrun.sdk.system.listeners.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

/**
 * This is the {@link INotifier} related to "serial number set" notifications.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class SetSerialNumberNotifier extends Notifier<ISystemListener> {

	private final boolean mResult;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param result
	 *            : the result of the set operation
	 * */
	protected SetSerialNumberNotifier(final boolean result) {
		super();

		this.mResult = result;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param result
	 *            : the parameter required by class constructor
	 * */
	public static SetSerialNumberNotifier create(final boolean result) {
		return new SetSerialNumberNotifier(result);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final ISystemListener listener) {
		listener.onSerialNumberSet(this.mResult);
	}

	// }

}
