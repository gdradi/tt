package com.technogym.android.myrun.sdk.bluetooth.dispatchers;

import java.util.List;

import com.technogym.android.myrun.sdk.bluetooth.listeners.BluetoothScanningListener;
import com.technogym.android.myrun.sdk.connection.listeners.EquipmentConnectionListener;

import android.bluetooth.BluetoothDevice;

public class BluetoothConnectionNotifier implements IBluetoothConnectionNotifier {

	private final List<BluetoothScanningListener> mScanListeners;
	private final List<EquipmentConnectionListener> mConnListeners;

	// { Construction

	protected BluetoothConnectionNotifier(final List<BluetoothScanningListener> scanListeners,
			final List<EquipmentConnectionListener> connListeners) {

		super();

		this.mScanListeners = scanListeners;
		this.mConnListeners = connListeners;
	}

	public static IBluetoothConnectionNotifier create(final List<BluetoothScanningListener> scanListeners,
			final List<EquipmentConnectionListener> connListeners) {

		return new BluetoothConnectionNotifier(scanListeners, connListeners);
	}

	// }

	// { IBluetoothConnectionNotifier implementation
	@Override
	public void  onLowKitUpgraded(boolean result){
		for (BluetoothScanningListener listener : this.mScanListeners) {
			listener.onLowKitUpgraded(result);
		}
	}

	@Override
	public void onHighKitUpgraded(boolean result) {
		for (BluetoothScanningListener listener : this.mScanListeners) {
			listener.onHighKitUpgraded(result);
		}
	}


	@Override
	public void onBluetoothScanStarted(final boolean isReboot) {
		for (BluetoothScanningListener listener : this.mScanListeners) {
			listener.onBluetoothScanStarted(isReboot);
		}
	}

	@Override
	public void onBluetoothScanTerminated() {
		for (BluetoothScanningListener listener : this.mScanListeners) {
			listener.onBluetoothScanTerminated();
		}
	}

	@Override
	public void onBluetoothDeviceFound(final BluetoothDevice device) {
		for (BluetoothScanningListener listener : this.mScanListeners) {
			listener.onBluetoothDeviceFound(device);
		}
	}

	@Override
	public void onMessageReceived(final String message) {
		for (EquipmentConnectionListener listener : this.mConnListeners) {
			listener.onMessageReceived(message);
		}
	}

	@Override
	public void onConnectionEstablished() {
		for (EquipmentConnectionListener listener : this.mConnListeners) {
			listener.onConnectionEstablished();
		}
	}

	@Override
	public void onConnectionTerminated() {
		for (EquipmentConnectionListener listener : this.mConnListeners) {
			listener.onConnectionTerminated();
		}
	}

	@Override
	public void onConnectionInterrupted() {
		for (EquipmentConnectionListener listener : this.mConnListeners) {
			listener.onConnectionInterrupted();
		}
	}

	@Override
	public void onConnectionFailed() {
		for (EquipmentConnectionListener listener : this.mConnListeners) {
			listener.onConnectionFailed();
		}
	}

	// }

}
