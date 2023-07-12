package com.technogym.equipmenttest.bleuart.technogymbtleuart;

import java.util.UUID;

public class BLEUartConstants {

    public static final String TAG = "technogymbtleuart";

    public static class Services {
        /**
         * See {@literal https://docs.google.com/document/d/1qBmpXW5OAz8O6IT_P7dfffXTSmjjtzpxsD997VbRwWQ/edit}
         */
        public final static UUID SERVICE_UART = UUID.fromString("beb7705e-bd66-4501-80c0-0c8f0bcca1a5");
    }

    /**
     * Characteristics Bluetooth low energy for Technogym Spin Trainer
     */
    public static class Characteristics {

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
