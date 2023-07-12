package com.technogym.equipmenttest.tags.app.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.technogym.equipmenttest.tags.app.R;
import com.technogym.equipmenttest.tags.app.models.EquipmentCode;
import com.technogym.equipmenttest.tags.app.services.TargetServices;
import com.technogym.equipmenttest.tags.app.services.requests.GetEquipmentCodesRequest;
import com.technogym.equipmenttest.tags.app.services.requests.IRequest;
import com.technogym.equipmenttest.tags.app.services.responses.GetEquipmentCodesResponse;
import com.technogym.equipmenttest.tags.app.tasks.ServiceTask;
import com.technogym.equipmenttest.tags.app.tasks.listeners.ITaskCompleteListener;

import it.spot.android.logger.domain.ILogger;
import it.spot.android.logger.domain.Logger;
import it.spot.android.logger.file.FileLogProxy;
import it.spot.android.logger.mint.MintLogProxy;

public class AuthActivity extends AppCompatActivity implements ITaskCompleteListener {

    // { Internal Fields

    private static final int REQUEST_PERMISSION_ID = 1;

    protected static int QR_CODE_REQUEST_ID = 0;
    protected static int NFC_REQUEST_ID = 1;

    private Button btnScan;
    private Button btnStart;
    private EditText editTextSn;

    private ProgressDialog progressDialog;
    private Handler mScanHandler;

    private ILogger mLogger;

    // { Public Fields

    public static String USER_PARAMETER = "USER";
    public static String EQUIPMENT_SERIAL_NUMBER_PARAMETER = "SERIAL_NUMBER";
    public static String ACTION_ID_PARAMETER = "ACTION_ID";

    // }

    // }

    // { Overriding methods

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_ID: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

//					// permission was granted, yay! Do the
//					// contacts-related task you need to do.
                } else {
//
//					// permission denied, boo! Disable the
//					// functionality that depends on this permission.
//
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mLogger = Logger.getInstance();
        mLogger.addLogger(new FileLogProxy());
        mLogger.addLogger(new MintLogProxy(this, "fe19cb48"));
        mLogger.enableLoggers(true);

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

            ActivityCompat.requestPermissions(AuthActivity.this,
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

        mScanHandler = new Handler();
        editTextSn = (EditText)this.findViewById(R.id.editTxtSerial);
        editTextSn.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                updateControlsVisibility();
            }
        });

        progressDialog = new ProgressDialog(AuthActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        // button per scannerizzare il barcode
        btnScan = (Button)this.findViewById(R.id.btnScanSN);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanBarCode();
            }
        });

        // button per avviare il test
        btnStart = (Button)this.findViewById(R.id.btnStartTest);
        btnStart.setVisibility(View.GONE);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTest();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onActivityResult] Request Code: " + requestCode);
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanResult == null) {
                return;
            }
            final String result = scanResult.getContents();
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onActivityResult] Result is not null: " + (result != null));
            if (result != null) {
                Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onActivityResult] Result value: " + result);
                mScanHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        editTextSn.setText(result);
                        updateControlsVisibility();
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        mLogger.enableLoggers(false);
        mLogger.destroy();

        super.onDestroy();
    }

    // }

    // { Private Methods

    /**
     * Metodo per aggiornare la visibilità dei controlli
     */
    private void updateControlsVisibility() {
        btnStart.setVisibility(editTextSn.getText().toString().trim().isEmpty() ? View.GONE : View.VISIBLE);
    }

    /**
     * Metodo per avviare il test
     */
    private void startTest() {
        editTextSn.setText(editTextSn.getText().toString().toUpperCase());

        IRequest req = new GetEquipmentCodesRequest(editTextSn.getText().toString());
        new ServiceTask(this, TargetServices.GET_EQUIPMENT_CODES, req).execute();

        this.progressDialog.setMessage(getResources().getString(R.string.setup_session_progress_waiting));
        this.progressDialog.show();
    }

    // }

    // { Public Methods

    /**
     * Metodo per scannerizzare il barcode
     */
    public void scanBarCode() {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Scanning bar code...");
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();

        /*
            OLD Android Version

            IntentIntegrator integrator = new IntentIntegrator(this.mActivity);
            integrator.addExtra("SCAN_WIDTH", 800);
            integrator.addExtra("SCAN_HEIGHT", 200);
            integrator.addExtra("RESULT_DISPLAY_DURATION_MS", 3000L);
            integrator.addExtra("PROMPT_MESSAGE", "Custom prompt to scan a product");
            integrator.initiateScan(IntentIntegrator.PRODUCT_CODE_TYPES);
         */
    }

    // }

    // { ITaskCompleteListener implementation

    @Override
    public void onTaskComplete(Object data, TargetServices targetService) {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onTaskComplete] Result Data: " + data);
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onTaskComplete] Service: " + targetService.toString());

        if(progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        // prelievo della response e dei codici della macchina
        GetEquipmentCodesResponse response = (GetEquipmentCodesResponse)data;
        EquipmentCode code = response.getEquipmentCode();

        Log.i(this.getClass().getSimpleName(), "ID: " + code.getID());
        Log.i(this.getClass().getSimpleName(), "QR Code: " + code.getQrCodeContent());
        Log.i(this.getClass().getSimpleName(), "NFC: " + code.getNfcContent());
        Log.i(this.getClass().getSimpleName(), "Serial Number Mask: " + code.getSerialNumberMask());

        // verifica presenza codici
        if(code.getID() == 0 || (code.getNfcContent() == null && code.getQrCodeContent() == null)) {
            // errore, nessun test per la macchina
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setTitle(this.getResources().getString(R.string.error));
            alertDialog.setMessage(this.getResources().getString(R.string.no_code_test_found));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                    this.getResources().getString(R.string.ok_text),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return;
        }

        // se il codice è ok si prosegue
        Intent myIntent = new Intent(AuthActivity.this, MainActivity.class);
        myIntent.putExtra(MainActivity.INTENT_KEY_SERIALNUMBER, editTextSn.getText().toString());
        myIntent.putExtra(MainActivity.INTENT_KEY_EXECUTE_QR_CODE_TEST, code.getQrCodeContent() != null);
        myIntent.putExtra(MainActivity.INTENT_KEY_QR_CODE_CONTENT, code.getQrCodeContent());
        myIntent.putExtra(MainActivity.INTENT_KEY_EXECUTE_NFC_TEST, code.getNfcContent() != null);
        myIntent.putExtra(MainActivity.INTENT_KEY_NFC_CONTENT, code.getNfcContent());
        AuthActivity.this.startActivity(myIntent);
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

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setTitle(this.getResources().getString(R.string.error));
        alertDialog.setMessage(this.getResources().getString(R.string.configuring_error));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                this.getResources().getString(R.string.ok_text),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    // }
}
