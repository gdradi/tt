package com.technogym.android.myrun.sdk.system.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.SetMeasureUnitNotifier;

public class SetMeasureUnitNotificationRule extends NotificationRule<ISystemListener> {

	// { Construction

	protected SetMeasureUnitNotificationRule() {
		super();
	}

	public static SetMeasureUnitNotificationRule create() {
		return new SetMeasureUnitNotificationRule();
	}

	// }

	// { NotificationRule abstract methods implementation

	@Override
	public String getIdentifier() {
		return "SSIUNITS";
	}

	@Override
	public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
		final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();

		try {
			//final Iterator<String[]> result = this.splitParams(notifyMessage);
			//final String[] statusData = result.next();
			notifiers.add(SetMeasureUnitNotifier.create(!notifyMessage.isEmpty()));
		} catch (Exception e) {
			notifiers.add(SetMeasureUnitNotifier.create(false));
		}

		return notifiers.iterator();
	}

	@Override
	protected String getRegex() {
		return "";
	}

	// }

}
