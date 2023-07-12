package com.technogym.android.myrun.sdk.workout.notificationRules;

import com.technogym.android.myrun.sdk.communication.notificationRules.INotificationRule;
import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;
import com.technogym.android.myrun.sdk.workout.notifiers.MyRunButtonPressNotifier;
import com.technogym.android.myrun.sdk.workout.notifiers.StartStopNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyRunButtonPressNotificationRule extends NotificationRule<IWorkoutListener> implements
		INotificationRule<IWorkoutListener> {

	// Construction

	protected MyRunButtonPressNotificationRule() {
		super();
	}

	public static MyRunButtonPressNotificationRule create() {
		return new MyRunButtonPressNotificationRule();
	}

	// }

	@Override
	public String getIdentifier() {
		return "SSP";
	}

	@Override
	public Iterator<INotifier<IWorkoutListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<IWorkoutListener>> notifiers = new ArrayList<INotifier<IWorkoutListener>>();
		notifiers.add(MyRunButtonPressNotifier.create());
		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "SSP";
	}
}
