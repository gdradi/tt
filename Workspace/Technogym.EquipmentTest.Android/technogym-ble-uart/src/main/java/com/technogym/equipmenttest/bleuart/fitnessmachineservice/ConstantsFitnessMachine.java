package com.technogym.equipmenttest.bleuart.fitnessmachineservice;


import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.FitnessType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by developer on 04/12/15.
 */
public class ConstantsFitnessMachine {

    public final static String TAG = "rw_debug";
    public static final long WAIT_TO_EXECUTE = 200;
    public static final long MAX_RETRY = 20;

    /**
     * Services Bluetooth low energy for Technogym Spin Trainer
     */
    public static class Services {
        /**
         * See {@literal https://developer.bluetooth.org/gatt/services/Pages/ServiceViewer.aspx?u=org.bluetooth.service.device_information.xml}
         */
        public final static UUID SERVICE_DEVICE_INFO_UUID = UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb");

        /**
         * See {@literal https://developer.bluetooth.org/gatt/services/Pages/ServiceViewer.aspx?u=org.bluetooth.service.generic_access.xml}
         */
        public final static UUID SERVICE_GENERIC_ACCESS = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb");

        /**
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID FITNESS_MACHINE_SERVICE = UUID.fromString("00001826-0000-1000-8000-00805f9b34fb");

        /**
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID ROWER_CURVE_SERVICE = UUID.fromString("6e40244a-b5a3-f393-e0a9-e50e24dcca9e");

        /**
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID ROWER_STATE_SERVICE = UUID.fromString("6e407121-b5a3-f393-e0a9-e50e24dcca9e");

        /**
         * Custom service for write hr value from the device.
         * Es. Samsung gear can connect to the equipment, read the equipment data and write the hr detected
         */
        public final static UUID CUSTOM_GYM_SERVICE = UUID.fromString("0000FD00-EBAE-4526-9511-8357C35D7BE2");
    }

    /**
     * Characteristics Bluetooth low energy for Technogym Spin Trainer
     */
    public static class Characteristics {
        /**
         * See {@literal https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.manufacturer_name_string.xml}
         */
        public final static UUID SERIAL_NUMBER_STRING = UUID.fromString("00002a25-0000-1000-8000-00805f9b34fb");        //read 16bit

        /**
         * See {@literal https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.firmware_revision_string.xml}
         */
        public final static UUID SOFTWARE_REVISION_STRING = UUID.fromString("00002a28-0000-1000-8000-00805f9b34fb");    //read 16bit

        /**
         * See {@literal https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.hardware_revision_string.xml}
         */
        public final static UUID HARDWARE_REVISION_STRING = UUID.fromString("00002a27-0000-1000-8000-00805f9b34fb");    //read 16bit

        /**
         * See {@literal https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.manufacturer_name_string.xml}
         */
        public final static UUID MANUFACTURER_NAME_STRING = UUID.fromString("00002a29-0000-1000-8000-00805f9b34fb");    //read 16bit

        /**
         * See {@literal https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.gap.device_name.xml}
         */
        public final static UUID DEVICE_NAME = UUID.fromString("00002a00-0000-1000-8000-00805f9b34fb");                 //read - write 16bit

        /**
         * See {@literal https://developer.bluetooth.org/gatt/descriptors/Pages/DescriptorViewer.aspx?u=org.bluetooth.descriptor.gatt.client_characteristic_configuration.xml}
         */
        public final static UUID DESCRIPTOR_CONFIGURATION_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

        /**
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID FITNESS_MACHINE_FEATURE = UUID.fromString("00002acc-0000-1000-8000-00805f9b34fb");     //read 64bit

        /**
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID TREADMILL_DATA = UUID.fromString("00002acd-0000-1000-8000-00805f9b34fb");               //notify

        /**
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID CROSS_TRAINER_DATA = UUID.fromString("00002ace-0000-1000-8000-00805f9b34fb");

        /**
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID STEP_CLIMBER_DATA = UUID.fromString("00002acf-0000-1000-8000-00805f9b34fb");

        /**
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID STAIR_CLIMBER_DATA = UUID.fromString("00002ad0-0000-1000-8000-00805f9b34fb");

        /**
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID ROWER_DATA = UUID.fromString("00002ad1-0000-1000-8000-00805f9b34fb");

        /**
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID INDOOR_BIKE_DATA = UUID.fromString("00002ad2-0000-1000-8000-00805f9b34fb");

        /**
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID TRAINING_STATUS = UUID.fromString("00002ad3-0000-1000-8000-00805f9b34fb"); // Notify, Read

        /**
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID CURVE_DATA = UUID.fromString("6e408f0b-b5a3-f393-e0a9-e50e24dcca9e");

        /**
         * Notify
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID TOTAL_LENGTH_DATA = UUID.fromString("6e403e57-b5a3-f393-e0a9-e50e24dcca9e");

        /**
         * Write and indicate
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID FITNESS_MACHINE_CONTROL_POINT = UUID.fromString("00002ad9-0000-1000-8000-00805f9b34fb");

        /**
         * Notify
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID FITNESS_MACHINE_STATUS = UUID.fromString("00002ada-0000-1000-8000-00805f9b34fb");

        /**
         * Notify
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID ROWER_STATE_DATA = UUID.fromString("6e40cfea-b5a3-f393-e0a9-e50e24dcca9e");

        /**
         * Write with no response
         * See {@literal https://docs.google.com/document/d/1rHBeVk05dvxmd8C2LOuK777vCGTs0GjB1mF3JHU8Kyc/edit#}
         */
        public final static UUID ROWER_SET_PHYSICAL_ACTIVITY_ID = UUID.fromString("6e404272-b5a3-f393-e0a9-e50e24dcca9e");

        /**
         * Indicate, read
         * Get the speed and inclination data from the myRun joysticks
         * byte 0 -> presence (inductive) - 00-00-00-00-00 (idle) - 01-00-00-00-00 (present)
         * byte 1 -> presence (optical) - 00-00-00-00-00 (idle) - 00-01-00-00-00 (present)
         * byte 2 -> main button - 00-00-00-00-00 (idle) - 00-00-01-00-00 (pressed)
         * byte 3 -> speed (left) - 00-00-00-00-00 (idle) - 00-00-00-01-00 (up) -  00-00-00-02-00 (down)
         * byte 4 -> inclination (right) - 00-00-00-00-00 (idle) - 00-00-00-01-00 (up) -  00-00-00-02-00 (down)
         * See {@literal https://mwcloud.atlassian.net/wiki/spaces/MRRB/pages/1961001173/Bluetooth+services}
         */
        public final static UUID MYRUN_PHYSICAL_SENSOR_DATA = UUID.fromString("00003B6F-0000-1000-8000-00805f9b34fb");

        /**
         * Write with no response
         * <p>
         * Custom characteristics where write hr value from the device.
         * Es. Samsung gear can connect to the equipment, read the equipment data and write the hr detected
         */
        public final static UUID CUSTOM_HR_DATA = UUID.fromString("0000FD01-EBAE-4526-9511-8357C35D7BE2");

        /**
         * Write with no response
         * <p>
         * Custom characteristics where write equipment user id value obtained from the user profile.
         * Es. when Samsung gear has established a connections with the equipment, the watch can send the user equipment id to perform the login into the machine
         */
        public final static UUID CUSTOM_LOGIN_POINT = UUID.fromString("0000FD03-EBAE-4526-9511-8357C35D7BE2");
    }

    public static Map<FitnessType, UUID> typeUUIDNotifyMap;

    static {
        typeUUIDNotifyMap = new HashMap<>();
        typeUUIDNotifyMap.put(FitnessType.TREADMILL, Characteristics.TREADMILL_DATA);
        typeUUIDNotifyMap.put(FitnessType.CROSS_TRAINER, Characteristics.CROSS_TRAINER_DATA);
        typeUUIDNotifyMap.put(FitnessType.STEP_CLIMBER, Characteristics.STEP_CLIMBER_DATA);
        typeUUIDNotifyMap.put(FitnessType.STAIR_CLIMBER, Characteristics.STAIR_CLIMBER_DATA);
        typeUUIDNotifyMap.put(FitnessType.ROWER, Characteristics.ROWER_DATA);
        typeUUIDNotifyMap.put(FitnessType.INDOOR_BIKE, Characteristics.INDOOR_BIKE_DATA);
    }
}
