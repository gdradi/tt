package com.technogym.equipmenttest.library.services.requests;

import org.json.JSONException;
import org.json.JSONObject;

import com.technogym.equipmenttest.library.services.RequestType;
import com.technogym.equipmenttest.library.services.ServiceData;

/**
 * Class related to the login request of the user
 * @author Federico Foschini
 *
 */
public class AuthenticateUserRequest extends ARequest {

	// { Internal Fields

	protected String _userpwd = "";
	
    // }

	// { Constructors

	/**
	 * Constructor
	 * @param userpassword: user's password
	 */
	public AuthenticateUserRequest(String userpassword) {
		super("");
		_userpwd = userpassword;
		_serviceURL = ServiceData.AUTH_USER_SERVICE;
	}

    // }

	// { Overriding Methods
	
	@Override
	public RequestType getRequestType() {
		return RequestType.HTTPPOST;
	}

	@Override
	public String getRequestQueryString() {
		return null;
	}

	@Override
	public String getRequestBodyString() throws JSONException {
		JSONObject jObj = new JSONObject();
        jObj.put("UserName", _userpwd);
        return jObj.toString();
	}

    // }

}
