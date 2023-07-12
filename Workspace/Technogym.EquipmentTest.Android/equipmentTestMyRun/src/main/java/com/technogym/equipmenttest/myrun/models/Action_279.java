package com.technogym.equipmenttest.myrun.models;

import android.content.Context;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.system.commands.GetCalibrationGradientValueMVCommand;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.myrun.proxies.SystemProxy;

import java.util.UUID;

import it.spot.android.logger.domain.Logger;

public class Action_279 extends BaseMyRunAction {

    private static final String TAG = Action_279.class.getSimpleName();
    private final ISystemProxy mSystemProxy;

    private String valA, valB, valC;
    private Integer curRetry;
    private Integer maxRetry;

    private boolean firstRkadcResponseObtained = false;
    private boolean secondRkadcResponseObtained = false;
    private boolean eepromdefResponseObtained = false;
    private boolean skadcResponseObtained = false;
    private boolean enabledRkadcSecondFlow = false;

    private String guid;
    /**
     * Base constructor for an {@link Action}.
     *
     * @param context : the application's {@link Context} within it gets executed.
     */
    protected Action_279(Context context) {
        super(context);

        this.mSystemProxy = SystemProxy.getInstance(null);
        mSystemProxy.registerForNotification(this);

        valA = "";
        valB = "";
        valC = "";
        maxRetry = 3;
        curRetry = 0;

        guid = UUID.randomUUID().toString();
    }

    public static Action_279 create(final Context context) {
        return new Action_279(context);
    }



    @Override
    public void execute(String data) {

//        1. Comando: @RKADC#
//           Risposta:!RKADC: A=<a> B=<b> C=<c>
//           Salvataggio valori A_old,B_old,C_old
//        2. Comando: @EEPROMDEF#
//           Risposta: !EEPROMDEF:OK (altrimenti !EEPROMDEF:ERROR)
//        3. Comando @SKADC_C C_old
//           Risposta !SKADC_C:C=C_old
//        4. Comando: @RKADC#
//           Risposta:!RKADC: A=<a> B=<b> C=<c>
//           Salvataggio valori A_new,B_new,C_new
//           Se A_old == A_new || B_old == B_new ||C_old == C_new RETURN OK, Azione completata
//           Altrimenti si riparte dal punto 2 (max 3 retry)

        Logger.getInstance().logDebug(TAG, "["+guid+"]Executing Action 279");
        sendRKADC();
    }

    // Send RKADC
    private void sendRKADC() {
        try {
            Logger.getInstance().logDebug(TAG, "["+guid+"][sendRKADC] Sending RKADC Command");
            this.mSystemProxy.sendCommand(GetCalibrationGradientValueMVCommand.create());
        } catch (WriteNotAllowedException e) {
            Logger.getInstance().logError(TAG, "["+guid+"][sendRKADC]  Error during RKADC Command: " + e.getLocalizedMessage());
            mSystemProxy.deregisterForNotification(this);
            this.mListener.onActionUpdate("false");
            e.printStackTrace();
        }
    }

    // Callback RKADC
    @Override
    public void onCalibrationGradientValueMVRetreived(String result) {
        Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback] RKADC Response: "+ result);

        if(valA.equals("") && valB.equals("") && valC.equals("")){
            if(!firstRkadcResponseObtained){
                firstRkadcResponseObtained = true;
                Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback][First] Saving values");

                String data = result.replace("A=", "");
                data = data.replace("B=", "_");
                data = data.replace("C=", "_");
                String[] all = data.split("_");

                valA = all[0];
                valB = all[1];
                valC = all[2];

                Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback][First] Values saved: valA="+valA+" valB="+valB+" valC="+valC);
                Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback][First] Sending @EEPROMDEF#");
                sendEEPROMDEF();
            }else{
                Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback][First] Multiple answer to Command RKADC. Skipping..");
            }
        }else{
            if(enabledRkadcSecondFlow){ // Questo flag viene abilitato nella callback di SKADC_C per ovviare alla problematica di risposte multiple durante il primo invio di SKADC (l'azione terminerebbe subito)
                if(!secondRkadcResponseObtained){
                    secondRkadcResponseObtained = true;
                    Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback][Second] Second response RKADC. Verifying values..");

                    String data = result.replace("A=", "");
                    data = data.replace("B=", "_");
                    data = data.replace("C=", "_");
                    String[] all = data.split("_");

                    Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback][Second] Check A: "+valA+" equals to "+all[0]+": "+valA.equals(all[0]));
                    Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback][Second] Check B: "+valB+" equals to "+all[1]+": "+valB.equals(all[1]));
                    Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback][Second] Check C: "+valC+" equals to "+all[2]+": "+valC.equals(all[2]));

                    if(valA.equals(all[0]) && valB.equals(all[1]) && valC.equals(all[2])){
                        Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback][Second] Values are OK. Calling onActionUpdate(true)");
                        mSystemProxy.deregisterForNotification(this);
                        this.mListener.onActionUpdate("true");
                    }else{
                        Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback][Second] Values are NOT OK");
                        Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback][Second] Retry done: "+curRetry+" - Max retry: "+ maxRetry);
                        if(curRetry < maxRetry){
                            secondRkadcResponseObtained = false;
                            eepromdefResponseObtained = false;
                            skadcResponseObtained = false;
                            enabledRkadcSecondFlow = false;
                            curRetry ++;
                            Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback][Second] Retry number: "+curRetry+" - Sending @EEPROMDEF#");
                            sendEEPROMDEF();
                        }else{
                            Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback][Second] Max Retry Done - Calling onActionUpdate(false)");
                            // Creating string result for showing error in web app
                            String incorrectValues = !valA.equals(all[0]) ? " A " : "";
                            incorrectValues += !valB.equals(all[1]) ? "B " : "";
                            incorrectValues += !valC.equals(all[2]) ? "C" : "";
                            mSystemProxy.deregisterForNotification(this);
                            this.mListener.onActionUpdate(incorrectValues);
                        }
                    }
                }else{
                    Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback][Second] Multiple answer to Command RKADC. Skipping..");
                }
            }else{
                Logger.getInstance().logDebug(TAG, "["+guid+"][RKADC Callback][Second] Second Flow not yet enabled. Skipping..");
            }
        }
    }


    // Send EEPROMDEF
    private void sendEEPROMDEF() {
        try {
            Logger.getInstance().logDebug(TAG, "["+guid+"][sendEEPROMDEF] Sending EEPROMDEF Command");
            this.mSystemProxy.setEEPROMDefaultValues();
        } catch (WriteNotAllowedException e) {
            Logger.getInstance().logError(TAG, "["+guid+"][sendEEPROMDEF] Error during EEPROMDEF Command: " + e.getLocalizedMessage());
            mSystemProxy.deregisterForNotification(this);
            this.mListener.onActionUpdate("false");
            e.printStackTrace();
        }
    }



    // Callback EEPROMDEF
    @Override
    public void onSetEEPROMDefaultValuesSet(String result) {
        Logger.getInstance().logDebug(TAG, "["+guid+"][EEPROMDEF Callback] EEPROMDEF Response:"+ result);
        if(!eepromdefResponseObtained){
            eepromdefResponseObtained = true;
            if(result.contains("OK")){
                Logger.getInstance().logDebug(TAG, "["+guid+"][EEPROMDEF Callback] Result is 'OK'. Sending @SKADC_C#");
                sendSKADC_C();
            }else{
                Logger.getInstance().logDebug(TAG, "["+guid+"][EEPROMDEF Callback] Result is NOT 'OK' - Calling onActionUpdate(false)");
                mSystemProxy.deregisterForNotification(this);
                this.mListener.onActionUpdate("false");
            }
        }else{
            Logger.getInstance().logDebug(TAG, "["+guid+"][EEPROMDEF Callback] Multiple answer to Command EEPROMDEF. Skipping..");
        }

    }


    // Send SKADC_C
    private void sendSKADC_C() {
        try {
            Logger.getInstance().logDebug(TAG, "["+guid+"][sendSKADC_C] Sending sendSKADC_C Command with parameter C="+this.valC);
            this.mSystemProxy.SetCalibrationGradientValueC(this.valC);
        } catch (WriteNotAllowedException e) {
            Logger.getInstance().logError(TAG, "["+guid+"][sendSKADC_C] Error during sendSKADC_C Command: " + e.getLocalizedMessage());
            mSystemProxy.deregisterForNotification(this);
            this.mListener.onActionUpdate("false");
            e.printStackTrace();
        }
    }




    // Callback SKADC_C
    @Override
    public void onCalibrationGradientValueCSet(String mResult) {
        Logger.getInstance().logDebug(TAG, "["+guid+"][SKADC_C Callback] SKADC_C Response: "+ mResult);
        if(!skadcResponseObtained){
            skadcResponseObtained = true;
            String obtainedCValue = mResult.replace("C=", "");
            if(obtainedCValue.equals(valC)){
                Logger.getInstance().logDebug(TAG, "["+guid+"][SKADC_C Callback] Obtained C Value " + obtainedCValue + " equals to old C Value "+ valC);
                Logger.getInstance().logDebug(TAG, "["+guid+"][SKADC_C Callback] Calling sendRKADC()");
                enabledRkadcSecondFlow = true;
                sendRKADC();
            }else{
                Logger.getInstance().logDebug(TAG, "["+guid+"][SKADC_C Callback] Obtained C Value " + obtainedCValue + " NOT equals to old C Value "+ valC);
                Logger.getInstance().logDebug(TAG, "["+guid+"][SKADC_C Callback] Calling onActionUpdate(false)");
                mSystemProxy.deregisterForNotification(this);
                this.mListener.onActionUpdate("false");
            }
        }else{
            Logger.getInstance().logDebug(TAG, "["+guid+"][SKADC_C Callback] Multiple answer to Command SKADC_C. Skipping..");
        }
    }

}
