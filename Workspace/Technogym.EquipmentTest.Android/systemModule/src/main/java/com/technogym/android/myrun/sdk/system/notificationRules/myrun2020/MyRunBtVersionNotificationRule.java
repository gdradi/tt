package com.technogym.android.myrun.sdk.system.notificationRules.myrun2020;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.myrun2020.MyRunBtVersionNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyRunBtVersionNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected MyRunBtVersionNotificationRule() {
		super();
	}

	public static MyRunBtVersionNotificationRule create() {
		return new MyRunBtVersionNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "RGYMKIT_VERSION";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
		notifiers.add(MyRunBtVersionNotifier.create(notifyMessage));
		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "";
	}

	// }
}