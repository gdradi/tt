package com.technogym.equipmenttest.bleuart.fitnessmachineservice;

import android.Manifest;
import android.content.Context;
import android.location.LocationManager;
import android.os.Build;

/**
 * Created by developer on 22/10/15.
 */
public class Permissions {

    public static boolean checkFineLocationOrCoarsePermission(final Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                return false;
            }
        }
        return true;
    }

    public static String[] getPermissionsForBtLEFeature() {
        //if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
            return new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        //else
          //  return new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION};
    }
}
