package com.technogym.equipmenttest.library.services.responses;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AResponse implements IResponse {

	// { Internal Fields
	
    protected String _token = "";
    protected int _resCode = 500;
    protected String _error = "";
    
    // }

	// { Constructors

    /**
     * Constructor
     * @param responseData: the data returned from the service
     * @throws JSONException
     */
    public AResponse(String responseData) throws JSONException {
        JSONObject jRec = new JSONObject(responseData);
        _resCode = jRec.getInt("ResultCode");
        _error = jRec.getString("Error");
        _token = jRec.getString("Token");
    }

    // }

	// { Overriding Methods

	@Override
	public int getResultCode() {
		return _resCode;
	}

	@Override
	public String getError() {
		return _error;
	}

	@Override
	public String getToken() {
		return _token;
	}

    // }

}
