package com.technogym.android.myrun.sdk.android.application;

import com.technogym.android.myrun.sdk.connection.controllers.IEquipmentController;

import android.app.Application;

public abstract class EquipmentApplication<TConnectionController extends IEquipmentController> extends Application {

	private TConnectionController mConnectionController;

	@Override
	public void onCreate() {
		super.onCreate();

		this.mConnectionController = initializeConnectionController();
	}

	// { Public methods

	public TConnectionController getConnectionController() {
		return this.mConnectionController;
	}

	// }

	// { Private and protected methods

	protected abstract TConnectionController initializeConnectionController();

	// }

}
