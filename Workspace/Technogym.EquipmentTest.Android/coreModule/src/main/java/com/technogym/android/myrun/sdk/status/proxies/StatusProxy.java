package com.technogym.android.myrun.sdk.status.proxies;

import com.technogym.android.myrun.sdk.communication.enums.MyRunStatusType;
import com.technogym.android.myrun.sdk.communication.proxies.EquipmentProxy;
import com.technogym.android.myrun.sdk.connection.controllers.IEquipmentController;
import com.technogym.android.myrun.sdk.status.listeners.IStatusListener;
import com.technogym.android.myrun.sdk.status.notificationRules.EquipmentStatusNotificationRule;

public final class StatusProxy extends EquipmentProxy<IStatusListener> implements IStatusProxy, IStatusListener {

	private static StatusProxy mInstance = null;

	private MyRunStatusType mEquipmentStatus;

	// { Construction

	protected StatusProxy(final IEquipmentController connectionController) {
		super(connectionController);

		this.mEquipmentStatus = null;
	}

	public static IStatusProxy getInstance(final IEquipmentController connectionController) {
		if (mInstance == null) {
			mInstance = new StatusProxy(connectionController);
		}
		return mInstance;
	}

	// }

	// { IEquipmentProxy abstract method implementation

	@Override
	public void initializeNotificationRules() {
		if (this.getInitializationState() == false) {
			this.addNotificationRule(EquipmentStatusNotificationRule.create());
		}
	}

	// }

	// { IStatusProxy implementation

	@Override
	public MyRunStatusType getEquipmentStatus() {
		return this.mEquipmentStatus;
	}

	// }

	// { IStatusListener implementation

	@Override
	public void onEquipmentStatusChanged(final MyRunStatusType status) {
		if (this.mEquipmentStatus != status) {
			this.mEquipmentStatus = status;
			this.notifyListenerOnEquipmentStatusChanged();
		}
	}

	// }

	// { Private methods

	private void notifyListenerOnEquipmentStatusChanged() {
		for (IStatusListener listener : this.getListeners()) {
			listener.onEquipmentStatusChanged(this.mEquipmentStatus);
		}
	}

	// }

}
