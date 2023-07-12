package com.technogym.android.myrun.sdk.bluetooth.threads;

import java.util.Timer;
import java.util.TimerTask;

import com.technogym.android.myrun.sdk.bluetooth.receivers.BluetoothScanningReceiver;
import com.technogym.android.myrun.sdk.connection.utils.Constants;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;

public class BluetoothScanningThread extends Thread {

	private final Handler mHandler;
	private final BluetoothAdapter mBluetoothAdapter;
	private final Context mApplicationContext;
	private BluetoothScanningReceiver mScanReceiver;

	private Timer mTimer;
	private TimerTask mTimerTask;

	// { Construction

	protected BluetoothScanningThread(final BluetoothAdapter bluetoothAdapter, final Context applicationContext,
			final Handler handler) {
		super();

		mTimer = null;
		mTimerTask = null;

		this.mScanReceiver = null;
		this.mHandler = handler;
		this.mBluetoothAdapter = bluetoothAdapter;
		this.mApplicationContext = applicationContext;
	}

	public static BluetoothScanningThread create(final BluetoothAdapter bluetoothAdapter,
			final Context applicationContext, final Handler handler) {
		return new BluetoothScanningThread(bluetoothAdapter, applicationContext, handler);
	}

	// }

	// { Thread methods overriding

	@Override
	public void run() {
		this.mScanReceiver = BluetoothScanningReceiver.create(this.mHandler);
		this.mApplicationContext.registerReceiver(this.mScanReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));

		this.mHandler.sendEmptyMessage(Constants.BLUETOOTH_SCAN_STARTED);

		this.mBluetoothAdapter.startDiscovery();

		mTimer = new Timer();
		mTimerTask = new TimerTask() {

			@Override
			public void run() {
				cancel();
			}
		};

		mTimer.schedule(mTimerTask, Constants.BLUETOOTH_MAX_SCANNING_PERIOD);
	}

	// }

	public void cancel() {
		try {
			this.mApplicationContext.unregisterReceiver(this.mScanReceiver);
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		}

		if (mTimer != null) {
			mTimerTask.cancel();
			mTimer.cancel();
			mTimer.purge();

			mTimer = null;
			mTimerTask = null;
		}

		this.mBluetoothAdapter.cancelDiscovery();

		this.mHandler.sendEmptyMessage(Constants.BLUETOOTH_SCAN_TERMINATED);
	}

}
