package com.technogym.equipmenttest.myrun;

import android.app.Application;

import com.technogym.android.myrun.sdk.bluetooth.controllers.IBluetoothController;
import com.technogym.android.myrun.sdk.connection.models.EquipmentConnectionState;
import com.technogym.android.myrun.sdk.support.FileSystemHelper;
import com.technogym.equipmenttest.myrun.connectors.MyRunUartConnector;

import java.io.File;

/**
 * This class extends the base Android {@link Application} and add features to it. It's used to store an instance of
 * {@link IBluetoothController} for Bluetooth communication with MyRun equipment.
 * 
 * @author Federico Foschini
 * @version 2.0
 * */
public class EquipmentTestApplication extends Application {

	public static MyRunUartConnector mBridgeConnector;

	// { Application methods overriding

	@Override
	public void onCreate() {
		super.onCreate();
		this.clearApplicationData();
		//mBridgeConnector = new MyRunBridgeConnector(this);
        mBridgeConnector = new MyRunUartConnector(EquipmentConnectionState.IDLE, this);
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


    public MyRunUartConnector getConnectionController() {
	    return mBridgeConnector;
    }
}