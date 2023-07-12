package com.technogym.sdk.fitnessmachineservice.characteristics

import android.bluetooth.BluetoothGattCharacteristic
import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic
import com.technogym.sdk.fitnessmachineservice.model.treadmill.MyRunPhysicalSensorData


class MyRunPhysicalSensorDataCharacteristic(c: BluetoothGattCharacteristic) : BaseCharacteristic(c.uuid, c.properties, c.permissions, c) {
    var sensorData = MyRunPhysicalSensorData()

    override fun elabData(): Boolean {
        sensorData.inductivePresence = characteristic.value[0].toInt() == 1
        sensorData.opticalPresence = characteristic.value[1].toInt() == 1
        sensorData.mainButtonPressed = characteristic.value[2].toInt() == 1
        sensorData.speedJoystickState = characteristic.value[3].toInt()
        sensorData.inclinationJoystickState = characteristic.value[4].toInt()
        return true
    }
}