package com.technogym.equipmenttest.photo.app.service;

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
	 * The service responsible to get the actions related to the verify image type
	 */
	public static final String ACTION_GET_PHOTO_VERIFICATION_ACTIONS_SERVICE = "external/GetPhotoVerificationActions";

	/**
	 * The service responsible to upload the file related to the verify image
	 */
	public static final String ACTION_UPLOAD_PHOTO_VERIFICATION_ACTIONS_SERVICE = "external/UploadImageToVerify";

	// }
}
