package com.technogym.mycycling.application;

import android.app.Application;

import com.technogym.mycycling.support.FileSystemHelper;

import java.io.File;

/**
 * This class extends the base Android {@link Application} and add features to it.
 *
 * @author Federico Foschini
 * @version 2.0
 * */
public class EquipmentTestApplication extends Application {

    // { Application methods overriding

    @Override
    public void onCreate() {
        super.onCreate();
        this.clearApplicationData();
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

    // }
}
