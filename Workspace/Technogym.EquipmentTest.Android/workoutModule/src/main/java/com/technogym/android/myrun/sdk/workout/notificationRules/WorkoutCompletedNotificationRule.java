package com.technogym.android.myrun.sdk.workout.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.notificationRules.INotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;
import com.technogym.android.myrun.sdk.workout.notifiers.WorkoutCompletedNotifier;

public class WorkoutCompletedNotificationRule extends NotificationRule<IWorkoutListener> implements
		INotificationRule<IWorkoutListener> {

	// Construction

	protected WorkoutCompletedNotificationRule() {
		super();
	}

	public static WorkoutCompletedNotificationRule create() {
		return new WorkoutCompletedNotificationRule();
	}

	// }

	@Override
	public String getIdentifier() {
		return "MYR_091";
	}

	@Override
	public Iterator<INotifier<IWorkoutListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<IWorkoutListener>> notifiers = new ArrayList<INotifier<IWorkoutListener>>();
		notifiers.add(WorkoutCompletedNotifier.create());
		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "(^$)";
	}
}
