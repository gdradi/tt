package com.technogym.android.myrun.sdk.android.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BluetoothManagerService extends Service {

	// { Service methods overriding

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	// }

}
