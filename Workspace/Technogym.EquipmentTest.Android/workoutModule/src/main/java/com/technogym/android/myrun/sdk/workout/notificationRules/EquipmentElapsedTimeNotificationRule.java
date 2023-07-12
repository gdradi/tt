package com.technogym.android.myrun.sdk.workout.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.notificationRules.INotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;
import com.technogym.android.myrun.sdk.workout.notifiers.EquipmentElapsedTimeNotifier;

public class EquipmentElapsedTimeNotificationRule extends NotificationRule<IWorkoutListener> implements
		INotificationRule<IWorkoutListener> {

	// Construction

	protected EquipmentElapsedTimeNotificationRule() {
		super();
	}

	public static EquipmentElapsedTimeNotificationRule create() {
		return new EquipmentElapsedTimeNotificationRule();
	}

	// }

	@Override
	public String getIdentifier() {
		return "MYR_031";
	}

	@Override
	public Iterator<INotifier<IWorkoutListener>> getNotifiers(final String notifyMessage) {
		final Iterator<String[]> result = this.splitParams(notifyMessage);

		final String[] statusData = result.next();

		final Integer elapsedTime = Integer.parseInt(statusData[11], 10);

		final List<INotifier<IWorkoutListener>> notifiers = new ArrayList<INotifier<IWorkoutListener>>();
		notifiers.add(EquipmentElapsedTimeNotifier.create(elapsedTime));

		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "([0-9]{2}.[0-9]),([0-9]{2}.[0-9]),([0-9]{2}.[0-9]),([-+]?[0-9]+.[0-9]),([0-9]{3}),(0|1),(0|1),(0|1),(0|1),(0|1),([0-9]{5}),([-][0-9]{4}|[0-9]{5}),([0-9]{2}),(0|1),(0|1)";
	}

}
