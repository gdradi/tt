package com.technogym.android.myrun.sdk.system.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.FirmwareVersionNotifier;

public class FirmwareVersionNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected FirmwareVersionNotificationRule() {
		super();
	}

	public static FirmwareVersionNotificationRule create() {
		return new FirmwareVersionNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "FWVER";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
		notifiers.add(FirmwareVersionNotifier.create(notifyMessage));
		return notifiers.iterator();
//		final Iterator<String[]> result = this.splitParams(notifyMessage);
//
//		final String[] statusData = result.next();
//
//		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
//		notifiers.add(FirmwareVersionNotifier.create(statusData[0]));
//
//		return notifiers.iterator();
	}

	@Override
	protected String getRegex() { return ""; }
	//protected String getRegex() { return "WFW0459_BA([0-9]+\\.[0-9]+)"; }

	// }

}
