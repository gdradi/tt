package com.technogym.android.myrun.sdk.communication.notifiers;

import com.technogym.android.myrun.sdk.communication.listeners.IListener;

public abstract class Notifier<TListener extends IListener> implements INotifier<TListener> {

	@Override
	public abstract void notify(TListener listener);

}
