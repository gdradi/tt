package com.technogym.android.myrun.sdk.system.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.StatusNotifier;

public class StatusNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected StatusNotificationRule() {
		super();
	}

	public static StatusNotificationRule create() {
		return new StatusNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "STATUS";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
		//final Iterator<String[]> result = this.splitParams(notifyMessage);
		//final String[] statusData = result.next();

		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
		notifiers.add(StatusNotifier.create(notifyMessage));

		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "(\\d+)(,\\s*\\d+)*";
	}

	// }
	
}
