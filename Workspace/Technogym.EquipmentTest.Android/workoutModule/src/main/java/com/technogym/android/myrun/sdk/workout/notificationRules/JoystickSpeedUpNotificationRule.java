package com.technogym.android.myrun.sdk.workout.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.notificationRules.INotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;
import com.technogym.android.myrun.sdk.workout.notifiers.JoystickSpeedUpNotifier;

public class JoystickSpeedUpNotificationRule extends NotificationRule<IWorkoutListener> implements
		INotificationRule<IWorkoutListener> {

	// Construction

	protected JoystickSpeedUpNotificationRule() {
		super();
	}

	public static JoystickSpeedUpNotificationRule create() {
		return new JoystickSpeedUpNotificationRule();
	}

	// }

	@Override
	public String getIdentifier() {
		return "IOM_007";
	}

	@Override
	public Iterator<INotifier<IWorkoutListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<IWorkoutListener>> notifiers = new ArrayList<INotifier<IWorkoutListener>>();
		notifiers.add(JoystickSpeedUpNotifier.create());
		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "(0|1)";
	}
}
