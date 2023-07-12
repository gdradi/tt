package com.technogym.equipmenttest.app;

import java.io.File;

import com.technogym.android.myrun.sdk.android.application.BluetoothApplication;
import com.technogym.android.myrun.sdk.bluetooth.controllers.BluetoothController;
import com.technogym.android.myrun.sdk.bluetooth.controllers.IBluetoothController;
import com.technogym.android.myrun.sdk.support.FileSystemHelper;

/**
 * This class extends the base Android {@link Application} and add features to it. It's used to store an instance of
 * {@link IBluetoothController} for Bluetooth communication with MyRun equipment.
 * 
 * @author Federico Foschini
 * @version 2.0
 * */
public class EquipmentTestApplication extends BluetoothApplication {

	public static IBluetoothController mConnectionController;

	// { Application methods overriding

	@Override
	public void onCreate() {
		super.onCreate();
		this.clearApplicationData();

		mConnectionController = BluetoothController.createEmpty();
		mConnectionController.removePairedDevices();
	}

	// }

	// { Public methods
	
	/**
	 * This method clears the Application Data
	 */
	public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    FileSystemHelper.deleteDir(new File(appDir, s));
                }
            }
        }
    }
	
	/**
	 * This method returns the stored instance of an {@link IBluetoothController}. It should never return a {@code null}
	 * value.
	 * 
	 * @return the controller
	 * */
	public IBluetoothController getConnectionController() {
		return mConnectionController;
	}

	// }

}