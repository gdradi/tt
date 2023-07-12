package com.technogym.equipmenttest.library.services.responses;

/**
 * Interface related to the services's main response
 * @author Federico Foschini
 *
 */
public interface IResponse {
	
	/**
	 * Method to get the result code of the response
	 * @return the result code of the response
	 */
	int getResultCode();

	/**
	 * Method to get the error message of the response
	 * @return the error message of the response
	 */
	String getError();

	/**
	 * Method to get the token of the response
	 * @return the token of the response
	 */
	String getToken();
	
}
