package com.technogym.android.myrun.sdk.system.listeners.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

/**
 * This is the {@link INotifier} related to "read equipment status" notifications.
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public final class StatusNotifier extends Notifier<ISystemListener> {

	private final String mStatus;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param status
	 *            : the status data received from the equipment
	 * */
	protected StatusNotifier(final String status) {
		super();

		this.mStatus = status;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param status
	 *            : the parameter required by class constructor
	 * */
	public static StatusNotifier create(final String status) {
		return new StatusNotifier(status);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final ISystemListener listener) {
		listener.onStatusRetreived(this.mStatus);
	}

	// }

}
