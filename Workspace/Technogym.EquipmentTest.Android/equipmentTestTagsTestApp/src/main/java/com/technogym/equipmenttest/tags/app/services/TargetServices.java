package com.technogym.equipmenttest.tags.app.services;

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
	 * Get the equipment codes
	 */
	GET_EQUIPMENT_CODES,
	/*
	 * Set the QR Code verification
	 */
	SET_EQUIPMENT_QRCODE_VERIFICATION,
	/*
	 * Set the NFC verification
	 */
	SET_EQUIPMENT_NFC_VERIFICATION
}
