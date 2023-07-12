package com.technogym.equipmenttest.bleuart.btlesupport;

import java.util.UUID;
import java.util.concurrent.Semaphore;

public class BTLEConstants {

    public static final String TAG = "com.technogym.sdk.btlesupport";

    public static Semaphore LOCK = new Semaphore(1);

    public static class BaseServices {
        /**
         * See {@literal https://developer.bluetooth.org/gatt/services/Pages/ServiceViewer.aspx?u=org.bluetooth.service.device_information.xml}
         */
        public final static UUID SERVICE_DEVICE_INFO_UUID = UUID.fromString("0000180a-0000-1000-8000-00805f9b34fb");

        /**
         * See {@literal https://developer.bluetooth.org/gatt/services/Pages/ServiceViewer.aspx?u=org.bluetooth.service.generic_access.xml}
         */
        public final static UUID SERVICE_GENERIC_ACCESS = UUID.fromString("00001800-0000-1000-8000-00805f9b34fb");

        /**
         * See {@literal https://docs.google.com/document/d/1qBmpXW5OAz8O6IT_P7dfffXTSmjjtzpxsD997VbRwWQ/edit}
         */
        public final static UUID SERVICE_UART = UUID.fromString("beb7705e-bd66-4501-80c0-0c8f0bcca1a5");
    }

    /**
     * Characteristics Bluetooth low energy for Technogym Spin Trainer
     */
    public static class BaseCharacteristics {
        /**
         * See {@literal https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.manufacturer_name_string.xml}
         */
        public final static UUID SERIAL_NUMBER_STRING = UUID.fromString("00002a25-0000-1000-8000-00805f9b34fb");        //read 16bit

        /**
         * See {@literal https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.firmware_revision_string.xml}
         */
        public final static UUID FIRMWARE_REVISION_STRING = UUID.fromString("00002a26-0000-1000-8000-00805f9b34fb");    //read 16bit

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
         * See {@literal https://docs.google.com/document/d/1qBmpXW5OAz8O6IT_P7dfffXTSmjjtzpxsD997VbRwWQ/edit}
         * PROTOCOL:
         * start char = "!"
         * end char = "#"
         */
        public final static UUID UART_RX = UUID.fromString("6f26de4b-dcef-4459-9465-931f1b144c20"); //read-notify 8 bit

        /**
         * See {@literal https://docs.google.com/document/d/1qBmpXW5OAz8O6IT_P7dfffXTSmjjtzpxsD997VbRwWQ/edit}
         * PROTOCOL:
         * start char = "@"
         * end char = "#"
         */
        public final static UUID UART_TX = UUID.fromString("df1eb8e4-1753-4bb9-a6a6-e018040af0a3"); //write-no-response 8 bit

    }

    }
