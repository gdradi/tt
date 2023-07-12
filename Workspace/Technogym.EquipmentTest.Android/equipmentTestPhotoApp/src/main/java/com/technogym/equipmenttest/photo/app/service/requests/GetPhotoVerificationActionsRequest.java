package com.technogym.equipmenttest.photo.app.service.requests;

import com.technogym.equipmenttest.photo.app.service.RequestType;
import com.technogym.equipmenttest.photo.app.service.ServiceData;

import org.json.JSONException;

/**
 * Class related to get the actions related to the verify image type request
 * @author Federico Foschini
 *
 */
public class GetPhotoVerificationActionsRequest extends ARequest {

    // { Internal Fields

    protected String _sn = "";

    // }

    // { Constructors

    /**
     * Constructor
     * @param serialNumber: serial number
     */
    public GetPhotoVerificationActionsRequest(String serialNumber) {
        super("");
        _sn = serialNumber;
        _serviceURL = ServiceData.ACTION_GET_PHOTO_VERIFICATION_ACTIONS_SERVICE;
    }

    // }

    // { Overriding Methods

    @Override
    public RequestType getRequestType() {
        return RequestType.HTTPGET;
    }

    @Override
    public String getRequestQueryString() {
        return ("serialNumber=" + _sn.trim());
    }

    @Override
    public String getRequestBodyString() throws JSONException  {
        return null;
    }

    // }
}