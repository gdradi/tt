package com.technogym.equipmenttest.myrun.proxies;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.communication.proxies.EquipmentProxy;
import com.technogym.android.myrun.sdk.connection.controllers.IEquipmentController;
import com.technogym.android.myrun.sdk.system.commands.AutocalibrateCommand;
import com.technogym.android.myrun.sdk.system.commands.BootloaderVersionCommand;
import com.technogym.android.myrun.sdk.system.commands.DumpIOSensorsValuesCommand;
import com.technogym.android.myrun.sdk.system.commands.FirmwareVersionCommand;
import com.technogym.android.myrun.sdk.system.commands.ForceSuspensionCommand;
import com.technogym.android.myrun.sdk.system.commands.GetCalibrationGradientValueMVCommand;
import com.technogym.android.myrun.sdk.system.commands.GetCalibrationSpeedValueRatioPWMCommand;
import com.technogym.android.myrun.sdk.system.commands.GetDownSensorStatusCommand;
import com.technogym.android.myrun.sdk.system.commands.GetErrorLogCommand;
import com.technogym.android.myrun.sdk.system.commands.GetJoystickStatusCommand;
import com.technogym.android.myrun.sdk.system.commands.GetMeasureUnitCommand;
import com.technogym.android.myrun.sdk.system.commands.GetSerialNumberCommand;
import com.technogym.android.myrun.sdk.system.commands.GetUpSensorStatusCommand;
import com.technogym.android.myrun.sdk.system.commands.InitSensorDownCommand;
import com.technogym.android.myrun.sdk.system.commands.InitSensorUpCommand;
import com.technogym.android.myrun.sdk.system.commands.SetCalibrationGradientValueCCommand;
import com.technogym.android.myrun.sdk.system.commands.TestWakeUpCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.HighKitVersionCommand;
import com.technogym.android.myrun.sdk.system.commands.myrun2020.DisableGymkitCommand;
import com.technogym.android.myrun.sdk.system.commands.myrun2020.MyRunBtVersionCommand;
import com.technogym.android.myrun.sdk.system.commands.myrun2020.MyRunMainboardBtVersionCommand;
import com.technogym.android.myrun.sdk.system.commands.myrun2020.MyRunSensoreInduttivoCommand;
import com.technogym.android.myrun.sdk.system.commands.ResetErrorLogCommand;
import com.technogym.android.myrun.sdk.system.commands.SetCalibrationGradientValueMVCommand;
import com.technogym.android.myrun.sdk.system.commands.SetEEPROMDefaultValuesCommand;
import com.technogym.android.myrun.sdk.system.commands.SetMeasureUnitCommand;
import com.technogym.android.myrun.sdk.system.commands.SetSerialNumberCommand;
import com.technogym.android.myrun.sdk.system.commands.SetUserDetectionCommand;
import com.technogym.android.myrun.sdk.system.commands.StatusCommand;
import com.technogym.android.myrun.sdk.system.commands.myrun2020.DetectBluetoothCommand;
import com.technogym.android.myrun.sdk.system.commands.WIDVersionCommand;
import com.technogym.android.myrun.sdk.system.commands.myrun2020.NFCCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.LowKitVersionCommand;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.notificationRules.GetCalibrationGradientValueMVNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.GetCalibrationSpeedValueRatioPWMNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.GetDownSensorStatusNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.GetErrorLogNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.GetJoystickStatusNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.GetSerialNumberNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.GetUpSensorStatusNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.InitSensorDeviceDownNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.InitSensorDeviceUpNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.MeasureUnitNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.SetCalibrationGradientValueCNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.TestWakeUpNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.myrun2020.DisableGymkitNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.myrun2020.MyRunBleBootloaderVersionNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.myrun2020.MyRunBleFirmwareVersionNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.myrun2020.MyRunBtVersionNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.myrun2020.MyRunMainboardBtVersionNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.myrun2020.MyRunSensoreInduttivoNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.ResetErrorLogNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.SetCalibrationGradientValueMVNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.SetEEPROMDefaultValuesNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.SetMeasureUnitNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.SetSerialNumberNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.StatusNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.WakeupSensorValueNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.myrun2020.MyRunStartCalibrationNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.myrun2020.DetectBluetoothNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.WIDVersionNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.myrun2020.NFCCommandNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.LowKitVersionNotificationRule;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;

import it.spot.android.logger.domain.Logger;

/**
 * This is a final implementation of {@link ISystemProxy} interface.<br/>
 * */
public final class SystemProxy extends EquipmentProxy<ISystemListener> implements ISystemProxy {

	private static SystemProxy mInstance = null;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It instantiates a new {@link EquipmentProxy} for the management of system commands.<br/>
	 * It simply calls the {@code super} constructor.
	 * 
	 * @param connectionController
	 *            : a reference to the controller of the connection with the equipment
	 * */
	protected SystemProxy(final IEquipmentController connectionController) {
		super(connectionController);
	}

	/**
	 * It's a static factory method.<br/>
	 * It achieves the implementation of Singleton pattern.
	 * 
	 * @param connectionController
	 *            : the connection controller required by the class constructor
	 * */
	public static ISystemProxy getInstance(final IEquipmentController connectionController) {
		if (mInstance == null) {
			mInstance = new SystemProxy(connectionController);
		}
		return mInstance;
	}


	public static void destroy() {
		mInstance = null;
	}

	// }

	// { IEquipmentProxy abstract methods implementation

	@Override
	public void initializeNotificationRules() {
		if (this.getInitializationState() == false) {
			// Setup
			this.addNotificationRule(GetSerialNumberNotificationRule.create());
			this.addNotificationRule(SetSerialNumberNotificationRule.create());
			this.addNotificationRule(MyRunBleFirmwareVersionNotificationRule.create());
			this.addNotificationRule(MyRunBleBootloaderVersionNotificationRule.create());
			this.addNotificationRule(LowKitVersionNotificationRule.create());
			// Azioni
			this.addNotificationRule(InitSensorDeviceDownNotificationRule.create());
			this.addNotificationRule(InitSensorDeviceUpNotificationRule.create());
			this.addNotificationRule(WakeupSensorValueNotificationRule.create());
			this.addNotificationRule(StatusNotificationRule.create());
			this.addNotificationRule(MeasureUnitNotificationRule.create());
			this.addNotificationRule(SetMeasureUnitNotificationRule.create());
			this.addNotificationRule(SetEEPROMDefaultValuesNotificationRule.create());
			this.addNotificationRule(ResetErrorLogNotificationRule.create());
			this.addNotificationRule(GetErrorLogNotificationRule.create());
			this.addNotificationRule(GetCalibrationGradientValueMVNotificationRule.create());
			this.addNotificationRule(SetCalibrationGradientValueMVNotificationRule.create());
			this.addNotificationRule(GetCalibrationSpeedValueRatioPWMNotificationRule.create());
			this.addNotificationRule(GetUpSensorStatusNotificationRule.create());
			this.addNotificationRule(GetDownSensorStatusNotificationRule.create());
			this.addNotificationRule(MyRunSensoreInduttivoNotificationRule.create());
			this.addNotificationRule(MyRunStartCalibrationNotificationRule.create());
			this.addNotificationRule(MyRunBtVersionNotificationRule.create());
			this.addNotificationRule(MyRunMainboardBtVersionNotificationRule.create());
			this.addNotificationRule(NFCCommandNotificationRule.create());
			this.addNotificationRule(DetectBluetoothNotificationRule.create());
			this.addNotificationRule(DisableGymkitNotificationRule.create());
			this.addNotificationRule(WIDVersionNotificationRule.create());
			this.addNotificationRule(TestWakeUpNotificationRule.create());
			this.addNotificationRule(SetCalibrationGradientValueCNotificationRule.create());
			this.addNotificationRule(GetJoystickStatusNotificationRule.create());
		}
	}

	// }

	// { IEquipmentProxy methods overriding

	@Override
	protected String getRuleIdentifierFromMessage(final String message) {
		final int index = message.indexOf(":");
		if (index > 0) {
			return message.substring(0, index);
		}
		return message;
	}

	@Override
	protected String getUnparsedParametersFromMessage(final String message) {
		final int index = message.indexOf(":");
		if (index > 0) {
			return message.substring(index + 1);
		}
		return "";
	}

	// }

	// { ISystemProxy implementation


	// Setup
	@Override
	public void askForSerialNumber() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "askForSerialNumber");
		this.sendCommand(GetSerialNumberCommand.create());
	}

	// Setup
	@Override
	public void setSerialNumber(final String serialNumber) throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "setSerialNumber");
		this.sendCommand(SetSerialNumberCommand.create(serialNumber));
	}

	// Setup
	@Override
	public void askForFirmwareVersion() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "askForFirmwareVersion");
		this.sendCommand(FirmwareVersionCommand.create());
	}

	// Setup
	@Override
	public void askForBootloaderFirmwareVersion() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "askForBootloaderFirmwareVersion");
		this.sendCommand(BootloaderVersionCommand.create());
	}

	// Setup
	@Override
	public void askLowKitVersion() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "askLowKitVersion");
		this.sendCommand(LowKitVersionCommand.create());
	}

	@Override
	public void askHighKitVersion() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "askHighKitVersion");
		this.sendCommand(HighKitVersionCommand.create());
	}

	// region Live Essentials
	@Override
	public void setTreadMillMachineType() throws WriteNotAllowedException {

	}

	@Override
	public void setSynchroMachineType() throws WriteNotAllowedException {

	}

	@Override
	public void setTopMachineType() throws WriteNotAllowedException {

	}

	@Override
	public void setBikeMachineType() throws WriteNotAllowedException {

	}

	@Override
	public void setReclineMachineType() throws WriteNotAllowedException {

	}

	@Override
	public void setClimbMachineType() throws WriteNotAllowedException {

	}

	@Override
	public void setVarioMachineType() throws WriteNotAllowedException {

	}

	@Override
	public void setWallPoweredType() throws WriteNotAllowedException {

	}

	@Override
	public void setSelfPoweredType() throws WriteNotAllowedException {

	}

	@Override
	public void getMachineType() throws WriteNotAllowedException {

	}

	@Override
	public void getPowerType() throws WriteNotAllowedException {

	}

	@Override
	public void sendBuzz() throws WriteNotAllowedException {

	}

	@Override
	public void sendKeyButton() throws WriteNotAllowedException {
		
	}

	@Override
	public void sendTextXScreen(String value) throws WriteNotAllowedException {

	}


	@Override
	public void sendTestScreenOff() throws WriteNotAllowedException {

	}

	@Override
	public void sendGetCardioricevitoreFw() throws WriteNotAllowedException {

	}

	@Override
	public void sendReadWifiConfig() throws WriteNotAllowedException {

	}

	@Override
	public void sendGetDate() throws WriteNotAllowedException {

	}

	@Override
	public void sendGetEmergency() throws WriteNotAllowedException {

	}

	@Override
	public void sendGetCardioHRValue() throws WriteNotAllowedException {

	}

	@Override
	public void sendStartCardioReceiver() throws WriteNotAllowedException {

	}

	@Override
	public void sendStopCardioReceiver() throws WriteNotAllowedException {

	}

	@Override
	public void sendGetSupercapStatus() throws WriteNotAllowedException {

	}

	@Override
	public void sendSetSpeed(String value) throws WriteNotAllowedException {

	}

	@Override
	public void sendSetGradient(String percentage) throws WriteNotAllowedException {

	}

	@Override
	public void sendKeyboardStatus() throws WriteNotAllowedException {

	}

	@Override
	public void sendReadInclinationTableCRC() throws WriteNotAllowedException {

	}


	// Action 1010
	@Override
	public void VerifyNFC() throws WriteNotAllowedException {
		this.sendCommand(NFCCommand.create());
	}

	// Action 1011
	@Override
	public void DetectBluetooth() throws WriteNotAllowedException{
		this.sendCommand(DetectBluetoothCommand.create());
	}

	// Action 1018
	@Override
	public void askBtVersion() throws WriteNotAllowedException {
		this.sendCommand(MyRunBtVersionCommand.create());
	}

	// Action 1019
	@Override
	public void askMainboardBtVersion() throws WriteNotAllowedException {
		this.sendCommand(MyRunMainboardBtVersionCommand.create());
	}

	// Action 1021
	@Override
	public void disableGymkit() throws WriteNotAllowedException {
		this.sendCommand(DisableGymkitCommand.create());
	}

	// Action 1022
	@Override
	public void askWIDVersion() throws WriteNotAllowedException {
		this.sendCommand(WIDVersionCommand.create());
	}

	// Action 1005
	@Override
	public void testWakeUp() throws WriteNotAllowedException {
		this.sendCommand(TestWakeUpCommand.create());
	}

	@Override
	public void resetLkLifeData() throws WriteNotAllowedException {

	}

	@Override
	public void resetLkErrorLog() throws WriteNotAllowedException {

	}

	@Override
	public void SetCalibrationGradientValueC(final String valueC) throws WriteNotAllowedException {
		this.sendCommand(SetCalibrationGradientValueCCommand.create(valueC));
	}

	@Override
	public void verifyButtonStatus() throws WriteNotAllowedException {

	}

	@Override
	public void getJoystickStatus(int param) throws WriteNotAllowedException {
		this.sendCommand(GetJoystickStatusCommand.create(Integer.toString(param)));
	}

	@Override
	public void autocalibrate() throws WriteNotAllowedException {
		this.sendCommand(AutocalibrateCommand.create());
	}

	@Override
	public void forceSuspension() throws WriteNotAllowedException {
		this.sendCommand(ForceSuspensionCommand.create());
	}

	@Override
	public void setUserDetection(final Integer targetValue) throws WriteNotAllowedException {
		this.sendCommand(SetUserDetectionCommand.create(targetValue));
	}

	@Override
	public void initSensorDeviceDown() throws WriteNotAllowedException {
		this.sendCommand(InitSensorDownCommand.create());
	}

	@Override
	public void initSensorDeviceUp() throws WriteNotAllowedException {
		this.sendCommand(InitSensorUpCommand.create());
	}

	@Override
	public void dumpIOSensorsValues() throws WriteNotAllowedException {
		this.sendCommand(DumpIOSensorsValuesCommand.create());
	}

	@Override
	public void getStatus() throws WriteNotAllowedException {
		this.sendCommand(StatusCommand.create());
	}

	@Override
	public void setMeasureUnit(int measureUnit) throws WriteNotAllowedException {
		this.sendCommand(SetMeasureUnitCommand.create(measureUnit));
	}

	@Override
	public void askForMeasureUnit() throws WriteNotAllowedException {
		this.sendCommand(GetMeasureUnitCommand.create());
	}

	@Override
	public void setEEPROMDefaultValues() throws WriteNotAllowedException {
		this.sendCommand(SetEEPROMDefaultValuesCommand.create());
	}

	@Override
	public void resetErrorLog() throws WriteNotAllowedException {
		this.sendCommand(ResetErrorLogCommand.create());
	}

	@Override
	public void getErrorLog() throws WriteNotAllowedException {
		this.sendCommand(GetErrorLogCommand.create());
	}

	@Override
	public void getCalibrationGradientValueMV() throws WriteNotAllowedException {
		this.sendCommand(GetCalibrationGradientValueMVCommand.create());
	}

	@Override
	public void getCalibrationSpeedValueRatioPWM() throws WriteNotAllowedException {
		this.sendCommand(GetCalibrationSpeedValueRatioPWMCommand.create());
	}

	@Override
	public void getUpSensorStatus() throws WriteNotAllowedException {
		this.sendCommand(GetUpSensorStatusCommand.create());
	}

	@Override
	public void getDownSensorStatus() throws WriteNotAllowedException {
		this.sendCommand(GetDownSensorStatusCommand.create());
	}

	@Override
	public void getMyRunSensoreInduttivoStatus() throws WriteNotAllowedException {
		this.sendCommand(MyRunSensoreInduttivoCommand.create());
	}

	@Override
	public void resetLifeTime() throws WriteNotAllowedException {

	}

	@Override
	public void setCalibrationGradientValueMV(String valueA, String valueB, String valueC)
			throws WriteNotAllowedException {
		this.sendCommand(SetCalibrationGradientValueMVCommand.create(valueA, valueB, valueC));
	}

	@Override
	public void rebootEquimpent() throws WriteNotAllowedException {

	}
	// }

}
