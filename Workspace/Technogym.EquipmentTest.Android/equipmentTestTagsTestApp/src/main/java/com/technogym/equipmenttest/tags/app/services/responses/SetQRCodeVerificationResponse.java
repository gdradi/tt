package com.technogym.equipmenttest.tags.app.services.responses;

import org.json.JSONException;
import org.json.JSONObject;

import it.spot.android.logger.domain.Logger;

/**
 * Class related to the response related to the set of the QR Code verification result
 * @author Federico Foschini
 */
public class SetQRCodeVerificationResponse extends AResponse {

    // { Internal Fields

    protected boolean _result = false;

    // }

    // { Constructors

    /**
     * Constructor
     * @param responseData: the data returned from the service
     * @throws JSONException
     */
    public SetQRCodeVerificationResponse(String responseData) throws JSONException {
        super(responseData);

        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[QR Code Verification Response: " + responseData);
        JSONObject jResp = new JSONObject(responseData);
        _result = jResp.getBoolean("Data");
    }

    // }

    // { Public Methods

    /**
     * Method to get the result of the response
     * @return The result of the response
     */
    public boolean getResult() { return _result; }

    // }
}
