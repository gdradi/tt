package com.technogym.equipmenttest.tags.app.services.responses;

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
		try {
			JSONObject jRec = new JSONObject(responseData);
			_resCode = jRec.getInt("ResultCode");
			_error = jRec.getString("Error");
			_token = jRec.getString("Token");
		} catch(Exception ex) {
			_resCode = 0;
			_error = "";
			_token = "";
		}
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
