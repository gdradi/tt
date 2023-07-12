package com.technogym.mycycling.bridges.android;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.technogym.mycycling.Configuration;
import com.technogym.mycycling.activities.MyCyclingTestActivity;
import com.technogym.mycycling.ble.models.BleDevice;
import com.technogym.mycycling.connection.controllers.IEquipmentController;
import com.technogym.mycycling.network.NetworkFileStreamManager;

import java.util.Calendar;
import java.util.GregorianCalendar;

import it.spot.android.logger.domain.Logger;
import it.spot.android.logger.domain.models.LogLevels;

public class MyCyclingAndroidBridge extends AndroidBridge implements IMyCyclingAndroidBridge {

    protected IEquipmentController mEquipmentController;

    // { Construction

    protected MyCyclingAndroidBridge(final Activity activity, final IEquipmentController equipmentController) {
        super(activity);
        mEquipmentController = equipmentController;
    }

    @JavascriptInterface
    public static IMyCyclingAndroidBridge create(final Activity activity, final IEquipmentController equipmentController) {
        return new MyCyclingAndroidBridge(activity, equipmentController);
    }

    // }

    // { Overriding methods

    @Override
    @JavascriptInterface
    public void log(final String level, final String message) {

        final LogLevels ll = LogLevels.valueOf(level);

        switch (ll) {
            case ERROR:
                Logger.getInstance().logError(this.getClass().getSimpleName(), message);
                break;
            case WARNING:
                Logger.getInstance().logWarning(this.getClass().getSimpleName(), message);
                break;
            case DEBUG:
                Logger.getInstance().logDebug(this.getClass().getSimpleName(), message);
                break;
            default:
                Logger.getInstance().logVerbose(this.getClass().getSimpleName(), message);
                break;
        }
    }

    @Override
    @JavascriptInterface
    public void executeNetworkBackupLog() {
        /*
    	 * Update
    	 * Date le problematiche di Android nella copia su shared che necessitano di autenticazione
    	 * si è deciso di non effettuare piu la copia del log su cartella di rete ma di effettuarlo
    	 * solamente nella cartella di backup presente all'interno del device (in locale)
    	 */
        Log.i(this.getClass().getSimpleName(), "Executing file log backup..");
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Starting Log File Backup");
        NetworkFileStreamManager man = new NetworkFileStreamManager();

        String localFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String localFileName = "collaudi.txt";

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
        String serialN = prefs.getString("EQUIPMENT_SERIAL_NUMBER", "test");
        //String username = prefs.getString("USERNAME", "usertest");

        GregorianCalendar date = new GregorianCalendar();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hours = date.get(Calendar.HOUR);
        int minutes = date.get(Calendar.MINUTE);
        int seconds = date.get(Calendar.SECOND);

        String remoteFileName = serialN + "_" + year + month + day + "_" + hours + "-" + minutes + "-" + seconds + ".txt";
        man.setLocalFile(localFolderPath, localFileName, "/");
        man.backupLocalFile(localFolderPath + "/backups", "" + remoteFileName, "/");
        Log.i(this.getClass().getSimpleName(), "File log backup completed");
    }

    @Override
    @JavascriptInterface
    public void setUser(String username) {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "PRE SET User -> " + username);
        // add username of the current user as shared preference
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("USERNAME", username);
        editor.commit();
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "User -> " + username);
    }

    @Override
    @JavascriptInterface
    public void setEquipmentSerialNumer(String serialNumber) {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "PRE SET Equipment Serial Number -> " + serialNumber);
        // add serial number of the current equipment as shared preference
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("EQUIPMENT_SERIAL_NUMBER", serialNumber);
        editor.commit();
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Equipment Serial Number -> " + serialNumber);
    }

    @Override
    @JavascriptInterface
    public void setEquipmentBluetoothName(String equipmentBluetoothName) {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "PRE SET Equipment Bluetooth Name -> " + equipmentBluetoothName);
        // add serial number of the current equipment as shared preference
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("EQUIPMENT_BLUETOOTH_NAME", equipmentBluetoothName);
        editor.commit();
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Equipment Bluetooth Name -> " + equipmentBluetoothName);
    }

    @Override
    @JavascriptInterface
    public void searchDevice(String deviceName) {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Searching the device BLE -> " + deviceName);
        MyCyclingTestActivity curActivity = (MyCyclingTestActivity)this.mActivity;
        mEquipmentController.searchDevice(deviceName);
    }

    @Override
    @JavascriptInterface
    public boolean connectToBleDevice(String deviceName) {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Connectioning to the device BLE: " + deviceName);
        MyCyclingTestActivity curActivity = (MyCyclingTestActivity)this.mActivity;
        BleDevice targetDevice = mEquipmentController.getBleDevice(deviceName);
        //Log.i(this.getClass().getSimpleName(), "Target device BLE -> " + (targetDevice != null));
        //Log.i(this.getClass().getSimpleName(), "Target device BLE -> " + targetDevice.getDevice().getAddress());
        //Log.i(this.getClass().getSimpleName(), "Target device BLE -> " + targetDevice.getDevice().getName());
        boolean resConn = mEquipmentController.connect(targetDevice.getDevice().getAddress());
        Log.i(this.getClass().getSimpleName(), "Connection to the device BLE Result: " + resConn);
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Connection to the device BLE Result: " + resConn);
        return resConn;
    }

    @Override
    @JavascriptInterface
    public void setEquipmentFirmwareVersion(String equipmentVersion) {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "PRE SET Equipment Firmware Version -> " + equipmentVersion);
        // add version of the current equipment as shared preference
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("EQUIPMENT_FIRMWARE_VERSION", equipmentVersion);
        editor.commit();
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Equipment Firmware Version -> " + equipmentVersion);
    }

    // }

    // { Public methods

    @JavascriptInterface
    public void checkUpgradeRequired(String currentVersion) {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Checking firmware upgrade required...");
        ((MyCyclingTestActivity)mActivity).checkFirmwareUpgrade(currentVersion);
    }

    @JavascriptInterface
    public void downloadFirmware(String currentVersion) {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Downloading firmware...");
        ((MyCyclingTestActivity)mActivity).downloadFirmware(currentVersion);
    }

    // }
}
