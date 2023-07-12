package com.technogym.android.myrun.sdk.system.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.InitSensorDeviceUpNotifier;

public class InitSensorDeviceUpNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected InitSensorDeviceUpNotificationRule() {
		super();
	}

	public static InitSensorDeviceUpNotificationRule create() {
		return new InitSensorDeviceUpNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "INITDSENS_UP";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(String notifyMessage) {
		final Iterator<String[]> result = this.splitParams(notifyMessage);

		final String[] statusData = result.next();

		final Integer value = Integer.parseInt(statusData[0], 10);

		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
		notifiers.add(InitSensorDeviceUpNotifier.create(value));

		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "[a-zA-Z\\s]+:[A-Z]+ - [a-zA-Z\\:]+([0-9]+)";
	}

	// }

}
