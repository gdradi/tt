package com.technogym.mycycling.services.requests;

import com.technogym.mycycling.services.RequestType;

import org.json.JSONException;

/**
 * Interface related to the services's main request
 * @author Federico Foschini
 *
 */
public interface IRequest {

	/**
	 * Method to get the request type
	 * @return the request type
	 */
	RequestType getRequestType();

    /**
     * Method to get the query string parameters of the request
     * @return all query string parameters with the template "?property=value"
     */
    String getRequestQueryString();

    /**
     * Method to get the body of the request
     * @return the request's body related string
     */
    String getRequestBodyString() throws JSONException;

    /**
     * Method to get the url of the service start
     * @return the url of the service
     */
    String getServiceUrl();
    
}
