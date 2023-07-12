package com.technogym.equipmenttest.bleuart.fitnessmachineservice;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;


import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.CustomHrMeasurement;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.FTMControlPointOp;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.FitnessBtDevice;
import com.technogym.equipmenttest.bleuart.technogymbtleuart.model.CommandModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Simone Venturini
 */
public class FitnessMachineManager {

    public interface OnScanCallback {

        void onDeviceFound(FitnessBtDevice device);

        void onConnected(FitnessBtDevice device);

        void onConfigured(FitnessBtDevice device);

        void onDisconnected();
    }

    /**
     * @param <T extends BaseCharacteristic>
     * @see com.technogym.sdk.fitnessmachineservice.characteristics.TreadmillDataCharacteristic
     */
    public interface OnReadCharacteristicCallback<T extends BaseCharacteristic> {
        void onReadSuccessfull(T characteristic);

        void onReadFailed(T characteristic);
    }

    /**
     * @param <T extends BaseCharacteristic>
     */
    public interface OnWriteCharacteristicCallback<T extends BaseCharacteristic> {
        void onWriteSuccessfull(T characteristic);

        void onWriteFailed(T characteristic);
    }

    /**
     * @param <T extends BaseCharacteristic>
     */
    public interface OnChangeCharacteristicCallback<T extends BaseCharacteristic> {
        void onChange(T characteristic);
    }

    private static FitnessMachineManager sInstance;
    private Context mContext;
    private List<OnScanCallback> mScanCallbacks;
    private Map<String, List<OnChangeCharacteristicCallback>> mMapChangeCallback;
    private Map<String, List<OnReadCharacteristicCallback>> mMapReadCallback;
    private Map<String, List<OnWriteCharacteristicCallback>> mMapWriteCallback;
    public static boolean fakeConnectionEnabled = false;
    public static int fakeConnectionPausedAfter = 0;
    private static int sStatus = ConnectionState.DISCONNECTED;
    private static FitnessBtDevice connectedDevice;

    private FitnessMachineService service;

    public static synchronized FitnessMachineManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FitnessMachineManager(context);
        }
        return sInstance;
    }

    /* package */
    static synchronized FitnessMachineManager getInstance() {
        return sInstance;
    }

    //Use getConnectedDevice
    @Deprecated
    public static int getsStatus() {
        return sStatus;
    }

    public static void setsStatus(int sStatus) {
        FitnessMachineManager.sStatus = sStatus;
    }

    /**
     * @return null if no devices connected
     */
    public static FitnessBtDevice getConnectedDevice() {
        return connectedDevice;
    }

    public static void setConnectedDevice(FitnessBtDevice connectedDevice) {
        FitnessMachineManager.connectedDevice = connectedDevice;
    }

    private FitnessMachineManager(Context context) {
        this.mContext = context;
        mHandler = new Handler();
    }

    public void addOnScanCallback(OnScanCallback scanCallback) {
        if (mScanCallbacks == null) mScanCallbacks = new ArrayList<>();
        mScanCallbacks.add(scanCallback);
    }

    public void removeOnScanCallback(OnScanCallback scanCallback) {
        if (mScanCallbacks == null) return;
        mScanCallbacks.remove(scanCallback);
    }

    private Handler mHandler;

    public void addOnChangeCharacteristicCallback(OnChangeCharacteristicCallback dataCallback, UUID characteristicNotifyUuid) {
        if (mMapChangeCallback == null) {
            mMapChangeCallback = new HashMap<>();
            List<OnChangeCharacteristicCallback> changeListCallback = new ArrayList<>();
            changeListCallback.add(dataCallback);
            mMapChangeCallback.put(characteristicNotifyUuid.toString(), changeListCallback);

        } else {
            List<OnChangeCharacteristicCallback> changeListCallback = mMapChangeCallback.get(characteristicNotifyUuid.toString());
            if (changeListCallback == null)
                changeListCallback = new ArrayList<>();
            changeListCallback.add(dataCallback);
            mMapChangeCallback.put(characteristicNotifyUuid.toString(), changeListCallback);

        }
    }

    public void removeChangeCharacteristicCallback(OnChangeCharacteristicCallback callback) {
        if (mMapChangeCallback == null) return;
        for (List<OnChangeCharacteristicCallback> changeCallbackList : mMapChangeCallback.values()) {
            if (changeCallbackList.contains(callback)) changeCallbackList.remove(callback);
        }
    }

    public void addOnReadCharacteristicCallback(OnReadCharacteristicCallback dataCallback, UUID characteristicReadUuid) {
        if (mMapReadCallback == null) {
            mMapReadCallback = new HashMap<>();
            List<OnReadCharacteristicCallback> readListCallback = new ArrayList<>();
            readListCallback.add(dataCallback);
            mMapReadCallback.put(characteristicReadUuid.toString(), readListCallback);

        } else {
            List<OnReadCharacteristicCallback> readListCallback = mMapReadCallback.get(characteristicReadUuid.toString());
            if (readListCallback == null) readListCallback = new ArrayList<>();
            readListCallback.add(dataCallback);
            mMapReadCallback.put(characteristicReadUuid.toString(), readListCallback);

        }
    }

    public void removeReadCharacteristicCallback(OnReadCharacteristicCallback callback) {
        if (mMapReadCallback == null) return;
        for (List<OnReadCharacteristicCallback> readCallbackList : mMapReadCallback.values()) {
            if (readCallbackList.contains(callback)) readCallbackList.remove(callback);
        }
    }

    public void addOnWriteCharacteristicCallback(OnWriteCharacteristicCallback dataCallback, UUID characteristicWriteUuid) {
        if (mMapWriteCallback == null) {
            mMapWriteCallback = new HashMap<>();
            List<OnWriteCharacteristicCallback> writeListCallback = new ArrayList<>();
            writeListCallback.add(dataCallback);
            mMapWriteCallback.put(characteristicWriteUuid.toString(), writeListCallback);

        } else {
            List<OnWriteCharacteristicCallback> writeListCallback = mMapWriteCallback.get(characteristicWriteUuid.toString());
            if (writeListCallback == null) writeListCallback = new ArrayList<>();
            writeListCallback.add(dataCallback);
            mMapWriteCallback.put(characteristicWriteUuid.toString(), writeListCallback);

        }
    }

    public void removeWriteCharacteristicCallback(OnWriteCharacteristicCallback callback) {
        if (mMapWriteCallback == null) return;
        for (List<OnWriteCharacteristicCallback> writeCallbackList : mMapWriteCallback.values()) {
            if (writeCallbackList.contains(callback)) writeCallbackList.remove(callback);
        }
    }

    public void destroy() {
        if (service != null)
            service.onDestroy();
    }

    public void startScan() {
        if (service == null)
            service = new FitnessMachineService(mContext);

        service.onStartCommand(FitnessMachineService.ACTION_SCAN, Bundle.EMPTY);
    }

    public void stopScan() {
        if (service == null)
            service = new FitnessMachineService(mContext);

        service.onStartCommand(FitnessMachineService.ACTION_STOP, Bundle.EMPTY);
    }

    /**
     *
     * @param device define the type of fitness machine
     */
    public void connect(FitnessBtDevice device) {
        if (service == null)
            service = new FitnessMachineService(mContext);

        Bundle bundle = new Bundle();
        bundle.putParcelable(FitnessMachineService.ARGS_DEVICE_CONNECT, device);
        service.onStartCommand(FitnessMachineService.ACTION_CONNECT, bundle);
    }

    /**
     *
     * @param device
     * @see #connect(FitnessBtDevice)
     */
    @Deprecated
    public void connect(BluetoothDevice device) {
        connect(new FitnessBtDevice(device));
    }

    public void connect(String macAddress) {
        connect(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(macAddress));
    }

    public void disconnect() {
        if (service == null)
            service = new FitnessMachineService(mContext);

        service.onStartCommand(FitnessMachineService.ACTION_DISCONNECT, Bundle.EMPTY);
    }

    /**
     * To receive the read callback you have to implement
     *
     * @see OnReadCharacteristicCallback and  use the method
     * addOnReadCharacteristicCallback passing the UUID you are interesting to read.
     */
    public void readAsync(UUID characteristicReadUuid) {
        if (service == null)
            service = new FitnessMachineService(mContext);

        Bundle bundle = new Bundle();
        bundle.putString(FitnessMachineService.ARGS_UUID, characteristicReadUuid.toString());
        service.onStartCommand(FitnessMachineService.ACTION_READ_ASYNC, bundle);
    }

    /**
     * To receive the write callback you have to implement
     *
     * @see OnWriteCharacteristicCallback and use the method
     * addOnWriteCharacteristicCallback passing the UUID you are interesting to read.
     */
    public void writeAsync(UUID characteristicWriteUuid, String rawData) {
        if (service == null)
            service = new FitnessMachineService(mContext);

        Bundle bundle = new Bundle();
        bundle.putString(FitnessMachineService.ARGS_UUID, characteristicWriteUuid.toString());
        bundle.putString(FitnessMachineService.ARGS_RAW_DATA, rawData);
        service.onStartCommand(FitnessMachineService.ACTION_WRITE_ASYNC, bundle);
    }

    public boolean writeCustomHrMeasurement(CustomHrMeasurement measurement) {
        if (service == null)
            service = new FitnessMachineService(mContext);

        Bundle bundle = new Bundle();
        bundle.putParcelable(FitnessMachineService.ARGS_CUSTOM_HR_MEASUREMENT, measurement);
        service.onStartCommand(FitnessMachineService.ACTION_WRITE_CUSTOM_HR_MEASUREMENT, bundle);
        return true;
    }

    public boolean writeCustomLogin(String userId) {
        if (service == null)
            service = new FitnessMachineService(mContext);

        Bundle bundle = new Bundle();
        bundle.putString(FitnessMachineService.ARGS_CUSTOM_LOGIN_VALUE, userId);
        service.onStartCommand(FitnessMachineService.ACTION_WRITE_CUSTOM_LOGIN_POINT, bundle);
        return true;
    }

    /**
     * @return false if there's no device connectedAndReady
     */
    public boolean writeUartCmd(CommandModel cmd) {
        if (service == null)
            service = new FitnessMachineService(mContext);

        Bundle bundle = new Bundle();
        bundle.putParcelable(FitnessMachineService.ARGS_UART_CMD, cmd);
        service.onStartCommand(FitnessMachineService.ACTION_WRITE_UART_CMD, bundle);
        return true;
    }

    public void writeControlPoint(FTMControlPointOp... paramsArray) {
        ArrayList<FTMControlPointOp> params = new ArrayList<>();
        Collections.addAll(params, paramsArray);

        if (service == null)
            service = new FitnessMachineService(mContext);

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(FitnessMachineService.ARGS_CONTROL_POINTS, params);
        service.onStartCommand(FitnessMachineService.ACTION_WRITE_CONTROL_POINT, bundle);
    }

    //region Callback for clients
    void dispatchDeviceFound(final FitnessBtDevice device) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mScanCallbacks == null) return;
                    for (OnScanCallback callback : mScanCallbacks) {
                        if (callback != null) callback.onDeviceFound(device);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void dispatchDeviceConnected(final FitnessBtDevice device) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mScanCallbacks == null) return;
                    for (OnScanCallback callback : mScanCallbacks) {
                        if (callback != null) callback.onConnected(device);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void dispatchDeviceConfigured(final FitnessBtDevice device) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mScanCallbacks == null) return;
                    for (OnScanCallback callback : mScanCallbacks) {
                        if (callback != null) callback.onConfigured(device);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    void dispatchDisconnected() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mScanCallbacks == null) return;
                    for (OnScanCallback callback : mScanCallbacks) {
                        if (callback == null) continue;
                        callback.onDisconnected();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void dispatchChange(final Object data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mMapChangeCallback == null) return;
                    BaseCharacteristic baseCharacteristic = (BaseCharacteristic) data;
                    List<OnChangeCharacteristicCallback> changeCharacteristicCallbackList = mMapChangeCallback.get(baseCharacteristic.getUuidString());
                    if (changeCharacteristicCallbackList == null) return;
                    for (OnChangeCharacteristicCallback dataCallback : changeCharacteristicCallbackList) {
                        if (dataCallback == null) continue;
                        dataCallback.onChange(baseCharacteristic);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void dispatchSuccessfulRead(final Object data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mMapReadCallback == null) return;
                    BaseCharacteristic baseCharacteristic = (BaseCharacteristic) data;
                    List<OnReadCharacteristicCallback> readCharacteristicCallbackList = mMapReadCallback.get(baseCharacteristic.getUuidString());
                    if (readCharacteristicCallbackList == null) return;
                    for (OnReadCharacteristicCallback dataCallback : readCharacteristicCallbackList) {
                        if (dataCallback == null) continue;
                        dataCallback.onReadSuccessfull(baseCharacteristic);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void dispatchFailedRead(final Object data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mMapReadCallback == null) return;
                    BaseCharacteristic baseCharacteristic = (BaseCharacteristic) data;
                    List<OnReadCharacteristicCallback> readCharacteristicCallbackList = mMapReadCallback.get(baseCharacteristic.getUuidString());
                    if (readCharacteristicCallbackList == null) return;
                    for (OnReadCharacteristicCallback dataCallback : readCharacteristicCallbackList) {
                        if (dataCallback == null) continue;
                        dataCallback.onReadFailed(baseCharacteristic);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void dispatchSuccessfulWrite(final Object data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mMapWriteCallback == null) return;
                    BaseCharacteristic baseCharacteristic = (BaseCharacteristic) data;
                    List<OnWriteCharacteristicCallback> writeCharacteristicCallbackList = mMapWriteCallback.get(baseCharacteristic.getUuidString());
                    if (writeCharacteristicCallbackList == null) return;
                    for (OnWriteCharacteristicCallback dataCallback : writeCharacteristicCallbackList) {
                        if (dataCallback == null) continue;
                        dataCallback.onWriteSuccessfull(baseCharacteristic);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void dispatchFailedWrite(final Object data) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mMapWriteCallback == null) return;
                    BaseCharacteristic baseCharacteristic = (BaseCharacteristic) data;
                    List<OnWriteCharacteristicCallback> writeCharacteristicCallbackList = mMapWriteCallback.get(baseCharacteristic.getUuidString());
                    if (writeCharacteristicCallbackList == null) return;
                    for (OnWriteCharacteristicCallback dataCallback : writeCharacteristicCallbackList) {
                        if (dataCallback == null) continue;
                        dataCallback.onWriteFailed(baseCharacteristic);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //endregion
}
