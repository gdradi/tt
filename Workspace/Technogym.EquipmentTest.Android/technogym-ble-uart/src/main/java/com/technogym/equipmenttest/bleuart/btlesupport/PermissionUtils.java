package com.technogym.equipmenttest.bleuart.btlesupport;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.security.InvalidParameterException;

/**
 * PermissionUtils to help determine if a particular permission is granted.
 */

public final class PermissionUtils {

    private PermissionUtils() {
    }

    /**
     * Determine whether  the location permission has been granted (ACCESS_COARSE_LOCATION or
     * ACCESS_FINE_LOCATION). Below Android M, will always return true.
     *
     * @param context to determine with if the permission is granted
     * @return true if you have any location permission or the sdk is below Android M.
     * @throws InvalidParameterException if {@code context} is null
     */
//    @SuppressLint("ObsoleteSdkInt")
//    public static boolean isLocationGranted(final Context context) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
//            return true;
//        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
//            return isPermissionGranted(context, Manifest.permission.ACCESS_COARSE_LOCATION) || isPermissionGranted(context, Manifest.permission.ACCESS_FINE_LOCATION);
//        else
//            return (isPermissionGranted(context, Manifest.permission.ACCESS_COARSE_LOCATION) || isPermissionGranted(context, Manifest.permission.ACCESS_FINE_LOCATION))
//                    && isPermissionGranted(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
//    }

    /**
     * Determine whether the provided context has been granted a particular permission.
     *
     * @param context    where from you determine if a permission is granted
     * @param permission you want to determinie if it is granted
     * @return true if you have the permission, or false if not
     */
    @TargetApi(Build.VERSION_CODES.M)
    private static boolean isPermissionGranted(final Context context, final String permission) {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }
}
