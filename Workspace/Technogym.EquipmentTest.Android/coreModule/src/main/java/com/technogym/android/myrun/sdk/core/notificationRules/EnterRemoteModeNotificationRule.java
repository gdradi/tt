package com.technogym.android.myrun.sdk.core.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.notificationRules.INotificationRule;
import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.core.listeners.ICoreListener;
import com.technogym.android.myrun.sdk.core.notifiers.EnterRemoteModeNotifier;

public class EnterRemoteModeNotificationRule extends NotificationRule<ICoreListener> implements
		INotificationRule<ICoreListener> {

	// Construction

	protected EnterRemoteModeNotificationRule() {
		super();
	}

	public static EnterRemoteModeNotificationRule create() {
		return new EnterRemoteModeNotificationRule();
	}

	// }

	@Override
	public String getIdentifier() {
		return "MYR_028";
	}

	@Override
	public Iterator<INotifier<ICoreListener>> getNotifiers(final String notifyMessage) {
		final Iterator<String[]> result = this.splitParams(notifyMessage);
		final Boolean success = result.next()[0].equals("OK");
		final List<INotifier<ICoreListener>> notifiers = new ArrayList<INotifier<ICoreListener>>();
		notifiers.add(EnterRemoteModeNotifier.create(success));
		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "(OK|ERR)";
	}

}
