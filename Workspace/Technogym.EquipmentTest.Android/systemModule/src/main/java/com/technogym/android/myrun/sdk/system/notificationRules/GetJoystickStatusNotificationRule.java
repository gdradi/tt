package com.technogym.android.myrun.sdk.system.notificationRules;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.GetJoystickStatusNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetJoystickStatusNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected GetJoystickStatusNotificationRule() {
		super();
	}

	public static GetJoystickStatusNotificationRule create() {
		return new GetJoystickStatusNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "RIO";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
		notifiers.add(GetJoystickStatusNotifier.create(notifyMessage));

		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "";
	}

	// }
}