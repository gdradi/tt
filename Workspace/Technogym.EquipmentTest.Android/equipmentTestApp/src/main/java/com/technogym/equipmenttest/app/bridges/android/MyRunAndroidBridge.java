package com.technogym.equipmenttest.app.bridges.android;

import java.util.Calendar;
import java.util.GregorianCalendar;

import it.spot.android.logger.domain.Logger;
import it.spot.android.logger.domain.models.LogLevels;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.webkit.JavascriptInterface;

import com.technogym.equipmenttest.app.network.file.manager.NetworkFileStreamManager;
import com.technogym.equipmenttest.library.bridges.android.AndroidBridge;

public class MyRunAndroidBridge extends AndroidBridge implements IMyRunAndroidBridge {

	// { Construction

	protected MyRunAndroidBridge(final Activity activity) {
		super(activity);

		// reset shared preferences
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
//		SharedPreferences.Editor editor = preferences.edit();
//		editor.clear();
//
//		editor.putString("USERNAME", "USERNAME");
//		editor.putString("EQUIPMENT_SERIAL_NUMBER", "EQUIPMENT_SERIAL_NUMBER");
//		editor.putString("EQUIPMENT_FIRMWARE_VERSION", "EQUIPMENT_FIRMWARE_VERSION");
//		
//		editor.commit();
	}

	public static IMyRunAndroidBridge create(final Activity activity) {
		return new MyRunAndroidBridge(activity);
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
	public void executeNetworkBackupLog() {
    	/*
    	 * Update
    	 * Date le problematiche di Android nella copia su shared che necessitano di autenticazione
    	 * si è deciso di non effettuare piu la copia del log su cartella di rete ma di effettuarlo
    	 * solamente nella cartella di backup presente all'interno del device (in locale)
    	 */
		
		Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Starting Log File Backup");
	    NetworkFileStreamManager man = new NetworkFileStreamManager();
	    
	    String localFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath();
	    String localFileName = "collaudi.txt";
	    
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mActivity);
	    String serialN = prefs.getString("EQUIPMENT_SERIAL_NUMBER", "test");
	    //String username = prefs.getString("USERNAME", "usertest");
	    
	    String remoteFileName = serialN + ".txt";
        //GregorianCalendar date = new GregorianCalendar();
	    //int year = date.get(Calendar.YEAR); 
	    //int month = date.get(Calendar.MONTH) + 1;
	    //int day = date.get(Calendar.DAY_OF_MONTH);
	    
	    //String remoteFolderPath = Configuration.MYRUN_LOG_SHARED_FOLDER_PATH.replace("[ANNO]", "" + year);
	    //remoteFolderPath = remoteFolderPath.replace("[MESE]", "" + month);
	    //remoteFolderPath = remoteFolderPath.replace("[GIORNO]", "" + day);
	    //remoteFolderPath = remoteFolderPath.replace("[USER]", username);

		//Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Remote Folder Path -> " + remoteFolderPath);
	    //Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Remote File Name -> " + remoteFileName);
		
    	man.setLocalFile(localFolderPath, localFileName, "/");
    	//man.setRemoteFile(remoteFolderPath, remoteFileName, "/");

    	man.backupLocalFile(localFolderPath + "/backups", "" + remoteFileName, "/");
    	//man.moveLocalFileContentToRemoteFileContent();
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

}
