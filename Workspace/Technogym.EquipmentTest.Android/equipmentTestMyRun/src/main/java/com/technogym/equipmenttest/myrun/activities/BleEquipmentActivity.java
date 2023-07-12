package com.technogym.equipmenttest.myrun.activities;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.technogym.android.myrun.sdk.R;
import com.technogym.android.myrun.sdk.android.activities.EquipmentActivity;
import com.technogym.android.myrun.sdk.bluetooth.listeners.BluetoothScanningListener;
import com.technogym.android.myrun.sdk.connection.utils.Constants;
import com.technogym.equipmenttest.myrun.EquipmentTestApplication;
import com.technogym.equipmenttest.myrun.connectors.MyRunUartConnector;

public abstract class BleEquipmentActivity extends EquipmentActivity<MyRunUartConnector> implements
		BluetoothScanningListener {

	// { Activity methods overriding

	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
//
//
//
//		Log.i("BTEquipActivity", "WRITE_EXTERNAL_STORAGE: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED));
//		Log.i("BTEquipActivity", "READ_CONTACTS: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED));
//		Log.i("BTEquipActivity", "INTERNET: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED));
//		Log.i("BTEquipActivity", "CHANGE_WIFI_STATE: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED));
//		Log.i("BTEquipActivity", "ACCESS_WIFI_STATE: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED));
//		Log.i("BTEquipActivity", "VIBRATE: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED));
//		Log.i("BTEquipActivity", "CAMERA: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED));
//		Log.i("BTEquipActivity", "WAKE_LOCK: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED));
//		Log.i("BTEquipActivity", "BLUETOOTH_ADMIN: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED));
//		Log.i("BTEquipActivity", "BLUETOOTH: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED));
//		Log.i("BTEquipActivity", "ACCESS_NETWORK_STATE: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED));
//		Log.i("BTEquipActivity", "ACCESS_COARSE_LOCATION: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED));
//		Log.i("BTEquipActivity", "ACCESS_FINE_LOCATION: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED));
//
//		// require permissions
//		if((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
//				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
//				//|| ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_HISTORY_BOOKMARKS) != PackageManager.PERMISSION_GRANTED)
//				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
//				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
//				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED)
//				//|| ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.FLASHLIGHT) != PackageManager.PERMISSION_GRANTED)
//				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
//				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED)
//				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)
//				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)
//				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED)) {
//			ActivityCompat.requestPermissions(BluetoothEquipmentActivity.this,
//					new String[]{
//							Manifest.permission.READ_CONTACTS,
//							//Manifest.permission.READ_HISTORY_BOOKMARKS,
//							Manifest.permission.CHANGE_WIFI_STATE,
//							Manifest.permission.ACCESS_NETWORK_STATE,
//							Manifest.permission.BLUETOOTH,
//							Manifest.permission.BLUETOOTH_ADMIN,
//							Manifest.permission.INTERNET,
//							Manifest.permission.CAMERA,
//							Manifest.permission.WRITE_EXTERNAL_STORAGE,
//							Manifest.permission.VIBRATE,
//							Manifest.permission.ACCESS_COARSE_LOCATION,
//							Manifest.permission.ACCESS_FINE_LOCATION,
//							//Manifest.permission.FLASHLIGHT,
//							Manifest.permission.WAKE_LOCK
//					}, 1);
//		}
//
//
		this.getConnectionController().registerBluetoothScanListener(this);
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		if (requestCode == Constants.BLUETOOTH_REQUEST_ENABLE_CODE) {
			if (this.getConnectionController() != null) {
				this.getConnectionController().onBluetoothAdapterTurnedOn(resultCode == RESULT_OK);
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	@Override
	protected void onDestroy() {
		getConnectionController().stopScanningDevices();
		getConnectionController().unregisterBluetoothScanListener(this);
		super.onDestroy();
	}

	// }

	@Override
	public void onConnectionFailed() {
		super.onConnectionFailed();

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle(R.string.bt_connection_error_label);
		builder.setMessage(R.string.bt_connection_failed_label);
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	// { BluetoothScanningListener implementation

	@Override
	public abstract void onBluetoothScanStarted(final boolean isReboot);

	@Override
	public abstract void onBluetoothScanTerminated();

	@Override
	public abstract void onBluetoothDeviceFound(final BluetoothDevice device);

	// }

	// { Abstract methods overriding

	@Override
	protected MyRunUartConnector getConnectionControllerFromApplication() {
		final EquipmentTestApplication application = (EquipmentTestApplication) this.getApplication();
		return application.getConnectionController();
	}

	// }

}
