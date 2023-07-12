package com.technogym.android.myrun.sdk.system.proxies;

import android.webkit.JavascriptInterface;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.communication.proxies.IEquipmentProxy;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

/**
 * It's the interface for an {@link IEquipmentProxy} which manages the system functionalities and allows it to
 * communicate properly with the equipment.<br/>
 * <br/>
 * It resolves the generic {@code TListener} with an appropriate {@link ISystemListener}, so that external components
 * can register themselves for system notification events.
 *
 * @author Federico Foschini
 * @version 1.0
 */
public interface ISystemProxy extends IEquipmentProxy<ISystemListener> {

    /**
     * This method sends to the equipment a request for the version of the firmware's bootloader currently installed.
     * The equipment should answer with a string representing the bootloader's version number.
     */
    void askForBootloaderFirmwareVersion() throws WriteNotAllowedException;

    // Setup
    void rebootEquimpent() throws WriteNotAllowedException;

    /**
     * This method sends to the equipment a request for the version of the firmware currently installed. The equipment
     * should answer with a string representing the version number.
     */
    void askForFirmwareVersion() throws WriteNotAllowedException;

    // Action 1011
    void DetectBluetooth() throws WriteNotAllowedException;

    // Action 1021
    void disableGymkit() throws WriteNotAllowedException;

    /**
     * This method sends to the equipment a request for auto-calibration: the equipment should automatically calibrate
     * its speed and this method does not expect an answer.
     */
    void autocalibrate() throws WriteNotAllowedException;

    /**
     * This method sends to the equipment a request for forced suspension. It does not expect any answer.
     */
    void forceSuspension() throws WriteNotAllowedException;

    /**
     * This method sends to the equipment the serial number to set.
     *
     * @param serialNumber : the {@code String} containing the serial number to set
     */
    void setSerialNumber(final String serialNumber) throws WriteNotAllowedException;

    /**
     * This method asks for the serial number directly to the equipment.
     */
    void askForSerialNumber() throws WriteNotAllowedException;

    /**
     * This method sends to the equipment the sensitivity value to set for user detection. This method does not expect a
     * confirmation response.
     *
     * @param targetValue : the {@code Integer} value to set
     */
    void setUserDetection(final Integer targetValue) throws WriteNotAllowedException;

    /**
     * This method sends a command to initialize "device-up" sensor.
     */
    void initSensorDeviceUp() throws WriteNotAllowedException;

    /**
     * This method sends a command to initialize "device-down" sensor.
     */
    void initSensorDeviceDown() throws WriteNotAllowedException;

    /**
     * This method sends a command to dump the IO sensors values.
     */
    void dumpIOSensorsValues() throws WriteNotAllowedException;

    /**
     * This method sends to the equipment a request in order to obtain its current status
     */
    void getStatus() throws WriteNotAllowedException;

    /**
     * This method sends to the equipment the measuer unit to set.
     *
     * @param measureUnit : the {@code Integer} containing the measure unit number to set
     */
    void setMeasureUnit(final int measureUnit) throws WriteNotAllowedException;

    /**
     * This method sends to the equipment a request for the measure unit. The equipment
     * should answer with a string representing the current measure unit
     */
    void askForMeasureUnit() throws WriteNotAllowedException;

    /**
     * This method sends to the equipment a request for the reset to default values of the eeprom.
     * The equipment should answer with a string representing the success or not of the reset
     */
    void setEEPROMDefaultValues() throws WriteNotAllowedException;

    /**
     * This method sends to the equipment a request for reset the error log.
     * The equipment should answer with a string representing the success or not of the reset
     */
    void resetErrorLog() throws WriteNotAllowedException;

    /**
     * This method sends to the equipment a request to get the error log.
     * The equipment should answer with a string representing the error log collection
     */
    void getErrorLog() throws WriteNotAllowedException;

    /**
     * This method sends to the equipment a request to get the Calibration Gradient Value vs MV.
     * The equipment should answer with a string representing the calibration values
     */
    void getCalibrationGradientValueMV() throws WriteNotAllowedException;

    /**
     * This method sends to the equipment a request to get the Calibration Speed Value Ratio PWM.
     * The equipment should answer with a string representing the calibration values
     */
    void getCalibrationSpeedValueRatioPWM() throws WriteNotAllowedException;

    /**
     * This method sends to the equipment a request to get the up sensor status.
     * The equipment should answer with a string representing the up sensor status
     */
    void getUpSensorStatus() throws WriteNotAllowedException;

    /**
     * This method sends to the equipment a request to get the down sensor status.
     * The equipment should answer with a string representing the down sensor status
     */
    void getDownSensorStatus() throws WriteNotAllowedException;

    /**
     * This method sends to the equipment a request to set the Calibration Gradient Value vs MV.
     * The equipment should answer with a string representing the calibration values
     *
     * @param valueA : the first value of the calibration corner of the mV ADC gradient
     * @param valueB : the second value of the calibration corner of the mV ADC gradient
     * @param valueC : the third value of the calibration corner of the mV ADC gradient
     */
    void setCalibrationGradientValueMV(final String valueA, final String valueB, final String valueC) throws WriteNotAllowedException;

    void getMyRunSensoreInduttivoStatus() throws WriteNotAllowedException;

    // Action 1004
    void resetLifeTime() throws WriteNotAllowedException;

    void askBtVersion() throws WriteNotAllowedException;

    void askMainboardBtVersion() throws WriteNotAllowedException;

    void askLowKitVersion() throws WriteNotAllowedException;

    void askHighKitVersion() throws WriteNotAllowedException;

    void VerifyNFC() throws WriteNotAllowedException;

    // Action 1022
    void askWIDVersion() throws WriteNotAllowedException;

    // Action 1005
    void testWakeUp() throws WriteNotAllowedException;

    // Action 1023
    void resetLkLifeData() throws WriteNotAllowedException;

    // Action 1024
    void resetLkErrorLog() throws WriteNotAllowedException;

    // Action 279 MyRun2020
    void SetCalibrationGradientValueC(final String valueC) throws WriteNotAllowedException;

    // Action 1020
    void verifyButtonStatus() throws WriteNotAllowedException;

    // Action 1001 MyRun2020
    void getJoystickStatus(final int param) throws WriteNotAllowedException;

    // region Live Essential

    void setTreadMillMachineType() throws WriteNotAllowedException;

    void setSynchroMachineType() throws WriteNotAllowedException;

    void setTopMachineType() throws WriteNotAllowedException;

    void setBikeMachineType() throws WriteNotAllowedException;

    void setReclineMachineType() throws WriteNotAllowedException;

    void setClimbMachineType() throws WriteNotAllowedException;

    void setVarioMachineType() throws WriteNotAllowedException;

    void setWallPoweredType() throws WriteNotAllowedException;

    void setSelfPoweredType() throws WriteNotAllowedException;

    void getMachineType() throws WriteNotAllowedException;

    void getPowerType() throws WriteNotAllowedException;

    void sendBuzz() throws WriteNotAllowedException;

    void sendKeyButton() throws WriteNotAllowedException;
    void sendTextXScreen(String value) throws WriteNotAllowedException;

    void sendTestScreenOff() throws WriteNotAllowedException;

    void sendGetCardioricevitoreFw() throws WriteNotAllowedException;

    void sendReadWifiConfig() throws WriteNotAllowedException;

    void sendGetDate() throws WriteNotAllowedException;

    void sendGetEmergency() throws WriteNotAllowedException;

    void sendGetCardioHRValue() throws WriteNotAllowedException;

    void sendStartCardioReceiver() throws WriteNotAllowedException;

    void sendStopCardioReceiver() throws WriteNotAllowedException;

    void sendGetSupercapStatus() throws WriteNotAllowedException;

    void sendSetSpeed(String value) throws WriteNotAllowedException;

    void sendSetGradient(String percentage) throws WriteNotAllowedException;void sendKeyboardStatus() throws WriteNotAllowedException;void sendReadInclinationTableCRC() throws WriteNotAllowedException;
}