package com.technogym.equipmenttest.bleuart.fitnessmachineservice.model;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

import com.technogym.equipmenttest.bleuart.fitnessmachineservice.ConstantsFitnessMachine;

import java.util.UUID;

/**
 * Created by sventurini on 12/01/2017.
 */

public class FitnessBtDevice implements Parcelable {

    private BluetoothDevice device;
    private boolean availability;
    private FitnessType type;
    private int rssi;
    private String localName;

    public FitnessBtDevice() {

    }

    public FitnessBtDevice(BluetoothDevice device) {
        this.device = device;
        availability = true;
        type = FitnessType.UNDEFINED;
        rssi = -1;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public BluetoothDevice getDevice() {
        return this.device;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public boolean getAvailability() {
        return this.availability;
    }

    public void setType(FitnessType type) {
        this.type = type;
    }

    public FitnessType getType() {
        return this.type;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public UUID getNotifyCharacteristicForthisDevice() {
        return ConstantsFitnessMachine.typeUUIDNotifyMap.get(type);
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    @Override
    public String toString() {
        return "FitnessBtDevice{" +
                "device=" + device.toString() +
                ", availability=" + availability +
                ", type=" + type +
                ", rssi=" + rssi +
                ", localName='" + localName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.device, flags);
        dest.writeByte(this.availability ? (byte) 1 : (byte) 0);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeInt(this.rssi);
    }

    protected FitnessBtDevice(Parcel in) {
        this.device = in.readParcelable(BluetoothDevice.class.getClassLoader());
        this.availability = in.readByte() != 0;
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : FitnessType.values()[tmpType];
        this.rssi = in.readInt();
    }

    public static final Parcelable.Creator<FitnessBtDevice> CREATOR = new Parcelable.Creator<FitnessBtDevice>() {
        @Override
        public FitnessBtDevice createFromParcel(Parcel source) {
            return new FitnessBtDevice(source);
        }

        @Override
        public FitnessBtDevice[] newArray(int size) {
            return new FitnessBtDevice[size];
        }
    };
}
