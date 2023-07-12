package com.technogym.android.myrun.sdk.system.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.GetSerialNumberNotifier;

public class GetSerialNumberNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected GetSerialNumberNotificationRule() {
		super();
	}

	public static GetSerialNumberNotificationRule create() {
		return new GetSerialNumberNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "RTG_ID";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
		final Iterator<String[]> result = this.splitParams(notifyMessage);
		final String[] statusData = result.next();

		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
		notifiers.add(GetSerialNumberNotifier.create(statusData[0]));

		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "TG_ID:([a-zA-Z0-9]+)";
	}

	// }

}
