package com.technogym.equipmenttest.photo.app.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.technogym.equipmenttest.photo.app.R;
import com.technogym.equipmenttest.photo.app.adapters.VerifyImageListAdapter;
import com.technogym.equipmenttest.photo.app.listeners.ITaskCompleteListener;
import com.technogym.equipmenttest.photo.app.models.VerifyImageModel;
import com.technogym.equipmenttest.photo.app.service.TargetServices;
import com.technogym.equipmenttest.photo.app.support.FileManager;
import com.technogym.equipmenttest.photo.app.tasks.HttpVerifyImageUploadTask;

import java.util.ArrayList;
import java.util.List;

import it.spot.android.logger.domain.Logger;

public class MainActivity extends AppCompatActivity implements ITaskCompleteListener {

    // { Public Fields

    public static String INTENT_KEY_SERIALNUMBER = "SERIAL_NUMBER";
    public static String INTENT_KEY_VERIFY_IMAGE_MODELS_COUNT = "INTENT_KEY_VERIFY_IMAGE_MODELS_COUNT";
    public static String INTENT_KEY_VERIFY_IMAGE_MODELS_PREFIX = "INTENT_KEY_VERIFY_IMAGE_MODEL_";

    public static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;    // result ID for image capture
    public static String CurrentActionID;                           // current action ID
    public static String CurrentActionSequence;                     // current action sequence
    public static String CurrentSerialNumber;                       // current serial number
    public static String CurrentImageFilePath;                      // current image filepath
    public static int CurrentVerifyImageIndex;                      // current image index

    // }

    // { Internal Fields

    private static final int REQUEST_PERMISSION_ID = 1;

    private String equipmentSerialNumber;                                       // serial number
    private int verifyImageModelsCount;                                         // size of Verify Image Models list
    private List<VerifyImageModel> verifyImageModels = new ArrayList<>();       // Verify Image Models list
    private String imagesFolderPath = "";                                       // app images folder app

    private TextView txtViewSerialNumber;
    private ListView lvActions;

    private ProgressDialog progressDialog;

    // }

    // { Overriding methods

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
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

        // get the intent and configure the activity
        Intent intent = getIntent();
        configureActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(this.getClass().getSimpleName(), "[onActivityResult] Request Code: " + requestCode);
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onActivityResult] Request Code: " + requestCode);
        Log.i(this.getClass().getSimpleName(), "[onActivityResult] Result Code: " + resultCode);
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onActivityResult] Result Code: " + resultCode);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode != 0) {
            Log.i(this.getClass().getSimpleName(), "[onActivityResult] Result related to the Image Capture");
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onActivityResult] Result related to the Image Capture");

            // setup and show progress dialog
            this.progressDialog.setMessage(getResources().getString(R.string.saving_image_progress));
            this.progressDialog.show();

            // upload the target image
            new HttpVerifyImageUploadTask(this, TargetServices.UPLOAD_PHOTO_VERIFICATION_ACTIONS_SERVICE,
                    CurrentImageFilePath, CurrentSerialNumber, CurrentActionID, CurrentActionSequence)
                .execute();
        }
    }

    // }

    // { Public Methods

    /**
     * Method to get the index of a verify image model
     * @param actionID: target action ID related to the verify image model
     * @return the index of the verify image model related to the target action ID
     */
    public int getVerifyImageModelIndex(String actionID) {
        for(int i = 0; i < verifyImageModels.size(); i++) {
            if(verifyImageModels.get(i).getActionID().equals(actionID)) {
                return i;
            }
        }
        return 0;
    }

    // }

    // { Private Methods

    /**
     * Method to configure the activity
     * @param intent: activity creation intent
     */
    private void configureActivity(Intent intent) {
        // gets the serial number of the target equipment
        equipmentSerialNumber = intent.getStringExtra(INTENT_KEY_SERIALNUMBER).trim();
        txtViewSerialNumber = (TextView)this.findViewById(R.id.txtViewSerialNumber);
        txtViewSerialNumber.setText(equipmentSerialNumber);
        Log.i(this.getClass().getSimpleName(), "[configureActivity] Serial Number: " + equipmentSerialNumber);
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onCreate] Serial Number: " + equipmentSerialNumber);

        // gets the verify image models number
        verifyImageModelsCount = intent.getIntExtra(INTENT_KEY_VERIFY_IMAGE_MODELS_COUNT, 0);
        Log.i(this.getClass().getSimpleName(), "[configureActivity] Actions count: " + verifyImageModelsCount);
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onCreate] Actions count: " + verifyImageModelsCount);

        // configures the verify image models
        verifyImageModels = new ArrayList<>();
        for (int i = 0; i < verifyImageModelsCount; i++) {
            VerifyImageModel cur = (VerifyImageModel) intent.getSerializableExtra(INTENT_KEY_VERIFY_IMAGE_MODELS_PREFIX + i);
            verifyImageModels.add(cur);
            Log.i(this.getClass().getSimpleName(),
                    "[configureActivity] Current Verify Image Model: {" + cur.getActionID()
                            + " | " + cur.getActionDescription()
                            + " | " + cur.getSerialNumber()
                            + " | " + cur.hasImage() + "}");
            Logger.getInstance().logDebug(this.getClass().getSimpleName(),
                    "[configureActivity] Current Verify Image Model: {" + cur.getActionID()
                            + " | " + cur.getActionDescription()
                            + " | " + cur.getSerialNumber()
                            + " | " + cur.hasImage() + "}");
        }
        CurrentVerifyImageIndex = 0;
        CurrentActionSequence = "000";
        CurrentActionID = verifyImageModels.size() > 0 ? verifyImageModels.get(0).getActionID() : "";

        // configures and creates the app folders
        imagesFolderPath = FileManager.createImagesFoldersTree();
        Log.i(this.getClass().getSimpleName(), "[configureActivity] Images folder path: " + imagesFolderPath);
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onCreate] Images folder path: " + imagesFolderPath);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        // configure UI controls to manage the actions
        lvActions = (ListView)findViewById(R.id.list);
        lvActions.setScrollContainer(true);
        lvActions.setClickable(true);
        lvActions.setAdapter(new VerifyImageListAdapter(this, R.layout.verifyimage_listitem, verifyImageModels));
    }

    // }

    // { ITaskCompleteListener implementation

    @Override
    public void onTaskComplete(Object data, TargetServices targetService) {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onTaskComplete] Service: " + targetService.toString());
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onTaskComplete] Result Data: " + data);

        switch (targetService) {
            case UPLOAD_PHOTO_VERIFICATION_ACTIONS_SERVICE:
                Boolean resultSave = (Boolean)data;
                Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[onTaskComplete] Upload Result Success: " + resultSave);

                if(progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                // update the target action and related ListView
                VerifyImageModel curVerifyImage = verifyImageModels.get(CurrentVerifyImageIndex);
                curVerifyImage.setImagePresence(resultSave ? true : curVerifyImage.hasImage());
                lvActions.setAdapter(new VerifyImageListAdapter(this, R.layout.verifyimage_listitem, verifyImageModels));

                // check if all photos have been captures
                boolean allImagesSaved = true;
                for(VerifyImageModel verifyImageModel : verifyImageModels) {
                    allImagesSaved = allImagesSaved && verifyImageModel.hasImage();
                }

                // clean the image folder
                FileManager.cleanImageFolder();

                // show feedback result
                final MainActivity mainActivity = this;
                final boolean imgPhotoSaveResult = resultSave;
                final boolean imgCaptureCompleted = allImagesSaved;
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setCancelable(false);
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setTitle(this.getResources().getString(R.string.save_image_title));
                alertDialog.setMessage(resultSave ?
                        this.getResources().getString(R.string.saved_image_ok)
                        : this.getResources().getString(R.string.saved_image_ko));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                        this.getResources().getString(R.string.ok_text),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                /*
                                 * if all images have been saved, the flow will be the following one:
                                 * - a feedback related to the phase completion will be provided to the user
                                 * - the activity will be closed
                                 * - another photo capture phase could be started relating to another equipment
                                 */
                                if(imgPhotoSaveResult && imgCaptureCompleted) {
                                    AlertDialog resultAlertDialog = new AlertDialog.Builder(mainActivity).create();
                                    resultAlertDialog.setCancelable(false);
                                    resultAlertDialog.setCanceledOnTouchOutside(false);
                                    resultAlertDialog.setTitle(mainActivity.getResources().getString(R.string.photo_capture_completion_title));
                                    resultAlertDialog.setMessage(mainActivity.getResources().getString(R.string.photo_capture_completion_message));
                                    resultAlertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                                            mainActivity.getResources().getString(R.string.ok_text),
                                            new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.dismiss();
                                                            mainActivity.finish();
                                                }
                                            });
                                    resultAlertDialog.show();
                                }
                            }
                        });
                alertDialog.show();
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

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setTitle(this.getResources().getString(R.string.error));
        alertDialog.setMessage(this.getResources().getString(R.string.upload_error));
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
