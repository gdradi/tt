package com.technogym.myrow;

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
	 * Dominio dei web services dell'app di collaudo
	 */
	public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "192.168.223.23";
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-it-sap.technogym.com";
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-demo-sap.technogym.com";
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services.sap.mydev.local";
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest.technogym.com";
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-demo.technogym.com";
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "tg.services.mydev.local";
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "equiptest.services.mydev.local";
	/**
	 * URL della web app di collaudo
	 */
	public static final String EQUIPMENT_TEST_CLIENT_URL = "http://192.168.223.23/Technogym.EquipmentTest.Web#/main/auth";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web-equiptest-demo-sap.technogym.com/#/main/auth";

	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web-equiptest-it-sap.technogym.com/#/main/auth";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web.sap.mydev.local/#/main/auth";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web-equiptest.technogym.com/#/main";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web-equiptest-demo.technogym.com/#/main";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://tg.web.mydev.local/#/main/auth";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://equiptest.web.mydev.local/#/main/auth";
	/**
	 * URL dei web services per il collaudo
	 */
	public static final String EQUIPMENT_TEST_SERVICES_URL = "http://192.168.223.23/Technogym.EquipmentTest.Services/api/";
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest-demo-sap.technogym.com/api/";

	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest-it-sap.technogym.com/api/";
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services.sap.mydev.local/api/";
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest.technogym.com/api/";
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest-demo.technogym.com/api/";
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://tg.services.mydev.local/api/";
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://equiptest.services.mydev.local/api/";

	/**
	 * Equipment Test App Upgrade Services
	 */
	public static final String CHECK_UPGRADE_ACTION_SERVICE = "External/MyRowFirmwareUpgradeRequired?currentVersion=";
	public static final String DOWNLOAD_FIRMWARE_ACTION_SERVICE = "External/DownloadMyRowFirmware?currentVersion=";

	/**
	 * Equipment Test App folders and files paths
	 */
	public static final String EQUIPTEST_FOLDER_PATH = "EquipmentTest";
	public static final String EQUIPTEST_DOWNLOAD_FOLDER = "Download";

	/**
	 * Path di rete della cartella per il log su rete del collaudo del MyRow
	 */
	public static final String MYROW_LOG_SHARED_FOLDER_PATH = "smb://baanpkg/repcolrun/LogMyRow/[ANNO]/[MESE]/[GIORNO]/[USER]";
//	public static final String MYROW_LOG_SHARED_FOLDER_PATH = "smb://baanpkg/passa/LogMyRow/[ANNO]/[MESE]/[GIORNO]/[USER]";
//	public static final String MYROW_LOG_SHARED_FOLDER_PATH = "smb://mydevnas/backup/FEDERICO/TG_FOLDERS/LogMyRow/[ANNO]/[MESE]/[GIORNO]/[USER]";

	/**
	 * Dominio per l'autenticazione sul path di rete della cartella per il log su rete del collaudo del MyRow
	 */
	public static final String MYROW_LOG_SHARED_FOLDER_DOMAIN = "TCHGYM";
//	public static final String MYROW_LOG_SHARED_FOLDER_DOMAIN = null;
	/**
	 * Username per l'autenticazione sul path di rete della cartella per il log su rete del collaudo del MyRow
	 */
	public static final String MYROW_LOG_SHARED_FOLDER_USERNAME = "coll_exc@technogym.com";
//	public static final String MYROW_LOG_SHARED_FOLDER_USERNAME = null;
	/**
	 * Password per l'autenticazione sul path di rete della cartella per il log su rete del collaudo del MyRow
	 */
	public static final String MYROW_LOG_SHARED_FOLDER_PASSWORD = "excite";
//	public static final String MYROW_LOG_SHARED_FOLDER_PASSWORD = null;

	// }

}
