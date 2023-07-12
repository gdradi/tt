package com.technogym.android.myrun.sdk.system.proxies;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.communication.proxies.EquipmentProxy;
import com.technogym.android.myrun.sdk.connection.controllers.IEquipmentController;
import com.technogym.android.myrun.sdk.system.commands.*;
import com.technogym.android.myrun.sdk.system.commands.myrun2020.MyRunBtVersionCommand;
import com.technogym.android.myrun.sdk.system.commands.myrun2020.MyRunMainboardBtVersionCommand;
import com.technogym.android.myrun.sdk.system.commands.myrun2020.MyRunSensoreInduttivoCommand;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.notificationRules.*;

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
			this.addNotificationRule(BootloaderVersionNotificationRule.create());
			this.addNotificationRule(FirmwareVersionNotificationRule.create());
			this.addNotificationRule(InitSensorDeviceDownNotificationRule.create());
			this.addNotificationRule(InitSensorDeviceUpNotificationRule.create());
			this.addNotificationRule(SetSerialNumberNotificationRule.create());
			this.addNotificationRule(GetSerialNumberNotificationRule.create());
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

	@Override
	public void askForBootloaderFirmwareVersion() throws WriteNotAllowedException {
		this.sendCommand(BootloaderVersionCommand.create());
	}

	@Override
	public void rebootEquimpent() throws WriteNotAllowedException {

	}

	@Override
	public void askForFirmwareVersion() throws WriteNotAllowedException {
		this.sendCommand(FirmwareVersionCommand.create());
	}

	@Override
	public void askLowKitVersion() throws WriteNotAllowedException {

	}

	@Override
	public void askHighKitVersion() throws WriteNotAllowedException {

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


	// endregion

	@Override
	public void VerifyNFC() throws WriteNotAllowedException {

	}

	@Override
	public void askWIDVersion() throws WriteNotAllowedException {

	}

	@Override
	public void testWakeUp() throws WriteNotAllowedException {

	}

	@Override
	public void resetLkLifeData() throws WriteNotAllowedException {

	}

	@Override
	public void resetLkErrorLog() throws WriteNotAllowedException {

	}

	@Override
	public void SetCalibrationGradientValueC(String valueC) throws WriteNotAllowedException {

	}

    @Override
    public void verifyButtonStatus() throws WriteNotAllowedException {

    }

	@Override
	public void getJoystickStatus(int param) throws WriteNotAllowedException {

	}

	@Override
	public void DetectBluetooth() throws WriteNotAllowedException {

	}

	@Override
	public void disableGymkit() throws WriteNotAllowedException {

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
	public void setSerialNumber(final String serialNumber) throws WriteNotAllowedException {
		this.sendCommand(SetSerialNumberCommand.create(serialNumber));
	}

	@Override
	public void askForSerialNumber() throws WriteNotAllowedException {
		this.sendCommand(GetSerialNumberCommand.create());
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
	public void setCalibrationGradientValueMV(String valueA, String valueB, String valueC)
			throws WriteNotAllowedException {
		this.sendCommand(SetCalibrationGradientValueMVCommand.create(valueA, valueB, valueC));
	}

	@Override
	public void getMyRunSensoreInduttivoStatus() throws WriteNotAllowedException {
		this.sendCommand(MyRunSensoreInduttivoCommand.create());
	}

	@Override
	public void resetLifeTime() throws WriteNotAllowedException {

	}

	@Override
	public void askBtVersion() throws WriteNotAllowedException {
		this.sendCommand(MyRunBtVersionCommand.create());
	}

	@Override
	public void askMainboardBtVersion() throws WriteNotAllowedException {
		this.sendCommand(MyRunMainboardBtVersionCommand.create());
	}

	// }

}
