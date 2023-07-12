package com.technogym.equipmenttest.appbleuart;

import android.os.Environment;

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

	/* IT */
	/* Demo */
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-demo-sap.technogym.com";
	/* Prod */
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest.technogym.com";

	/* SK */
	/* Demo */
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-demo-sk.technogym.com";
	/* Prod */
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-sk.technogym.com";

	/* Local */
	/* IT */
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "192.168.250.201/Technogym.EquipmentTest.Services";
	/* SK */
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "192.168.137.1/Technogym.EquipmentTest.Services.SK";


	/**
	 * URL della web app di collaudo
	 */

	/* IT */
	/* Demo */
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web-equiptest-demo-sap.technogym.com/#/main";
	/* Prod */
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web-equiptest.technogym.com/#/main";

	/* SK */
	/* Demo */
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web-equiptest-demo-sk.technogym.com//#/main";
	/* Prod */
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web-equiptest-sk.technogym.com/#/main";

	/* Local */
	/* IT */
	public static final String EQUIPMENT_TEST_CLIENT_URL = "http://192.168.43.192/#/main";
	/* SK */
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://192.168.137.1/tgym-sk-web/";


	/**
	 * URL dei web services per il collaudo
	 */

	/* IT */
	/* Demo */
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest-demo-sap.technogym.com/api/";
	/* Prod */
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest.technogym.com/api/";

	/* SK */
	/* Demo */
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest-demo-sk.technogym.com/api/";
	/* Prod */
	public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest-sk.technogym.com/api/";

	/* Local */
	/* IT */
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://192.168.250.201/Technogym.EquipmentTest.Services/api/";
	/* SK */
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://192.168.137.1/Technogym.EquipmentTest.Services.SK/api/";


	/**
	 * Path per chiamata api di download low kit upgrade file
	 */
	public static final String LOWKIT_FILE_DOWNLOAD_PATH = "common/GetBleUartLowKitUpgradeFile";
	public static final String HIGHKIT_FILE_DOWNLOAD_PATH = "common/GetBleUartHighKitUpgradeFile";

	/**
	 * Directory per salvare il file necessario all'upgrade LK + BT o HK
	 */
	public static final String FIRMWARE_DOWNLOAD_FOLDER = Environment
			.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
			.getAbsolutePath() + "/firmwareupgrade";


	/**
	 * Path di rete della cartella per il log su rete del collaudo del MYCYCLE
	 */
//	public static final String MYCYCLE_LOG_SHARED_FOLDER_PATH = "smb://baanpkg/repcolrun/LogMyCycle/[ANNO]/[MESE]/[GIORNO]/[USER]";
//	public static final String MYCYCLE_LOG_SHARED_FOLDER_PATH = "smb://baanpkg/passa/LogMyCycle/[ANNO]/[MESE]/[GIORNO]/[USER]";
//	public static final String MYCYCLE_LOG_SHARED_FOLDER_PATH = "smb://mydevnas/backup/FEDERICO/TG_FOLDERS/LogMyCycle/[ANNO]/[MESE]/[GIORNO]/[USER]";
	
	/**
	 * Dominio per l'autenticazione sul path di rete della cartella per il log su rete del collaudo del MYCYCLE
	 */
	public static final String MYCYCLE_LOG_SHARED_FOLDER_DOMAIN = "TCHGYM";
//	public static final String MYCYCLE_LOG_SHARED_FOLDER_DOMAIN = null;
	/**
	 * Username per l'autenticazione sul path di rete della cartella per il log su rete del collaudo del MYCYCLE
	 */
	public static final String MYCYCLE_LOG_SHARED_FOLDER_USERNAME = "coll_exc@technogym.com";
//	public static final String MYCYCLE_LOG_SHARED_FOLDER_USERNAME = null;
	/**
	 * Password per l'autenticazione sul path di rete della cartella per il log su rete del collaudo del MYCYCLE
	 */
	public static final String MYCYCLE_LOG_SHARED_FOLDER_PASSWORD = "excite";
//	public static final String MYCYCLE_LOG_SHARED_FOLDER_PASSWORD = null;

	// }

}
