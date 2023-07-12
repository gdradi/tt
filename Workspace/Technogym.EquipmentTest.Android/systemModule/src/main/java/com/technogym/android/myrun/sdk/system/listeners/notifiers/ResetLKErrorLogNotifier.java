package com.technogym.android.myrun.sdk.system.listeners.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

/**
 * This is the {@link INotifier} related to "get down sensor status" notifications.
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public class ResetLKErrorLogNotifier extends Notifier<ISystemListener> {

	private final String mStatus;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 *
	 * @param status
	 *            : the down sensor status retreived from the equipment
	 * */
	protected ResetLKErrorLogNotifier(final String status) {
		super();

		this.mStatus = status;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param status
	 *            : the parameter required by class constructor
	 * */
	public static ResetLKErrorLogNotifier create(final String status) {
		return new ResetLKErrorLogNotifier(status);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final ISystemListener listener) {
		listener.onLKErrorLogResetted(this.mStatus);
	}

	// }

}
