package com.technogym.equipmenttest.bleuart.fitnessmachineservice;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.util.Log;

import com.technogym.equipmenttest.bleuart.fitnessmachineservice.characteristics.CurveDataCharacteristic;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.characteristics.FitnessMachineControlPointCharacteristic;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.characteristics.TotalLengthDataCharacteristic;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.interfaces.CharacteristicListener;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.interfaces.ConnectionListener;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.CustomHrMeasurement;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.FTMControlPointOp;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.FitnessBtDevice;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.FitnessType;
import com.technogym.equipmenttest.bleuart.technogymbtleuart.BLEUartConstants;
import com.technogym.equipmenttest.bleuart.technogymbtleuart.model.CommandModel;

import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.BLUETOOTH_SERVICE;


/**
 * Created by sventurini
 */
public class FitnessMachineService implements ConnectionListener, CharacteristicListener<Object> {

    static final String ACTION_SCAN = "com.technogym.mywellness.sdk.android.fitness_machine_service.SCAN";
    static final String ACTION_CONNECT = "com.technogym.mywellness.sdk.android.fitness_machine_service.CONNECT";
    static final String ACTION_DISCONNECT = "com.technogym.mywellness.sdk.android.fitness_machine_service.DISCONNECT";
    static final String ACTION_READ_ASYNC = "com.technogym.mywellness.sdk.android.fitness_machine_service.READ_ASYNC";
    static final String ACTION_WRITE_ASYNC = "com.technogym.mywellness.sdk.android.fitness_machine_service.WRITE_ASYNC";
    static final String ACTION_WRITE_CONTROL_POINT = "com.technogym.mywellness.sdk.android.fitness_machine_service.ACTION_WRITE_CONTROL_POINT";
    static final String ACTION_WRITE_UART_CMD = "com.technogym.mywellness.sdk.android.fitness_machine_service.ACTION_WRITE_UART_CMD";
    static final String ACTION_WRITE_CUSTOM_HR_MEASUREMENT = "com.technogym.mywellness.sdk.android.fitness_machine_service.ACTION_WRITE_CUSTOM_HR_MEASUREMENT";
    static final String ACTION_WRITE_CUSTOM_LOGIN_POINT = "com.technogym.mywellness.sdk.android.fitness_machine_service.ACTION_WRITE_CUSTOM_LOGIN_POINT";

    static final String ARGS_DEVICE_CONNECT = "deviceConnect";
    static final String ARGS_UUID = "uuid";
    static final String ARGS_CONTROL_POINTS = "control_points";
    public static final String ACTION_STOP = "com.technogym.mywellness.sdk.android.fitness_machine_service.STOP";
    public static final String ARGS_RAW_DATA = "args_raw_data";
    static final String ARGS_UART_CMD = "uart_cmd";
    static final String ARGS_CUSTOM_HR_MEASUREMENT = "custom_hr_measurement";
    static final String ARGS_CUSTOM_LOGIN_VALUE = "custom_login_value";

    private BluetoothAdapter mBluetoothAdapter;
    private FitnessMachineGattController mFitnessMachineGattController;
    private Devices mDevices;
    private ScanCallback mScanCallBack;
    private List<ParcelUuid> services;

    private FitnessMachineManager mManager;
    private ArrayList<FTMControlPointOp> queueWriteControlPoint;

    private WeakReference<Context> context;

    FitnessMachineService(Context context) {
        this.context = new WeakReference<>(context);
        onCreate();
    }

    void onDestroy() {
        mDevices.clearAll();
        mFitnessMachineGattController.onDestroy();
        //handler.removeCallbacks(fakeNotifyRowerDataThread);
        handler.removeCallbacks(fakecurenotify);
    }

    void onCreate() {
        mManager = FitnessMachineManager.getInstance();
        mDevices = new Devices();

        mFitnessMachineGattController = new FitnessMachineGattController(this, this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            initScanCallBack();
        }
    }

    public void onStartCommand(String action, Bundle bundle) {
        Context contextImp = context.get();
        if (contextImp != null && mBluetoothAdapter == null) {
            BluetoothManager bluetoothManager = (BluetoothManager) contextImp.getSystemService(BLUETOOTH_SERVICE);
            mBluetoothAdapter = bluetoothManager.getAdapter();
        }

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            FitnessMachineManager.setsStatus(ConnectionState.BT_ADAPTER_OFF);
            Log.d(ConstantsFitnessMachine.TAG, "Connection state: bt adapter off");
            return;
        }

        if (ACTION_SCAN.equals(action)) {
            if (FitnessMachineManager.fakeConnectionEnabled) {
                if (mDevices != null) mDevices.clearAll();
                FitnessBtDevice fitnessBtDevice = new FitnessBtDevice();
                fitnessBtDevice.setRssi(-1);
                fitnessBtDevice.setAvailability(true);
                fitnessBtDevice.setType(FitnessType.ROWER);
                setDeviceFound(fitnessBtDevice);

            } else {
                startScan();
            }

        } else if (ACTION_CONNECT.equals(action)) {
            if (FitnessMachineManager.fakeConnectionEnabled) {
                FitnessBtDevice fitnessBtDevice = new FitnessBtDevice();
                fitnessBtDevice.setAvailability(true);
                fitnessBtDevice.setType(FitnessType.ROWER);
                connected(null, fitnessBtDevice);
                //handler.post(fakeNotifyRowerDataThread);
                handler.post(fakecurenotify);

            } else {
                FitnessBtDevice device = bundle.getParcelable(ARGS_DEVICE_CONNECT);
                connectToFitnessDevice(device);

            }
        } else if (ACTION_STOP.equals(action)) {
            stopScan();

        } else if (ACTION_DISCONNECT.equals(action)) {
            if (FitnessMachineManager.fakeConnectionEnabled) {
                mManager.dispatchDisconnected();
            } else {
                mFitnessMachineGattController.disconnect();
            }

        } else if (ACTION_READ_ASYNC.equals(action)) {
            if (FitnessMachineManager.getConnectedDevice() != null) {
                String uuid = bundle.getString(ARGS_UUID);
                mFitnessMachineGattController.readCharacteristic(uuid);

            } else {
                //mManager.dispatchFailedRead();
            }

        } else if (ACTION_WRITE_ASYNC.equals(action)) {
            if (FitnessMachineManager.getConnectedDevice() != null) {
                String uuid = bundle.getString(ARGS_UUID);
                String rawData = bundle.getString(ARGS_RAW_DATA);
                mFitnessMachineGattController.writeCharacteristic(uuid, rawData.getBytes());
            }

        } else if (ACTION_WRITE_CONTROL_POINT.equals(action)) {
            if (FitnessMachineManager.getConnectedDevice() != null) {
                queueWriteControlPoint = bundle.getParcelableArrayList(ARGS_CONTROL_POINTS);
                checkPendingQueueWriteControlPoint();
            }
        } else if (ACTION_WRITE_UART_CMD.equals(action)) {
            if (FitnessMachineManager.getConnectedDevice() != null) {
                CommandModel cmd = bundle.getParcelable(ARGS_UART_CMD);
                mFitnessMachineGattController.writeUartCmd(cmd, BLEUartConstants.Characteristics.UART_TX.toString());
            }
        } else if (ACTION_WRITE_CUSTOM_HR_MEASUREMENT.equals(action)) {
            if (FitnessMachineManager.getConnectedDevice() != null) {
                CustomHrMeasurement cmd = bundle.getParcelable(ARGS_CUSTOM_HR_MEASUREMENT);
                mFitnessMachineGattController.writeCustomHrMeasurement(cmd, ConstantsFitnessMachine.Characteristics.CUSTOM_HR_DATA.toString());
            }
        } else if (ACTION_WRITE_CUSTOM_LOGIN_POINT.equals(action)) {
            if (FitnessMachineManager.getConnectedDevice() != null) {
                String userId = bundle.getString(ARGS_CUSTOM_LOGIN_VALUE);
                mFitnessMachineGattController.writeCustomLogin(userId, ConstantsFitnessMachine.Characteristics.CUSTOM_LOGIN_POINT.toString());
            }
        }
    }

    private void checkPendingQueueWriteControlPoint() {
        if (queueWriteControlPoint == null || queueWriteControlPoint.size() <= 0) return;
        Log.d(ConstantsFitnessMachine.TAG,
                "Write To control point " + queueWriteControlPoint.get(0).getOpCodeControl().toString()
                        + " - value: " + queueWriteControlPoint.get(0).getParameter()
                        + " - RAW: " + Arrays.toString(queueWriteControlPoint.get(0).getRawData())
        );
        mFitnessMachineGattController.writeCharacteristic(ConstantsFitnessMachine.Characteristics.FITNESS_MACHINE_CONTROL_POINT.toString(), queueWriteControlPoint.get(0).getRawData());
        queueWriteControlPoint.remove(0);
    }

    /**
     *
     */
    private void startScan() {
        if (mDevices != null) mDevices.clearAll();
        FitnessMachineManager.setsStatus(ConnectionState.SCANNING);
        Log.d(ConstantsFitnessMachine.TAG, "Connection state: scanning...");

        if (!Permissions.checkFineLocationOrCoarsePermission(context.get().getApplicationContext()))
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBluetoothAdapter.getBluetoothLeScanner().startScan(null, Utils.getSpinScanSettings(), mScanCallBack);

        } else {
            mBluetoothAdapter.startLeScan(mLeScanCallback);

        }
    }

    /**
     * To introduce rssi or tx power level:
     * double distance = Utils.calculateDistance(result.getScanRecord().getTxPowerLevel(), result.getRssi());
     * Log.d(ConstantsFitnessMachine.TAG, "device found at distance = " + distance + " m " + " RSSI = " + result.getRssi());
     * if (distance > 2.5) return;
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initScanCallBack() {
        services = null;
        mScanCallBack = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                //scanRecord = result.getScanRecord();
                //if (scanRecord == null) return;
                //services = result.getScanRecord().getServiceUuids();
                //if (services == null) return;
                //for (ParcelUuid uuid : services) {
                //if (uuid.getUuid().toString().equals(ConstantsFitnessMachine.Services.FITNESS_MACHINE_SERVICE.toString())) {
                byte[] scanrecords = new byte[0];
                if (result.getScanRecord() != null) scanrecords = result.getScanRecord().getBytes();
                setDeviceFound(Utils.getFitnessBtDeviceFromBTDevice(result.getDevice(), scanrecords, result.getRssi()));
                //}
                //}
            }
        };
    }


    /**
     * To introduce rssi or tx power level:
     * int txPowerLevel = Integer.valueOf(BLEBase.ParseRecord(scanRecord).get(BLEBase.EBLE_TXPOWERLEVEL));
     * double distance = Utils.calculateDistance(txPowerLevel, rssi);
     * Log.d(ConstantsFitnessMachine.TAG, "device found at distance = " + distance + " m " + " RSSI = " + rssi);
     * if (distance > 2.5) return;
     * if (rssi < ConstantsFitnessMachine.RANGE_RSSI) return;
     */
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            services = BLEBase.getServicesFromBytes(scanRecord);
            if (services == null) return;
            for (ParcelUuid uuid : services) {
                if (uuid.getUuid().toString().equals(ConstantsFitnessMachine.Services.FITNESS_MACHINE_SERVICE.toString())) {
                    setDeviceFound(Utils.getFitnessBtDeviceFromBTDevice(device, scanRecord, rssi));
                }
            }
        }
    };

    /**
     *
     */
    private void stopScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            mBluetoothAdapter.getBluetoothLeScanner().stopScan(mScanCallBack);
        else mBluetoothAdapter.stopLeScan(mLeScanCallback);
        Log.d(ConstantsFitnessMachine.TAG, "Stop scanning");
    }

    /**
     * @param device
     */
    private void connectToFitnessDevice(@Nullable FitnessBtDevice device) {
        if (device == null) return;
        stopScan();
        Log.d(ConstantsFitnessMachine.TAG, "Connecting to: " + device.getDevice().getAddress());
        FitnessMachineManager.setsStatus(ConnectionState.CONNECTING);
        if (device.getDevice() != null && mDevices.getIndexOfBTDev(device.getDevice()) == -1) mDevices.addDevice(device);
        mFitnessMachineGattController.setFitnessBtDevice(mDevices.getDevice(mDevices.getIndexOfBTDev(device.getDevice())));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            device.getDevice().connectGatt(context.get(), false, mFitnessMachineGattController, BluetoothDevice.TRANSPORT_LE);

        } else {
            device.getDevice().connectGatt(context.get(), false, mFitnessMachineGattController);

        }
    }

    /**
     * @param device
     */
    private void setDeviceFound(FitnessBtDevice device) {
        if (mDevices.addDevice(device)) {
            if (FitnessMachineManager.fakeConnectionEnabled)
                Log.d(ConstantsFitnessMachine.TAG, "Device Fake Found");
            else
                Log.d(ConstantsFitnessMachine.TAG, "Device Found: " + device.getDevice().getAddress());
            mManager.dispatchDeviceFound(device);
        }
    }

    @Override
    public void onReadSuccessful(Object characteristic) {
        Log.d(ConstantsFitnessMachine.TAG, "Read successful");
        if (characteristic != null) mManager.dispatchSuccessfulRead(characteristic);
    }

    @Override
    public void onReadFailed(Object characteristic) {
        Log.d(ConstantsFitnessMachine.TAG, "Read failed");
        if (characteristic != null) mManager.dispatchFailedRead(characteristic);
    }

    @Override
    public void onWriteSuccessful(Object characteristic) {
        Log.d(ConstantsFitnessMachine.TAG, "Write successful");
        if (characteristic != null) mManager.dispatchSuccessfulWrite(characteristic);
    }

    @Override
    public void onWriteFailed(Object characteristic) {
        Log.d(ConstantsFitnessMachine.TAG, "Write failed");
        if (characteristic != null) mManager.dispatchFailedWrite(characteristic);
    }

    @Override
    public void onNotify(Object characteristic) {
        if (characteristic instanceof FitnessMachineControlPointCharacteristic)
            checkPendingQueueWriteControlPoint();
        if (characteristic != null) mManager.dispatchChange(characteristic);
    }

    @Override
    public void connected(BluetoothGatt gatt, FitnessBtDevice device) {
        Log.d(ConstantsFitnessMachine.TAG, "CONNECTED");
        FitnessMachineManager.setsStatus(ConnectionState.CONNECTED);
        FitnessMachineManager.setConnectedDevice(device);
        mManager.dispatchDeviceConnected(device);
    }

    @Override
    public void disconnected() {
        Log.d(ConstantsFitnessMachine.TAG, "DISCONNECTED");
        FitnessMachineManager.setsStatus(ConnectionState.DISCONNECTED);
        FitnessMachineManager.setConnectedDevice(null);
        mManager.dispatchDisconnected();
    }

    @Override
    public void configured(FitnessBtDevice device) {
        Log.d(ConstantsFitnessMachine.TAG, "Finish configured machine");
        mManager.dispatchDeviceConfigured(device);
    }

    private Handler handler = new Handler();
    //private FakeNotifyRowerDataThread fakeNotifyRowerDataThread = new FakeNotifyRowerDataThread();
    private FakeCurve fakecurenotify = new FakeCurve();
    private long time = System.currentTimeMillis();
    //private FitnessRowerData fitnessRowerData = new FitnessRowerData();


    private class FakeCurve implements Runnable {

        CurveDataCharacteristic curveDataCharacteristic = new CurveDataCharacteristic();

        TotalLengthDataCharacteristic totalLengthDataCharacteristic = new TotalLengthDataCharacteristic();

        BluetoothGattCharacteristic fakeDataCharacteristic = new BluetoothGattCharacteristic(ConstantsFitnessMachine.Characteristics.CURVE_DATA, 0, 0);

        BluetoothGattCharacteristic fakeTotalLengthCharacteristic = new BluetoothGattCharacteristic(ConstantsFitnessMachine.Characteristics.TOTAL_LENGTH_DATA, 0, 0);

        int count = 0;
        int max = 5;

        FakeCurve() {
            count = 0;
        }

        @Override
        public void run() {

            if (count <= max) {
                int i = (max - count);
                curveDataCharacteristic.setIndexSample0(i);
                curveDataCharacteristic.setSample0(Random.getInt(5, 20) * (i + 1));
                curveDataCharacteristic.setSample1(Random.getInt(5, 20) * (i + 1));
                curveDataCharacteristic.setSample2(Random.getInt(5, 20) * (i + 1));
                curveDataCharacteristic.setSample3(Random.getInt(5, 20) * (i + 1));
                curveDataCharacteristic.setSample4(Random.getInt(5, 20) * (i + 1));

                curveDataCharacteristic.setCharacteristic(fakeDataCharacteristic);
                curveDataCharacteristic.fakeElabData();
                mManager.dispatchChange(curveDataCharacteristic);

                count++;
            } else {
                totalLengthDataCharacteristic.setTotalLengthData(Random.getInt(70, 100));
                totalLengthDataCharacteristic.setCharacteristic(fakeTotalLengthCharacteristic);
                mManager.dispatchChange(totalLengthDataCharacteristic);

                count = 0;
            }

            handler.postDelayed(this, 500);
        }
    }
}
