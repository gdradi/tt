package com.technogym.android.myrun.sdk.bluetooth.handlers;

import java.util.List;

import com.technogym.android.myrun.sdk.bluetooth.controllers.IBluetoothController;
import com.technogym.android.myrun.sdk.bluetooth.dispatchers.IBluetoothConnectionNotifier;
import com.technogym.android.myrun.sdk.connection.models.EquipmentConnectionState;
import com.technogym.android.myrun.sdk.connection.utils.Constants;
import com.technogym.android.myrun.sdk.support.Logger;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

/**
 * @author Federico Foschini
 * @version 1.0
 * */
public final class BluetoothMessageHandler extends Handler {

	private static final String LOGTAG = "BluetoothMessageHandler";

	private final IBluetoothController mController;
	private final IBluetoothConnectionNotifier mNotifier;
	
	private BluetoothDevice nearestDevice;
	private int nearestRSSI = Integer.MIN_VALUE;
	private final static int MIN_RSSI_VALUE = -45;
	//private final static int MIN_RSSI_VALUE = -99999; // *DEBUG ONLY* Estende il range di rilevazione della macchina alla capacità massima
	private final static int MAX_RSSI_VALUE = 0;
	
	// { Construction

	protected BluetoothMessageHandler(final IBluetoothController controller, final IBluetoothConnectionNotifier notifier) {
		super();

		this.mController = controller;
		this.mNotifier = notifier;
	}

	public static BluetoothMessageHandler create(final IBluetoothController controller,
			final IBluetoothConnectionNotifier notifier) {
		return new BluetoothMessageHandler(controller, notifier);
	}

	// }

	@Override
	public void handleMessage(final Message msg) {
		switch (msg.what) {
			case Constants.BLUETOOTH_ADAPTER_TURNED_OFF:
				Logger.w(LOGTAG, "The bluetooth adapter is turned off. Please turn it on.");
				break;
			case Constants.BLUETOOTH_ADAPTER_TURNED_ON:
				Logger.w(LOGTAG, "The bluetooth adapter is correctly turned on.");
				break;
			case Constants.BLUETOOTH_SCAN_STARTED:
				
				nearestDevice = null;
				nearestRSSI = Integer.MIN_VALUE;
				
				this.mNotifier.onBluetoothScanStarted(false);
				break;
			case Constants.BLUETOOTH_SCAN_TERMINATED:
				this.mNotifier.onBluetoothScanTerminated();
				break;
			case Constants.BLUETOOTH_SCAN_DEVICE_FOUND:
				
				if(msg.arg1 >= MIN_RSSI_VALUE && msg.arg1 <= MAX_RSSI_VALUE){
					if(nearestDevice != null && nearestDevice.getName() != ((BluetoothDevice) msg.obj).getName()) {
						if(nearestRSSI < msg.arg1) {
							nearestRSSI = msg.arg1;
							nearestDevice = (BluetoothDevice) msg.obj;
							this.mNotifier.onBluetoothDeviceFound(nearestDevice);
						}
					} else if(nearestDevice == null) {
						nearestRSSI = msg.arg1;
						nearestDevice = (BluetoothDevice) msg.obj;
						this.mNotifier.onBluetoothDeviceFound(nearestDevice);
					}
				}
				
				//this.mNotifier.onBluetoothDeviceFound((BluetoothDevice) msg.obj);
				break;
			case Constants.BLUETOOTH_DEVICE_PAIRED:
				this.mController.connectToDevice((BluetoothDevice) msg.obj);
				break;
			case Constants.CONNECTION_ESTABLISHED:
				Logger.w(LOGTAG, "A bluetooth connection has been established.");
				this.mController.setConnectionState(EquipmentConnectionState.CONNECTED);
				this.onBluetoothConnectionEstablished(msg);
				break;
			case Constants.CONNECTION_FAILED:
				Logger.w(LOGTAG, "An attempt to build a bluetooth connection failed.");
				this.mController.setConnectionState(EquipmentConnectionState.LISTEN);
				this.mNotifier.onConnectionFailed();
				break;
			case Constants.CONNECTION_INTERRUPTED:
				Logger.w(LOGTAG, "The bluetooth connection was interrupted..");
				this.mController.setConnectionState(EquipmentConnectionState.LISTEN);
				this.mNotifier.onConnectionInterrupted();
				break;
			case Constants.CONNECTION_CLOSED:
				this.mController.setConnectionState(EquipmentConnectionState.LISTEN);
				this.mNotifier.onConnectionTerminated();
				break;
			case Constants.MESSAGE_RECEIVED:
				this.mNotifier.onMessageReceived((String) msg.obj);
				break;
			case Constants.MESSAGE_SENT:
				// INF: Empty
				break;
			default:
				break;
		}
	}

	// { Private and protected methods

	private void onBluetoothConnectionEstablished(final Message msg) {
		final List<?> params = (List<?>) msg.obj;
		final BluetoothSocket socket = (BluetoothSocket) params.get(0);
		final BluetoothDevice device = (BluetoothDevice) params.get(1);

		this.mController.connectedToDevice(socket, device);

		this.mNotifier.onConnectionEstablished();
	}

	// }

}
