package com.technogym.android.myrun.sdk.core.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.core.listeners.ICoreListener;

public class EnterRemoteModeNotifier extends Notifier<ICoreListener> {

	protected final Boolean mSuccess;

	// { Construction

	protected EnterRemoteModeNotifier(final Boolean success) {
		super();
		this.mSuccess = success;
	}

	public static EnterRemoteModeNotifier create(final Boolean success) {
		return new EnterRemoteModeNotifier(success);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final ICoreListener listener) {
		listener.onEnterRemoteModeResult(this.mSuccess);
	}

	// }

}
