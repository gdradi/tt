package com.technogym.equipmenttest.appbleuart.bridges.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.JavascriptInterface;

import com.google.zxing.integration.android.IntentIntegrator;
import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.connection.models.EquipmentConnectionState;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.bridges.android.EquipmentBridge;
import com.technogym.equipmenttest.appbleuart.connectors.BleUartConnector;
import com.technogym.equipmenttest.appbleuart.manager.IUpgradeManager;
import com.technogym.equipmenttest.appbleuart.manager.BleUartActionManager;
import com.technogym.equipmenttest.appbleuart.manager.UpgradeManager;
import com.technogym.equipmenttest.appbleuart.proxies.SystemProxy;
import com.technogym.equipmenttest.appbleuart.R;

import it.spot.android.logger.domain.Logger;


public class AppBleUartEquipmentBridge extends EquipmentBridge<BleUartActionManager> implements IAppBleUartEquipmentBridge {

	private ISystemProxy mSystemProxy;
	private ISystemListener mListener;
	private BleUartConnector uartBridgeConnector;
	private IUpgradeManager upgradeManager;
	private final String TAG = getClass().getSimpleName();

	// { Construction

	protected AppBleUartEquipmentBridge(final Activity activity, final BleUartActionManager actionManager,
										final BleUartConnector uartBridgeConnector, final ISystemListener listener) {

		super(activity, actionManager);

		mListener = listener;
		this.uartBridgeConnector = uartBridgeConnector;
		mSystemProxy = SystemProxy.getInstance(null);
		upgradeManager = new UpgradeManager(this.uartBridgeConnector, activity.getApplicationContext());
	}

	public static AppBleUartEquipmentBridge create(final Activity activity, final BleUartActionManager actionManager,
												   final BleUartConnector myRunBridgeConnector, final ISystemListener listener) {

		return new AppBleUartEquipmentBridge(activity, actionManager, myRunBridgeConnector, listener);
	}

	// }

	// { IMyRunAndroidBridge abstract methods implementation


	//region Equipment Identifiers

	@Override
	@JavascriptInterface
	public boolean isMyRun() {
		return false;
	}

	@Override
	@JavascriptInterface
	public boolean isMyRun2020() { return false; }

	@Override
	@JavascriptInterface
	public boolean isBleUart() {
		return true;
	}

	@Override
	@JavascriptInterface
	public boolean isUnity() {
		return false;
	}

	@Override
	@JavascriptInterface
	public boolean isMyCycling() { return false; }

	//endregion


	//region Setup Flow

	@Override
	@JavascriptInterface
	public void askForEquipmentSerialNumber() {

		Logger.getInstance().logDebug(TAG, "ASKING FOR SERIAL NUMBER");

		this.mSystemProxy.registerForNotification(this.mListener);

		Logger.getInstance().logDebug(TAG, "AFTER REGISTER -> ASKING FOR SERIAL NUMBER");

		try {
			this.mSystemProxy.askForSerialNumber();
			Logger.getInstance().logDebug(TAG, "ASKED SERIAL NUMBER");
		} catch (WriteNotAllowedException ex) {
			Logger.getInstance().logDebug(TAG, "CATCH ASKED SERIAL NUMBER");
			ex.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void setEquipmentSerialNumber(final String serialNumber) {

		Logger.getInstance().logDebug(TAG, "SETTING SERIAL NUMBER " + serialNumber);

		this.mSystemProxy.registerForNotification(this.mListener);

		try {
			this.mSystemProxy.setSerialNumber(serialNumber);
		} catch (WriteNotAllowedException ex) {
			ex.printStackTrace();
		}
	}


	@Override
	@JavascriptInterface
	public void askForEquipmentFirmwareVersion() {
		Logger.getInstance().logDebug(TAG, "ASKING FOR FIRMWARE VERSION ");
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

		Logger.getInstance().logDebug(TAG, "ASKING FOR FIRMWARE BOOTLOADER VERSION ");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.askForBootloaderFirmwareVersion();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void getLowKitVersion() {

		Logger.getInstance().logDebug(TAG, "ASKING FOR LOW KIT VERSION ");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.askLowKitVersion();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void getHighKitVersion() {

		Logger.getInstance().logDebug(TAG, "ASKING FOR HIGH KIT VERSION ");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.askHighKitVersion();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void rebootEquipment(int pollingTime) {
		/*
		REBOOT se:
		1- Invio comando STG_ID
			oppure
		2- Upgrade LK + BT

		Per il caso 1 basta attendere 10 secondi e ci si può riconnettere (pollingTime in questo caso sarà 0).
		Per il caso 2 invece vengono attesi 10 secondi e viene effettuato un polling che ogni pollingTime ms tenta la riconnessione.
		 */

		long rebootWaitTime = 10000;
		Logger.getInstance().logDebug(TAG, "SENDING REBOOT COMMAND. pollingTime: "+pollingTime);

		try {
			this.mSystemProxy.rebootEquimpent();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}

		try {
			Logger.getInstance().logDebug(TAG, " Waiting "+rebootWaitTime+" ms..");
			Thread.sleep(rebootWaitTime);
			Logger.getInstance().logDebug(TAG, " Waited "+rebootWaitTime+" ms");
			uartBridgeConnector.reconnectAfterReboot(pollingTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	@JavascriptInterface
	public void upgradeLowKitFwVersion(String itemCode, String widVersion, String token){
		Logger.getInstance().logDebug(TAG, "UPGRADE LOW KIT FW VERSION");

		String localFilePath = upgradeManager.downloadLowKitUpgradeFile(itemCode, widVersion, token);

		if(localFilePath == null){
			Logger.getInstance().logDebug(TAG, "localFilePath is null. ERROR");
			uartBridgeConnector.onLowKitUpgraded(false);
			return;
		}

		Logger.getInstance().logDebug(TAG, "FILE CORRECTLY DOWLOADED and saved in: " + localFilePath);

		upgradeManager.copyLowKitFileToEquipmentMemory(localFilePath);
	}

	@Override
	@JavascriptInterface
	public void upgradeHighKitFwVersion(String itemCode, String widVersion, String token){
		Logger.getInstance().logDebug(TAG, "UPGRADE HIGH KIT FW VERSION");

		String localFilePath = upgradeManager.downloadHighKitUpgradeFile(itemCode, widVersion, token);

		if(localFilePath == null){
			Logger.getInstance().logDebug(TAG, "localFilePath is null. ERROR");
			uartBridgeConnector.onHighKitUpgraded(false);
			return;
		}

		Logger.getInstance().logDebug(TAG, "FILE CORRECTLY DOWLOADED and saved in: " + localFilePath);

		upgradeManager.copyHighKitFileToEquipmentMemory(localFilePath);
	}

	@Override
	@JavascriptInterface
	public void getWIDVersion() {
		Logger.getInstance().logDebug(TAG, "ASKING FOR WID VERSION ");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.askWIDVersion();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	// end region


	// region Bluetooth

	@Override
	@JavascriptInterface
	public boolean isBluetoothConnected() {
		return uartBridgeConnector.getConnectionState() == EquipmentConnectionState.CONNECTED;
	}

	@Override
	@JavascriptInterface
	public void toggleBluetooth() {
		Logger.getInstance().logDebug(TAG, "[toggleBluetooth] Connected - Showing connected device message");
		if (uartBridgeConnector.getConnectionState() == EquipmentConnectionState.CONNECTED
				|| uartBridgeConnector.getConnectionState() == EquipmentConnectionState.CONNECTING
				|| uartBridgeConnector.getConnectionState() == EquipmentConnectionState.DISCONNECTING) {

			String connectionMessage = String.format(mActivity.getString(R.string.currently_connected_to),uartBridgeConnector.getConnectedDevice().getDevice().getAddress());

			Logger.getInstance().logDebug(TAG, "[toggleBluetooth] Device : " + uartBridgeConnector.getConnectedDevice().toString());

			Logger.getInstance().logDebug(TAG, "[toggleBluetooth] Message: " + connectionMessage);

			new AlertDialog.Builder(mActivity)
					.setIcon(R.drawable.launcher_icon)
					.setMessage(connectionMessage)
					.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					}).setPositiveButton(R.string.disconnect, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							uartBridgeConnector.disconnect();
						}
					}).show();
		} else {
			Logger.getInstance().logDebug(TAG, "[toggleBluetooth] Non connected - calling scanDevices()");
			uartBridgeConnector.scanDevices();
		}
	}

	// end region

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


	// region Live Essential
	@Override
	@JavascriptInterface
	public void setTreadMillMachineType() {
		Logger.getInstance().logDebug(TAG, "SETTING TREADMILL MACHINE TYPE");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.setTreadMillMachineType();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void setSynchroMachineType() {
		Logger.getInstance().logDebug(TAG, "SETTING SYNCHRO MACHINE TYPE");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.setSynchroMachineType();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void setTopMachineType() {
		Logger.getInstance().logDebug(TAG, "SETTING TOP MACHINE TYPE");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.setTopMachineType();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void setBikeMachineType() {
		Logger.getInstance().logDebug(TAG, "SETTING BIKE MACHINE TYPE");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.setBikeMachineType();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void setReclineMachineType() {
		Logger.getInstance().logDebug(TAG, "SETTING RECLINE MACHINE TYPE");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.setReclineMachineType();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void setClimbMachineType() {
		Logger.getInstance().logDebug(TAG, "SETTING CLIMB MACHINE TYPE");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.setClimbMachineType();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void setVarioMachineType() {
		Logger.getInstance().logDebug(TAG, "SETTING VARIO MACHINE TYPE");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.setVarioMachineType();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void setWallPoweredType() {
		Logger.getInstance().logDebug(TAG, "SETTING WALL POWERED POWER TYPE");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.setWallPoweredType();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void setSelfPoweredType() {
		Logger.getInstance().logDebug(TAG, "SETTING SELF POWERED POWER TYPE");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.setSelfPoweredType();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void getMachineType() {
		Logger.getInstance().logDebug(TAG, "GETTING MACHINE TYPE");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.getMachineType();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public void getPowerType() {
		Logger.getInstance().logDebug(TAG, "GETTING POWER TYPE");

		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.getPowerType();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendBuzz() {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendBuzz();
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendKeyButton() {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendKeyButton();
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendTextXScreen(String value) {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendTextXScreen(value);
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendTestScreenOff() {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendTestScreenOff();
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendGetCardioricevitoreFw() {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendGetCardioricevitoreFw();
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendReadWifiConfig() {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendReadWifiConfig();
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendGetDate() {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendGetDate();
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendGetEmergency() {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendGetEmergency();
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendGetCardioHRValue() {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendGetCardioHRValue();
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendStartCardioReceiver() {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendStartCardioReceiver();
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendStopCardioReceiver() {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendStopCardioReceiver();
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendGetSupercapStatus() {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendGetSupercapStatus();
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendSetSpeed(String value) {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendSetSpeed(value);
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendSetGradient(String percentage) {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendSetGradient(percentage);
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendReadInclinationTableCRC() {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendReadInclinationTableCRC();
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@JavascriptInterface
	public boolean sendKeyboardStatus() {
		this.mSystemProxy.registerForNotification(this.mListener);
		try {
			this.mSystemProxy.sendKeyboardStatus();
			return true;
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			return false;
		}
	}
	// endregion


}
