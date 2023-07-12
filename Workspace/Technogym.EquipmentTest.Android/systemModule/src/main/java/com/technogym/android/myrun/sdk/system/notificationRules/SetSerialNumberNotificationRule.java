package com.technogym.android.myrun.sdk.system.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.SetSerialNumberNotifier;

public class SetSerialNumberNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected SetSerialNumberNotificationRule() {
		super();
	}

	public static SetSerialNumberNotificationRule create() {
		return new SetSerialNumberNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "STG_ID";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();

		try {
			final Iterator<String[]> result = this.splitParams(notifyMessage);
			final String[] statusData = result.next();
			notifiers.add(SetSerialNumberNotifier.create(!statusData[0].isEmpty()));
		} catch (Exception e) {
			notifiers.add(SetSerialNumberNotifier.create(false));
		}

		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "TG_ID:([a-zA-Z0-9]+)";
	}

	// }

}
