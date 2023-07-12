package com.technogym.android.myrun.sdk.system.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.SetEEPROMDefaultValuesNotifier;

public class SetEEPROMDefaultValuesNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected SetEEPROMDefaultValuesNotificationRule() {
		super();
	}

	public static SetEEPROMDefaultValuesNotificationRule create() {
		return new SetEEPROMDefaultValuesNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "EEPROMDEF";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(String notifyMessage) {
		//final Iterator<String[]> result = this.splitParams(notifyMessage);
		//final String[] statusData = result.next();

		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
		notifiers.add(SetEEPROMDefaultValuesNotifier.create(notifyMessage));

		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "";
	}

	// }
}
