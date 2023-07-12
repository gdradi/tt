package com.technogym.android.myrun.sdk.system.listeners.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

public class SetEEPROMDefaultValuesNotifier extends Notifier<ISystemListener> {

	private final String mResult;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param result
	 *            : the result of the set operation
	 * */
	protected SetEEPROMDefaultValuesNotifier(final String result) {
		super();

		this.mResult = result;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param result
	 *            : the parameter required by class constructor
	 * */
	public static SetEEPROMDefaultValuesNotifier create(final String result) {
		return new SetEEPROMDefaultValuesNotifier(result);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(ISystemListener listener) {
		listener.onSetEEPROMDefaultValuesSet(this.mResult);
	}

	// }
}
