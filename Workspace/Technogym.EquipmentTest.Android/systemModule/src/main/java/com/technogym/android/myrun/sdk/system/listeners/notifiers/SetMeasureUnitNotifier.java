package com.technogym.android.myrun.sdk.system.listeners.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

/**
 * This is the {@link INotifier} related to "serial number set" notifications.
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public final class SetMeasureUnitNotifier extends Notifier<ISystemListener> {

	private final boolean mResult;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param result
	 *            : the result of the set operation
	 * */
	protected SetMeasureUnitNotifier(final boolean result) {
		super();

		this.mResult = result;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param result
	 *            : the parameter required by class constructor
	 * */
	public static SetMeasureUnitNotifier create(final boolean result) {
		return new SetMeasureUnitNotifier(result);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final ISystemListener listener) {
		listener.onMeasureUnitSet(this.mResult);
	}

	// }

}
