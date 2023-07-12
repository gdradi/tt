package com.technogym.equipmenttest.app.models;

import android.content.Context;

import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.equipmenttest.library.models.Action;

public class BaseAction extends Action implements ISystemListener {

    /**
     * Base constructor for an {@link Action}.
     *
     * @param context : the application's {@link Context} within it gets executed.
     */
    protected BaseAction(Context context) {
        super(context);
    }

    @Override
    public void execute(String data) {

    }

    @Override
    public void onLKLifeDataResetted(String status) {

    }

    @Override
    public void onLKErrorLogResetted(String status) {

    }

    @Override
    public void onLifeTimeResetted(String status) {

    }

    @Override
    public void onBluetoothRetrieved(String status) {

    }

    @Override
    public void onNFCRetrieved(String status) {

    }

    @Override
    public void onDisableGymkitRetrieved(String status) {

    }

    @Override
    public void onFirmwareBootloaderVersionRetrieved(String bootloaderVersion) {

    }

    @Override
    public void onFirmwareVersionRetrieved(String firmwareVersion) {

    }

    @Override
    public void onValueFromSensorUp(Integer value) {

    }

    @Override
    public void onValueFromSensorDown(Integer value) {

    }

    @Override
    public void onSerialNumberSet(Boolean result) {

    }

    @Override
    public void onSerialNumberRetrieved(String serialNumber) {

    }

    @Override
    public void onWakeupSensorRetrieved(Integer wakeupSensorValue) {

    }

    @Override
    public void onStatusRetreived(String status) {

    }

    @Override
    public void onMeasureUnitSet(Boolean result) {

    }

    @Override
    public void onMeasureUnitRetreived(String measureUnit) {

    }

    @Override
    public void onSetEEPROMDefaultValuesSet(String result) {

    }

    @Override
    public void onResetErrorLog(String result) {

    }

    @Override
    public void onErrorLogRetreived(String errorLogs) {

    }

    @Override
    public void onCalibrationGradientValueMVRetreived(String result) {

    }

    @Override
    public void onCalibrationGradientValueMVSet(String result) {

    }

    @Override
    public void onCalibrationSpeedValueRatioPWMRetreived(String result) {

    }

    @Override
    public void onUpSensorStatusRetreived(String status) {

    }

    @Override
    public void onDownSensorStatusRetreived(String status) {

    }

    @Override
    public void onSensoreInduttivoRetrieved(String status) {

    }

    @Override
    public void onStartCalibrationResponse(String mStatus) {

    }

    @Override
    public void onBtVersionRetrieved(String version) {

    }

    @Override
    public void onMainboardBtVersionRetrieved(String version) {

    }

    @Override
    public void onWIDVersionRetrieved(String version) {

    }

    @Override
    public void onLowKitVersionRetrieved(String version) {

    }

    @Override
    public void onHighKitVersionRetrieved(String version) {

    }

    @Override
    public void onSuspendCommandResponseRetrieved(String mStatus) {

    }

    @Override
    public void onTestWakeUpResponseRetrieved(String mStatus) {

    }

    @Override
    public void onCalibrationGradientValueCSet(String mResult) {

    }

    @Override
    public void onButtonStatusResponseRetrieved(String mStatus) {

    }

    @Override
    public void onJoystickStatusRetrieved(String mStatus) {

    }

    @Override
    public void onBuzzResponse(String response) {

    }

    @Override
    public void onMachineTypeRetrieved(String machineType) {

    }

    @Override
    public void onPowerTypeRetrieved(String powerType) {

    }

    @Override
    public void onKeyButtonResponse(String response) {

    }

    @Override
    public void onTextXScreenResponse(String response) {

    }

    @Override
    public void onTestScreenOffResponse(String response) {

    }

    @Override
    public void onGetCardioricevitoreFwResponse(String response) {

    }

    @Override
    public void onReadWifiConfigResponse(String response) {

    }

    @Override
    public void onGetDateResponse(String response) {

    }

    @Override
    public void onGetEmergencyResponse(String response) {

    }

    @Override
    public void onGetCardioHRValueResponse(String response) {

    }

    @Override
    public void onStartCardioReceiverResponse(String response) {

    }

    @Override
    public void onStopCardioReceiverResponse(String response) {

    }

    @Override
    public void onGetSupercapStatusResponse(String response) {

    }

    @Override
    public void onSetSpeedResponse(String response) {

    }

    @Override
    public void onSetGradientResponse(String response) {

    }

    @Override
    public void onKeyboardStatusResponse(String response) {

    }

    @Override
    public void onReadInclinationTableCRCResponse(String response) {

    }
}
