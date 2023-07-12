package com.technogym.equipmenttest.photo.app.service.response;

import com.technogym.equipmenttest.photo.app.models.VerifyImageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class related to get the actions related to the verify image type response
 * @author Federico Foschini
 *
 */
public class GetPhotoVerificationActionsResponse extends AResponse {

    // { Internal Fields

    protected ArrayList<VerifyImageModel> verifyImageModels = new ArrayList<>();

    // }

    // { Constructors

    /**
     * Constructor
     * @param responseData: the data returned from the service
     * @throws JSONException
     */
    public GetPhotoVerificationActionsResponse(String responseData) throws JSONException {
        super(responseData);
        verifyImageModels = new ArrayList<>();
        JSONObject jResp = new JSONObject(responseData);
        JSONArray jsonArray = jResp.getJSONArray("Data");
        for (int i=0; i< jsonArray.length(); i++) {
            JSONObject actor = jsonArray.getJSONObject(i);
            String actionId = actor.getString("ActionID");
            String actionSeq = actor.getString("ActionSequence");
            String actionDesc = actor.getString("ActionDescription");
            String serialNumber = actor.getString("SerialNumber");
            Boolean hasImage = actor.getBoolean("HasImage");
            VerifyImageModel model = new VerifyImageModel(actionId, actionDesc, actionSeq, serialNumber, hasImage);
            verifyImageModels.add(model);
        }
    }

    // }

    // { Public Methods

    /**
     * Method to get the Verify Image Models
     * @return the Verify Image Models
     */
    public List<VerifyImageModel> getVerifyImageModels() { return verifyImageModels; }

    // }
}
