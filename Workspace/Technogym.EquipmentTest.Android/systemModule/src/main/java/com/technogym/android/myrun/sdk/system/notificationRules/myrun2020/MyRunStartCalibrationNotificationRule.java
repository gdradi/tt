package com.technogym.android.myrun.sdk.system.notificationRules.myrun2020;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.myrun2020.MyRunStartCalibrationNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyRunStartCalibrationNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected MyRunStartCalibrationNotificationRule() {
		super();
	}

	public static MyRunStartCalibrationNotificationRule create() {
		return new MyRunStartCalibrationNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "IS_START_CAL";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
		notifiers.add(MyRunStartCalibrationNotifier.create(notifyMessage));
		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "";
	}

	// }
}