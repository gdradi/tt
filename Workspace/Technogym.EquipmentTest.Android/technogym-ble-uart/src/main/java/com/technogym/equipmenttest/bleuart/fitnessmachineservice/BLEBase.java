package com.technogym.equipmenttest.bleuart.fitnessmachineservice;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.ParcelUuid;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by juksilve on 20.4.2015.
 */
public class BLEBase {

    interface CallBack {
        public void debug(String who, String text);
    }

    public static boolean isBLESupported(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    public static boolean isBLEAdvertisingSupported(Context context) {
        boolean ret = false;
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            BluetoothManager tmpMan = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            if (tmpMan != null) {
                BluetoothAdapter tmpAdapter = tmpMan.getAdapter();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (tmpAdapter != null && tmpAdapter.isMultipleAdvertisementSupported()) {
                        ret = true;
                    }
                }
            }
        }
        return ret;
    }

    public static String getConnectionState(int State) {
        switch (State) {
            case BluetoothProfile.STATE_DISCONNECTED:
                return "DisConnected";
            case BluetoothProfile.STATE_CONNECTING:
                return "Connecting";
            case BluetoothProfile.STATE_CONNECTED:
                return "Connected";
            case BluetoothProfile.STATE_DISCONNECTING:
                return "Disconnecting";
            default:
                return "Uknown";

        }
    }

    public static String getGATTStatus(int State) {
        switch (State) {
            case BluetoothGatt.GATT_SUCCESS:
                return "SUCCESS";
            case BluetoothGatt.GATT_READ_NOT_PERMITTED:
                return "READ_NOT_PERMITTED";
            case BluetoothGatt.GATT_WRITE_NOT_PERMITTED:
                return "WRITE_NOT_PERMITTED";
            case BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION:
                return "INSUFFICIENT_AUTHENTICATION";
            case BluetoothGatt.GATT_REQUEST_NOT_SUPPORTED:
                return "REQUEST_NOT_SUPPORTED";
            case BluetoothGatt.GATT_INSUFFICIENT_ENCRYPTION:
                return "INSUFFICIENT_ENCRYPTION";
            case BluetoothGatt.GATT_INVALID_OFFSET:
                return "INVALID_OFFSET";
            case BluetoothGatt.GATT_INVALID_ATTRIBUTE_LENGTH:
                return "INVALID_ATTRIBUTE_LENGTH";
            case BluetoothGatt.GATT_CONNECTION_CONGESTED:
                return "CONNECTION_CONGESTED";
            case BluetoothGatt.GATT_FAILURE:
                return "FAILURE";
            default:
                return "Uknown(" + String.format("%02X", State) + ").";
        }
    }

    public static String getDeviceNameOrAddress(String deviceAddress, Context context) {
        String ret = "";

        BluetoothManager tmpBluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        if (tmpBluetoothManager != null) {
            BluetoothDevice tmpDev = tmpBluetoothManager.getAdapter().getRemoteDevice(deviceAddress);
            if (tmpDev != null && tmpDev.getName() != null) {
                ret = tmpDev.getName();
            }
        }

        if (ret.length() == 0) {
            ret = deviceAddress;
        }

        return ret;
    }

    /*
    BLE Scan record type IDs
    data from:
    https://www.bluetooth.org/en-us/specification/assigned-numbers/generic-access-profile
    */
    static final int EBLE_FLAGS = 0x01;//«Flags»	Bluetooth Core Specification:
    static final int EBLE_16BitUUIDInc = 0x02;//«Incomplete List of 16-bit Service Class UUIDs»	Bluetooth Core Specification:
    static final int EBLE_16BitUUIDCom = 0x03;//«Complete List of 16-bit Service Class UUIDs»	Bluetooth Core Specification:
    static final int EBLE_32BitUUIDInc = 0x04;//«Incomplete List of 32-bit Service Class UUIDs»	Bluetooth Core Specification:
    static final int EBLE_32BitUUIDCom = 0x05;//«Complete List of 32-bit Service Class UUIDs»	Bluetooth Core Specification:
    static final int EBLE_128BitUUIDInc = 0x06;//«Incomplete List of 128-bit Service Class UUIDs»	Bluetooth Core Specification:
    static final int EBLE_128BitUUIDCom = 0x07;//«Complete List of 128-bit Service Class UUIDs»	Bluetooth Core Specification:
    static final int EBLE_SHORTNAME = 0x08;//«Shortened Local Name»	Bluetooth Core Specification:
    static final int EBLE_LOCALNAME = 0x09;//«Complete Local Name»	Bluetooth Core Specification:
    static final int EBLE_TXPOWERLEVEL = 0x0A;//«Tx Power Level»	Bluetooth Core Specification:
    static final int EBLE_DEVICECLASS = 0x0D;//«Class of Device»	Bluetooth Core Specification:
    static final int EBLE_SIMPLEPAIRHASH = 0x0E;//«Simple Pairing Hash C»	Bluetooth Core Specification:​«Simple Pairing Hash C-192»	​Core Specification Supplement, Part A, section 1.6
    static final int EBLE_SIMPLEPAIRRAND = 0x0F;//«Simple Pairing Randomizer R»	Bluetooth Core Specification:​«Simple Pairing Randomizer R-192»	​Core Specification Supplement, Part A, section 1.6
    static final int EBLE_DEVICEID = 0x10;//«Device ID»	Device ID Profile v1.3 or later,«Security Manager TK Value»	Bluetooth Core Specification:
    static final int EBLE_SECURITYMANAGER = 0x11;//«Security Manager Out of Band Flags»	Bluetooth Core Specification:
    static final int EBLE_SLAVEINTERVALRA = 0x12;//«Slave Connection Interval Range»	Bluetooth Core Specification:
    static final int EBLE_16BitSSUUID = 0x14;//«List of 16-bit Service Solicitation UUIDs»	Bluetooth Core Specification:
    static final int EBLE_128BitSSUUID = 0x15;//«List of 128-bit Service Solicitation UUIDs»	Bluetooth Core Specification:
    static final int EBLE_SERVICEDATA = 0x16;//«Service Data»	Bluetooth Core Specification:​«Service Data - 16-bit UUID»	​Core Specification Supplement, Part A, section 1.11
    static final int EBLE_PTADDRESS = 0x17;//«Public Target Address»	Bluetooth Core Specification:
    static final int EBLE_RTADDRESS = 0x18;
    ;//«Random Target Address»	Bluetooth Core Specification:
    static final int EBLE_APPEARANCE = 0x19;//«Appearance»	Bluetooth Core Specification:
    static final int EBLE_DEVADDRESS = 0x1B;//«​LE Bluetooth Device Address»	​Core Specification Supplement, Part A, section 1.16
    static final int EBLE_LEROLE = 0x1C;//«​LE Role»	​Core Specification Supplement, Part A, section 1.17
    static final int EBLE_PAIRINGHASH = 0x1D;//«​Simple Pairing Hash C-256»	​Core Specification Supplement, Part A, section 1.6
    static final int EBLE_PAIRINGRAND = 0x1E;//«​Simple Pairing Randomizer R-256»	​Core Specification Supplement, Part A, section 1.6
    static final int EBLE_32BitSSUUID = 0x1F;//​«List of 32-bit Service Solicitation UUIDs»	​Core Specification Supplement, Part A, section 1.10
    static final int EBLE_32BitSERDATA = 0x20;//​«Service Data - 32-bit UUID»	​Core Specification Supplement, Part A, section 1.11
    static final int EBLE_128BitSERDATA = 0x21;//​«Service Data - 128-bit UUID»	​Core Specification Supplement, Part A, section 1.11
    static final int EBLE_SECCONCONF = 0x22;//​«​LE Secure Connections Confirmation Value»	​Core Specification Supplement Part A, Section 1.6
    static final int EBLE_SECCONRAND = 0x23;//​​«​LE Secure Connections Random Value»	​Core Specification Supplement Part A, Section 1.6​
    static final int EBLE_3DINFDATA = 0x3D;//​​«3D Information Data»	​3D Synchronization Profile, v1.0 or later
    static final int EBLE_MANDATA = 0xFF;//«Manufacturer Specific Data»	Bluetooth Core Specification:

    /*
    BLE Scan record parsing
    inspired by:
    http://stackoverflow.com/questions/22016224/ble-obtain-uuid-encoded-in-advertising-packet
     */
    static public Map<Integer, byte[]> ParseRecord(byte[] scanRecord) {
        HashMap ret = new HashMap();
        int index = 0;
        while (index < scanRecord.length) {
            int length = scanRecord[index++];
            //Zero value indicates that we are done with the record now
            if (length == 0) break;

            int type = scanRecord[index];
            //if the type is zero, then we are pass the significant section of the data,
            // and we are thud done
            if (type == 0) break;

            byte[] data = Arrays.copyOfRange(scanRecord, index + 1, index + length);
            if (data != null && data.length > 0) {
                StringBuilder hex = new StringBuilder(data.length * 2);
                // the data appears to be there backwards
                byte[] value = new byte[data.length];
                for (int bb = 0; bb < data.length; bb++) {
                    value[bb] = data[bb];
                }
                ret.put(type, value);
            }
            index += length;
        }

        return ret;
    }

    static public String getServiceUUID(Map<Integer, String> record) {
        String ret = "";
        // for example: 0105FACB00B01000800000805F9B34FB --> 010510ee-0000-1000-8000-00805f9b34fb
        if (record.containsKey(EBLE_128BitUUIDCom)) {
            String tmpString = record.get(EBLE_128BitUUIDCom).toString();
            ret = tmpString.substring(0, 8) + "-" + tmpString.substring(8, 12) + "-" + tmpString.substring(12, 16) + "-" + tmpString.substring(16, 20) + "-" + tmpString.substring(20, tmpString.length());
            //010510EE --> 010510ee-0000-1000-8000-00805f9b34fb
        } else if (record.containsKey(EBLE_32BitUUIDCom)) {
            ret = record.get(EBLE_32BitUUIDCom).toString() + "-0000-1000-8000-00805f9b34fb";
        }
        return ret;
    }

    private static final int DATA_TYPE_FLAGS = 0x01;
    private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_PARTIAL = 0x02;
    private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_COMPLETE = 0x03;
    private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_PARTIAL = 0x04;
    private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_COMPLETE = 0x05;
    private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_PARTIAL = 0x06;
    private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_COMPLETE = 0x07;
    private static final int DATA_TYPE_LOCAL_NAME_SHORT = 0x08;
    private static final int DATA_TYPE_LOCAL_NAME_COMPLETE = 0x09;
    private static final int DATA_TYPE_TX_POWER_LEVEL = 0x0A;
    private static final int DATA_TYPE_SERVICE_DATA = 0x16;
    private static final int DATA_TYPE_MANUFACTURER_SPECIFIC_DATA = 0xFF;
    public static List<ParcelUuid> getServicesFromBytes(byte[] scanRecord) {
        if (scanRecord == null) {
            return null;
        }

        int currentPos = 0;
        int advertiseFlag = -1;
        List<ParcelUuid> serviceUuids = new ArrayList<ParcelUuid>();
        String localName = null;
        int txPowerLevel = Integer.MIN_VALUE;

        SparseArray<byte[]> manufacturerData = new SparseArray<byte[]>();
        Map<ParcelUuid, byte[]> serviceData = new HashMap<ParcelUuid, byte[]>();

        try {
            while (currentPos < scanRecord.length) {
                // length is unsigned int.
                int length = scanRecord[currentPos++] & 0xFF;
                if (length == 0) {
                    break;
                }
                // Note the length includes the length of the field type itself.
                int dataLength = length - 1;
                // fieldType is unsigned int.
                int fieldType = scanRecord[currentPos++] & 0xFF;
                switch (fieldType) {
                    case DATA_TYPE_FLAGS:
                        advertiseFlag = scanRecord[currentPos] & 0xFF;
                        break;
                    case DATA_TYPE_SERVICE_UUIDS_16_BIT_PARTIAL:
                    case DATA_TYPE_SERVICE_UUIDS_16_BIT_COMPLETE:
                        parseServiceUuid(scanRecord, currentPos,
                                dataLength, BluetoothUuid.UUID_BYTES_16_BIT, serviceUuids);
                        break;
                    case DATA_TYPE_SERVICE_UUIDS_32_BIT_PARTIAL:
                    case DATA_TYPE_SERVICE_UUIDS_32_BIT_COMPLETE:
                        parseServiceUuid(scanRecord, currentPos, dataLength,
                                BluetoothUuid.UUID_BYTES_32_BIT, serviceUuids);
                        break;
                    case DATA_TYPE_SERVICE_UUIDS_128_BIT_PARTIAL:
                    case DATA_TYPE_SERVICE_UUIDS_128_BIT_COMPLETE:
                        parseServiceUuid(scanRecord, currentPos, dataLength,
                                BluetoothUuid.UUID_BYTES_128_BIT, serviceUuids);
                        break;
                    case DATA_TYPE_LOCAL_NAME_SHORT:
                    case DATA_TYPE_LOCAL_NAME_COMPLETE:
                        localName = new String(
                                extractBytes(scanRecord, currentPos, dataLength));
                        break;
                    case DATA_TYPE_TX_POWER_LEVEL:
                        txPowerLevel = scanRecord[currentPos];
                        break;
                    case DATA_TYPE_SERVICE_DATA:
                        // The first two bytes of the service data are service data UUID in little
                        // endian. The rest bytes are service data.
                        int serviceUuidLength = BluetoothUuid.UUID_BYTES_16_BIT;
                        byte[] serviceDataUuidBytes = extractBytes(scanRecord, currentPos,
                                serviceUuidLength);
                        ParcelUuid serviceDataUuid = BluetoothUuid.parseUuidFrom(
                                serviceDataUuidBytes);
                        byte[] serviceDataArray = extractBytes(scanRecord,
                                currentPos + serviceUuidLength, dataLength - serviceUuidLength);
                        serviceData.put(serviceDataUuid, serviceDataArray);
                        break;
                    case DATA_TYPE_MANUFACTURER_SPECIFIC_DATA:
                        // The first two bytes of the manufacturer specific data are
                        // manufacturer ids in little endian.
                        int manufacturerId = ((scanRecord[currentPos + 1] & 0xFF) << 8) +
                                (scanRecord[currentPos] & 0xFF);
                        byte[] manufacturerDataBytes = extractBytes(scanRecord, currentPos + 2,
                                dataLength - 2);
                        manufacturerData.put(manufacturerId, manufacturerDataBytes);
                        break;
                    default:
                        // Just ignore, we don't handle such data type.
                        break;
                }
                currentPos += dataLength;
            }

            if (serviceUuids.isEmpty()) {
                serviceUuids = null;
            }
            return serviceUuids;

        } catch (Exception e) {
            // As the record is invalid, ignore all the parsed results for this packet
            // and return an empty record with raw scanRecord bytes in results
            return null;
        }
    }

    // Parse service UUIDs.
    private static int parseServiceUuid(byte[] scanRecord, int currentPos, int dataLength,
                                        int uuidLength, List<ParcelUuid> serviceUuids) {
        while (dataLength > 0) {
            byte[] uuidBytes = extractBytes(scanRecord, currentPos,
                    uuidLength);
            serviceUuids.add(BluetoothUuid.parseUuidFrom(uuidBytes));
            dataLength -= uuidLength;
            currentPos += uuidLength;
        }
        return currentPos;
    }

    // Helper method to extract bytes from byte array.
    private static byte[] extractBytes(byte[] scanRecord, int start, int length) {
        byte[] bytes = new byte[length];
        System.arraycopy(scanRecord, start, bytes, 0, length);
        return bytes;
    }
}