package com.technogym.android.myrun.sdk.system.notificationRules;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.ResetLKErrorLogNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResetLKErrorLogNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected ResetLKErrorLogNotificationRule() {
		super();
	}

	public static ResetLKErrorLogNotificationRule create() {
		return new ResetLKErrorLogNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "RESETLKERRORLOG";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
		notifiers.add(ResetLKErrorLogNotifier.create(notifyMessage));
		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "";
	}

	// }
}