//package com.technogym.equipmenttest.myrun.activities;
//
//import android.Manifest;
//import android.app.Activity;
//import android.bluetooth.BluetoothDevice;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.util.Log;
//import android.view.Window;
//import android.view.WindowManager;
//
//
//import com.technogym.equipmenttest.myrun.R;
//import com.technogym.equipmenttest.myrun.ble.BleController;
//import com.technogym.equipmenttest.myrun.ble.BleEventsNotifier;
//import com.technogym.equipmenttest.myrun.ble.BleUUIDs;
//import com.technogym.equipmenttest.myrun.ble.IBleEventsListener;
//
//import java.util.UUID;
//
//import it.spot.android.logger.domain.ILogger;
//import it.spot.android.logger.domain.Logger;
//import it.spot.android.logger.file.FileLogProxy;
//import it.spot.android.logger.mint.MintLogProxy;
//
//public class MyRunTestActivity extends Activity implements IBleEventsListener {
//
//    // { Private fields
//
//    private static final int REQUEST_PERMISSION_ID = 1;
//    private static final String TAG = MyRunTestActivity.class.getSimpleName();
//    private ILogger mLogger;
//	private BleController bleController;
//
////    protected IEquipmentController equipmentController;
////
////    protected TestWebView mWebView;
////    protected IMyCyclingAndroidBridge mAndroidBridge;
////    protected IMyCyclingJavascriptBridge mJSBridge;
////    protected MyCyclingTestActionManager mActionManager;
////    protected String firmwareUpgradeFilePath;
////
////    private Handler mScanHandler;
////    private ILogger mLogger;
//
//    // }
//
//    // { Public methods
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_PERMISSION_ID: {
//
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
////					// permission was granted, yay! Do the
////					// contacts-related task you need to do.
//                } else {
////
////					// permission denied, boo! Disable the
////					// functionality that depends on this permission.
////
//                }
//                return;
//            }
//
//            // other 'case' lines to check for other
//            // permissions this app might request
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstance) {
//        super.onCreate(savedInstance);
//
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        getWindow().requestFeature(Window.FEATURE_PROGRESS);
//
//        // Check permissions and grants to obtain
//        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
//                || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//                || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//                || (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
//                || (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
//                || (ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED)
//                || (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
//                || (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED)
//                || (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)
//                || (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED)
//                || (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_PRIVILEGED) != PackageManager.PERMISSION_GRANTED)
//                || (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//                || (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//        ) {
//
//            ActivityCompat.requestPermissions(MyRunTestActivity.this,
//                    new String[]{
//                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.CHANGE_WIFI_STATE,
//                            Manifest.permission.ACCESS_WIFI_STATE,
//                            Manifest.permission.WAKE_LOCK,
//                            Manifest.permission.CAMERA,
//                            Manifest.permission.VIBRATE,
//                            Manifest.permission.INTERNET,
//                            Manifest.permission.BLUETOOTH,
//                            Manifest.permission.BLUETOOTH_ADMIN,
//                            Manifest.permission.BLUETOOTH_PRIVILEGED,
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    },
//                    REQUEST_PERMISSION_ID);
//
//            // Should we show an explanation?
//            /*if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET))
//                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION))
//                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
//                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CHANGE_WIFI_STATE))
//                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_WIFI_STATE))
//                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WAKE_LOCK))
//                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA))
//                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.VIBRATE))
//                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.BLUETOOTH))
//                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.BLUETOOTH_ADMIN))
//                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.BLUETOOTH_PRIVILEGED))
//                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
//                    || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {
//
//                // No explanation needed, we can request the permission.
//
//                ActivityCompat.requestPermissions(MyCyclingTestActivity.this,
//                        new String[]{
//                                Manifest.permission.ACCESS_COARSE_LOCATION,
//                                Manifest.permission.ACCESS_FINE_LOCATION,
//                                Manifest.permission.CHANGE_WIFI_STATE,
//                                Manifest.permission.ACCESS_WIFI_STATE,
//                                Manifest.permission.WAKE_LOCK,
//                                Manifest.permission.CAMERA,
//                                Manifest.permission.VIBRATE,
//                                Manifest.permission.INTERNET,
//                                Manifest.permission.BLUETOOTH,
//                                Manifest.permission.BLUETOOTH_ADMIN,
//                                Manifest.permission.BLUETOOTH_PRIVILEGED,
//                                Manifest.permission.READ_EXTERNAL_STORAGE,
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE
//                        },
//                        REQUEST_PERMISSION_ID);
//
//                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                // app-defined int constant. The callback method gets the
//                // result of the request.
//            }*/
//        }
//
//        setContentView(R.layout.activity_web);
//        mLogger = Logger.getInstance();
//        mLogger.addLogger(new FileLogProxy());
//        mLogger.addLogger(new MintLogProxy(this, "fe19cb48"));
//        mLogger.enableLoggers(true);
//
//        //initializeFolders();
//        //initializeWebView();
//
//        //mScanHandler = new Handler();
//
//        // SETUP
//        BleEventsNotifier notifier = new BleEventsNotifier();
//        notifier.addListener(this);
//        bleController = new BleController(this, notifier);
//		bleController.scanDevices();
//
//    }
//
//    @Override
//    public void onScanStarted() {
//        Log.e(TAG, "onScanStarted");
//    }
//
//    @Override
//    public void onScanEnded() {
//        Log.e(TAG, "onScanEnded");
//    }
//
//    @Override
//    public void onDeviceFound(BluetoothDevice device) {
//        Log.e(TAG, "onDeviceFound " + device.getName() + " " + device.getAddress());
//		if (device.getName() != null && device.getName().contains("MyRun")) {
//			bleController.connect(device);
//		}
//    }
//
//	@Override
//	public void onDeviceConnected(BluetoothDevice device) {
//		Log.e(TAG, "onDeviceConnected " + device.getName() + " " + device.getAddress());
//		bleController.readCharacteristic(BleUUIDs.UUID_SERIAL_NUMBER_CHAR);
//	}
//
//    @Override
//    public void onDisconnected(String reason) {
//        Log.e(TAG, "onDisconnected - reason: " + reason);
//    }
//
//    @Override
//    public void onReadFailed(String reason) {
//        Log.e(TAG, "onReadFailed - reason: " + reason);
//    }
//
//    @Override
//    public void onWriteFailed() {
//
//    }
//
//    @Override
//    public void onCharacteristicRead(UUID uuid, String value) {
//        Log.e(TAG, "onCharacteristicRead - uuid = "+uuid+ " value: "+ value);
//        bleController.writeUartCommand("tag1", "@FWVER#");
//    }
//
//    @Override
//    public void onWriteCompleted(String tag, String response) {
//        Log.e(TAG, "onWriteCompleted - tag = "+tag+ " response: "+ response);
//        bleController.disconnect();
//    }
//
//    @Override
//	public void onDestroy() {
//    	super.onDestroy();
//		bleController.disconnect();
//	}
//}


package com.technogym.equipmenttest.appbleuart.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.core.listeners.ICoreListener;
import com.technogym.android.myrun.sdk.core.proxies.CoreProxy;
import com.technogym.android.myrun.sdk.core.proxies.ICoreProxy;
import com.technogym.android.myrun.sdk.status.proxies.IStatusProxy;
import com.technogym.android.myrun.sdk.status.proxies.StatusProxy;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;
import com.technogym.android.myrun.sdk.workout.proxies.IWorkoutProxy;
import com.technogym.equipmenttest.library.views.TestWebView;
import com.technogym.equipmenttest.appbleuart.Configuration;
import com.technogym.equipmenttest.appbleuart.R;
import com.technogym.equipmenttest.appbleuart.adapters.BluetoothScanAdapter;
import com.technogym.equipmenttest.appbleuart.bridges.android.IAppBleUartAndroidBridge;
import com.technogym.equipmenttest.appbleuart.bridges.android.AppBleUartAndroidBridge;
import com.technogym.equipmenttest.appbleuart.bridges.android.AppBleUartEquipmentBridge;
import com.technogym.equipmenttest.appbleuart.bridges.web.IAppBleUartJavascriptBridge;
import com.technogym.equipmenttest.appbleuart.bridges.web.AppBleUartJavascriptBridge;
import com.technogym.equipmenttest.appbleuart.manager.NetworkFileStreamManager;
import com.technogym.equipmenttest.appbleuart.manager.BleUartActionManager;
import com.technogym.equipmenttest.appbleuart.proxies.SystemProxy;
import com.technogym.equipmenttest.appbleuart.proxies.WorkoutProxy;
import com.technogym.sdk.fitnessmachineservice.model.FitnessBtDevice;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.spot.android.logger.domain.ILogger;
import it.spot.android.logger.domain.Logger;
import it.spot.android.logger.domain.models.LoggerTypes;
import it.spot.android.logger.domain.proxies.ILogProxy;
import it.spot.android.logger.file.FileLogProxy;
import it.spot.android.logger.mint.MintLogProxy;


public class MainActivity extends BleEquipmentActivity implements IWorkoutListener, ICoreListener, ISystemListener {

	private static final int REQUEST_PERMISSION_ID = 1;
	protected static final String LOGTAG = MainActivity.class.getSimpleName();

	private Dialog mScanDialog;
	private ProgressDialog mProgressDialog;
	private ArrayAdapter<BluetoothDevice> mScanAdapter;

	private ICoreProxy mCoreProxy;
	private ISystemProxy mSystemProxy;
	private IStatusProxy mStatusProxy;
	private IWorkoutProxy mWorkoutProxy;

	private boolean mInitializing;
	private boolean mRestartingScan;
	private boolean mRebooted;

	protected TestWebView mWebView;
	protected IAppBleUartAndroidBridge mAndroidBridge;
	protected IAppBleUartJavascriptBridge mJSBridge;
	protected BleUartActionManager mActionManager;

	private Handler mScanHandler;

	private ILogger mLogger;

	private FitnessBtDevice connectedDevice;

	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);


		String text = "SpeedSet: 20.0, Gradient: 12.0\n" +
				"MeasSpeed: 20.0, LastADCMeasGradient: 00.0\n" +
				"ElevON: 0, MotON:1\n" +
				"Active: 1, Distance: 00255\n" +
				"Seconds: 078\n" +
				"Kcal: 24\n" +
				"ElevGain: 22\n" +
				"Status: QSRunning\n" +
				"SensDOWN:0, SensUP:0\n" +
				"SPM:000, Info:000";
		Pattern pattern = Pattern.compile("LastADCMeasGradient: (-?\\d*\\.?\\d*)");
		Matcher matcher = pattern.matcher(text);
		while(matcher.find()) {
			System.out.println(matcher.group(1));
		}

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		getWindow().requestFeature(Window.FEATURE_PROGRESS);

		setContentView(R.layout.activity_web);

		mScanDialog = null;
		mProgressDialog = null;

		mInitializing = true;
		mRestartingScan = false;
		mRebooted = false;

		mLogger = Logger.getInstance();
		mLogger.addLogger(new ILogProxy() {
			@Override
			public LoggerTypes getLoggerType() {
				return LoggerTypes.ANALYTICS;
			}

			@Override
			public void setEnabled(boolean b) {

			}

			@Override
			public void logVerbose(String s, String s1) {
				Log.i(s, s1);
			}

			@Override
			public void logDebug(String s, String s1) {
				Log.i(s, s1);
			}

			@Override
			public void logWarning(String s, String s1) {
				Log.i(s, s1);
			}

			@Override
			public void logError(String s, String s1) {
				Log.i(s, s1);
			}
		});
		mLogger.addLogger(new FileLogProxy());
		mLogger.addLogger(new MintLogProxy(this, "fe19cb48"));
		mLogger.enableLoggers(true);

		initializeEquipmentProxies();

/*
		Log.i("MyRunTestActivity", "WRITE_EXTERNAL_STORAGE: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED));
		Log.i("MyRunTestActivity", "READ_CONTACTS: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED));
		Log.i("MyRunTestActivity", "INTERNET: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED));
		Log.i("MyRunTestActivity", "CHANGE_WIFI_STATE: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED));
		Log.i("MyRunTestActivity", "ACCESS_WIFI_STATE: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED));
		Log.i("MyRunTestActivity", "VIBRATE: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED));
		Log.i("MyRunTestActivity", "CAMERA: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED));
		Log.i("MyRunTestActivity", "WAKE_LOCK: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED));
		Log.i("MyRunTestActivity", "BLUETOOTH_ADMIN: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED));
		Log.i("MyRunTestActivity", "BLUETOOTH: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED));
		Log.i("MyRunTestActivity", "ACCESS_NETWORK_STATE: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED));
		Log.i("MyRunTestActivity", "ACCESS_COARSE_LOCATION: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED));
		Log.i("MyRunTestActivity", "ACCESS_FINE_LOCATION: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED));
		Log.i("MyRunTestActivity", "READ_EXTERNAL_STORAGE: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED));
		Log.i("MyRunTestActivity", "BLUETOOTH_PRIVILEGED: " + (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_PRIVILEGED) != PackageManager.PERMISSION_GRANTED));
*/

		// require permissions
		if((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_PRIVILEGED) != PackageManager.PERMISSION_GRANTED)
				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
				//|| ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_HISTORY_BOOKMARKS) != PackageManager.PERMISSION_GRANTED)
				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED)
				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED)
				//|| ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.FLASHLIGHT) != PackageManager.PERMISSION_GRANTED)
				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED)
				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)
				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED)
				|| (ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED)) {
			ActivityCompat.requestPermissions(MainActivity.this,
					new String[]{
							Manifest.permission.READ_CONTACTS,
							//Manifest.permission.READ_HISTORY_BOOKMARKS,
							Manifest.permission.CHANGE_WIFI_STATE,
							Manifest.permission.ACCESS_NETWORK_STATE,
							Manifest.permission.BLUETOOTH,
							Manifest.permission.BLUETOOTH_ADMIN,
							Manifest.permission.INTERNET,
							Manifest.permission.CAMERA,
							Manifest.permission.WRITE_EXTERNAL_STORAGE,
							Manifest.permission.VIBRATE,
							Manifest.permission.ACCESS_COARSE_LOCATION,
							Manifest.permission.ACCESS_FINE_LOCATION,
							//Manifest.permission.FLASHLIGHT,
							Manifest.permission.READ_EXTERNAL_STORAGE,
							Manifest.permission.BLUETOOTH_PRIVILEGED,
							Manifest.permission.WAKE_LOCK
					},
					REQUEST_PERMISSION_ID);
		}

		getConnectionController().setCurrentActivity(this);


		// TODO Rimuovere
		//AndroidBridge.create(this).openDocument("PDF", "http://192.168.241.54/Technogym.EquipmentTest.Web.Admin/upload/actions/C0000020/DFK/Help_it.pdf");
	}

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

		// -----------------------------------------------------------------------------------------------------------------------
		// In ambiente di test viene effettuato il backup nell'apposita cartella locale quando viene chiusa l'app
		// Questo avviene per motivi di praticità per quanto riguarda le sessioni di test dell'applicazione
		// In questo caso il nome del file di backup corrisponde al formato: [SERIALE]_[YYYYMMDD]_[hh]-[mm]-[ss].txt
		// -----------------------------------------------------------------------------------------------------------------------
		if(!Configuration.IS_PRODUCTION) {
			Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[NOT PRODUCTION BACKUP] - Starting Log File Backup");
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
    	// -----------------------------------------------------------------------------------------------------------------------

		if (mActionManager != null) {
			mActionManager.killTest();
		}

		if (this.mAndroidBridge != null) {
			this.mAndroidBridge.clearResources();
		}
		mLogger.enableLoggers(false);
		mLogger.destroy();

		//getConnectionController().disconnect();

		// ToDo-Low: gestire l'unpair della macchina che si è appena collaudata, non di tutti i dispositivi bluetooth connessi al tablet
		//getConnectionController().disconnectFromDevice(getConnectionController().getConnectedDevice());
		getConnectionController().disconnect();
		super.onDestroy();
	}

	@Override
	public void onConnectionEstablished() {
		mLogger.logDebug(MainActivity.LOGTAG, "[onConnectionEstablished] calling onBluetoothConnectionStateChanged");
		
		if (mJSBridge != null) {
			mJSBridge.onBluetoothConnectionStateChanged();
		}

		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}

		// -----------------------------------------------------------------------------------------------------------------------
		// ENTER REMOTE MODE AND SET WORKOUT LISTENER
		// -----------------------------------------------------------------------------------------------------------------------
//		try {
//			mCoreProxy.enterRemoteMode();
//			mCoreProxy.disableKeepAlive();
//
//			Logger.getInstance().logDebug(
//				getClass().getSimpleName(), "Enter remote mode init equipment proxies");
//			onEnterRemoteModeResult(Boolean.TRUE);
//		} catch (WriteNotAllowedException e) {
//			e.printStackTrace();
//
//			Logger.getInstance().logDebug(
//				getClass().getSimpleName(), "Enter remote mode init equipment proxies --> FAILED");
//		}

//		Logger.getInstance().logDebug(getClass().getSimpleName(), "MAIN ACTIVITY - PREPARE FAKE WORKOUT");
//		try {
//		    int defaultSpeed = 8;
//		    int defaultGradient = 0;
//			mWorkoutProxy.registerForNotification(this);
//
//			mWorkoutProxy.startWorkout(defaultSpeed, defaultGradient);
//			Logger.getInstance().logDebug(getClass().getSimpleName(), "MAIN ACTIVITY - START FAKE WORKOUT");
//
//			//mWorkoutProxy.stopWorkout();
//			Logger.getInstance().logDebug(getClass().getSimpleName(), "MAIN ACTIVITY - STOP FAKE WORKOUT");
//
//		} catch (WriteNotAllowedException e) {
//			e.printStackTrace();
//		}

		try {
			//getConnectionController().sendMessage("@SDISP_FULL_ON#");
			//getConnectionController().sendMessage("@SDISP_NORMAL#");
			//getConnectionController().sendMessage("@STG_ID DCKN2B18000997#");
			//getConnectionController().sendMessage("@FWVER#");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// -----------------------------------------------------------------------------------------------------------------------

		if (mInitializing) {
			mInitializing = false;
			mLogger.logDebug(MainActivity.LOGTAG, "[onConnectionEstablished] calling initializeWebView()");
			initializeWebView();
		}else{
			if(!mRebooted){
				mRebooted = true;
				mLogger.logDebug(MainActivity.LOGTAG, "[onConnectionEstablished] calling reboot callback");
				mJSBridge.onBleUartEquipmentRebooted(true);

			}
		}
	}

	@Override
	public void onConnectionFailed() {
		mLogger.logDebug(MainActivity.LOGTAG, "[onConnectionFailed] calling onBluetoothConnectionStateChanged");

		if (mJSBridge != null) {
			mJSBridge.onBluetoothConnectionStateChanged();
		}

		mLogger.logDebug(MainActivity.LOGTAG, getString(R.string.bluetooth_connection_failed));
		Toast.makeText(this, R.string.bluetooth_connection_failed, Toast.LENGTH_LONG).show();
		finish();
	}

	@Override
	public void onConnectionInterrupted() {
		mLogger.logDebug(MainActivity.LOGTAG, "[onConnectionInterrupted] calling onBluetoothConnectionStateChanged");

		if (mJSBridge != null) {
			mJSBridge.onBluetoothConnectionStateChanged();
		}

		mLogger.logError(MainActivity.LOGTAG, getString(R.string.bluetooth_connection_interrupted));

		super.onConnectionInterrupted();

		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}

	@Override
	public void onConnectionTerminated() {
		mLogger.logDebug(MainActivity.LOGTAG, "[onConnectionTerminated] calling onBluetoothConnectionStateChanged");

		if (mJSBridge != null) {
			mJSBridge.onBluetoothConnectionStateChanged();
		}

		mLogger.logDebug(MainActivity.LOGTAG, getString(R.string.bluetooth_connection_terminated));

		super.onConnectionTerminated();
	}

	// { Private methods

	private void initializeEquipmentProxies() {
		mCoreProxy = CoreProxy.getInstance(null);
		mCoreProxy.deregisterForNotification(this);

		mStatusProxy = StatusProxy.getInstance(null);
		mStatusProxy.deregisterForNotification(this);

		mSystemProxy = SystemProxy.getInstance(getConnectionControllerFromApplication());
		mSystemProxy.initializeNotificationRules();

		mWorkoutProxy = WorkoutProxy.getInstance(getConnectionControllerFromApplication());
		mWorkoutProxy.initializeNotificationRules();
	}

	private void initializeWebView() {
		this.runOnUiThread(new Runnable() {
			public void run() {
				mWebView = (TestWebView) findViewById(R.id.webview);

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

					WebView.setWebContentsDebuggingEnabled(true);
				}

				mJSBridge = AppBleUartJavascriptBridge.create(mWebView);
				mActionManager = BleUartActionManager.create(MainActivity.this, mJSBridge);

				mAndroidBridge = AppBleUartAndroidBridge.create(MainActivity.this);

				mWebView.initializeForTest(
						AppBleUartEquipmentBridge.create(MainActivity.this, mActionManager, getConnectionController(), MainActivity.this),
						mAndroidBridge);
				mWebView.loadUrl(Configuration.EQUIPMENT_TEST_CLIENT_URL);

				mScanHandler = new Handler();
			}
		});
	}

	// }

	// { BluetoothEquipmentActivity abstract methods implementation

	@Override
	public void onBluetoothDeviceFound(final BluetoothDevice device) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Logger.getInstance().logDebug("PAIR_ACTIVITY", "["+MainActivity.LOGTAG+"] Bluetooth device found: "+device.toString());
				mScanAdapter.add(device);
			}
		});
	}


	@Override
	public void onLowKitUpgraded(final boolean result){
		mRebooted = false;
		mJSBridge.onBleUartLowKitUpgraded(result);
	}

	@Override
	public void onHighKitUpgraded(final boolean result) {
		mRebooted = false;
		mJSBridge.onBleUartHighKitUpgraded(result);
	}


	@SuppressLint("InflateParams")
	@Override
	public void onBluetoothScanStarted(final boolean isReboot) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Logger.getInstance().logDebug(MainActivity.LOGTAG, "Bluetooth scan started");

				if(!isReboot){
					mScanAdapter = new BluetoothScanAdapter(MainActivity.this, android.R.layout.select_dialog_item);

					AlertDialog.Builder builderSingle = new AlertDialog.Builder(MainActivity.this);

					builderSingle.setIcon(R.drawable.ico_bluetooth).setTitle(R.string.choose_device).setCancelable(false)
							.setCustomTitle(getLayoutInflater().inflate(R.layout.dialog_title, null))
							.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog, int which) {
									getConnectionController().stopScanningDevices();
									dialog.dismiss();
								}
							}).setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							mRestartingScan = true;
							getConnectionController().setup(false);
						}
					}).setAdapter(mScanAdapter, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();

							Logger.getInstance().logDebug(MainActivity.LOGTAG, "CHOOSEN DEVICE " + mScanAdapter.getItem(which).getName());

							mProgressDialog = new ProgressDialog(MainActivity.this);
							mProgressDialog.setTitle(R.string.connecting);
							mProgressDialog.setCancelable(false);
							mProgressDialog.setMessage(getString(R.string.connecting_to_equipment));
							mProgressDialog.show();

							BluetoothDevice device = mScanAdapter.getItem(which);
							//getConnectionController().stopScanningDevices();
							getConnectionController().connect(device);
							connectedDevice = new FitnessBtDevice(device);
						}
					});

					Logger.getInstance().logDebug(
							MainActivity.LOGTAG, "SHOWING MODAL with bt devices list");
					mScanDialog = builderSingle.show();
				}else{
					//reconnect after reboot
					Logger.getInstance().logDebug(
							MainActivity.LOGTAG, "RECONNECT AFTER REBOOT to "+connectedDevice.toString());

					getConnectionController().connect(connectedDevice.getDevice());
				}
			}
		});

	}

	@Override
	public void onBluetoothScanTerminated() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Logger.getInstance().logDebug(MainActivity.LOGTAG, "Bluetooth scan terminated");

				if (mScanDialog != null) {
					mScanDialog.findViewById(R.id.dialog_title_progress).setVisibility(View.GONE);
					if (mRestartingScan) {
						mScanDialog.dismiss();
						mRestartingScan = false;
						getConnectionController().scanDevices();
					}
				}

			}
		});

	}

	// }

	// { ISystem Listener

	@Override
	public void onValueFromSensorUp(final Integer value) {
		// INF: Empty
	}

	@Override
	public void onValueFromSensorDown(final Integer value) {
		// INF: Empty
	}

	@Override
	public void onSerialNumberRetrieved(final String serialNumber) {

		Logger.getInstance().logDebug(MainActivity.LOGTAG, "ON SERIAL NUMBER RETRIEVED " + serialNumber);

		mJSBridge.onEquipmentSerialNumberRetrieved(serialNumber);
		mSystemProxy.deregisterForNotification(this);
	}

	@Override
	public void onSerialNumberSet(final Boolean result) {

		Logger.getInstance().logDebug(MainActivity.LOGTAG, "ON SERIAL NUMBER SET " + result.toString());

		mJSBridge.onEquipmentSerialNumberSet(result);
		mSystemProxy.deregisterForNotification(this);
	}

	@Override
	public void onLKLifeDataResetted(String status) {

	}

	@Override
	public void onLKErrorLogResetted(String status) {

	}

	@Override
	public void onLifeTimeResetted(String status) {

	}

	@Override
	public void onBluetoothRetrieved(String status) {

	}

	@Override
	public void onNFCRetrieved(String status) {

	}

	@Override
	public void onDisableGymkitRetrieved(String status) {

	}

	@Override
	public void onFirmwareBootloaderVersionRetrieved(String bootloaderVersion) {
		Logger.getInstance().logDebug(MainActivity.LOGTAG, "ON FIRMWARE BOOTLOADER VERSION RETRIEVED " + bootloaderVersion);

		mJSBridge.onFirmwareBootloaderVersionRetrieved(bootloaderVersion);
		mSystemProxy.deregisterForNotification(this);
	}

	@Override
	public void onFirmwareVersionRetrieved(final String firmwareVersion) {

		Logger.getInstance().logDebug(MainActivity.LOGTAG, "ON FIRMWARE VERSION RETRIEVED " + firmwareVersion);

		mJSBridge.onEquipmentFirmwareVersionRetrieved(firmwareVersion);
		mSystemProxy.deregisterForNotification(this);
	}

	@Override
	public void onWakeupSensorRetrieved(Integer wakeupSensorValue) {
		// INF: Empty
	}

	@Override
	public void onStatusRetreived(String status) {
		// INF: Empty
	}

	@Override
	public void onMeasureUnitSet(Boolean result) {
		// INF: Empty
	}

	@Override
	public void onMeasureUnitRetreived(String measureUnit) {
		// INF: Empty
	}

	@Override
	public void onSetEEPROMDefaultValuesSet(String result) {
		// INF: Empty
	}

	@Override
	public void onResetErrorLog(String result) {
		// INF: Empty
	}

	@Override
	public void onErrorLogRetreived(String errorLogs) {
		// INF: Empty
	}

	@Override
	public void onCalibrationGradientValueMVRetreived(String result) {
		// INF: Empty

	}

	@Override
	public void onCalibrationSpeedValueRatioPWMRetreived(String result) {
		// INF: Empty

	}

	@Override
	public void onUpSensorStatusRetreived(String status) {
		// INF: Empty

	}

	@Override
	public void onDownSensorStatusRetreived(String status) {
		// INF: Empty

	}

	@Override
	public void onSensoreInduttivoRetrieved(String status) {

	}

	@Override
	public void onStartCalibrationResponse(String mStatus) {

	}

	@Override
	public void onBtVersionRetrieved(String version) {

	}

	@Override
	public void onMainboardBtVersionRetrieved(String version) {

	}

	@Override
	public void onWIDVersionRetrieved(String version) {
		Logger.getInstance().logDebug(MainActivity.LOGTAG, "onWIDVersionRetrieved : " + version);

		mJSBridge.onWIDVersionRetrieved(version);
		mSystemProxy.deregisterForNotification(this);
	}

	@Override
		public void onCalibrationGradientValueMVSet(String result) {
			// INF: Empty

		}

	// }


	// { IWorkoutListener

	@Override
	public void onWorkoutCompleted() {
		// INF: Empty

	}

	@Override
	public void onStartStopButtonPressed() {

		/*
		 * Entro sempre in remote mode quando viene premuto il joystick
		 */
		try {
			Logger.getInstance().logDebug(MainActivity.LOGTAG,
					"ON START/STOP BUTTON - Entering Remote Mode");
			mCoreProxy.enterRemoteMode();

		} catch (WriteNotAllowedException e) {
			Logger.getInstance().logDebug(MainActivity.LOGTAG,
					"ON START/STOP BUTTON - Entering Remote Mode Error: " + e.getMessage());
		}

	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    String fwVersion = prefs.getString("EQUIPMENT_FIRMWARE_VERSION", "01.64");
	    String[] fwVersionFull = fwVersion.split("\\.");

		Logger.getInstance().logDebug(MainActivity.LOGTAG,
				"ON START/STOP BUTTON - FIRMWARE VERSION: "
						+ fwVersion + " - " + fwVersionFull[fwVersionFull.length - 1]);

		/*
		 * Versione MyRun e MyRun RC:
		 *
		 * Es. 01.70 -> Major = 01   Minor = 70
		 *
		 * - Major = 01 -> MyRun
		 * - Major = 02 -> MyRun RC
		 *
		 * In questo caso la velocità di default va messa a:
		 * - 0.8 -> se MyRun classico e versione Minor <= 64
		 * - 1.2 -> se MyRun classico e versione Minor > 64 oppure se MyRun RC
		 */
		boolean isMyRunRC = fwVersionFull[0].trim().equals("02");
	    int v = Integer.parseInt(fwVersionFull[fwVersionFull.length - 1]);
	    int defaultSpeed = (!isMyRunRC && v < 64) ? 8 : 12;
	    int defaultGradient = 0;

		try {
			mWorkoutProxy.setJoystickMode(true);
			mWorkoutProxy.startWorkout(defaultSpeed, defaultGradient);
		} catch (WriteNotAllowedException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void onButtonPress() {

	}

	@Override
	public void onButtonRelease() {

	}

	@Override
	public void onSpeedValueRetrieved(Integer speed) {
		// INF: Empty

	}

	@Override
	public void onJoystickSpeedUpReleased() {
		// INF: Empty

	}

	@Override
	public void onJoystickSpeedUpPressed() {
		// INF: Empty

	}

	@Override
	public void onJoystickSpeedDownReleased() {
		// INF: Empty

	}

	@Override
	public void onJoystickSpeedDownPressed() {
		// INF: Empty

	}

	@Override
	public void onJoystickGradientUpReleased() {
		// INF: Empty

	}

	@Override
	public void onJoystickGradientUpPressed() {
		// INF: Empty

	}

	@Override
	public void onJoystickGradientDownReleased() {
		// INF: Empty

	}

	@Override
	public void onJoystickGradientDownPressed() {
		// INF: Empty

	}

	@Override
	public void onHeartRateValueRetrieved(Integer heartRate) {
		// INF: Empty

	}

	@Override
	public void onGradientValueRetrieved(Integer gradient) {
		// INF: Empty

	}

	@Override
	public void onElapsedTimeValueRetrieved(Integer elapsedTime) {
		// INF: Empty

	}

	@Override
	public void onDistanceValueRetrieved(Integer distance) {
		// INF: Empty

	}

	@Override
	public void onCountDownPaused(Integer countDown) {
		// INF: Empty

	}

	// }

	// { ICoreListener implementation

	@Override
	public void onEnterRemoteModeResult(Boolean success) {
		Logger.getInstance().logDebug(
				MainActivity.LOGTAG, "onEnterRemoteModeResult : " + success);
	}

	@Override
	public void onExitRemoteModeResult(final Boolean success) {
		// INF: Empty
	}

	@Override
	public void onKeepAliveExpired() {
		// INF: Empty
	}


	@Override
	public void onLowKitVersionRetrieved(String version) {
		Logger.getInstance().logDebug(MainActivity.LOGTAG, "onLowKitVersionRetrieved : " + version);

		mJSBridge.onLowKitVersionRetrieved(version);
		mSystemProxy.deregisterForNotification(this);
	}

	@Override
	public void onHighKitVersionRetrieved(String version) {
		Logger.getInstance().logDebug(MainActivity.LOGTAG, "onHighKitVersionRetrieved : " + version);

		mJSBridge.onHighKitVersionRetrieved(version);
		mSystemProxy.deregisterForNotification(this);
	}

	@Override
	public void onSuspendCommandResponseRetrieved(String mStatus) {

	}

	@Override
	public void onTestWakeUpResponseRetrieved(String mStatus) {

	}

	@Override
	public void onCalibrationGradientValueCSet(String mResult) {

	}

	@Override
	public void onButtonStatusResponseRetrieved(String mStatus) {

	}

	@Override
	public void onJoystickStatusRetrieved(String mStatus) {

	}

	@Override
	public void onBuzzResponse(String response) {

	}

	// region Live Essentials

	@Override
	public void onMachineTypeRetrieved(String machineType) {
		Logger.getInstance().logDebug(MainActivity.LOGTAG, "onMachineTypeRetrieved : " + machineType);

		mJSBridge.onMachineTypeRetrieved(machineType);
		mSystemProxy.deregisterForNotification(this);
	}

	@Override
	public void onPowerTypeRetrieved(String powerType) {
		Logger.getInstance().logDebug(MainActivity.LOGTAG, "onPowerTypeRetrieved : " + powerType);

		mJSBridge.onPowerTypeRetrieved(powerType);
		mSystemProxy.deregisterForNotification(this);
	}

	@Override
	public void onKeyButtonResponse(String response) {

	}

	@Override
	public void onTextXScreenResponse(String response) {

	}

	@Override
	public void onTestScreenOffResponse(String response) {

	}

	@Override
	public void onGetCardioricevitoreFwResponse(String response) {

	}

	@Override
	public void onReadWifiConfigResponse(String response) {

	}

	@Override
	public void onGetDateResponse(String response) {

	}

	@Override
	public void onGetEmergencyResponse(String response) {

	}

	@Override
	public void onGetCardioHRValueResponse(String response) {

	}

	@Override
	public void onStartCardioReceiverResponse(String response) {

	}

	@Override
	public void onStopCardioReceiverResponse(String response) {

	}

	@Override
	public void onGetSupercapStatusResponse(String response) {

	}

	@Override
	public void onSetSpeedResponse(String response) {

	}

	@Override
	public void onSetGradientResponse(String response) {

	}

	@Override
	public void onKeyboardStatusResponse(String response) {

	}

	@Override
	public void onReadInclinationTableCRCResponse(String response) {

	}

	// endregion

	// }

}
