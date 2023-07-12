package com.technogym.myrow.services;

/**
 * Enumerator related to the possible services that can called from the app
 * @author Federico Foschini
 *
 */
public enum TargetServices {

	/*
	 * Authentication Service
	 */
	AUTH_USER,
	/*
	 * Check Firmware Upgrade Service
	 */
	CHECK_UPGRADE_FIRMWARE_REQUIRED,
	/*
	 * Download Firmware Service
	 */
	DOWNLOAD_FIRMWARE,
	/*
	 * Looper Service
	 */
	LOOPER_SERVICE
}
