package com.technogym.android.myrun.sdk.system.notificationRules;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.WIDVersionNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WIDVersionNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected WIDVersionNotificationRule() {
		super();
	}

	public static WIDVersionNotificationRule create() {
		return new WIDVersionNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "RWID";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
		notifiers.add(WIDVersionNotifier.create(notifyMessage));
		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "";
	}

	// }
}