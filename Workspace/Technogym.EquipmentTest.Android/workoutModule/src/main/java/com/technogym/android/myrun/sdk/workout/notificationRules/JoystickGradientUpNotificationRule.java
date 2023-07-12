package com.technogym.android.myrun.sdk.workout.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.notificationRules.INotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;
import com.technogym.android.myrun.sdk.workout.notifiers.JoystickGradientUpNotifier;

public class JoystickGradientUpNotificationRule extends NotificationRule<IWorkoutListener> implements
		INotificationRule<IWorkoutListener> {

	// Construction

	protected JoystickGradientUpNotificationRule() {
		super();
	}

	public static JoystickGradientUpNotificationRule create() {
		return new JoystickGradientUpNotificationRule();
	}

	// }

	@Override
	public String getIdentifier() {
		return "IOM_011";
	}

	@Override
	public Iterator<INotifier<IWorkoutListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<IWorkoutListener>> notifiers = new ArrayList<INotifier<IWorkoutListener>>();
		notifiers.add(JoystickGradientUpNotifier.create());
		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "(0|1)";
	}
}
