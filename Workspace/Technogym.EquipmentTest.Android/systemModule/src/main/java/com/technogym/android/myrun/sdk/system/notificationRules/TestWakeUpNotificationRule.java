package com.technogym.android.myrun.sdk.system.notificationRules;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.TestWakeUpNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestWakeUpNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected TestWakeUpNotificationRule() {
		super();
	}

	public static TestWakeUpNotificationRule create() {
		return new TestWakeUpNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "TESTWAKEUP";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
		notifiers.add(TestWakeUpNotifier.create(notifyMessage));
		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "";
	}

	// }
}