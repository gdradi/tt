package com.technogym.equipmenttest.bleuart.fitnessmachineservice;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.location.LocationManager;
import android.os.Build;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;


import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.FitnessBtDevice;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.FitnessType;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by sventurini on 04/12/15.
 */
public class Utils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    static ScanSettings getSpinScanSettings() {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        builder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
        return builder.build();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    static ArrayList<ScanFilter> getSpinFilter(boolean enable) {
        ScanFilter.Builder scanFilterBuilder = new ScanFilter.Builder();
        ArrayList<ScanFilter> listFilt = new ArrayList<ScanFilter>();
        if (enable) {
            listFilt.add(scanFilterBuilder.setServiceUuid(new ParcelUuid(ConstantsFitnessMachine.Services.FITNESS_MACHINE_SERVICE)).build());
        }
        return listFilt;
    }

    public static boolean locationProviderIsEnabled(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static byte[] marshall(Parcelable parceable) {
        Parcel parcel = Parcel.obtain();
        parceable.writeToParcel(parcel, 0);
        byte[] bytes = parcel.marshall();
        parcel.recycle();
        return bytes;
    }

    public static <T> T unmarshall(byte[] bytes, Parcelable.Creator<T> creator) {
        Parcel parcel = unmarshall(bytes);
        T result = creator.createFromParcel(parcel);
        parcel.recycle();
        return result;
    }

    public static Parcel unmarshall(byte[] bytes) {
        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0); // This is extremely important!
        return parcel;
    }

    /**
     * @param value
     * @param checkSum
     * @param module
     * @return
     */
    public static boolean checkSumModule(String value, String checkSum, int module) {
        try {
            int chechSumVal = Integer.valueOf(checkSum);
            int sumValue = 0;
            for (int charVal : value.getBytes()) {
                sumValue += charVal;
            }
            int moduleVal = (sumValue % module);
            return moduleVal == chechSumVal;

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param txPower
     * @param rssi
     * @return
     */
    public static double calculateDistance(int txPower, double rssi) {
        if (rssi == 0) {
            return -1.0; // if we cannot determine distance, return -1.
        }

        double ratio = rssi * 1.0 / txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            double accuracy = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            return accuracy;
        }
    }

    /**
     * @param device
     * @param scanRecord
     * @return
     */
    public static FitnessBtDevice getFitnessBtDeviceFromBTDevice(BluetoothDevice device, byte[] scanRecord, int rssi) {
        FitnessBtDevice fitnessBtDevice = new FitnessBtDevice();
        fitnessBtDevice.setRssi(rssi);
        Map<Integer, byte[]> scanMap = BLEBase.ParseRecord(scanRecord);
        byte[] value = scanMap.get(BLEBase.EBLE_SERVICEDATA);
        byte[] localName = scanMap.get(BLEBase.EBLE_LOCALNAME);
        byte[] shortName = scanMap.get(BLEBase.EBLE_SHORTNAME);
        boolean availablity = false;
        FitnessType type = FitnessType.UNDEFINED;

        if (value != null && value.length >= 5) {
            int serviceUUID = ((value[1] << 8) + value[0]);
            int flags = value[2];

            int fitnesMachineType = ((value[4] << 8) + value[3]);
            switch (fitnesMachineType) {
                case 1:
                    type = FitnessType.TREADMILL;
                    break;
                case 2:
                    type = FitnessType.CROSS_TRAINER;
                    break;
                case 4:
                    type = FitnessType.STEP_CLIMBER;
                    break;
                case 8:
                    type = FitnessType.STAIR_CLIMBER;
                    break;
                case 16:
                    type = FitnessType.ROWER;
                    break;
                case 32:
                    type = FitnessType.INDOOR_BIKE;
                    break;
                default:
                    type = FitnessType.UNDEFINED;
                    break;
            }
            availablity = (flags & 0x01) == 1;
        }

        fitnessBtDevice.setAvailability(availablity);
        fitnessBtDevice.setDevice(device);
        fitnessBtDevice.setType(type);

        if (localName != null)
            fitnessBtDevice.setLocalName(new String(localName));
        else if (shortName != null)
            fitnessBtDevice.setLocalName(new String(shortName));

        return fitnessBtDevice;
    }

    /**
     * @param array1
     * @param array2
     * @return
     */
    public static byte[] joinByteArray(byte[] array1, byte[] array2) {
        byte[] joinedArray = new byte[array1.length + array2.length];
        for (int i = 0; i < array1.length; i++) {
            joinedArray[i] = array1[i];
        }

        for (int i = 0; i < array2.length; i++) {
            joinedArray[i + array1.length] = array2[i];
        }
        return joinedArray;
    }

    /**
     * @param value
     * @param array2
     * @return
     */
    public static byte[] joinByteAndByteArray(byte value, byte[] array2) {
        byte[] array1 = new byte[1];
        array1[0] = value;
        return joinByteArray(array1, array2);
    }
}
