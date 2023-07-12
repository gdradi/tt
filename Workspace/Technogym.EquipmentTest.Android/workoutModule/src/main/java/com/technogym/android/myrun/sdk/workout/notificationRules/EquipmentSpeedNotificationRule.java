package com.technogym.android.myrun.sdk.workout.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.notificationRules.INotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;
import com.technogym.android.myrun.sdk.workout.notifiers.EquipmentSpeedNotifier;

public class EquipmentSpeedNotificationRule extends NotificationRule<IWorkoutListener> implements
		INotificationRule<IWorkoutListener> {

	// Construction

	protected EquipmentSpeedNotificationRule() {
		super();
	}

	public static EquipmentSpeedNotificationRule create() {
		return new EquipmentSpeedNotificationRule();
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

		final Float speed = Float.parseFloat(statusData[0]) * Float.valueOf(10);

		final List<INotifier<IWorkoutListener>> notifiers = new ArrayList<INotifier<IWorkoutListener>>();
		notifiers.add(EquipmentSpeedNotifier.create(speed.intValue()));

		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "([0-9]{2}.[0-9]),([0-9]{2}.[0-9]),([0-9]{2}.[0-9]),([-+]?[0-9]+.[0-9]),([0-9]{3}),(0|1),(0|1),(0|1),(0|1),(0|1),([0-9]{5}),([-][0-9]{4}|[0-9]{5}),([0-9]{2}),(0|1),(0|1)";
	}

}
