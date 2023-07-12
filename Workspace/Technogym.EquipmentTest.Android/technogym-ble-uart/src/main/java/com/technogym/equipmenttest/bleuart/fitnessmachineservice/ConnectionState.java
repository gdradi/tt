package com.technogym.equipmenttest.bleuart.fitnessmachineservice;

/**
 * Created by sventurini on 24/04/16.
 */
class ConnectionState {

    public static final String BLE_CONNECTION_STATE = "device_connection_state";
    public static final int DISCONNECTED = 0;
    public static final int CONNECTING = 1;
    public static final int CONNECTED = 2;
    public static final int SCANNING = 3;
    public static final int BT_ADAPTER_OFF = 4;
    public static final int SCANNING_FIRED = 5;
    public static final int LOCATION_PROVIDER_OFF = 6;
}
