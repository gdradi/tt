package com.technogym.mycycling.ble.utilities;

import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;

/***
 * Bluetooth Low Energy Utilities
 *
 * @author Federico Foschini
 * @version 1.0
 */
public class BleUtils {

    private BleUtils() { }

    /** check if BLE Supported device */
    public static boolean isBLESupported(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    /** get BluetoothManager */
    public static BluetoothManager getManager(Context context) {
        return (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
    }
}
