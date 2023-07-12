package com.technogym.android.myrun.sdk.bluetooth.receivers;

import com.technogym.android.myrun.sdk.connection.utils.Constants;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class BluetoothBondingReceiver extends BroadcastReceiver {

	private final Handler mHandler;

	// { Construction

	protected BluetoothBondingReceiver(final Handler handler) {
		super();

		this.mHandler = handler;
	}

	public static BluetoothBondingReceiver create(final Handler handler) {
		return new BluetoothBondingReceiver(handler);
	}

	// }

	// { BroadcastReceiver abstract methods implementation

	@Override
	public void onReceive(final Context context, final Intent intent) {

		if (intent.getAction().equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {

			// DEVICE PAIRED

			final Message msg = Message.obtain();
			final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

			final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
			final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

			if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
				Log.e("BLUETOOTH_SCANNING_RECEIVER", "BONDED WITH " + device.getName());

				msg.what = Constants.BLUETOOTH_DEVICE_PAIRED;
				msg.arg1 = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.BOND_NONE);
				msg.obj = device;
				new Thread(){
					public void run() {
						try {
							Thread.sleep(2000);
							mHandler.sendMessage(msg);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					};
				}.start();

			} else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED) {
				Log.e("BLUETOOTH_SCANNING_RECEIVER", "UNBONDED WITH " + device.getName());
			} else {
				Log.e("BLUETOOTH_SCANNING_RECEIVER", "UNDEFINED STATE BONDING WITH " + device.getName());
			}
		}
	}

	// }

}
