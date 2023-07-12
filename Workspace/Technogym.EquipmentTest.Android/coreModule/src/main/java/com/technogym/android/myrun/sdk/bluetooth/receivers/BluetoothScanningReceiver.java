package com.technogym.android.myrun.sdk.bluetooth.receivers;

import com.technogym.android.myrun.sdk.connection.utils.Constants;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

// FOSCHINI FEDERICO

public class BluetoothScanningReceiver extends BroadcastReceiver {

	private final Handler mHandler;

	// { Construction

	protected BluetoothScanningReceiver(final Handler handler) {
		super();

		this.mHandler = handler;
	}

	public static BluetoothScanningReceiver create(final Handler handler) {
		return new BluetoothScanningReceiver(handler);
	}

	// }

	// { BroadcastReceiver abstract methods implementation

	@Override
	public void onReceive(final Context context, final Intent intent) {
		if (intent.getAction().equals(BluetoothDevice.ACTION_FOUND)) {

			// FOUND A NEW DEVICE

			final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			try {
				if (device.getName() != null && (device.getName().contains("MyRun") || device.getName().contains("UNICO"))) {
					final Message msg = Message.obtain();
					msg.what = Constants.BLUETOOTH_SCAN_DEVICE_FOUND;
					msg.obj = device;
					msg.arg1 = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
					
					mHandler.sendMessage(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// }

}
