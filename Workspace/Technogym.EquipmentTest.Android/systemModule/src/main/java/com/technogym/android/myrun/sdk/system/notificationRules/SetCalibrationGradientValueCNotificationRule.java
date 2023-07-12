package com.technogym.android.myrun.sdk.system.notificationRules;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.SetCalibrationGradientValueCNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SetCalibrationGradientValueCNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected SetCalibrationGradientValueCNotificationRule() {
		super();
	}

	public static SetCalibrationGradientValueCNotificationRule create() {
		return new SetCalibrationGradientValueCNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "SKADC_C";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
		notifiers.add(SetCalibrationGradientValueCNotifier.create(notifyMessage));

		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "";
	}

	// }
}