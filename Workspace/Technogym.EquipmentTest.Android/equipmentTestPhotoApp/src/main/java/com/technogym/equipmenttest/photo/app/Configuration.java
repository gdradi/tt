package com.technogym.equipmenttest.photo.app;

public class Configuration {

    // { Construction

    public Configuration() {
        super();
    }

    // }

    // { Public Constants

    /**
     * Flag indicante se si è in produzione o meno
     */
    public static final boolean IS_PRODUCTION = true;

    /**
     * Dominio dei web services del sistema di collaudo
     */
    //public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-it-sap.technogym.com";
    public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-demo-sap.technogym.com";
    //public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-demo-sk.technogym.com";
    //public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services.sap.mydev.local";
    //public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest.technogym.com";
    //public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-demo-sk.technogym.com";
    //public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-sk.technogym.com";
    // public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "equiptest.services.mydev.local";

    /**
     * URL dei web services del sistema di collaudo
     */
    //public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest-it-sap.technogym.com/api/";
    //public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest-demo-sap.technogym.com/api/";
    public static final String EQUIPMENT_TEST_SERVICES_URL = "http://192.168.250.197/Technogym.EquipmentTest.Services/api/";

    //public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services.sap.mydev.local/api/";
    //public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest.technogym.com/api/";
    //public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest-demo-sk.technogym.com/api/";
    //public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest-sk.technogym.com/api/";
    //public static final String EQUIPMENT_TEST_SERVICES_URL = "http://equiptest.services.mydev.local/api/";

    /**
     * Name of the app root folder
     */
    public static final String APP_ROOT_FOLDER = "EquipmentTestPhotoApp";

    /**
     * Name of the app images folder
     */
    public static final String APP_IMAGES_FOLDER = "Images";

    // }
}