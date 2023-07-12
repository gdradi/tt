package com.technogym.android.myrun.sdk.bluetooth.controllers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.technogym.android.myrun.sdk.android.activities.BluetoothEquipmentActivity;
import com.technogym.android.myrun.sdk.bluetooth.dispatchers.BluetoothConnectionNotifier;
import com.technogym.android.myrun.sdk.bluetooth.dispatchers.IBluetoothConnectionNotifier;
import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.bluetooth.handlers.BluetoothMessageHandler;
import com.technogym.android.myrun.sdk.bluetooth.listeners.BluetoothScanningListener;
import com.technogym.android.myrun.sdk.bluetooth.receivers.BluetoothBondingReceiver;
import com.technogym.android.myrun.sdk.bluetooth.threads.BluetoothConnectedThread;
import com.technogym.android.myrun.sdk.bluetooth.threads.BluetoothConnectionThread;
import com.technogym.android.myrun.sdk.bluetooth.threads.BluetoothScanningThread;
import com.technogym.android.myrun.sdk.connection.controllers.EquipmentController;
import com.technogym.android.myrun.sdk.connection.models.EquipmentConnectionState;
import com.technogym.android.myrun.sdk.connection.utils.Constants;
import com.technogym.android.myrun.sdk.support.Logger;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

/**
 * @author Federico Foschini
 * @version 1.0
 * */
public class BluetoothController extends EquipmentController implements IBluetoothController {

	private static final String LOGTAG = "MYR_BLUETOOTH_CONTROLLER";

	private final List<BluetoothScanningListener> mScanListeners;
	private final BluetoothAdapter mBluetoothAdapter;

	private BluetoothEquipmentActivity mCurrentActivity;

	private BluetoothBondingReceiver mBondReceiver;

	private BluetoothDevice mConnectedDevice;

	private final BluetoothMessageHandler mHandler;

	private BluetoothScanningThread mScanningThread;
	private BluetoothConnectionThread mConnectionThread;
	private BluetoothConnectedThread mConnectedThread;

	// { Construction

	protected BluetoothController(final BluetoothEquipmentActivity currentActivity,
			final EquipmentConnectionState initialState) {
		super(initialState);

		this.mScanListeners = new ArrayList<BluetoothScanningListener>();

		this.mCurrentActivity = currentActivity;

		this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		this.mConnectedDevice = null;

		this.mScanningThread = null;
		this.mConnectionThread = null;
		this.mConnectedThread = null;

		final IBluetoothConnectionNotifier notifier = BluetoothConnectionNotifier.create(
			this.mScanListeners, this.mConnectionListeners);

		this.mHandler = BluetoothMessageHandler.create(this, notifier);
	}

	public static IBluetoothController create(final BluetoothEquipmentActivity currentActivity,
			final EquipmentConnectionState initialState) {
		return new BluetoothController(currentActivity, initialState);
	}

	public static IBluetoothController createWithDefaults(final BluetoothEquipmentActivity currentActivity) {
		return new BluetoothController(currentActivity, EquipmentConnectionState.IDLE);
	}

	public static IBluetoothController createEmpty() {
		return createWithDefaults(null);
	}

	// }

	// { IBluetoothController implementation

	@Override
	public final void setup(final boolean tryAutoconnect) {

		if (this.mConnectedThread != null && this.mConnectedThread.isAlive()) {
			Logger.e(LOGTAG, "Bluetooth connection is already up.");
		} else {

			this.setConnectionState(EquipmentConnectionState.LISTEN);

			if (this.mBluetoothAdapter.isEnabled()) {
				Logger.e(LOGTAG, "Bluetooth adapter is already enabled.");
				if (tryAutoconnect) {
					this.searchForEquipmentToConnect();
				} else {
					this.startScanningDevices();
				}
			} else {
				Logger.e(LOGTAG, "Bluetooth adapter is not already enabled.");
				Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				this.mCurrentActivity.startActivityForResult(intent, Constants.BLUETOOTH_REQUEST_ENABLE_CODE);
			}
		}
	}

	@Override
	public void removePairedDevices() {
        Set<BluetoothDevice> pairedDevice = BluetoothAdapter.getDefaultAdapter().getBondedDevices();            
        if(pairedDevice.size()>0)
        {
            for(BluetoothDevice device : pairedDevice)
            {
            	if(device.getName().contains("MyRun") || device.getName().contains("UNICO")) {
					removeBond(device);
	    			Log.i("BLUETOOTH_CONTROLLER", "Remove bonding with " + device.getName());
            	}
            }
        }
    }

	@Override
	public void terminatePendingActions() {
		this.resetBluetoothScanningThread();
	}

	@Override
	public void disconnect() {
		this.terminatePendingActions();
		this.resetBluetoothConnectionThread();
		this.resetBluetoothConnectedThread();
		this.setConnectionState(EquipmentConnectionState.IDLE);
	}

	@Override
	public void restart() {
		this.disconnect();
		this.setup(true);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	public boolean bondToDevice(final BluetoothDevice device) {
		if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
			connectToDevice(device);
			return true;
		} else {
			mBondReceiver = BluetoothBondingReceiver.create(mHandler);
			mCurrentActivity.registerReceiver(
				mBondReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
			Log.e("BLUETOOTH_CONTROLLER", "Started bonding with " + device.getName());
			return device.createBond();
		}
	}

	@Override
	public boolean removeBond(final BluetoothDevice device)
    {  
		try {
	        Method removeBondMethod = Class.forName("android.bluetooth.BluetoothDevice").getMethod("removeBond");  
	        Boolean returnValue = (Boolean) removeBondMethod.invoke(device);  
	        return returnValue.booleanValue();  
		} catch (Exception e) {
			Log.e("BLUETOOTH_CONTROLLER", "Remove bonding with " + device.getName() + " - ERROR: " + e.getMessage());
		}
		return false;
    }

	@Override
	public List<BluetoothDevice> getBoundedDevices() {
		final List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
		if (this.mBluetoothAdapter != null) {
			devices.addAll(this.mBluetoothAdapter.getBondedDevices());
		}
		return devices;
	}

	@Override
	public final void startScanningDevices() {
		if (this.mScanningThread == null) {
			this.mScanningThread = BluetoothScanningThread.create(
				this.mBluetoothAdapter, this.mCurrentActivity, this.mHandler);
			this.mScanningThread.start();
		}
	}

	@Override
	public final void stopScanningDevices() {
		this.resetBluetoothScanningThread();
	}

	@Override
	public final void onBluetoothAdapterTurnedOn(final boolean turnedOn) {
		if (turnedOn) {
			Logger.e(LOGTAG, "Bluetooth adapter has been enabled.");
			this.startScanningDevices();
		} else {
			Logger.e(LOGTAG, "Bluetooth adapter has not been enabled.");
		}
	}

	@Override
	public final BluetoothDevice getConnectedDevice() {
		return this.mConnectedDevice;
	}

	@Override
	public final void connectToDevice(final BluetoothDevice device) {
		this.stopScanningDevices();

		if (mBondReceiver != null) {
			mCurrentActivity.unregisterReceiver(mBondReceiver);
			mBondReceiver = null;
		}

		this.mConnectionThread = BluetoothConnectionThread.create(this.mHandler, device);
		this.mConnectionThread.start();
	}

	@Override
	public final void connectedToDevice(final BluetoothSocket socket, final BluetoothDevice device) {
		this.resetBluetoothConnectionThread();

		this.mConnectedDevice = device;

		this.mConnectedThread = BluetoothConnectedThread.create(this.mHandler, socket);
		this.mConnectedThread.start();
	}

	@Override
	public void sendMessage(String message) throws WriteNotAllowedException {
		if (mState == EquipmentConnectionState.CONNECTED) {
			this.mConnectedThread.write(message);
		} else {
			throw new WriteNotAllowedException();
		}
	}

	@Override
	public final void disconnectFromDevice(final BluetoothDevice device) {
		// TODO: implements this functionality
	}

	@Override
	public final void registerBluetoothScanListener(final BluetoothScanningListener listener) {
		if (!this.mScanListeners.contains(listener)) {
			this.mScanListeners.add(listener);
		}
	}

	@Override
	public void unregisterBluetoothScanListener(final BluetoothScanningListener listener) {
		if (mScanListeners.contains(listener)) {
			this.mScanListeners.remove(listener);
		}
	}

	@Override
	public void setCurrentActivity(final BluetoothEquipmentActivity activity) {
		this.mCurrentActivity = activity;
	}

	// }

	// { Private & protected methods

	private void searchForEquipmentToConnect() {
		final List<BluetoothDevice> pairedEquipments = new ArrayList<BluetoothDevice>();
		final Iterator<BluetoothDevice> devices = this.mBluetoothAdapter.getBondedDevices().iterator();

		while (devices.hasNext()) {
			final BluetoothDevice device = devices.next();
			if (device.getName().contains("MyRun") || device.getName().contains("UNICO")) {
				Logger.e(LOGTAG, "Found a myRun device while iterating bounded devices");
				
				pairedEquipments.add(device);
			}
		}

		switch (pairedEquipments.size()) {
			case 1:
				Logger.e(LOGTAG, "Trying to connect to the only paired myrun that was found.");
				this.connectToDevice(pairedEquipments.get(0));
				break;
			default:
				Logger.e(LOGTAG, "Starting to scan all available devices, looking for a my run equipment.");
				this.startScanningDevices();
				break;
		}

		// TODO: step 1 - check if a myrun device is already connected
		// TODO: step 2 - check if a myrun device is paired and turned on so that a connection attempt can be done
		// TODO: step 3 - discover devices and show a modal dialog with equipments list
	}

	private void resetBluetoothScanningThread() {
		if (this.mScanningThread != null) {
			this.mScanningThread.cancel();
			this.mScanningThread = null;
		}
	}

	private void resetBluetoothConnectionThread() {
		if (this.mConnectionThread != null) {
			this.mConnectionThread.cancel();
			this.mConnectionThread = null;
		}
	}

	private void resetBluetoothConnectedThread() {
		if (this.mConnectedThread != null) {
			this.mConnectedThread.cancel();
			this.mConnectedThread = null;
		}
	}

	// }

}
