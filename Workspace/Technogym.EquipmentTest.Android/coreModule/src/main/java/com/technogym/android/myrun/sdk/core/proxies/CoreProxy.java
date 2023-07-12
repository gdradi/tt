package com.technogym.android.myrun.sdk.core.proxies;

import java.util.List;

import com.technogym.android.myrun.sdk.communication.proxies.EquipmentProxy;
import com.technogym.android.myrun.sdk.connection.controllers.IEquipmentController;
import com.technogym.android.myrun.sdk.core.commands.DisableKeepAliveCommand;
import com.technogym.android.myrun.sdk.core.commands.EnterRemoteModeCommand;
import com.technogym.android.myrun.sdk.core.commands.ExitRemoteModeCommand;
import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.core.commands.KeepAliveCommand;
import com.technogym.android.myrun.sdk.core.listeners.ICoreListener;
import com.technogym.android.myrun.sdk.core.notificationRules.EnterRemoteModeNotificationRule;
import com.technogym.android.myrun.sdk.core.notificationRules.ExitRemoteModeNotificationRule;

/**
 * This is a final implementation of {@link ICoreProxy}.<br/>
 * <br/>
 * It supports the following notification rules:
 * <ul>
 * <li>the {@link EnterRemoteModeNotificationRule} for making the equipment enter remote mode</li>
 * <li>the {@link ExitRemoteModeNotificationRule} for making the equipment exit from remote mode</li>
 * </ul>
 * */
public final class CoreProxy extends EquipmentProxy<ICoreListener> implements ICoreProxy {

	private static CoreProxy mInstance;

	private boolean mRemoteModeEnabled;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It instantiates a new {@link EquipmentProxy} for the management of core commands.<br/>
	 * It simply calls the {@code super} constructor.
	 * 
	 * @param connectionController
	 *            : a reference to the controller of the connection with the equipment
	 * */
	protected CoreProxy(final IEquipmentController connectionController) {
		super(connectionController);

		this.mRemoteModeEnabled = false;
	}

	/**
	 * It's a static factory method.<br/>
	 * It achieves the implementation of Singleton pattern.
	 * 
	 * @param connectionController
	 *            : the connection controller required by the class constructor
	 * */
	public static ICoreProxy getInstance(final IEquipmentController connectionController) {
		if (mInstance == null) {
			mInstance = new CoreProxy(connectionController);
		}
		return mInstance;
	}

	// }

	// { ICoreProxy implementation

	@Override
	public void enterRemoteMode() throws WriteNotAllowedException {
		this.sendCommand(EnterRemoteModeCommand.create());
		this.mRemoteModeEnabled = true;
	}

	@Override
	public void exitRemoteMode() throws WriteNotAllowedException {
		this.sendCommand(ExitRemoteModeCommand.create());
		this.mRemoteModeEnabled = false;
	}

	@Override
	public void sendKeepAlive() throws WriteNotAllowedException {
		this.sendCommand(KeepAliveCommand.create());
	}

	@Override
	public boolean isRemoteModeEnabled() {
		return this.mRemoteModeEnabled;
	}

	
	// }

	// { IEquipmentProxy abstract method implementation

	@Override
	public void initializeNotificationRules() {
		if (this.getInitializationState() == false) {
			this.addNotificationRule(EnterRemoteModeNotificationRule.create());
			this.addNotificationRule(ExitRemoteModeNotificationRule.create());
		}
	}

	// }

	// { IKeepAliveExpiredListener implementation

	@Override
	public void onKeepAliveExpired() {
		final List<ICoreListener> listeners = this.getListeners();
		for (final ICoreListener listener : listeners) {
			listener.onKeepAliveExpired();
		}

		this.mRemoteModeEnabled = false;
	}

	@Override
	public void disableKeepAlive() throws WriteNotAllowedException {
		this.sendCommand(DisableKeepAliveCommand.create());
	}

	// }

}
