package com.technogym.android.myrun.sdk.bluetooth.dispatchers;

import com.technogym.android.myrun.sdk.bluetooth.listeners.BluetoothScanningListener;
import com.technogym.android.myrun.sdk.connection.listeners.EquipmentConnectionListener;

public interface IBluetoothConnectionNotifier extends BluetoothScanningListener,
		EquipmentConnectionListener {

    void onLowKitUpgraded(boolean result);
    void onHighKitUpgraded(boolean result);
}
