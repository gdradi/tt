package com.technogym.android.myrun.sdk.status.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.technogym.android.myrun.sdk.communication.enums.MyRunStatusType;
import com.technogym.android.myrun.sdk.communication.notificationRules.INotificationRule;
import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.status.listeners.IStatusListener;
import com.technogym.android.myrun.sdk.status.notifiers.EquipmentStatusNotifier;

public class EquipmentStatusNotificationRule extends NotificationRule<IStatusListener> implements
		INotificationRule<IStatusListener> {

	// Construction

	protected EquipmentStatusNotificationRule() {
		super();
	}

	public static EquipmentStatusNotificationRule create() {
		return new EquipmentStatusNotificationRule();
	}

	// }

	@Override
	public String getIdentifier() {
		return "MYR_031";
	}

	@Override
	public Iterator<INotifier<IStatusListener>> getNotifiers(final String notifyMessage) {
		final Iterator<String[]> result = this.splitParams(notifyMessage);

		final String[] statusData = result.next();

		final Integer status = Integer.parseInt(statusData[12], 10);

		final List<INotifier<IStatusListener>> notifiers = new ArrayList<INotifier<IStatusListener>>();
		notifiers.add(EquipmentStatusNotifier.create(MyRunStatusType.valueOf(status)));

		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "([0-9]{2}.[0-9]),([0-9]{2}.[0-9]),([0-9]{2}.[0-9]),([-+]?[0-9]+.[0-9]),([0-9]{3}),(0|1),(0|1),(0|1),(0|1),(0|1),([0-9]{5}),([-][0-9]{4}|[0-9]{5}),([0-9]{2}),(0|1),(0|1)";
	}

}
