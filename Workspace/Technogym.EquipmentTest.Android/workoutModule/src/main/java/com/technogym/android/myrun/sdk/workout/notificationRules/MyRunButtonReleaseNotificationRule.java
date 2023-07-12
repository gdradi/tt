package com.technogym.android.myrun.sdk.workout.notificationRules;

import com.technogym.android.myrun.sdk.communication.notificationRules.INotificationRule;
import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;
import com.technogym.android.myrun.sdk.workout.notifiers.MyRunButtonPressNotifier;
import com.technogym.android.myrun.sdk.workout.notifiers.MyRunButtonReleaseNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyRunButtonReleaseNotificationRule extends NotificationRule<IWorkoutListener> implements
		INotificationRule<IWorkoutListener> {

	// Construction

	protected MyRunButtonReleaseNotificationRule() {
		super();
	}

	public static MyRunButtonReleaseNotificationRule create() {
		return new MyRunButtonReleaseNotificationRule();
	}

	// }

	@Override
	public String getIdentifier() {
		return "SSR";
	}

	@Override
	public Iterator<INotifier<IWorkoutListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<IWorkoutListener>> notifiers = new ArrayList<INotifier<IWorkoutListener>>();
		notifiers.add(MyRunButtonReleaseNotifier.create());
		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "SSR";
	}
}
