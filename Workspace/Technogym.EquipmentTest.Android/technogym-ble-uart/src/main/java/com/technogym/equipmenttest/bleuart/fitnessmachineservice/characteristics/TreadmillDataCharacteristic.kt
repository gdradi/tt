package com.technogym.sdk.fitnessmachineservice.characteristics

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattCharacteristic.*
import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic
import com.technogym.sdk.fitnessmachineservice.model.treadmill.FitnessTreadmillData
import kotlin.Byte.Companion.SIZE_BITS


class TreadmillDataCharacteristic(c: BluetoothGattCharacteristic) : BaseCharacteristic(c.uuid, c.properties, c.permissions, c) {
    var treadmillData = FitnessTreadmillData()

    private fun Byte.bit(position: Int): Int = toInt() shr position and 1
    private fun Byte.toBits(): List<Int> = List(SIZE_BITS) { bit(it) }
    private fun Collection<Byte>.toBits(): List<Int> = map { it.toBits() }.flatten()

    override fun elabData(): Boolean {
        var offset = 0

        fun BluetoothGattCharacteristic.getFlags(size: Int = 2): List<Boolean> = value.take(size).toBits().map { it != 0 }.also { offset += size }
        fun BluetoothGattCharacteristic.getNextUInt8(): Int = getIntValue(FORMAT_UINT8, offset++)
        fun BluetoothGattCharacteristic.getNextUInt16(): Int = getIntValue(FORMAT_UINT16, offset).also { offset += 2 }
        fun BluetoothGattCharacteristic.getNextSInt16(): Int = getIntValue(FORMAT_SINT16, offset).also { offset += 2 }
        fun BluetoothGattCharacteristic.getNextUInt(bit: Int): Int = List(bit / SIZE_BITS) { getNextUInt8() }.withIndex().sumBy { it.value shl it.index }

        val flags = characteristic.getFlags()

        val moreData = flags[0]
        val instantaneousSpeedPresent = !moreData
        val averageSpeedSupported = flags[1]
        val totalDistanceSupported = flags[2]
        val inclinationSupported = flags[3]
        val elevationGainSupported = flags[4]
        val instantaneousPaceSupported = flags[5]
        val averagePacePresent = flags[6]
        val expendedEnergySupported = flags[7]
        val hearRateMeasurementSupported = flags[8]
        val metabolicEquivalentSupported = flags[9]
        val elapsedTimeSupported = flags[10]
        val remainingTimeSupported = flags[11]
        val forceOnBeltAndPowerOutputSupported = flags[12]

        if (instantaneousSpeedPresent) {
            // Kilometer per hour with a resolution of 0.01
            treadmillData.instantaneousSpeed = characteristic.getNextUInt16() / 100.0
        }
        if (averageSpeedSupported) {
            // Kilometer per hour with a resolution of 0.01
            treadmillData.averageSpeed = characteristic.getNextUInt16() / 100.0
        }
        if (totalDistanceSupported) {
            // Meters with a resolution of 1
            treadmillData.totalDistance = characteristic.getNextUInt(24)
        }
        if (inclinationSupported) {
            // Percent with a resolution of 0.1
            treadmillData.inclination = characteristic.getNextSInt16() / 10.0
            // Degree with a resolution of 0.1
            treadmillData.rampAngleSetting = characteristic.getNextSInt16() / 10.0
        }
        if (elevationGainSupported) {
            // Meters with a resolution of 0.1
            treadmillData.elevationGainPositive = characteristic.getNextUInt16() / 10.0
            // Meters with a resolution of 0.1
            treadmillData.elevationGainNegative = characteristic.getNextUInt16() / 10.0
        }
        if (instantaneousPaceSupported) {
            // Kilometer per minute with a resolution of 0.1
            treadmillData.instantaneousPace = characteristic.getNextUInt8() / 10.0
        }
        if (averagePacePresent) {
            // Kilometer per minute with a resolution of 0.1
            treadmillData.averagePace = characteristic.getNextUInt8() / 10.0
        }
        if (expendedEnergySupported) {
            // Kilo Calorie with a resolution of 1
            treadmillData.totalEnergy = characteristic.getNextUInt16()
            // Kilo Calorie with a resolution of 1
            treadmillData.energyPerHour = characteristic.getNextUInt16()
            // Kilo Calorie with a resolution of 1
            treadmillData.energyPerMinute = characteristic.getNextUInt8()
        }
        if (hearRateMeasurementSupported) {
            // Beats per minute with a resolution of 1
            treadmillData.heartRate = characteristic.getNextUInt8()
        }
        if (metabolicEquivalentSupported) {
            // Metabolic Equivalent with a resolution of 0.1
            treadmillData.metabolicEquivalent = characteristic.getNextUInt8() / 10.0
        }
        if (elapsedTimeSupported) {
            // Second with a resolution of 1
            treadmillData.elapsedTime = characteristic.getNextUInt16()
        }
        if (remainingTimeSupported) {
            // Second with a resolution of 1
            treadmillData.remainingTime = characteristic.getNextUInt16()
        }
        if (forceOnBeltAndPowerOutputSupported) {
            // Newton with a resolution of 1
            treadmillData.forceOnBelt = characteristic.getNextSInt16()
            // Watts with a resolution of 1
            treadmillData.powerOutput = characteristic.getNextSInt16()
        }

        return !moreData
    }
}