package com.technogym.android.myrun.sdk.core.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.core.listeners.ICoreListener;

public class ExitRemoteModeNotifier extends Notifier<ICoreListener> {

	protected final Boolean mSuccess;

	// { Construction

	protected ExitRemoteModeNotifier(final Boolean success) {
		super();
		this.mSuccess = success;
	}

	public static ExitRemoteModeNotifier create(final Boolean success) {
		return new ExitRemoteModeNotifier(success);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final ICoreListener listener) {
		listener.onExitRemoteModeResult(this.mSuccess);
	}

	// }

}
