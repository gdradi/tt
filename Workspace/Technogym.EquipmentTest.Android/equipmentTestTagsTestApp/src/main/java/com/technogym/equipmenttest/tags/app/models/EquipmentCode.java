package com.technogym.equipmenttest.tags.app.models;

/**
 * Model related to the Equipment Code configuration
 * @author Federico Foschini
 *
 */
public class EquipmentCode {

    // { Internal Fields

    private int mID;
    private String mSerialNumberMask;
    private String mQrCodeContent;
    private String mNfcContent;

    // }

    // { Constructors

    /**
     * Constructor
     * @param id: equipment code ID
     * @param serialNumberMask: target equipment serial number mask
     * @param qrCodeContent: qr code content
     * @param nfcContent: nfc content
     */
    public EquipmentCode(int id, String serialNumberMask, String qrCodeContent, String nfcContent) {
        mID = id;
        mSerialNumberMask = serialNumberMask;
        mQrCodeContent = qrCodeContent;
        mNfcContent = nfcContent;
    }

    // }

    // { Public Methods

    /**
     * Method to get the Equipment Code ID
     * @return the Equipment Code ID
     */
    public int getID() { return mID; }

    /**
     * Method to get the serial number mask
     * @return the serial number mask
     */
    public String getSerialNumberMask() { return mSerialNumberMask; }

    /**
     * Method to get the QR Code content
     * @return the QR Code content
     */
    public String getQrCodeContent() { return mQrCodeContent; }

    /**
     * Method to get the NFC content
     * @return the NFC content
     */
    public String getNfcContent() { return mNfcContent; }

    // }
}
