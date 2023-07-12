package com.technogym.sdk.fitnessmachineservice

import android.bluetooth.BluetoothGattCharacteristic
import android.util.Log
import com.technogym.equipmenttest.bleuart.btlesupport.BTLEConstants.BaseCharacteristics.UART_RX
import com.technogym.equipmenttest.bleuart.btlesupport.BTLEConstants.BaseCharacteristics.UART_TX
import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.ConstantsFitnessMachine.Characteristics.*
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.ConstantsFitnessMachine.TAG
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.characteristics.*
import com.technogym.equipmenttest.bleuart.technogymbtleuart.characteristics.UartRxCharacteristic
import com.technogym.equipmenttest.bleuart.technogymbtleuart.characteristics.UartTxCharacteristic
import com.technogym.sdk.fitnessmachineservice.characteristics.*
import java.util.*

/**
 * Created by developer on 10/12/15.
 */
class Characteristics {

    /**
     * String -> UUID
     * BaseCharacteristic instance of a characteristic custom class.
     */
    private val characteristicMap: MutableMap<String, BaseCharacteristic> = mutableMapOf()

    fun addCharacteristic(characteristic: BluetoothGattCharacteristic) {
        Log.d(TAG, "addCharacteristic " + characteristic.uuid.toString())

        characteristicMap[characteristic.uuid.toString()] = when (characteristic.uuid) {
            SOFTWARE_REVISION_STRING, HARDWARE_REVISION_STRING,
            MANUFACTURER_NAME_STRING, SERIAL_NUMBER_STRING, DEVICE_NAME,
            ROWER_SET_PHYSICAL_ACTIVITY_ID -> StringUtf8Characteristic(characteristic)
            FITNESS_MACHINE_FEATURE -> FitnessMachineFatureCharacteristic(characteristic)
            TREADMILL_DATA -> TreadmillDataCharacteristic(characteristic)
            CROSS_TRAINER_DATA -> CrossTrainerDataCharacteristic(characteristic)
            STEP_CLIMBER_DATA -> StepClimberDataCharacteristic(characteristic)
            STAIR_CLIMBER_DATA -> StairClimberDataCharacteristic(characteristic)
            //ROWER_DATA -> RowerDataCharacteristic(characteristic)
            FITNESS_MACHINE_CONTROL_POINT -> FitnessMachineControlPointCharacteristic(characteristic)
            FITNESS_MACHINE_STATUS -> FitnessMachineStatusCharacteristic(characteristic)
            CURVE_DATA -> CurveDataCharacteristic(characteristic)
            TOTAL_LENGTH_DATA -> TotalLengthDataCharacteristic(characteristic)
            //ROWER_STATE_DATA -> RowerStateDataCharacteristic(characteristic)
            UART_TX -> UartTxCharacteristic(characteristic)
            UART_RX -> UartRxCharacteristic(characteristic)
            CUSTOM_HR_DATA -> CustomHrCharacteristic(characteristic)
            CUSTOM_LOGIN_POINT -> CustomLoginCharacteristic(characteristic)
            MYRUN_PHYSICAL_SENSOR_DATA -> MyRunPhysicalSensorDataCharacteristic(characteristic)
            TRAINING_STATUS -> TrainingStatusCharacteristic(characteristic)
            else -> return
        }
    }

    fun getCharacteristic(uuid: UUID): BaseCharacteristic? = characteristicMap[uuid.toString()]

    fun elabCharacteristic(characteristic: BluetoothGattCharacteristic): BaseCharacteristic? {
        val baseCharacteristic = characteristicMap[characteristic.uuid.toString()]
        baseCharacteristic?.characteristic = characteristic
        return if (baseCharacteristic?.elabData() == true) baseCharacteristic else null
    }
}