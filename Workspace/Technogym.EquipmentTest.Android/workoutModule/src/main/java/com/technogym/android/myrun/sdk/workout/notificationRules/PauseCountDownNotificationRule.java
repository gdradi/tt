package com.technogym.android.myrun.sdk.workout.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.notificationRules.INotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;
import com.technogym.android.myrun.sdk.workout.notifiers.PauseCountDownNotifier;

public class PauseCountDownNotificationRule extends NotificationRule<IWorkoutListener> implements
		INotificationRule<IWorkoutListener> {

	// Construction

	protected PauseCountDownNotificationRule() {
		super();
	}

	public static PauseCountDownNotificationRule create() {
		return new PauseCountDownNotificationRule();
	}

	// }

	@Override
	public String getIdentifier() {
		return "MYR_089";
	}

	@Override
	public Iterator<INotifier<IWorkoutListener>> getNotifiers(final String notifyMessage) {
		final Iterator<String[]> result = this.splitParams(notifyMessage);
		final Integer countDown = Integer.parseInt(result.next()[0], 10);
		final List<INotifier<IWorkoutListener>> notifiers = new ArrayList<INotifier<IWorkoutListener>>();
		notifiers.add(PauseCountDownNotifier.create(countDown));
		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "([0-9]{1,3})";
	}
}
