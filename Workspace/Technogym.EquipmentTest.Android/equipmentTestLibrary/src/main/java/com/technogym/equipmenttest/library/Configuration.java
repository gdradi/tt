package com.technogym.equipmenttest.library;

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

	public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-demo-sap.technogym.com";
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest.technogym.com";

//	public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-demo-sk.technogym.com";
//	public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-sk.technogym.com";

	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "BAAN.Technogym.EquipmentTest.Services";
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-it-sap.technogym.com";
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services-equiptest-demo-sap.technogym.com";
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services.tg.dmz.dom";
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "tg.services.mydev.local";
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "services.tg.mydev.local";
	//public static final String EQUIPMENT_TEST_SERVICES_DOMAIN = "equiptest.services.mydev.local";

	/**
	 * URL della web app di collaudo
	 */

	public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web-equiptest-demo-sap.technogym.com/#/main";
//	public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web-equiptest.technogym.com/#/main";

//	public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web-equiptest-demo-sk.technogym.com/#/main";
//	public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web-equiptest-sk.technogym.com/#/main";

	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://10.0.0.5/BAAN.Technogym.EquipmentTest.Web#/main/auth";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://192.168.223.26/BAAN.Technogym.EquipmentTest.Web#/main/auth";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://192.168.223.22/Technogym.EquipmentTest.Web/#/main/auth";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://192.168.1.105/";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web-equiptest-demo-sap.technogym.com/#/main/auth";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://10.0.0.240/BAAN.Technogym.EquipmentTest.Web#/main/auth";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web-equiptest-demo-sap.technogym.com/#/main/auth";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web-equiptest-it-sap.technogym.com/#/main/auth";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web.tg.dmz.dom/#/main/auth";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://tg.web.mydev.local/#/main/auth";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://web.tg.mydev.local/#/main/auth";
	//public static final String EQUIPMENT_TEST_CLIENT_URL = "http://equiptest.web.mydev.local/#/main/auth";

	/**
	 * URL dei web services per il collaudo
	 */

	public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest-demo-sap.technogym.com/api/";
//	public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest.technogym.com/api/";

//	public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest-demo-sk.technogym.com/api/";
//	public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest-sk.technogym.com/api/";

	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://10.0.0.5/BAAN.Technogym.EquipmentTest.Services";
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://192.168.223.26/BAAN.Technogym.EquipmentTest.Services";
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://192.168.223.22/Technogym.EquipmentTest.Services";
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest-demo-sap.technogym.com/api/";
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://10.0.0.240/BAAN.Technogym.EquipmentTest.Services";
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services-equiptest-it-sap.technogym.com/api/";
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "services.tg.dmz.dom/api/";
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://tg.services.mydev.local/api/";
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://services.tg.mydev.local/api/";
	//public static final String EQUIPMENT_TEST_SERVICES_URL = "http://equiptest.services.mydev.local/api/";

	/**
	 * Path di rete della cartella per il log su rete del collaudo del MyRun
	 */
	public static final String MYRUN_LOG_SHARED_FOLDER_PATH = "smb://baanpkg/repcolrun/LogMyRun/[ANNO]/[MESE]/[GIORNO]/[USER]";
//	public static final String MYRUN_LOG_SHARED_FOLDER_PATH = "smb://baanpkg/passa/LogMyRun/[ANNO]/[MESE]/[GIORNO]/[USER]";
//	public static final String MYRUN_LOG_SHARED_FOLDER_PATH = "smb://mydevnas/backup/FEDERICO/TG_FOLDERS/LogMyRun/[ANNO]/[MESE]/[GIORNO]/[USER]";
	
	/**
	 * Dominio per l'autenticazione sul path di rete della cartella per il log su rete del collaudo del MyRun
	 */
	public static final String MYRUN_LOG_SHARED_FOLDER_DOMAIN = "TCHGYM";
//	public static final String MYRUN_LOG_SHARED_FOLDER_DOMAIN = null;
	/**
	 * Username per l'autenticazione sul path di rete della cartella per il log su rete del collaudo del MyRun
	 */
	public static final String MYRUN_LOG_SHARED_FOLDER_USERNAME = "coll_exc@technogym.com";
//	public static final String MYRUN_LOG_SHARED_FOLDER_USERNAME = null;
	/**
	 * Password per l'autenticazione sul path di rete della cartella per il log su rete del collaudo del MyRun
	 */
	public static final String MYRUN_LOG_SHARED_FOLDER_PASSWORD = "excite";
//	public static final String MYRUN_LOG_SHARED_FOLDER_PASSWORD = null;

	// }

}
