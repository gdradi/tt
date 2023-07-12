package com.technogym.android.myrun.sdk.communication.notifiers;

import com.technogym.android.myrun.sdk.communication.listeners.IListener;

public interface INotifier<TListener extends IListener>  {

	void notify(TListener listener);
}
