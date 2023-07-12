package com.technogym.android.myrun.sdk.system.listeners;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.communication.listeners.IListener;

/**
 * It's the interface which must be implemented by every object that wants to be notified of system command reception.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public interface ISystemListener extends IListener {

    // { ISystemListener implementation
	void onLKLifeDataResetted(String status);

	void onLKErrorLogResetted(String status);

	void onLifeTimeResetted(String status);

    void onBluetoothRetrieved(String status);

    void onNFCRetrieved(String status);

    void onDisableGymkitRetrieved(String status);

    /**
	 * This method notifies the installed firmware's bootloader version sent by the equipment.
	 *
	 * @param bootloaderVersion
	 *            : the version of the mounted bootloader
	 * */
	public void onFirmwareBootloaderVersionRetrieved(final String bootloaderVersion);

	/**
	 * This method notifies the installed firmware version sent by the equipment.
	 * 
	 * @param firmwareVersion
	 *            : the version of the mounted firmware
	 * */
	public void onFirmwareVersionRetrieved(final String firmwareVersion);

	/**
	 * This method notifies the result of the equipment's sensor "up" initialization.
	 * 
	 * @param value
	 *            : the received value
	 * */
	public void onValueFromSensorUp(final Integer value);

	/**
	 * This method notifies the result of the equipment's sensor "down" initialization.
	 * 
	 * @param value
	 *            : the received value
	 * */
	public void onValueFromSensorDown(final Integer value);

	/**
	 * This method notifies the result of the equipment's serial number set.
	 * 
	 * @param result
	 *            : {@code true if the serial number was set without problems, {@code false otherwise.
	 * */
	public void onSerialNumberSet(final Boolean result);

	/**
	 * This method notifies the serial number received by the equipment itself.
	 * 
	 * @param serialNumber
	 *            : the serial number
	 * */
	public void onSerialNumberRetrieved(final String serialNumber);

	/**
	 * This method notifies the value of the wakeup sensor.
	 * 
	 * @param wakeupSensorValue
	 *            : the value of the wakeup sensor. @code{0 if free, @code{1 if covered.
	 */
	public void onWakeupSensorRetrieved(final Integer wakeupSensorValue);

	/**
	 * This method notifies the current status of the equipment.
	 * 
	 * @param status
	 *            : the current status of the equipment.
	 */
	public void onStatusRetreived(final String status);

	/**
	 * This method notifies the result of the equipment's measure unit set.
	 * 
	 * @param result
	 *            : {@code true it the measure was set without problems, {@code false otherwise.
	 * */
	public void onMeasureUnitSet(final Boolean result);

	/**
	 * This method notifies the value of the measure unit of the equipment.
	 * 
	 * @param measureUnit
	 *            : the value of the measure unit of the equipment.
	 */
	public void onMeasureUnitRetreived(final String measureUnit);

	/**
	 * This method notifies the result of the equipment's eeprom default values set.
	 * 
	 * @param result
	 *            : {@code OK if the equipment's eeprom default values was set without problems, {@code ERROR otherwise.
	 * */
	public void onSetEEPROMDefaultValuesSet(final String result);

	/**
	 * This method notifies the result of the equipment's error log reset.
	 * 
	 * @param result
	 *            : {@code OK if the equipment's error was reset without problems, {@code ERROR otherwise.
	 * */
	public void onResetErrorLog(final String result);
	
	/**
	 * This method notifies the current error log of the equipment.
	 * 
	 * @param errorLogs
	 * 			  : the error log of the equipment
	 */
	public void onErrorLogRetreived(final String errorLogs);
	
	/**
	 * This method notifies the calibration gradient values vs mV of the equipment
	 * 
	 * @param result
	 * 			  : the calibration gradient values vs mV ridden
	 */
	public void onCalibrationGradientValueMVRetreived(final String result);

	/**
	 * This method notifies the set of the calibration gradient values vs mV of the equipment
	 * 
	 * @param result
	 * 			  : the calibration gradient values vs mV set
	 */
	public void onCalibrationGradientValueMVSet(final String result);
	
	/**
	 * This method notifies the calibration speed values vs Ratio PWM of the equipment
	 * 
	 * @param result
	 * 			  : the calibration speed values vs Ratio PWM ridden
	 */
	public void onCalibrationSpeedValueRatioPWMRetreived(final String result);

	/**
	 * This method notifies the up sensor status of the equipment
	 * 
	 * @param status
	 * 			  : the up sensor status
	 */
	public void onUpSensorStatusRetreived(final String status);

	/**
	 * This method notifies the down sensor status of the equipment
	 * 
	 * @param status
	 * 			  : the down sensor status
	 */
	public void onDownSensorStatusRetreived(final String status);

	public void onSensoreInduttivoRetrieved(final String status);

	public void onStartCalibrationResponse(String mStatus);

	public void onBtVersionRetrieved(final String version);

	public void onMainboardBtVersionRetrieved(final String version);

	public void onWIDVersionRetrieved(final String version);

	public void onLowKitVersionRetrieved (final String version);

	public void onHighKitVersionRetrieved (final String version);

	public void onSuspendCommandResponseRetrieved (final String mStatus);

    public void onTestWakeUpResponseRetrieved(String mStatus);

	public void onCalibrationGradientValueCSet(String mResult);

    public void onButtonStatusResponseRetrieved(String mStatus);

    public void onJoystickStatusRetrieved(String mStatus);

	public void onBuzzResponse(String response);

	// region Live Essential
	void onMachineTypeRetrieved(final String machineType);

	void onPowerTypeRetrieved(final String powerType);
	// endregionpublic void onKeyButtonResponse(String response);public void onTextXScreenResponse(String response);public void onTestScreenOffResponse(String response);public void onGetCardioricevitoreFwResponse(String response);public void onReadWifiConfigResponse(String response);public void onGetDateResponse(String response);public void onGetEmergencyResponse(String response);public void onGetCardioHRValueResponse(String response);public void onStartCardioReceiverResponse(String response);public void onStopCardioReceiverResponse(String response);public void onGetSupercapStatusResponse(String response);public void onSetSpeedResponse(String response);public void onSetGradientResponse(String response);public void onKeyboardStatusResponse(String response);public void onReadInclinationTableCRCResponse(String response);
}