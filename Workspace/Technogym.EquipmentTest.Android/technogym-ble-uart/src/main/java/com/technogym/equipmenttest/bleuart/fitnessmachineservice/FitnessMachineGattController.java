package com.technogym.equipmenttest.bleuart.fitnessmachineservice;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.interfaces.CharacteristicListener;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.interfaces.ConnectionListener;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.CustomHrMeasurement;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.FitnessBtDevice;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.threads.ReadCharacteristicThread;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.threads.WriteCharactThread;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.threads.WriteCustomHrMeasurementThread;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.threads.WriteCustomLoginPointThread;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.threads.WriteDescriptorIndicationThread;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.threads.WriteDescriptorThread;
import com.technogym.equipmenttest.bleuart.technogymbtleuart.BLEUartConstants;
import com.technogym.equipmenttest.bleuart.technogymbtleuart.model.CommandModel;
import com.technogym.equipmenttest.bleuart.technogymbtleuart.threads.WriteUartTxCharactThread;
import com.technogym.sdk.fitnessmachineservice.Characteristics;


import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sventurini on 04/12/15.
 */
public class FitnessMachineGattController extends BluetoothGattCallback {

    private Characteristics characteristicsSt = new Characteristics();
    private ExecutorService executorService;
    private ConnectionListener connectionListener;
    private CharacteristicListener<Object> characteristicListener;
    private BluetoothGatt mGatt;
    private FitnessBtDevice fitnessDevice;

    public FitnessMachineGattController() {
        super();
        executorService = Executors.newSingleThreadExecutor();
    }

    public FitnessMachineGattController(ConnectionListener connListener, CharacteristicListener<Object> charListener) {
        this();
        connectionListener = connListener;
        characteristicListener = charListener;
    }

    public void setConnectionListener(ConnectionListener listener) {
        connectionListener = listener;
    }

    public void setCharacteristicListener(CharacteristicListener<Object> listener) {
        characteristicListener = listener;
    }

    public void writeUartCmd(CommandModel cmd, String uuid) {
        BaseCharacteristic baseCharacteristic = (BaseCharacteristic) characteristicsSt.getCharacteristic(UUID.fromString(uuid));
        if (baseCharacteristic != null && baseCharacteristic.getCharacteristic() != null) {
            WriteUartTxCharactThread.send(executorService, mGatt, baseCharacteristic.getCharacteristic(), cmd);
        } else {
            characteristicListener.onWriteFailed(baseCharacteristic);
        }
    }

    public void writeCustomHrMeasurement(CustomHrMeasurement measurement, String uuid) {
        BaseCharacteristic baseCharacteristic = (BaseCharacteristic) characteristicsSt.getCharacteristic(UUID.fromString(uuid));
        if (baseCharacteristic != null && baseCharacteristic.getCharacteristic() != null) {
            WriteCustomHrMeasurementThread.send(executorService, mGatt, baseCharacteristic.getCharacteristic(), measurement);
        } else {
            characteristicListener.onWriteFailed(baseCharacteristic);
        }
    }

    public void writeCustomLogin(String userId, String uuid) {
        BaseCharacteristic baseCharacteristic = (BaseCharacteristic) characteristicsSt.getCharacteristic(UUID.fromString(uuid));
        if (baseCharacteristic != null && baseCharacteristic.getCharacteristic() != null) {
            WriteCustomLoginPointThread.send(executorService, mGatt, baseCharacteristic.getCharacteristic(), userId);
        } else {
            characteristicListener.onWriteFailed(baseCharacteristic);
        }
    }

    public void readCharacteristic(String uuid) {
        BaseCharacteristic baseCharacteristic = (BaseCharacteristic) characteristicsSt.getCharacteristic(UUID.fromString(uuid));
        if (baseCharacteristic != null && baseCharacteristic.getCharacteristic() != null) {
            ReadCharacteristicThread.send(executorService, mGatt, baseCharacteristic.getCharacteristic());
        } else {
            characteristicListener.onReadFailed(baseCharacteristic);
        }
    }

    public void writeCharacteristic(String uuid, byte[] rawData) {
        BaseCharacteristic baseCharacteristic = (BaseCharacteristic) characteristicsSt.getCharacteristic(UUID.fromString(uuid));
        if (baseCharacteristic != null && baseCharacteristic.getCharacteristic() != null) {
            baseCharacteristic.getCharacteristic().setValue(rawData);
            WriteCharactThread.send(executorService, mGatt, baseCharacteristic.getCharacteristic());
        } else {
            characteristicListener.onWriteFailed(baseCharacteristic);
        }
    }

    public void setFitnessBtDevice(FitnessBtDevice fitnessDevice) {
        this.fitnessDevice = fitnessDevice;
    }

    public void disconnect() {
        if (mGatt != null) mGatt.disconnect();
    }

    public void resetGatt() {
        if (mGatt != null) {
            mGatt.disconnect();
            mGatt.close();
        }
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_CONNECTED) {
            Log.w("FitnessMachineGatt", "[FitnessMachineGattController][onConnectionStateChange] status = BluetoothGatt.GATT_SUCCESS & newState = STATE_CONNECTED");

            // From NRF Connect App:
            // After 1.6s the services are already discovered so the following gatt.discoverServices() finishes almost immediately.
            // NOTE: This also works with shorted waiting time. The gatt.discoverServices() must be called after the indication is received which is
            // about 600ms after establishing connection. Values 600 - 1600ms should be OK.
            try {
                Thread.sleep(1600);

            } catch (Exception e) {
                e.printStackTrace();

            }
            if (gatt.discoverServices())
                Log.d(ConstantsFitnessMachine.TAG, "Discover services Started");
            else Log.d(ConstantsFitnessMachine.TAG, "Discover services Failed");

            mGatt = gatt;
            fitnessDevice.setDevice(gatt.getDevice());


            if (connectionListener != null) connectionListener.connected(mGatt, fitnessDevice);

        } else if (status == BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_DISCONNECTED) {
            Log.w("FitnessMachineGatt", "[FitnessMachineGattController][onConnectionStateChange] status = BluetoothGatt.GATT_SUCCESS & newState = STATE_DISCONNECTED");
            Log.d(ConstantsFitnessMachine.TAG, "Correct disconnection!");
            if (mGatt != null) {
                mGatt.disconnect();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mGatt.close();
            }
            mGatt = null;
            fitnessDevice.setDevice(null);
            if (connectionListener != null) connectionListener.disconnected();

        } else if (status != BluetoothGatt.GATT_SUCCESS && newState == BluetoothProfile.STATE_DISCONNECTED) {
            Log.w("FitnessMachineGatt", "[FitnessMachineGattController][onConnectionStateChange] status != BluetoothGatt.GATT_SUCCESS & newState = STATE_DISCONNECTED");

            Log.d(ConstantsFitnessMachine.TAG, "Errore: " + status + " state: " + newState);
            if (mGatt == null) {
                Log.d(ConstantsFitnessMachine.TAG, "Controller Gatt is null -> use Callback's gatt");
                mGatt = gatt;

            } else {
                fitnessDevice.setDevice(null);

                /*
                 * CP_IP_2021_011 - Gestione reboot My Platform
                 * Quando viene fatto il reboot per My Platform  c'è una disconnessione non gestita (status è diverso da BluetoothGatt.GATT_SUCCESS).
                 * Se non viene fatto mGatt.close() (come nel caso di disconnessione gestita), alla riconnessione dopo il reboot,
                 * rimangono aperti due canali di connessione tra app e macchina e di conseguenza l'invio dei comandi non funziona.
                 */

                mGatt.close();


                if (connectionListener != null) connectionListener.disconnected();
                return;
            }

            refreshGatt(mGatt);
            Log.d(ConstantsFitnessMachine.TAG, "Gatt disconnect");
            mGatt.disconnect();
            try {
                Log.d(ConstantsFitnessMachine.TAG, "Sleep 1 sec");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(ConstantsFitnessMachine.TAG, "Gatt close");
            mGatt.close();
            try {
                Log.d(ConstantsFitnessMachine.TAG, "Sleep 800 milliSec");
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(ConstantsFitnessMachine.TAG, "Gatt connect");
            mGatt.connect();
        }
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            for (BluetoothGattService service : gatt.getServices()) {
                if (ConstantsFitnessMachine.Services.SERVICE_DEVICE_INFO_UUID.equals(service.getUuid())) {
                    Log.d(ConstantsFitnessMachine.TAG, "Found SERVICE_DEVICE_INFO_UUID!");
                } else if (ConstantsFitnessMachine.Services.SERVICE_GENERIC_ACCESS.equals(service.getUuid())) {
                    Log.d(ConstantsFitnessMachine.TAG, "Found SERVICE_GENERIC_ACCESS!");
                } else if (ConstantsFitnessMachine.Services.FITNESS_MACHINE_SERVICE.equals(service.getUuid())) {
                    Log.d(ConstantsFitnessMachine.TAG, "Found FITNESS_MACHINE_SERVICE!");
                } else if (ConstantsFitnessMachine.Services.ROWER_CURVE_SERVICE.equals(service.getUuid())) {
                    Log.d(ConstantsFitnessMachine.TAG, "Found ROWER_CURVE_SERVICE!");
                } else if (ConstantsFitnessMachine.Services.ROWER_STATE_SERVICE.equals(service.getUuid())) {
                    Log.d(ConstantsFitnessMachine.TAG, "Found ROWER_STATE_SERVICE!");
                } else if (ConstantsFitnessMachine.Services.CUSTOM_GYM_SERVICE.equals(service.getUuid())) {
                    Log.d(ConstantsFitnessMachine.TAG, "Found CUSTOM_GYM_SERVICE!");
                }
                discoverCharacteristic(service);
            }
            initConfig();
        }
    }

    /**
     * Only below characteristics are supported
     */
    private void discoverCharacteristic(BluetoothGattService service) {
        for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
            characteristicsSt.addCharacteristic(characteristic);
            Log.d(ConstantsFitnessMachine.TAG, "Found characteristic = " + characteristic.getUuid().toString());
        }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
        elabCharacteristic(characteristic);

    }

    @Override
    // Result of a characteristic write operation
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            characteristicListener.onWriteSuccessful(characteristicsSt.getCharacteristic(characteristic.getUuid()));

        } else {
            characteristicListener.onWriteFailed(characteristicsSt.getCharacteristic(characteristic.getUuid()));

        }
    }

    @Override
    // Result of a descriptor write operation
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            //writed descriptor correctly
            if (descriptor.getCharacteristic().getUuid().toString().equals(ConstantsFitnessMachine.Characteristics.FITNESS_MACHINE_CONTROL_POINT.toString())) {
                if (connectionListener != null)
                    connectionListener.configured(fitnessDevice);
            }
        }
    }

    @Override
    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorRead(gatt, descriptor, status);
    }

    @Override
    // Result of a characteristic read operation
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            characteristicListener.onReadSuccessful(characteristicsSt.elabCharacteristic(characteristic));

        } else {
            characteristicListener.onReadFailed(characteristicsSt.getCharacteristic(characteristic.getUuid()));

        }
    }

    /**
     *
     */
    public void onDestroy() {
        if (!executorService.isShutdown()) executorService.shutdownNow();
    }

    /**
     *
     */
    private void initConfig() {
        BaseCharacteristic machineStatus;
        if ((machineStatus = characteristicsSt.getCharacteristic(ConstantsFitnessMachine.Characteristics.FITNESS_MACHINE_STATUS)) != null) {
            Log.d(ConstantsFitnessMachine.TAG, "Write Descriptor notification Fitness machine status");
            WriteDescriptorThread.enableNotificationValue(executorService, mGatt, machineStatus);
        }

        BaseCharacteristic rowerDataObject;
        if ((rowerDataObject = characteristicsSt.getCharacteristic(ConstantsFitnessMachine.Characteristics.ROWER_DATA)) != null) {
            Log.d(ConstantsFitnessMachine.TAG, "Write Descriptor notification Rower data");
            WriteDescriptorThread.enableNotificationValue(executorService, mGatt, rowerDataObject);
        }

        BaseCharacteristic treadmillDataObject = characteristicsSt.getCharacteristic(ConstantsFitnessMachine.Characteristics.TREADMILL_DATA);
        if (treadmillDataObject != null) {
            Log.d(ConstantsFitnessMachine.TAG, "Write Descriptor notification Treadmill data");
            WriteDescriptorThread.enableNotificationValue(executorService, mGatt, treadmillDataObject);
        }

        BaseCharacteristic crossTrainerDataObject = characteristicsSt.getCharacteristic(ConstantsFitnessMachine.Characteristics.CROSS_TRAINER_DATA);
        if (crossTrainerDataObject != null) {
            Log.d(ConstantsFitnessMachine.TAG, "Write Descriptor notification Cross Trainer data");
            WriteDescriptorThread.enableNotificationValue(executorService, mGatt, crossTrainerDataObject);
        }

        BaseCharacteristic startClimberDataObject = characteristicsSt.getCharacteristic(ConstantsFitnessMachine.Characteristics.STAIR_CLIMBER_DATA);
        if (startClimberDataObject != null) {
            Log.d(ConstantsFitnessMachine.TAG, "Write Descriptor notification Stair Climber data");
            WriteDescriptorThread.enableNotificationValue(executorService, mGatt, startClimberDataObject);
        }

        BaseCharacteristic stepClimberDataObject = characteristicsSt.getCharacteristic(ConstantsFitnessMachine.Characteristics.STEP_CLIMBER_DATA);
        if (stepClimberDataObject != null) {
            Log.d(ConstantsFitnessMachine.TAG, "Write Descriptor notification Step Climber data");
            WriteDescriptorThread.enableNotificationValue(executorService, mGatt, stepClimberDataObject);
        }

        BaseCharacteristic indoorBikeDataObject = characteristicsSt.getCharacteristic(ConstantsFitnessMachine.Characteristics.INDOOR_BIKE_DATA);
        if (indoorBikeDataObject != null) {
            Log.d(ConstantsFitnessMachine.TAG, "Write Descriptor notification Indoor Bike data");
            WriteDescriptorThread.enableNotificationValue(executorService, mGatt, indoorBikeDataObject);
        }

        BaseCharacteristic curveDataObject;
        if ((curveDataObject = characteristicsSt.getCharacteristic(ConstantsFitnessMachine.Characteristics.CURVE_DATA)) != null) {
            Log.d(ConstantsFitnessMachine.TAG, "Write Descriptor notification Curve data");
            WriteDescriptorThread.enableNotificationValue(executorService, mGatt, curveDataObject);
        }

        BaseCharacteristic totalLengthDataObject;
        if ((totalLengthDataObject = characteristicsSt.getCharacteristic(ConstantsFitnessMachine.Characteristics.TOTAL_LENGTH_DATA)) != null) {
            Log.d(ConstantsFitnessMachine.TAG, "Write Descriptor notification total length data");
            WriteDescriptorThread.enableNotificationValue(executorService, mGatt, totalLengthDataObject);
        }

        BaseCharacteristic uartRxObjcet;
        if ((uartRxObjcet = characteristicsSt.getCharacteristic(BLEUartConstants.Characteristics.UART_RX)) != null) {
            Log.d(ConstantsFitnessMachine.TAG, "Write Descriptor notification uart rx");
            WriteDescriptorThread.enableNotificationValue(executorService, mGatt, uartRxObjcet);
        }

        BaseCharacteristic machineControlPointObject;
        if ((machineControlPointObject = characteristicsSt.getCharacteristic(ConstantsFitnessMachine.Characteristics.FITNESS_MACHINE_CONTROL_POINT)) != null) {
            Log.d(ConstantsFitnessMachine.TAG, "Write Descriptor indication machine control point");
            WriteDescriptorIndicationThread.enableIndicationValue(executorService, mGatt, machineControlPointObject);
        }

        BaseCharacteristic myRunPhysicalSensorDataObject;
        if ((myRunPhysicalSensorDataObject = characteristicsSt.getCharacteristic(ConstantsFitnessMachine.Characteristics.MYRUN_PHYSICAL_SENSOR_DATA)) != null) {
            Log.d(ConstantsFitnessMachine.TAG, "Write Descriptor indication MyRun physical sensor data");
            WriteDescriptorIndicationThread.enableIndicationValue(executorService, mGatt, myRunPhysicalSensorDataObject);
        }

        BaseCharacteristic trainingStatusObject = characteristicsSt.getCharacteristic(ConstantsFitnessMachine.Characteristics.TRAINING_STATUS);
        if (trainingStatusObject != null) {
            Log.d(ConstantsFitnessMachine.TAG, "Write Descriptor indication Training status");
            WriteDescriptorThread.enableNotificationValue(executorService, mGatt, trainingStatusObject);
        }
    }

    private void elabCharacteristic(BluetoothGattCharacteristic characteristic) {
        characteristicListener.onNotify(characteristicsSt.elabCharacteristic(characteristic));
    }

    private boolean refreshGatt(BluetoothGatt gatt) {
        try {
            Log.d(ConstantsFitnessMachine.TAG, "Refresh Gatt");
            BluetoothGatt localBluetoothGatt = gatt;
            Method localMethod = localBluetoothGatt.getClass().getMethod("refresh", new Class[0]);
            if (localMethod != null) {
                boolean bool = ((Boolean) localMethod.invoke(localBluetoothGatt, new Object[0])).booleanValue();
                return bool;
            }
        } catch (Exception localException) {

        }
        return false;
    }

    public boolean hasCharacteristics(UUID uuid) {
        return characteristicsSt.getCharacteristic(uuid) != null;
    }
}
