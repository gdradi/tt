package com.technogym.android.myrun.sdk.bluetooth.threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.technogym.android.myrun.sdk.connection.utils.Constants;
import com.technogym.android.myrun.sdk.support.Logger;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class BluetoothConnectionThread extends Thread {

	private static final String LOGTAG = "BluetoothConnectionThread";

	private final BluetoothDevice mDevice;
	private final Handler mHandler;

	// { Construction

	protected BluetoothConnectionThread(final Handler handler, final BluetoothDevice device) {
		super();

		this.mHandler = handler;
		this.mDevice = getBondendDevice(device);
	}

	public static BluetoothConnectionThread create(final Handler handler, final BluetoothDevice device) {
		return new BluetoothConnectionThread(handler, device);
	}

	// }

	// { Thread methods override

	@Override
	public void run() {

		Log.e(LOGTAG, "running connected thread.");
		
		BluetoothSocket socket = null;
		try {
			socket = mDevice.createRfcommSocketToServiceRecord(mDevice.getUuids()[0].getUuid());

			socket.connect();

			this.onBluetoothDeviceConnected(socket, this.mDevice);

			Log.e(LOGTAG, "running connected thread.");

			return;

		} catch (NullPointerException ex) {
			Log.e(LOGTAG, "No UUID. Null pointer exception.");
		} catch (IOException ex) {
			Log.e(LOGTAG, "An IO Exception occurred.");
		}

		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.mHandler.sendEmptyMessage(Constants.CONNECTION_FAILED);
	}

	// }

	public void cancel() {
		// do stuff (?)
	}

	// { Private and protected methods

	private BluetoothDevice getBondendDevice(final BluetoothDevice pairedDevice) {
		for (BluetoothDevice device : BluetoothAdapter.getDefaultAdapter().getBondedDevices()) {
			if (pairedDevice.getAddress().equals(device.getAddress())) {
				return BluetoothAdapter.getDefaultAdapter().getRemoteDevice(device.getAddress());
			}
		}
		return null;
	}

	private void onBluetoothDeviceConnected(final BluetoothSocket socket, final BluetoothDevice device) {
		Logger.e(LOGTAG, "Connected to the socket");

		final List<Object> params = new ArrayList<Object>();
		params.add(socket);
		params.add(device);

		final Message msg = Message.obtain();
		msg.what = Constants.CONNECTION_ESTABLISHED;
		msg.obj = params;

		this.mHandler.sendMessage(msg);
	}

	// }

}
