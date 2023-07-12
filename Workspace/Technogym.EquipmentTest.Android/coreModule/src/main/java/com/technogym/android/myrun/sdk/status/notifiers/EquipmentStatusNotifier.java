package com.technogym.android.myrun.sdk.status.notifiers;

import com.technogym.android.myrun.sdk.communication.enums.MyRunStatusType;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.status.listeners.IStatusListener;

public class EquipmentStatusNotifier extends Notifier<IStatusListener> {

	private final MyRunStatusType mStatus;

	// { Construction

	protected EquipmentStatusNotifier(final MyRunStatusType status) {
		super();

		this.mStatus = status;
	}

	public static EquipmentStatusNotifier create(final MyRunStatusType status) {
		return new EquipmentStatusNotifier(status);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final IStatusListener listener) {
		listener.onEquipmentStatusChanged(this.mStatus);
	}

	// }

}