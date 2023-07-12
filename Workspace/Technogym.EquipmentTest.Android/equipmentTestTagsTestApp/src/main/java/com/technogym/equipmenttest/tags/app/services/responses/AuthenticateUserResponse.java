package com.technogym.equipmenttest.tags.app.services.responses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class related to the login response of the user
 * @author Federico Foschini
 *
 */
public class AuthenticateUserResponse extends AResponse {

	// { Internal Fields

	protected String _username = "";
	
    // }

	// { Constructors

	/**
	 * Constructor
     * @param responseData: the data returned from the service
	 * @throws JSONException
	 */
	public AuthenticateUserResponse(String responseData) throws JSONException {
		super(responseData);
        JSONObject jResp = new JSONObject(responseData);
        _username = jResp.getString("Data");
	}

    // }

	// { Public Methods

	/**
	 * Method to get the username of the current logged user
	 * @return the username of the current logged user
	 */
	public String getUsername() {
		return _username;
	}
	
    // }

}
