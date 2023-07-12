package com.technogym.android.myrun.sdk.system.listeners.notifiers.myrun2020;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

/**
 * This is the {@link INotifier} related to "get down sensor status" notifications.
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public class MyRunBtVersionNotifier extends Notifier<ISystemListener> {

	private final String mStatus;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 *
	 * @param status
	 *            : the down sensor status retreived from the equipment
	 * */
	protected MyRunBtVersionNotifier(final String status) {
		super();

		this.mStatus = status;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param status
	 *            : the parameter required by class constructor
	 * */
	public static MyRunBtVersionNotifier create(final String status) {
		return new MyRunBtVersionNotifier(status);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final ISystemListener listener) {
		listener.onBtVersionRetrieved(this.mStatus);
	}

	// }

}
