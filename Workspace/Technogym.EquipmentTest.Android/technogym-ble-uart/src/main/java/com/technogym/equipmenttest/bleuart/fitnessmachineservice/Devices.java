package com.technogym.equipmenttest.bleuart.fitnessmachineservice;

import android.bluetooth.BluetoothDevice;


import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.FitnessBtDevice;

import java.util.ArrayList;

/**
 * Created by sventurini on 24/04/15.
 */
public class Devices {
    private ArrayList<FitnessBtDevice> devices;

    public Devices() {
        devices = new ArrayList<FitnessBtDevice>();
    }

    public boolean addDevice(FitnessBtDevice device) {
        if (devices.size() <= 0) devices.add(device);
        else {
            for (FitnessBtDevice btDevice : devices) {
                if (btDevice.getDevice().equals(device.getDevice())) {
                    if (btDevice.getRssi() == device.getRssi()) {
                        return false;

                    } else {
                        btDevice.setRssi(device.getRssi());
                        return true;

                    }
                }
            }
            devices.add(device);
        }
        return true;
    }

    public FitnessBtDevice getDevice(int indexDev) {
        if (indexDev < 0 || indexDev >= devices.size()) return null;
        else return devices.get(indexDev);
    }

    public int getIndexOfBTDev(BluetoothDevice device) {
        if (devices == null) return -1;
        int i = 0;
        for (FitnessBtDevice fitnessBtDevice : devices){
            if (fitnessBtDevice.getDevice().equals(device)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void clearAll() {
        devices.clear();
    }

}
