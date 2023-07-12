package com.technogym.equipmenttest.tags.app.services.requests;

import com.technogym.equipmenttest.tags.app.services.RequestType;
import com.technogym.equipmenttest.tags.app.services.ServiceData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class related to the get request of the equioment codes
 * @author Federico Foschini
 *
 */
public class GetEquipmentCodesRequest extends ARequest  {

    // { Internal Fields

    protected String _serialNumber = "";

    // }

    // { Constructors

    /**
     * Constructor
     * @param serialNumber: equipment serial number
     */
    public GetEquipmentCodesRequest(String serialNumber) {
        super("");
        _serialNumber = serialNumber;
        _serviceURL = ServiceData.ACTION_GET_EQUIPMENT_CODES_SERVICE;
    }

    // }

    // { Overriding Methods

    @Override
    public RequestType getRequestType() {
        return RequestType.HTTPGET;
    }

    @Override
    public String getRequestQueryString() {
        return ("serialNumber=" + _serialNumber.trim());
    }

    @Override
    public String getRequestBodyString() throws JSONException  {
        return null;
    }

    // }
}
