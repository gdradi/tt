package com.technogym.equipmenttest.appbleuart.proxies;

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
import com.technogym.android.myrun.sdk.system.commands.GetMeasureUnitCommand;
import com.technogym.android.myrun.sdk.system.commands.GetSerialNumberCommand;
import com.technogym.android.myrun.sdk.system.commands.GetUpSensorStatusCommand;
import com.technogym.android.myrun.sdk.system.commands.InitSensorDownCommand;
import com.technogym.android.myrun.sdk.system.commands.InitSensorUpCommand;
import com.technogym.android.myrun.sdk.system.commands.ResetLKErrorLogCommand;
import com.technogym.android.myrun.sdk.system.commands.ResetLKLifeDataCommand;
import com.technogym.android.myrun.sdk.system.commands.TestWakeUpCommand;
import com.technogym.android.myrun.sdk.system.commands.WIDVersionCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.BuzzCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.GetCardioHRValueCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.GetCardioricevitoreFwCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.GetDateCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.GetEmergencyCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.GetMachineTypeCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.GetPowerTypeCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.GetSupercapStatusCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.KeyButtonCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.KeyboardStatusCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.ReadInclinationTableCRCCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.ReadWifiConfigCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.SetBikeMachineTypeCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.SetClimbMachineTypeCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.SetGradientCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.SetReclineMachineTypeCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.SetSelfPoweredTypeCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.SetSpeedCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.SetSynchroMachineTypeCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.SetTopMachineTypeCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.SetTreadMillMachineTypeCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.SetVarioMachineTypeCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.SetWallPoweredTypeCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.StartCardioReceiverCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.StopCardioReceiverCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.TestScreenOffCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.TextXScreenCommand;
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
import com.technogym.android.myrun.sdk.system.commands.bleUart.ButtonStatusCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.DetectBluetoothCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.LowKitVersionCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.NFCCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.RebootCommand;
import com.technogym.android.myrun.sdk.system.commands.bleUart.ResetLifeTimeCommand;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.BuzzNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.GetCalibrationGradientValueMVNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.GetCalibrationSpeedValueRatioPWMNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.GetDownSensorStatusNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.GetErrorLogNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.GetSerialNumberNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.GetUpSensorStatusNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.InitSensorDeviceDownNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.InitSensorDeviceUpNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.MeasureUnitNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.ResetLKErrorLogNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.ResetLKLifeDataNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.TestWakeUpNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.WIDVersionNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.GetCardioHRValueNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.GetCardioricevitoreFwNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.GetDateNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.GetEmergencyNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.GetSupercapStatusNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.HighKitVersionNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.KeyButtonNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.KeyboardStatusNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.MachineTypeNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.PowerTypeNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.ReadInclinationTableCRCNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.ReadWifiConfigNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.SetGradientNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.SetSpeedNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.StartCardioReceiverNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.StopCardioReceiverNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.TestScreenOffNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.TextXScreenNotificationRule;
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
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.ButtonStatusNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.DetectBluetoothNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.ForceSuspensionNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.LowKitVersionNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.NFCCommandNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.ResetLifeTimeNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.BleUartBootloaderVersionNotificationRule;
import com.technogym.android.myrun.sdk.system.notificationRules.bleUart.BleUartFirmwareVersionNotificationRule;
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

	// }

	// { IEquipmentProxy abstract methods implementation

	@Override
	public void initializeNotificationRules() {
		if (this.getInitializationState() == false) {

			/*
			 * Per Technogym Cycle vengono utilizzati alcuni comandi del MyRun2020, per evitare duplicazioni.
			 */

			// Setup
			this.addNotificationRule(SetSerialNumberNotificationRule.create());
			this.addNotificationRule(GetSerialNumberNotificationRule.create());
			this.addNotificationRule(BleUartBootloaderVersionNotificationRule.create());
			this.addNotificationRule(BleUartFirmwareVersionNotificationRule.create());
			this.addNotificationRule(LowKitVersionNotificationRule.create());
			this.addNotificationRule(HighKitVersionNotificationRule.create());

			// Setup Live Essentials
			this.addNotificationRule(MachineTypeNotificationRule.create());
			this.addNotificationRule(PowerTypeNotificationRule.create());

			// Azioni
			this.addNotificationRule(SetMeasureUnitNotificationRule.create());
			this.addNotificationRule(MeasureUnitNotificationRule.create());
			this.addNotificationRule(MyRunBtVersionNotificationRule.create());
			this.addNotificationRule(MyRunMainboardBtVersionNotificationRule.create());
			this.addNotificationRule(ForceSuspensionNotificationRule.create());
			this.addNotificationRule(NFCCommandNotificationRule.create());
			this.addNotificationRule(DetectBluetoothNotificationRule.create());
			this.addNotificationRule(ResetErrorLogNotificationRule.create());
			this.addNotificationRule(ResetLifeTimeNotificationRule.create());
			this.addNotificationRule(ResetLKErrorLogNotificationRule.create());
			this.addNotificationRule(ResetLKLifeDataNotificationRule.create());
			this.addNotificationRule(WIDVersionNotificationRule.create());
			this.addNotificationRule(TestWakeUpNotificationRule.create());
			this.addNotificationRule(ButtonStatusNotificationRule.create());
			this.addNotificationRule(BuzzNotificationRule.create());
			this.addNotificationRule(KeyButtonNotificationRule.create());
			this.addNotificationRule(TextXScreenNotificationRule.create());
			this.addNotificationRule(TestScreenOffNotificationRule.create());
			this.addNotificationRule(GetCardioricevitoreFwNotificationRule.create());
			this.addNotificationRule(ReadWifiConfigNotificationRule.create());
			this.addNotificationRule(GetDateNotificationRule.create());
			this.addNotificationRule(GetEmergencyNotificationRule.create());
			this.addNotificationRule(GetCardioHRValueNotificationRule.create());
			this.addNotificationRule(StartCardioReceiverNotificationRule.create());
			this.addNotificationRule(StopCardioReceiverNotificationRule.create());
			this.addNotificationRule(GetSupercapStatusNotificationRule.create());
			this.addNotificationRule(SetSpeedNotificationRule.create());
			this.addNotificationRule(SetGradientNotificationRule.create());
			this.addNotificationRule(KeyboardStatusNotificationRule.create());
			this.addNotificationRule(ReadInclinationTableCRCNotificationRule.create());

			// Verificare quali servono
			this.addNotificationRule(InitSensorDeviceDownNotificationRule.create());
			this.addNotificationRule(InitSensorDeviceUpNotificationRule.create());
			this.addNotificationRule(WakeupSensorValueNotificationRule.create());
			this.addNotificationRule(StatusNotificationRule.create());
			this.addNotificationRule(SetEEPROMDefaultValuesNotificationRule.create());
			this.addNotificationRule(GetErrorLogNotificationRule.create());
			this.addNotificationRule(GetCalibrationGradientValueMVNotificationRule.create());
			this.addNotificationRule(SetCalibrationGradientValueMVNotificationRule.create());
			this.addNotificationRule(GetCalibrationSpeedValueRatioPWMNotificationRule.create());
			this.addNotificationRule(GetUpSensorStatusNotificationRule.create());
			this.addNotificationRule(GetDownSensorStatusNotificationRule.create());
			this.addNotificationRule(MyRunSensoreInduttivoNotificationRule.create());
			this.addNotificationRule(MyRunStartCalibrationNotificationRule.create());
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
		//this.sendCommand(HighKitVersionCommand.create());
	}

	// Setup
	@Override
	public void rebootEquimpent()throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "rebootEquimpent");
		this.sendCommand(RebootCommand.create());
	}

	// region Live Essential
	@Override
	public void setTreadMillMachineType() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "setTreadMillMachineType");
		this.sendCommand(SetTreadMillMachineTypeCommand.create());
	}

	@Override
	public void setSynchroMachineType() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "setSynchroMachineType");
		this.sendCommand(SetSynchroMachineTypeCommand.create());
	}

	@Override
	public void setTopMachineType() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "setTopMachineType");
		this.sendCommand(SetTopMachineTypeCommand.create());
	}

	@Override
	public void setBikeMachineType() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "setBikeMachineType");
		this.sendCommand(SetBikeMachineTypeCommand.create());
	}

	@Override
	public void setReclineMachineType() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "setReclineMachineType");
		this.sendCommand(SetReclineMachineTypeCommand.create());
	}

	@Override
	public void setClimbMachineType() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "setClimbMachineType");
		this.sendCommand(SetClimbMachineTypeCommand.create());
	}

	@Override
	public void setVarioMachineType() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "setVarioMachineType");
		this.sendCommand(SetVarioMachineTypeCommand.create());
	}

	@Override
	public void setWallPoweredType() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "setWallPoweredType");
		this.sendCommand(SetWallPoweredTypeCommand.create());

	}

	@Override
	public void setSelfPoweredType() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "setSelfPoweredType");
		this.sendCommand(SetSelfPoweredTypeCommand.create());
	}

	@Override
	public void getMachineType() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "getMachineType");
		this.sendCommand(GetMachineTypeCommand.create());
	}

	@Override
	public void getPowerType() throws WriteNotAllowedException {
		Logger.getInstance().logDebug("SystemProxy", "getPowerType");
		this.sendCommand(GetPowerTypeCommand.create());
	}
	// endregion


	// Action 1002
	@Override
	public void setMeasureUnit(int measureUnit) throws WriteNotAllowedException {
		this.sendCommand(SetMeasureUnitCommand.create(measureUnit));
	}

	// Action 1002
	@Override
	public void askForMeasureUnit() throws WriteNotAllowedException {
		this.sendCommand(GetMeasureUnitCommand.create());
	}

	// Action 1004
	@Override
	public void resetLifeTime() throws WriteNotAllowedException{
		this.sendCommand(ResetLifeTimeCommand.create());
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

	// Action 1015
	@Override
	public void getStatus() throws WriteNotAllowedException {
		this.sendCommand(StatusCommand.create());
	}

	// Action
	@Override
	public void forceSuspension() throws WriteNotAllowedException {
		this.sendCommand(ForceSuspensionCommand.create());
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

	// Action 1022
	@Override
	public void askWIDVersion() throws WriteNotAllowedException {
		this.sendCommand(WIDVersionCommand.create());
	}

	// Action 1023
	@Override
	public void resetLkLifeData() throws WriteNotAllowedException {
		this.sendCommand(ResetLKLifeDataCommand.create());
	}

	// Action 1024
	@Override
	public void resetLkErrorLog() throws WriteNotAllowedException {
		this.sendCommand(ResetLKErrorLogCommand.create());
	}

	@Override
	public void SetCalibrationGradientValueC(String valueC) throws WriteNotAllowedException {

	}

	// Action 1020
	@Override
	public void verifyButtonStatus() throws WriteNotAllowedException {
		this.sendCommand(ButtonStatusCommand.create());
	}

	@Override
	public void getJoystickStatus(int param) throws WriteNotAllowedException {

	}

	// Action 1005
	@Override
	public void testWakeUp() throws WriteNotAllowedException {
		this.sendCommand(TestWakeUpCommand.create());
	}

	@Override
	public void disableGymkit() throws WriteNotAllowedException {

	}

	@Override
	public void autocalibrate() throws WriteNotAllowedException {
		this.sendCommand(AutocalibrateCommand.create());
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
	public void setCalibrationGradientValueMV(String valueA, String valueB, String valueC)throws WriteNotAllowedException {
		this.sendCommand(SetCalibrationGradientValueMVCommand.create(valueA, valueB, valueC));
	}

	@Override
	public void sendBuzz()throws WriteNotAllowedException {
		this.sendCommand(BuzzCommand.create());
	}

	@Override
	public void sendKeyButton()throws WriteNotAllowedException {
		this.sendCommand(KeyButtonCommand.create());
	}


	@Override
	public void sendTextXScreen(String value)throws WriteNotAllowedException {
		this.sendCommand(TextXScreenCommand.create(value));
	}

	@Override
	public void sendTestScreenOff()throws WriteNotAllowedException {
		this.sendCommand(TestScreenOffCommand.create());
	}

	@Override
	public void sendGetCardioricevitoreFw()throws WriteNotAllowedException {
		this.sendCommand(GetCardioricevitoreFwCommand.create());
	}

	@Override
	public void sendReadWifiConfig()throws WriteNotAllowedException {
		this.sendCommand(ReadWifiConfigCommand.create());
	}

	@Override
	public void sendGetDate()throws WriteNotAllowedException {
		this.sendCommand(GetDateCommand.create());
	}

	@Override
	public void sendGetEmergency()throws WriteNotAllowedException {
		this.sendCommand(GetEmergencyCommand.create());
	}

	@Override
	public void sendGetCardioHRValue()throws WriteNotAllowedException {
		this.sendCommand(GetCardioHRValueCommand.create());
	}

	@Override
	public void sendStartCardioReceiver()throws WriteNotAllowedException {
		this.sendCommand(StartCardioReceiverCommand.create());
	}

	@Override
	public void sendStopCardioReceiver()throws WriteNotAllowedException {
		this.sendCommand(StopCardioReceiverCommand.create());
	}

	@Override
	public void sendGetSupercapStatus()throws WriteNotAllowedException {
		this.sendCommand(GetSupercapStatusCommand.create());
	}

	@Override
	public void sendSetSpeed(String value)throws WriteNotAllowedException {
		this.sendCommand(SetSpeedCommand.create(value));
	}

	@Override
	public void sendSetGradient(String percentage)throws WriteNotAllowedException {
		this.sendCommand(SetGradientCommand.create(percentage));
	}

	@Override
	public void sendKeyboardStatus()throws WriteNotAllowedException {
		this.sendCommand(KeyboardStatusCommand.create());
	}

	@Override
	public void sendReadInclinationTableCRC()throws WriteNotAllowedException {
		this.sendCommand(ReadInclinationTableCRCCommand.create());
	}



	// }

}
