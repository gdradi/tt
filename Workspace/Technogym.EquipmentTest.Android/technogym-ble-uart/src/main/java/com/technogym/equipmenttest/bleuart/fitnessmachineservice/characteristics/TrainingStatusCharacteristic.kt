package com.technogym.sdk.fitnessmachineservice.characteristics

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.*
import android.util.Log
import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic
import com.technogym.sdk.fitnessmachineservice.model.TrainingStatus
import kotlin.Byte.Companion.SIZE_BITS

class TrainingStatusCharacteristic(c: BluetoothGattCharacteristic) : BaseCharacteristic(c.uuid, c.properties, c.permissions, c) {
    var trainingStatus: TrainingStatus? = null

    private fun Byte.bit(position: Int): Int = toInt() shr position and 1
    private fun Byte.toBits(): List<Int> = List(SIZE_BITS) { bit(it) }

    override fun elabData(): Boolean {
        val flags = characteristic.value[0].toBits().map { it != 0 }

        val trainingStatusStringPresent = flags[0]
        val extendedStringPresent = flags[1]

        trainingStatus = TrainingStatus.values()[characteristic.value[1].toInt()]

        return !extendedStringPresent
    }
}