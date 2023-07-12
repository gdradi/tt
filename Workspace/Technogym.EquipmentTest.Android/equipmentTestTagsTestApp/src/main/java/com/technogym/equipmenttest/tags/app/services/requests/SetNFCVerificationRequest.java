package com.technogym.equipmenttest.tags.app.services.requests;

import com.technogym.equipmenttest.tags.app.services.RequestType;
import com.technogym.equipmenttest.tags.app.services.ServiceData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class related to the request to set the NFC tag verification result
 * @author Federico Foschini
 */
public class SetNFCVerificationRequest extends ARequest {

    // { Internal Fields

    protected String _serialNumber = "";
    protected boolean _result = false;

    // }

    // { Constructors

    /**
     * Constructor
     * @param serialNumber: equipment serial number
     * @param result: verification result
     */
    public SetNFCVerificationRequest(String serialNumber, boolean result) {
        super("");
        _serialNumber = serialNumber;
        _result = result;
        _serviceURL = ServiceData.ACTION_SET_EQUIPMENT_NFC_VERIFICATION_SERVICE;
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
        jObj.put("SerialNumber", _serialNumber.trim());
        jObj.put("VerificationResult", _result);
        return jObj.toString();
    }

    // }
}
