package com.technogym.equipmenttest.photo.app.models;

import java.io.Serializable;

/**
 * Model related to the verify image action
 * Created by federico.foschini.
 */
public class VerifyImageModel implements Serializable {

    // { Internal Fields

    private String _actionID;           // Action ID
    private String _actionDescription;  // Action description
    private String _actionSequence;      // Action sequence
    private String _serialNumber;       // Equipment Serial Number
    private Boolean _hasImage;          // Flag related to the image presence

    // { Constructors

    /**
     * Constructos
     * @param actionID: action ID
     * @param actionDescription: action description
     * @param actionSequence: action sequence
     * @param serialNumber: serial number
     * @param hasImage: flag related to the image presence
     */
    public VerifyImageModel(String actionID, String actionDescription, String actionSequence, String serialNumber, Boolean hasImage) {
        _actionID = actionID;
        _actionDescription = actionDescription;
        _actionSequence = actionSequence;
        _serialNumber = serialNumber;
        _hasImage = hasImage;
    }

    // }

    // { Public Methods

    /**
     * Method to set the action ID
     * @param actionSequence: the target action sequence
     */
    public void setActionSequence(String actionSequence) { _actionSequence = actionSequence; }

    /**
     * Method to get the action sequence
     * @return the action sequence
     */
    public String getActionSequence() { return _actionSequence; }





    /**
     * Method to set the action ID
     * @param actionID: the target action ID
     */
    public void setActionID(String actionID) { _actionID = actionID; }

    /**
     * Method to get the action ID
     * @return the action ID
     */
    public String getActionID() { return _actionID; }

    /**
     * Method to set the action description
     * @param actionDescription: the target action description
     */
    public void setActionDescription(String actionDescription) { _actionDescription = actionDescription; }

    /**
     * Method to get the action description
     * @return the action description
     */
    public String getActionDescription() { return _actionDescription; }

    /**
     * Method to set the serial number
     * @param serialNumber: the serial number
     */
    public void setSerialNumber(String serialNumber) { _serialNumber = serialNumber; }

    /**
     * Method to get the serial number
     * @return the serial number
     */
    public String getSerialNumber() { return _serialNumber; }

    /**
     * Method to set the flag related to the image presence
     * @param hasImage: the flag related to the image presence
     */
    public void setImagePresence(Boolean hasImage) { _hasImage = hasImage; }

    /**
     * Method to get the flag related to the image presence
     * @return the flag related to the image presence
     */
    public Boolean hasImage() { return _hasImage; }

    // }
}
