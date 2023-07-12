package com.technogym.equipmenttest.bleuart.fitnessmachineservice.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.nio.ByteBuffer;
import java.util.BitSet;

public class CustomHrMeasurement implements Parcelable {

    public static Builder newBuilder() {
        return new Builder();
    }

    private boolean sensorContactDetected;
    private boolean energyExpendedStatus;
    private boolean rrIntervalFlag;

    private int measurementValue;
    private int energyExpended;
    private int rrInterval;

    public CustomHrMeasurement() {
    }

    protected CustomHrMeasurement(Parcel in) {
        this.sensorContactDetected = in.readByte() != 0;
        this.energyExpendedStatus = in.readByte() != 0;
        this.rrIntervalFlag = in.readByte() != 0;
        this.measurementValue = in.readInt();
        this.energyExpended = in.readInt();
        this.rrInterval = in.readInt();
    }

    private CustomHrMeasurement(Builder builder) {
        sensorContactDetected = builder.sensorContactDetected;
        energyExpendedStatus = builder.energyExpendedStatus;
        rrIntervalFlag = builder.rrIntervalFlag;
        measurementValue = builder.measurementValue;
        energyExpended = builder.energyExpended;
        rrInterval = builder.rrInterval;
    }

    public byte[] encode() {
        BitSet bitSet = new BitSet(8);
        if (sensorContactDetected) {//11
            bitSet.set(1, 3);
        } else {                    //10
            bitSet.set(1);
        }

        if (energyExpendedStatus)//if true set to 1
            bitSet.set(3);

        if (rrIntervalFlag)//if true set to 1
            bitSet.set(4);

        byte[] payload = new byte[0];
        payload = appendData(payload, bitSet.toByteArray());
        payload = appendData(payload, (byte) measurementValue);
        payload = appendData(payload, new byte[]{(byte) (energyExpended >>> 8), (byte) (energyExpended & 0xFF)});
        payload = appendData(payload, new byte[]{(byte) (rrInterval >>> 8), (byte) (rrInterval & 0xFF)});
        return payload;

//        ByteBuffer buffer = ByteBuffer.allocate(bitSet.length() + 3);
//        buffer.put(bitSet.toByteArray());
//        buffer.put((byte) measurementValue);
//        buffer.putShort((short) energyExpended);
//        buffer.putShort((short) rrInterval);
//        return buffer.array();
    }

    private byte[] appendData(byte[] original, byte[] content) {
        byte[] payload = new byte[original.length + content.length];

        //copy input data
        System.arraycopy(original, 0, payload, 0, original.length);

        //add new data
        System.arraycopy(content, 0, payload, original.length, content.length);

        return payload;
    }

    private byte[] appendData(byte[] original, byte content) {
        byte[] payload = new byte[original.length + 1];

        //copy input data
        System.arraycopy(original, 0, payload, 0, original.length);

        //add new data
        payload[original.length] = content;

        return payload;
    }

    @Override
    public String toString() {
        return "CustomHrMeasurement{" +
                "sensorContactDetected=" + sensorContactDetected +
                ", energyExpendedStatus=" + energyExpendedStatus +
                ", rrIntervalFlag=" + rrIntervalFlag +
                ", measurementValue=" + measurementValue +
                ", energyExpended=" + energyExpended +
                ", rrInterval=" + rrInterval +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.sensorContactDetected ? (byte) 1 : (byte) 0);
        dest.writeByte(this.energyExpendedStatus ? (byte) 1 : (byte) 0);
        dest.writeByte(this.rrIntervalFlag ? (byte) 1 : (byte) 0);
        dest.writeInt(this.measurementValue);
        dest.writeInt(this.energyExpended);
        dest.writeInt(this.rrInterval);
    }

    public static final class Builder {
        private boolean sensorContactDetected;
        private boolean energyExpendedStatus;
        private boolean rrIntervalFlag;
        private int measurementValue;
        private int energyExpended;
        private int rrInterval;

        private Builder() {
        }

        public Builder withSensorContactDetected(boolean sensorContactDetected) {
            this.sensorContactDetected = sensorContactDetected;
            return this;
        }

        public Builder withEnergyExpendedStatus(boolean energyExpendedStatus) {
            this.energyExpendedStatus = energyExpendedStatus;
            return this;
        }

        public Builder withRrIntervalFlag(boolean rrIntervalFlag) {
            this.rrIntervalFlag = rrIntervalFlag;
            return this;
        }

        public Builder withMeasurementValue(int measurementValue) {
            this.measurementValue = measurementValue;
            return this;
        }

        public Builder withEnergyExpended(int energyExpended) {
            this.energyExpended = energyExpended;
            return this;
        }

        public Builder withRrInterval(int rrInterval) {
            this.rrInterval = rrInterval;
            return this;
        }

        public CustomHrMeasurement build() {
            return new CustomHrMeasurement(this);
        }
    }

    public static final Parcelable.Creator<CustomHrMeasurement> CREATOR = new Parcelable.Creator<CustomHrMeasurement>() {
        @Override
        public CustomHrMeasurement createFromParcel(Parcel source) {
            return new CustomHrMeasurement(source);
        }

        @Override
        public CustomHrMeasurement[] newArray(int size) {
            return new CustomHrMeasurement[size];
        }
    };
}
