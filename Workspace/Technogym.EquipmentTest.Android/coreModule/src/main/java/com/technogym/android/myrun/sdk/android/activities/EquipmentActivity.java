package com.technogym.android.myrun.sdk.android.activities;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.communication.enums.MyRunStatusType;
import com.technogym.android.myrun.sdk.connection.controllers.IEquipmentController;
import com.technogym.android.myrun.sdk.connection.listeners.EquipmentConnectionListener;
import com.technogym.android.myrun.sdk.core.listeners.ICoreListener;
import com.technogym.android.myrun.sdk.core.proxies.CoreProxy;
import com.technogym.android.myrun.sdk.core.proxies.ICoreProxy;
import com.technogym.android.myrun.sdk.core.routines.KeepAliveRoutine;
import com.technogym.android.myrun.sdk.status.listeners.IStatusListener;
import com.technogym.android.myrun.sdk.status.proxies.IStatusProxy;
import com.technogym.android.myrun.sdk.status.proxies.StatusProxy;
import com.technogym.android.myrun.sdk.support.Logger;

import android.app.Activity;
import android.os.Bundle;

public abstract class EquipmentActivity<TConnectionController extends IEquipmentController> extends Activity implements
		EquipmentConnectionListener, IStatusListener, ICoreListener {

	protected static final String LOGTAG = "MYR_EQUIPMENT_ACTIVITY";

	private boolean mAlreadySetup;

	private ICoreProxy mCoreProxy;
	private IStatusProxy mStatusProxy;
	private KeepAliveRoutine mKeepAliveRoutine;
	private TConnectionController mConnectionController;

	// { Activity methods overriding

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TConnectionController connController = this.getConnectionControllerFromApplication();
		this.setConnectionController(connController);

		this.mCoreProxy = CoreProxy.getInstance(this.getConnectionController());
		this.mCoreProxy.initializeNotificationRules();
		this.mCoreProxy.registerForNotification(this);

		this.mStatusProxy = StatusProxy.getInstance(this.getConnectionController());
		this.mStatusProxy.initializeNotificationRules();
		this.mStatusProxy.registerForNotification(this);

		this.mKeepAliveRoutine = KeepAliveRoutine.create(this.mCoreProxy, this.mStatusProxy, this.mCoreProxy);
		this.mKeepAliveRoutine.initRoutine();
	}

	@Override
	protected void onResume() {
		super.onResume();

		this.getConnectionController().registerEquipmentConnectionListener(this);
		if (!this.mAlreadySetup) {
			this.getConnectionController().setup(false);
			this.mAlreadySetup = true;
		}
	}

	@Override
	protected void onPause() {
		this.getConnectionController().unregisterEquipmentConnectionListener(this);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		this.mStatusProxy.deregisterForNotification(this);
		this.mCoreProxy.deregisterForNotification(this);
		this.getConnectionController().terminatePendingActions();
		this.getConnectionController().unregisterEquipmentConnectionListener(this);
		super.onDestroy();
	}

	// }

	// { Getters and setters (private and protected)

	protected ICoreProxy getCoreProxy() {
		return this.mCoreProxy;
	}

	protected IStatusProxy getStatusProxy() {
		return this.mStatusProxy;
	}

	protected TConnectionController getConnectionController() {
		return this.mConnectionController;
	}

	private void setConnectionController(final TConnectionController connectionController) {
		this.mConnectionController = connectionController;
	}

	// }

	// { IStatusListener

	@Override
	public void onEquipmentStatusChanged(final MyRunStatusType status) {
		Logger.i(LOGTAG, "status value retrieved: " + status.getValue());
	}

	// }

	// { ICoreListener

	@Override
	public void onEnterRemoteModeResult(final Boolean success) {
		if (success == Boolean.FALSE) {
			try {
				this.getCoreProxy().enterRemoteMode();
			} catch (WriteNotAllowedException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public void onExitRemoteModeResult(final Boolean success) {
		Logger.e(LOGTAG, "Exited remote mode: " + success.toString());
	}

	@Override
	public void onKeepAliveExpired() {
		Logger.i(LOGTAG, "Keep alive expired");
	}

	// }

	// { EquipmentConnectionListener implementation

	@Override
	public void onMessageReceived(String message) {
		// TODO: this method is final because we don't want the "end-user" to know the structure of the messages.
	}

	@Override
	public void onConnectionEstablished() {
		Logger.e(LOGTAG, "connection established");
		try {
			this.mCoreProxy.enterRemoteMode();
		} catch (WriteNotAllowedException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onConnectionTerminated() {
		if (this.mCoreProxy.isRemoteModeEnabled()) {
			this.mKeepAliveRoutine.cancelRoutine();
		}
	}

	@Override
	public void onConnectionInterrupted() {
		if (this.mCoreProxy.isRemoteModeEnabled()) {
			this.mKeepAliveRoutine.cancelRoutine();
		}
	}

	@Override
	public void onConnectionFailed() {
		Logger.e(LOGTAG, "connection failed");
	}

	// }

	// { Private and protected methods

	protected abstract TConnectionController getConnectionControllerFromApplication();

	// }

}
