package com.technogym.equipmenttest.tags.app.services.responses;

import android.util.Log;

import com.technogym.equipmenttest.tags.app.models.EquipmentCode;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class related to the get response of the equioment codes
 * @author Federico Foschini
 *
 */
public class GetEquipmentCodesResponse extends AResponse {

    // { Internal Fields

    protected EquipmentCode _equipmentCode;

    // }

    // { Constructors

    /**
     * Constructor
     * @param responseData: the data returned from the service
     * @throws JSONException
     */
    public GetEquipmentCodesResponse(String responseData) throws JSONException {
        super(responseData);
        Log.i(this.getClass().getSimpleName(), "JSON ---> " + responseData);
        JSONObject jResp = new JSONObject(responseData);
        int id =  jResp.getInt("ID");
        String qrCode = jResp.isNull( "QRCodeContent" ) ? null : jResp.getString("QRCodeContent");
        String nfc =  jResp.isNull( "NFCContent" ) ? null : jResp.getString("NFCContent");
        String sn =  jResp.getString("SerialNumberMask");

        _equipmentCode = new EquipmentCode(id, sn, qrCode, nfc);

        Log.i(this.getClass().getSimpleName(), "ID: " + _equipmentCode.getID());
        Log.i(this.getClass().getSimpleName(), "QR Code: " + _equipmentCode.getQrCodeContent());
        Log.i(this.getClass().getSimpleName(), "NFC: " + _equipmentCode.getNfcContent());
        Log.i(this.getClass().getSimpleName(), "Serial Number Mask: " + _equipmentCode.getSerialNumberMask());
    }

    // }

    // { Public Methods

    /**
     * Method to get the equipment code
     * @return the equipment code
     */
    public EquipmentCode getEquipmentCode() {
        return _equipmentCode;
    }

    // }
}
