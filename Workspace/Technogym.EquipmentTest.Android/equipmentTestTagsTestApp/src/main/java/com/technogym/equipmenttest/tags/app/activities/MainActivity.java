package com.technogym.equipmenttest.tags.app.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.technogym.equipmenttest.tags.app.R;
import com.technogym.equipmenttest.tags.app.services.TargetServices;
import com.technogym.equipmenttest.tags.app.services.requests.IRequest;
import com.technogym.equipmenttest.tags.app.services.requests.SetNFCVerificationRequest;
import com.technogym.equipmenttest.tags.app.services.requests.SetQRCodeVerificationRequest;
import com.technogym.equipmenttest.tags.app.services.responses.SetNFCVerificationResponse;
import com.technogym.equipmenttest.tags.app.services.responses.SetQRCodeVerificationResponse;
import com.technogym.equipmenttest.tags.app.tasks.ServiceTask;
import com.technogym.equipmenttest.tags.app.tasks.listeners.ITaskCompleteListener;

import java.util.HashMap;
import java.util.Map;

import it.spot.android.logger.domain.Logger;

public class MainActivity extends AppCompatActivity implements ITaskCompleteListener {

    //region Public Fields

    public static int NFC_REQUEST_ID = 1;

    // key relative ai dati passati come payload dell'intent
    public static String INTENT_KEY_SERIALNUMBER = "SERIAL_NUMBER";
    public static String INTENT_KEY_EXECUTE_QR_CODE_TEST = "EXECUTE_QR_CODE_TEST";
    public static String INTENT_KEY_QR_CODE_CONTENT = "QR_CODE_CONTENT";
    public static String INTENT_KEY_EXECUTE_NFC_TEST = "EXECUTE_NFC_TEST";
    public static String INTENT_KEY_NFC_CONTENT = "NFC_CONTENT";

    //endregion

    //region Internal Fields

    private static final int REQUEST_PERMISSION_ID = 1;
    private static final String LINE_SPLIT_REGEX = "\\r?\\n";

    private String equipmentSerialNumber;       // serial number
    private boolean executeQrCodeTest;          // flag indicante se si deve eseguire il test del QRCode
    private boolean executeNfcTest;             // flag indicante se si deve eseguire il test del NFC
    private Map<String, Boolean> qrCodesChecked;// map qr -> esito (CP_IP_2019_027 gestire N qrcode)
    private Map<String, Boolean> nfcsChecked;   // map tag -> esito (CP_IP_2019_027 gestire M tag NFC)

    private Button btnQrCodeTest;
    private Button btnNfcTest;
    private AlertDialog alertValidity;
    private AlertDialog alertInfo;
    private ProgressDialog pDialog;
    //endregion

    //region Overriding methods

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (requestCode == REQUEST_PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                Log.i(MainActivity.class.getSimpleName(), "Permission granted");
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
                Log.i(MainActivity.class.getSimpleName(), "Permission denied");
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check permissions and grants to obtain
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_PRIVILEGED) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.NFC) != PackageManager.PERMISSION_GRANTED)
                || (ContextCompat.checkSelfPermission(this, Manifest.permission.BIND_NFC_SERVICE) != PackageManager.PERMISSION_GRANTED)
                ){

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.CHANGE_WIFI_STATE,
                            Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.WAKE_LOCK,
                            Manifest.permission.CAMERA,
                            Manifest.permission.VIBRATE,
                            Manifest.permission.INTERNET,
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN,
                            Manifest.permission.BLUETOOTH_PRIVILEGED,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    REQUEST_PERMISSION_ID);
        }

        Intent intent = getIntent();
        equipmentSerialNumber = intent.getStringExtra(INTENT_KEY_SERIALNUMBER).trim();

        // Handling QRCodes (stringa a DB che contiene i diversi qr separati da andata a capo)
        qrCodesChecked = new HashMap<>();
        executeQrCodeTest = intent.getBooleanExtra(INTENT_KEY_EXECUTE_QR_CODE_TEST, false);
        String qrCodeDBContent = intent.getStringExtra(INTENT_KEY_QR_CODE_CONTENT);
        executeQrCodeTest = qrCodeDBContent != null;
        if (executeQrCodeTest) {
            String[] qrContents = qrCodeDBContent.split(LINE_SPLIT_REGEX);
            for(String qr : qrContents) {
                qrCodesChecked.put(qr, false);
            }
        }
        // Handling NFC tags (stringa a DB che contiene i diversi tag separati da andata a capo)
        nfcsChecked = new HashMap<>();
        executeNfcTest = intent.getBooleanExtra(INTENT_KEY_EXECUTE_NFC_TEST, false);
        String nfcDBContent = intent.getStringExtra(INTENT_KEY_NFC_CONTENT);
        executeNfcTest = nfcDBContent != null;
        if (executeNfcTest) {
            String[] nfcTags = nfcDBContent.split(LINE_SPLIT_REGEX);
            for(String tag : nfcTags) {
                nfcsChecked.put(tag, false);
            }
        }

        TextView txtViewSerialNumber = (TextView) this.findViewById(R.id.txtViewSerialNumber);
        txtViewSerialNumber.setText(equipmentSerialNumber);

        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onCreate] Serial Number: " + equipmentSerialNumber);
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onCreate] Execute QR Code test: " + executeQrCodeTest);
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onCreate] QR Code right content: " + qrCodeDBContent);
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onCreate] Execute NFC test: " + executeNfcTest);
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onCreate] NFC right content: " + nfcDBContent);

        Log.i(this.getClass().getSimpleName(), "[onCreate] Serial Number: " + equipmentSerialNumber);
        Log.i(this.getClass().getSimpleName(), "[onCreate] Execute QR Code test: " + executeQrCodeTest);
        Log.i(this.getClass().getSimpleName(), "[onCreate] QR Code right content: " + qrCodeDBContent);
        Log.i(this.getClass().getSimpleName(), "[onCreate] Execute NFC test: " + executeNfcTest);
        Log.i(this.getClass().getSimpleName(), "[onCreate] NFC right content: " + nfcDBContent);

        // button per scannerizzare il QR Code
        btnQrCodeTest = (Button)this.findViewById(R.id.btnScanQrCode);
        btnQrCodeTest.setVisibility(View.GONE);
        btnQrCodeTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQrCode();
            }
        });

        // button per scannerizzare l'NFC
        btnNfcTest = (Button)this.findViewById(R.id.btnScanNFC);
        btnNfcTest.setVisibility(View.GONE);
        btnNfcTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanNfc();
            }
        });

        // update visibilità controlli
        updateControlsVisibility();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onActivityResult] Request Code: " + requestCode);
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onActivityResult] QR Code Result triggered");
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanResult == null) {
                return;
            }
            // Ottenimento del risultato dello scanning del QR Code e controllo validità
            final String result = scanResult.getContents();
            boolean validContent = qrCodesChecked.containsKey(result);
            if (validContent) {
                qrCodesChecked.put(result, true);
                if (isQRCodeTestCompleted()) {
                    // salvataggio risultato solo in caso di OK per tutti i QRCode
                    showSaveProgressDialog();
                    IRequest req = new SetQRCodeVerificationRequest(equipmentSerialNumber.toUpperCase(), true);
                    new ServiceTask(this, TargetServices.SET_EQUIPMENT_QRCODE_VERIFICATION, req).execute();
                } else {
                    showValidityDialog(R.string.qr_code_valid_title, R.string.qr_code_valid_msg);
                }
            } else {
                showValidityDialog(R.string.error, R.string.qr_code_not_valid_error);
            }

            //Toast.makeText(this, "QR CODE RESULT: " + result, Toast.LENGTH_LONG).show();
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onActivityResult] QR Code Result is null: " + (result != null));
        }

        if (requestCode == NFC_REQUEST_ID) {
	    	// Ottenimento del risultato dello scanning del tag NFC e controllo validità
            String contents = data != null
                    ? data.getStringExtra(NfcReaderActivity.NFC_TAG_CONTENT)
                    : null;
            boolean validContent = nfcsChecked.containsKey(contents);
            if (validContent) {
                nfcsChecked.put(contents, true);
                if(isNFCTestCompleted()) {
                    // salvataggio risultato solo in caso di OK per tutti i TAG
                    showSaveProgressDialog();
                    IRequest req = new SetNFCVerificationRequest(equipmentSerialNumber.toUpperCase(), true);
                    new ServiceTask(this, TargetServices.SET_EQUIPMENT_NFC_VERIFICATION, req).execute();
                } else {
                    showValidityDialog(R.string.nfc_valid_title, R.string.nfc_valid_msg);
                }
            } else {
                showValidityDialog(R.string.error, R.string.nfc_not_valid_error);
            }

            //Toast.makeText(this, "NFC RESULT: "+ contents , Toast.LENGTH_LONG).show();
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onActivityResult] NFC Result: " + contents);
        }
    }

    //endregion

    //region ITaskCompleteListener implementation

    @Override
    public void onTaskComplete(Object data, TargetServices targetService) {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        switch (targetService) {
            case SET_EQUIPMENT_QRCODE_VERIFICATION:
                // prelievo della response e dei codici della macchina
                SetQRCodeVerificationResponse qrCodeResponse = (SetQRCodeVerificationResponse)data;
                Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onTaskComplete - QR Code Save] Result Data: " + qrCodeResponse.getResult());

                // gestione abilitazione controllli in base al risultato del salvataggio
                btnQrCodeTest.setVisibility(qrCodeResponse.getResult() ? View.GONE : View.VISIBLE);

                // feeback sul risultato
                if(qrCodeResponse.getResult()) {
                    showInfoDialog(R.string.save_title, R.string.save_completed_success);
                    checkTestCompletion();
                } else {
                    showInfoDialog(R.string.save_title, R.string.save_completed_error);
                }
                break;

            case SET_EQUIPMENT_NFC_VERIFICATION:
                // prelievo della response e dei codici della macchina
                SetNFCVerificationResponse nfcResponse = (SetNFCVerificationResponse)data;
                Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onTaskComplete - NFC Save] Result Data: " + nfcResponse.getResult());

                // gestione abilitazione controllli in base al risultato del salvataggio
                btnNfcTest.setVisibility(nfcResponse.getResult() ? View.GONE : View.VISIBLE);

                // feeback sul risultato
                if(nfcResponse.getResult()) {
                    showInfoDialog(R.string.save_title, R.string.save_completed_success);
                    checkTestCompletion();
                } else {
                    showInfoDialog(R.string.save_title, R.string.save_completed_error);
                }
                break;
        }
    }

    @Override
    public void onSuccess(Object data, TargetServices targetService) {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onSuccess] Result Data: " + data);
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onSuccess] Service: " + targetService.toString());
    }

    @Override
    public void onError(String errorMessage, TargetServices targetService) {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onError] Error Message: " + errorMessage);
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onError] Service: " + targetService.toString());
        showInfoDialog(R.string.error, R.string.services_error);
    }

    //endregion }

    //region Private Methods

    /**
     * Metodo per completare se il test è stato completato
     */
    private void checkTestCompletion() {
        boolean testCompleted = !executeQrCodeTest || isQRCodeTestCompleted();
        testCompleted = testCompleted && (!executeNfcTest || isNFCTestCompleted());
        if(testCompleted) {
            final Activity currentActivity = this;
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setTitle(this.getResources().getString(R.string.completed_test_title));
            alertDialog.setMessage(this.getResources().getString(R.string.completed_test_message));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                    this.getResources().getString(R.string.ok_text),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            // chiusura activity e back all'autenticazione
                            currentActivity.finish();
                        }
                    });
            alertDialog.show();
        }
    }

    /**
     * Metodo per scannerizzare il QR Code
     */
    private void scanQrCode() {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Scanning QR Code...");
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();
    }

    /**
     * Metodo per scannerizzare il tag NFC
     */
    private void scanNfc() {
        Intent intent = new Intent(MainActivity.this, NfcReaderActivity.class);
        startActivityForResult(intent, NFC_REQUEST_ID);
//		Toast.makeText(this, "executeNfcTest", Toast.LENGTH_LONG).show();
    }

    /**
     * Metodo per aggiornare la visibilità dei controlli
     */
    private void updateControlsVisibility() {
        btnQrCodeTest.setVisibility(executeQrCodeTest ? View.VISIBLE : View.GONE);
        btnNfcTest.setVisibility(executeNfcTest ? View.VISIBLE : View.GONE);
    }

    /**
     * Metodo per visualizzare la dialog di validità lettura QRCode/NFC
     */
    private void showValidityDialog(final int titleResId, final int msgResId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String title = MainActivity.this.getResources().getString(titleResId);
                if (titleResId != R.string.error) {
                    title = titleResId == R.string.nfc_valid_title
                            ? String.format(title, countValidNFCs(), nfcsChecked.size())
                            : String.format(title, countValidQRCodes(), qrCodesChecked.size());
                }
                alertValidity = new AlertDialog.Builder(MainActivity.this).create();
                alertValidity.setCancelable(false);
                alertValidity.setCanceledOnTouchOutside(false);
                alertValidity.setTitle(title);
                alertValidity.setMessage(MainActivity.this.getResources().getString(msgResId));
                alertValidity.setButton(AlertDialog.BUTTON_NEGATIVE,
                        MainActivity.this.getResources().getString(android.R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                // Add positive button for retry or continue
                int positiveRes = titleResId == R.string.error ? R.string.retry : R.string.next;
                alertValidity.setButton(AlertDialog.BUTTON_POSITIVE,
                        MainActivity.this.getResources().getString(positiveRes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                switch (msgResId) {
                                    case R.string.nfc_valid_msg:
                                    case R.string.nfc_not_valid_error:
                                        scanNfc();
                                        break;
                                    case R.string.qr_code_valid_msg:
                                    case R.string.qr_code_not_valid_error:
                                        scanQrCode();
                                        break;
                                }
                            }
                        });
                alertValidity.show();
            }
        });
    }

    /**
     * Metodo per visualizzare una dialog informativa
     * @param titleResId Id della risorsa stringa per il titolo
     * @param msgResId Id della risorsa stringa per il messaggio
     */
    private void showInfoDialog(final int titleResId, final int msgResId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertInfo = new AlertDialog.Builder(MainActivity.this).create();
                alertInfo.setCancelable(false);
                alertInfo.setCanceledOnTouchOutside(false);
                alertInfo.setTitle(MainActivity.this.getResources().getString(titleResId));
                alertInfo.setMessage(MainActivity.this.getResources().getString(msgResId));
                alertInfo.setButton(AlertDialog.BUTTON_NEUTRAL,
                        MainActivity.this.getResources().getString(R.string.ok_text),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertInfo.show();
            }
        });
    }

    /**
     * Metodo che controlla se sono stati testati tutti i QRCode
     */
    private boolean isQRCodeTestCompleted() {
        for(boolean value : qrCodesChecked.values()) {
            if(!value) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metodo che controlla se sono stati testati tutti i tag NFC
     */
    private boolean isNFCTestCompleted() {
        for(boolean value : nfcsChecked.values()) {
            if(!value) {
                return false;
            }
        }
        return true;
    }

    /**
     * Metodo che restituisce il conteggio di tag NFC validati
     */
    private int countValidNFCs() {
        int count = 0;
        for(boolean val : nfcsChecked.values()) {
            if (val) {count++;}
        }
        return count;
    }

    /**
     * Metodo che restituisce il conteggio di qrcode validati
     */
    private int countValidQRCodes() {
        int count = 0;
        for(boolean val : qrCodesChecked.values()) {
            if (val) {count++;}
        }
        return count;
    }

    /**
     * Metodo per mostrare una prograss dialog quando si stanno salvando i risultati
     */
    private void showSaveProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pDialog = ProgressDialog.show(
                        MainActivity.this,
                        "",
                        getResources().getString(R.string.save_action_result_progress_waiting));
            }
        });

    }
    //endregion
}
