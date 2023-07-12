package com.technogym.android.myrun.sdk.system.listeners.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

public class GetErrorLogNotifier extends Notifier<ISystemListener> {

	private final String mErrorLog;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param errorLog
	 *            : the error log retreived
	 * */
	protected GetErrorLogNotifier(final String errorLog) {
		super();

		this.mErrorLog = errorLog;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param errorLog
	 *            : the parameter required by class constructor
	 * */
	public static GetErrorLogNotifier create(final String errorLog) {
		return new GetErrorLogNotifier(errorLog);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(ISystemListener listener) {
		listener.onErrorLogRetreived(this.mErrorLog);
	}

	// }
}
