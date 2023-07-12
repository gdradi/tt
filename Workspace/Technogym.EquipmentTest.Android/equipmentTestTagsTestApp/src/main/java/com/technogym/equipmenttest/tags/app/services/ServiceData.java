package com.technogym.equipmenttest.tags.app.services;

/**
 * Class related to the data about the possible services that can called from the app
 * @author Federico Foschini
 *
 */
public class ServiceData {

	// { Construction

	public ServiceData() {
		super();
	}

	// }

	// { Public Constants

	/**
	 * The service responsible to authenticate the user
	 */
	public static final String AUTH_USER_SERVICE = "Auth/AuthorizeUser";

	/**
	 * The service responsible to get the equipment codes data
	 */
	public static final String ACTION_GET_EQUIPMENT_CODES_SERVICE = "external/GetEquipmentCodes";

	/**
	 * The service responsible to set the equipment QR Code verification result
	 */
	public static final String ACTION_SET_EQUIPMENT_QRCODE_VERIFICATION_SERVICE = "external/SetQRCodeVerification";

	/**
	 * The service responsible to set the equipment NFC verification result
	 */
	public static final String ACTION_SET_EQUIPMENT_NFC_VERIFICATION_SERVICE = "external/SetNFCVerification";

	// }
}
