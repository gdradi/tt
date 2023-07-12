package com.technogym.equipmenttest.app.bridges.android;

import it.spot.android.logger.domain.Logger;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.JavascriptInterface;

import com.google.zxing.integration.android.IntentIntegrator;
import com.technogym.equipmenttest.app.R;
import com.technogym.android.myrun.sdk.bluetooth.controllers.IBluetoothController;
import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.connection.models.EquipmentConnectionState;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.android.myrun.sdk.system.proxies.SystemProxy;
import com.technogym.equipmenttest.app.managers.MyRunTestActionManager;
import com.technogym.equipmenttest.library.bridges.android.EquipmentBridge;

public class MyRunEquipmentBridge extends EquipmentBridge<MyRunTestActionManager> implements IMyRunEquipmentBridge {

	private ISystemProxy mSystemProxy;
	private ISystemListener mListener;
	private IBluetoothController mBluetoothController;

	// { Construction

	protected MyRunEquipmentBridge(final Activity activity, final MyRunTestActionManager actionManager,
			final IBluetoothController bluetoothController, final ISystemListener listener) {

		super(activity, actionManager);

		mListener = listener;
		mBluetoothController = bluetoothController;
		mSystemProxy = SystemProxy.getInstance(null);
	}

	public static MyRunEquipmentBridge create(final Activity activity, final MyRunTestActionManager actionManager,
			final IBluetoothController bluetoothController, final ISystemListener listener) {

		return new MyRunEquipmentBridge(activity, actionManager, bluetoothController, listener);
	}

	// }

	// { IMyRunAndroidBridge abstract methods implementation

	@Override
	@JavascriptInterface
	public boolean isBluetoothConnected() {
		return mBluetoothController.getConnectionState() == EquipmentConnectionState.CONNECTED;
	}

	@Override
	@JavascriptInterface
	public void toggleBluetooth() {
		if (mBluetoothController.getConnectionState() == EquipmentConnectionState.CONNECTED
				|| mBluetoothController.getConnectionState() == EquipmentConnectionState.CONNECTING
				|| mBluetoothController.getConnectionState() == EquipmentConnectionState.DISCONNECTING) {

			new AlertDialog.Builder(mActivity)
					.setIcon(R.drawable.ic_launcher)
					.setMessage(
						String.format(mActivity.getString(R.string.currently_connected_to), mBluetoothController
								.getConnectedDevice().getName()))
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					}).setPositiveButton(R.string.disconnect, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							mBluetoothController.disconnect();
						}
					}).show();
		} else {
			mBluetoothController.startScanningDevices();
		}
	}

	@Override
	@JavascriptInterface
	public void askForEquipmentFirmwareVersion() {

		Logger.getInstance().logDebug(getClass().getSimpleName(), "ASKING FOR FIRMWARE VERSION ");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.askForFirmwareVersion();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void askForBootloaderFirmwareVersion() {

		Logger.getInstance().logDebug(getClass().getSimpleName(), "ASKING FOR FIRMWARE BOOTLOADER VERSION ");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.askForBootloaderFirmwareVersion();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public boolean isMyRun() {
		return true;
	}

	@Override
	@JavascriptInterface
	public boolean isMyRun2020() { return false; }

	@Override
	@JavascriptInterface
	public boolean isBleUart() { return false; }

	@Override
	@JavascriptInterface
	public boolean isUnity() { return false; }

	@Override
	@JavascriptInterface
	public boolean isMyCycling() { return false; }

	@Override
	@JavascriptInterface
	public void askForEquipmentSerialNumber() {

		Logger.getInstance().logDebug(getClass().getSimpleName(), "ASKING FOR SERIAL NUMBER");

		this.mSystemProxy.registerForNotification(this.mListener);

		Logger.getInstance().logDebug(getClass().getSimpleName(), "AFTER REGISTER -> ASKING FOR SERIAL NUMBER");

		try {
			this.mSystemProxy.askForSerialNumber();
			Logger.getInstance().logDebug(getClass().getSimpleName(), "ASKED SERIAL NUMBER");
		} catch (WriteNotAllowedException ex) {
			Logger.getInstance().logDebug(getClass().getSimpleName(), "CATCH ASKED SERIAL NUMBER");
			ex.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void setEquipmentSerialNumber(final String serialNumber) {

		Logger.getInstance().logDebug(getClass().getSimpleName(), "SETTING SERIAL NUMBER " + serialNumber);

		this.mSystemProxy.registerForNotification(this.mListener);

		try {
			this.mSystemProxy.setSerialNumber(serialNumber);
		} catch (WriteNotAllowedException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void scanBarCode() {
		IntentIntegrator integrator = new IntentIntegrator(this.mActivity);
		integrator.setOrientationLocked(true);
		integrator.initiateScan();

        /*
            OLD Android Version

			IntentIntegrator integrator = new IntentIntegrator(this.mActivity);
			integrator.addExtra("SCAN_WIDTH", 800);
			integrator.addExtra("SCAN_HEIGHT", 200);
			integrator.addExtra("RESULT_DISPLAY_DURATION_MS", 3000L);
			integrator.addExtra("PROMPT_MESSAGE", "Custom prompt to scan a product");
			integrator.initiateScan(IntentIntegrator.TARGET_BARCODE_SCANNER_ONLY);
		*/
	}

	// }

}
