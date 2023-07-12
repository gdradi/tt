package com.technogym.android.myrun.sdk.system.notificationRules;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.ResetLKLifeDataNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResetLKLifeDataNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected ResetLKLifeDataNotificationRule() {
		super();
	}

	public static ResetLKLifeDataNotificationRule create() {
		return new ResetLKLifeDataNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "RESETLKLIFEDATA";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
		notifiers.add(ResetLKLifeDataNotifier.create(notifyMessage));
		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "";
	}

	// }
}