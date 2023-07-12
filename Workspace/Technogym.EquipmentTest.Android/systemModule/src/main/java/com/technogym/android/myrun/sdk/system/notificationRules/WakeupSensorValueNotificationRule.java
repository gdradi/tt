package com.technogym.android.myrun.sdk.system.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.WakeupSensorValueNotifier;

public class WakeupSensorValueNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected WakeupSensorValueNotificationRule() {
		super();
	}

	public static WakeupSensorValueNotificationRule create() {
		return new WakeupSensorValueNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "DUMPIO";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {

		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
		
		try {
			final Iterator<String[]> result = this.splitParams(notifyMessage);
			final String[] statusData = result.next();
			notifiers.add(WakeupSensorValueNotifier.create(Integer.valueOf(statusData[0])));
		} catch (Exception e) {
			notifiers.add(WakeupSensorValueNotifier.create(Integer.valueOf(0)));
		}

		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "IN  WK_SENSOR = (0|1)";
	}

	// }

}
