package com.technogym.mycycling.ble.models;

import java.util.UUID;

public class BleUUIDs {

    protected static final String RX_CONFIG_DESCRIPTOR_STRING = "00002902-0000-1000-8000-00805f9b34fb";
    public static final UUID UUID_RX_CONFIG_DESCRIPTOR = UUID.fromString(RX_CONFIG_DESCRIPTOR_STRING);

    protected static final String UART_TX_SERVICE_UUID = "beb7705e-bd66-4501-80c0-0c8f0bcca1a5";
    public final static UUID UUID_UART_TX_SERVICE = UUID.fromString(UART_TX_SERVICE_UUID);

    //protected static final String UART_RX_SERVICE_UUID = "a913bfc0-929e-11e5-b928-0002a5d5c51b";
    //public final static UUID UUID_UART_RX_SERVICE = UUID.fromString(UART_RX_SERVICE_UUID);
    protected static final String UART_RX_SERVICE_UUID = "beb7705e-bd66-4501-80c0-0c8f0bcca1a5";
    public final static UUID UUID_UART_RX_SERVICE = UUID.fromString(UART_RX_SERVICE_UUID);

    //protected static final String RX_SERVICE_UUID = "58094966-498c-470d-8051-37e617a13895";
    protected static final String RX_SERVICE_UUID = "6f26de4b-dcef-4459-9465-931f1b144c20";
    public final static UUID UUID_RX_SERVICE = UUID.fromString(RX_SERVICE_UUID);

    protected static final String TX_SERVICE_UUID = "df1eb8e4-1753-4bb9-a6a6-e018040af0a3";
    public final static UUID UUID_TX_SERVICE = UUID.fromString(TX_SERVICE_UUID);

    protected static final String SERVICE_DEVICE_INFORMATION = "0000180a-0000-1000-8000-00805f9b34fb";
    public final static UUID UUID_DEVICE_INFORMATION_SERVICE = UUID.fromString(SERVICE_DEVICE_INFORMATION);

    protected static final String CHAR_MANUFACTURER_NAME_STRING = "00002a29-0000-1000-8000-00805f9b34fb";
    public final static UUID UUID_CHAR_MANUFACTURER_NAME_CHAR = UUID.fromString(CHAR_MANUFACTURER_NAME_STRING);

    protected static final String CHAR_MODEL_NUMBER_STRING = "00002a24-0000-1000-8000-00805f9b34fb";
    public final static UUID UUID_CHAR_MODEL_NUMBER_CHAR = UUID.fromString(CHAR_MODEL_NUMBER_STRING);

    protected static final String CHAR_SERIAL_NUMBER_STRING = "00002a25-0000-1000-8000-00805f9b34fb";
    public final static UUID UUID_SERIAL_NUMBER_CHAR = UUID.fromString(CHAR_SERIAL_NUMBER_STRING);

    protected static final String FIRMWARE_REVISION_STRING = "00002a26-0000-1000-8000-00805f9b34fb";
    public final static UUID UUID_FIRMWARE_REVISION_CHAR = UUID.fromString(FIRMWARE_REVISION_STRING);

    protected static final String FIRMWARE_VERSION_STRING = "00002a27-0000-1000-8000-00805f9b34fb";
    public final static UUID UUID_FIRMWARE_VERSION_CHAR = UUID.fromString(FIRMWARE_VERSION_STRING);

    protected static final String CYCLING_POWER_STRING = "00001818-0000-1000-8000-00805f9b34fb";
    public final static UUID UUID_CYCLING_POWER_SERVICE = UUID.fromString(CYCLING_POWER_STRING);

    protected static final String FIRMWARE_UPGRADE_SERVICE_STRING = "00001530-1212-efde-1523-785feabcd123";
    public final static UUID UUID_FIRMWARE_UPGRADE_SERVICE = UUID.fromString(FIRMWARE_UPGRADE_SERVICE_STRING);
}
