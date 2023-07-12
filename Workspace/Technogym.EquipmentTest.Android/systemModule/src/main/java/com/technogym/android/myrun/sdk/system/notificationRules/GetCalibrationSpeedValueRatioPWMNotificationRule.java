package com.technogym.android.myrun.sdk.system.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.GetCalibrationSpeedValueRatioPWMNotifier;

public class GetCalibrationSpeedValueRatioPWMNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected GetCalibrationSpeedValueRatioPWMNotificationRule() {
		super();
	}

	public static GetCalibrationSpeedValueRatioPWMNotificationRule create() {
		return new GetCalibrationSpeedValueRatioPWMNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "RKPWM";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
		notifiers.add(GetCalibrationSpeedValueRatioPWMNotifier.create(notifyMessage));

		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "";
	}

	// }
}