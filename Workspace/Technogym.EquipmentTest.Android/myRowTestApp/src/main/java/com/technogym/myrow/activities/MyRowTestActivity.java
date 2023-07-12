package com.technogym.myrow.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.technogym.myrow.Configuration;
import com.technogym.myrow.R;
import com.technogym.myrow.bridges.android.IMyRowAndroidBridge;
import com.technogym.myrow.bridges.android.MyRowAndroidBridge;
import com.technogym.myrow.bridges.android.MyRowEquipmentBridge;
import com.technogym.myrow.bridges.web.IMyRowJavascriptBridge;
import com.technogym.myrow.bridges.web.MyRowJavascriptBridge;
import com.technogym.myrow.connection.controllers.IEquipmentController;
import com.technogym.myrow.connection.controllers.MyRowEquipmentController;
import com.technogym.myrow.connection.models.EquipmentConnectionState;
import com.technogym.myrow.managers.MyRowTestActionManager;
import com.technogym.myrow.network.NetworkFileStreamManager;
import com.technogym.myrow.services.TargetServices;
import com.technogym.myrow.tasks.listeners.ITaskCompleteListener;
import com.technogym.myrow.views.TestWebView;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import it.spot.android.logger.domain.ILogger;
import it.spot.android.logger.domain.Logger;
import it.spot.android.logger.file.FileLogProxy;
import it.spot.android.logger.mint.MintLogProxy;

public class MyRowTestActivity extends Activity implements ITaskCompleteListener {

    private static final int REQUEST_PERMISSION_ID = 1;

    protected IEquipmentController equipmentController;

    protected TestWebView mWebView;
    protected IMyRowAndroidBridge mAndroidBridge;
    protected IMyRowJavascriptBridge mJSBridge;
    protected MyRowTestActionManager mActionManager;
    protected String firmwareUpgradeFilePath;

    private Handler mScanHandler;
    private ILogger mLogger;

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
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);

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
                ){

            ActivityCompat.requestPermissions(MyRowTestActivity.this,
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

            // Should we show an explanation?
            /*if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET))
                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION))
                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CHANGE_WIFI_STATE))
                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_WIFI_STATE))
                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WAKE_LOCK))
                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.VIBRATE))
                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.BLUETOOTH))
                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.BLUETOOTH_ADMIN))
                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.BLUETOOTH_PRIVILEGED))
                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MyCyclingTestActivity.this,
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

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }*/
        }

        setContentView(R.layout.activity_my_row_test);
        mLogger = Logger.getInstance();
        mLogger.addLogger(new FileLogProxy());
        mLogger.addLogger(new MintLogProxy(this, "fe19cb48"));
        mLogger.enableLoggers(true);

        initializeFolders();
        initializeWebView();

        mScanHandler = new Handler();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanResult == null) {
                return;
            }
            final String result = scanResult.getContents();
            if (result != null) {
                mScanHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mJSBridge.onDataScanned(result);
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        backupLogFile();

        mAndroidBridge.clearResources();
        equipmentController.disconnect();

        mLogger.enableLoggers(false);
        mLogger.destroy();

        super.onDestroy();
    }


    // { Private methods

    private void backupLogFile() {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Starting Log File Backup");
        NetworkFileStreamManager man = new NetworkFileStreamManager();

        String localFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        String localFileName = "collaudi.txt";

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String serialN = prefs.getString("EQUIPMENT_SERIAL_NUMBER", "test");

        GregorianCalendar date = new GregorianCalendar();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hours = date.get(Calendar.HOUR);
        int minutes = date.get(Calendar.MINUTE);
        int seconds = date.get(Calendar.SECOND);

        String remoteFileName = serialN + "_" + year + month + day + "_" + hours + "-" + minutes + "-" + seconds + ".txt";

        man.setLocalFile(localFolderPath, localFileName, "/");
        man.backupLocalFile(localFolderPath + "/backups", "" + remoteFileName, "/");
    }


    private void initializeFolders() {

        // equipment test app root folder path management
        String localFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File rootDir = new File(localFolderPath + "/" + Configuration.EQUIPTEST_FOLDER_PATH);
        if(!rootDir.exists()) {
            rootDir.mkdir();
        }

        // equipment test app downloads folder path management
        File downloadDir = new File(localFolderPath + "/" + Configuration.EQUIPTEST_FOLDER_PATH +
                "/" + Configuration.EQUIPTEST_DOWNLOAD_FOLDER);
        if(!downloadDir.exists()) {
            downloadDir.mkdir();
        }
    }

    private void initializeWebView() {

        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "initializing WebView");

        mWebView = (TestWebView) findViewById(R.id.webview);

        mJSBridge = MyRowJavascriptBridge.create(mWebView);
        equipmentController = MyRowEquipmentController.create(this, EquipmentConnectionState.STATE_DISCONNECTED);
        equipmentController.registerEquipmentConnectionListener(mJSBridge);
        mActionManager = MyRowTestActionManager.create(this, mJSBridge, equipmentController);

        this.mAndroidBridge = MyRowAndroidBridge.create(this, equipmentController);
        MyRowEquipmentBridge equipmentBridge = MyRowEquipmentBridge.create(this, equipmentController, mActionManager);

        /*Logger.getInstance().logDebug(this.getClass().getSimpleName(), "ActionManager is null: " + (this.mActionManager == null));
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "AndroidBridge is null: " + (this.mAndroidBridge == null));
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "EquipmentBridge is null: " + (equipmentBridge == null));
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "EquipmentBridge exists: " + (equipmentBridge.exists()));
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "EquipmentBridge is MyCycling: " + (equipmentBridge.isMyCycling()));*/

        mWebView.initializeForTest(equipmentBridge, this.mAndroidBridge);
        mWebView.loadUrl(Configuration.EQUIPMENT_TEST_CLIENT_URL);

        //mScanHandler = new Handler();
    }


    // }

    @Override
    public void onTaskComplete(Object data, TargetServices targetService) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void onSuccess(Object data, TargetServices targetService) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void onError(String errorMessage, TargetServices targetService) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
