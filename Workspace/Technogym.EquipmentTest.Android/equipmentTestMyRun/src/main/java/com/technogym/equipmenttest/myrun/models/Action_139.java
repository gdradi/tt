package com.technogym.equipmenttest.myrun.models;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.core.listeners.ICoreListener;
import com.technogym.android.myrun.sdk.core.proxies.CoreProxy;
import com.technogym.android.myrun.sdk.core.proxies.ICoreProxy;
import com.technogym.android.myrun.sdk.core.routines.KeepAliveRoutine;
import com.technogym.android.myrun.sdk.status.listeners.IStatusListener;
import com.technogym.android.myrun.sdk.support.DecimalsFormatHelper;
import com.technogym.android.myrun.sdk.system.commands.GetCalibrationGradientValueMVCommand;
import com.technogym.android.myrun.sdk.system.commands.GetCalibrationSpeedValueRatioPWMCommand;
import com.technogym.android.myrun.sdk.system.commands.GetDownSensorStatusCommand;
import com.technogym.android.myrun.sdk.system.commands.GetUpSensorStatusCommand;
import com.technogym.android.myrun.sdk.system.commands.myrun2020.MyRunSensoreInduttivoCommand;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.myrun.R;
import com.technogym.equipmenttest.myrun.proxies.SystemProxy;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;
import com.technogym.android.myrun.sdk.workout.proxies.IWorkoutProxy;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.myrun.proxies.WorkoutProxy;

import org.json.JSONArray;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.spot.android.logger.domain.Logger;

/**
 * This class makes the equipment perform a specific sequence of actions:
 * <ul>
 * <li>starts the equipment at full speed and full gradient</li>
 * <li>waits 50 seconds</li>
 * <li>sets speed at 1 km/h and gradient to 0%</li>
 * <li>waits for other 30 seconds</li>
 * <li>stops the equipment</li>
 * </ul>
 *
 * So, this class makes the equipment entering remote mode: if this command returns a positive result, than the test can
 * be started properly. The {@link KeepAliveRoutine} should be automatically managed by the main {@link Activity},
 * because it's registered as an {@link ICoreListener} and an {@link IStatusListener} by default.<br/>
 * <br/>
 * It's an automatic action.
 *
 * @author Federico Foschini
 * @version 2.0
 * */
public class Action_139 extends BaseMyRunAction implements ICoreListener, IWorkoutListener {

	private static final int REACH_MAX_VALUES_MILLIS = 50000;
	//private static final int REACH_MAX_VALUES_MILLIS = 5000;
	private static final int REACH_MIN_VALUES_MILLIS = 35000;
	//private static final int REACH_MIN_VALUES_MILLIS = 10000;

	private static final int UPDATE_COUNTDOWN_TIME_MILLIS = 1000;
	private static final int START_COUNTDOWN_DATA_TIME_MILLIS = 1000;

	private static final int UPDATE_DATA_TIME_MILLIS = 5000;
	private static final int START_UPDATE_DATA_TIME_MILLIS = 1000;

	private static final Pattern speedPattern = Pattern.compile("MeasSpeed: (\\d*\\.?\\d*)");
	private static final Pattern gradientPattern = Pattern.compile("LastADCMeasGradient: (-?\\d*\\.?\\d*)");

	//private static final int DEFAULT_WORKOUT_SPEED = 8;
	//private static final int DEFAULT_WORKOUT_GRADIENT = 0;

	/*
	 * ----------------------------------------------------------------------------------------------------------------
	 * Test PASSED if both of following conditions are sadisfacted
	 * -> Speed @20 kmh : value between 19.5 e 20.5 kmh (inclose)
	 * -> Gradient @12% : value between 11.6 e 12.4 % (inclose)
	 * -> Gradient @0%  : value between -0.2 e +0.2 % (inclose)
	 * If one of thees or both fail the application must be alert the user about which of them failed
	 * ----------------------------------------------------------------------------------------------------------------
	 */

	//private static final int MAX_GRADIENT_UPPER_BOUND = 124;
	//private static final int MAX_GRADIENT_UPPER_BOUND = 124;
	//private static final int MAX_GRADIENT_LOWER_BOUND = 116;
	//private static final int MAX_GRADIENT_LOWER_BOUND = 0;

	//private static final int MIN_GRADIENT_UPPER_BOUND = 2;
	//private static final int MIN_GRADIENT_LOWER_BOUND = -2;

	//private static final int MAX_SPEED_UPPER_BOUND = 205;
	//private static final int MAX_SPEED_UPPER_BOUND = 205;
	//private static final int MAX_SPEED_LOWER_BOUND = 195;
	//private static final int MAX_SPEED_LOWER_BOUND = 0;

	private final ICoreProxy mCoreProxy;
	private final IWorkoutProxy mWorkoutProxy;
	private final ISystemProxy mSystemProxy;

	private final ActionModel mModel;

	private Timer mTimer;
	private TimerTask mUpdateTimerTask;

	/*
	 * Task to get the current values of speed and gradient
	 */
	private TimerTask mUpdateCurrentValuesTask;

	private Handler mHandler;

	private int mMaxSpeed;
	private int mMaxSpeedLower;
	private int mMaxSpeedUpper;

	private int mMaxGradient;
	private int mMaxGradientUpper;
	private int mMaxGradientLower;


	private boolean mIsStopping;

	// { Construction

	protected Action_139(final Context context) {
		super(context);

		mMaxSpeed = 0;
		mMaxGradient = 0;

		mIsStopping = false;

		mHandler = new Handler();

		mModel = new ActionModel();

		mTimer = null;
		mUpdateTimerTask = null;

		mCoreProxy = CoreProxy.getInstance(null);
		mWorkoutProxy = WorkoutProxy.getInstance(null);
		mSystemProxy = SystemProxy.getInstance(null);
	}

	public static Action_139 create(final Context context) {
		return new Action_139(context);
	}

	// }

	// { Action abstract method implementation

	@Override
	public void execute(final String data) {
		try {
			final JSONArray params = new JSONArray(data);

			/**
			 * *******************************
			 * TestValues Array
			 * *******************************
			 * 0  : maxSpeed
			 * 1  : maxSpeedUpper
			 * 2  : maxSpeedLower
			 *
			 * 3  : minSpeed
			 * 4  : minSpeedUpper
			 * 5  : minSpeedLower
			 *
			 * 6  : maxGradient
			 * 7  : maxGradientUpper
			 * 8  : maxGradientLower
			 *
			 * 9  : minGradient
			 * 10 : minGradientUpper
			 * 11 : minGradientLower
			 * *******************************
			 */

			mMaxSpeed = params.getInt(0);
			mMaxSpeedUpper = params.getInt(1);
			mMaxSpeedLower = params.getInt(2);

			mMaxGradient = params.getInt(6);
			mMaxGradientUpper = params.getInt(7);
			mMaxGradientLower = params.getInt(8);

			mIsStopping = false;

			mCoreProxy.registerForNotification(this);
			mWorkoutProxy.registerForNotification(this);
			mSystemProxy.registerForNotification(this);

			startAction();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// }

	// { ICoreListener implementation

	@Override
	public void onEnterRemoteModeResult(Boolean success) {

	}

	@Override
	public void onExitRemoteModeResult(final Boolean success) {
		// INF: Empty
	}

	@Override
	public void onKeepAliveExpired() {
		// INF: Empty
	}

	// }

	// { IWorkoutListener implementation

	@Override
	public void onSpeedValueRetrieved(final Integer speed) {
		// INF: Empty
	}

	@Override
	public void onGradientValueRetrieved(final Integer gradient) {
		// INF: Empty
	}

	@Override
	public void onHeartRateValueRetrieved(final Integer heartRate) {
		// INF: Empty
	}

	@Override
	public void onDistanceValueRetrieved(final Integer distance) {
		// INF: Empty
	}

	@Override
	public void onElapsedTimeValueRetrieved(final Integer elapsedTime) {
		// INF: Empty
	}

	@Override
	public void onWorkoutCompleted() {

		if (!mIsStopping) {

			stopTimers();
		}
	}

	@Override
	public void onCountDownPaused(final Integer countDown) {
		// INF: Empty
	}

	@Override
	public void onJoystickGradientUpPressed() {
		// INF: Empty
	}

	@Override
	public void onJoystickGradientUpReleased() {
		// INF: Empty
	}

	@Override
	public void onJoystickGradientDownPressed() {
		// INF: Empty
	}

	@Override
	public void onJoystickGradientDownReleased() {
		// INF: Empty
	}

	@Override
	public void onJoystickSpeedUpPressed() {
		// INF: Empty
	}

	@Override
	public void onJoystickSpeedUpReleased() {
		// INF: Empty
	}

	@Override
	public void onJoystickSpeedDownPressed() {
		// INF: Empty
	}

	@Override
	public void onJoystickSpeedDownReleased() {
		// INF: Empty
	}

	@Override
	public void onStartStopButtonPressed() {

	}

	@Override
	public void onButtonPress() {

	}

	@Override
	public void onButtonRelease() {

	}

	// }

	// { ISystemListener implementation

	@Override
	public void onStatusRetreived(String status) {
		//String[] statusVals = status.split(",");
		//String curSpeed = statusVals[2];
		//String curGradient = statusVals[3];
		String curSpeed = "0";
		String curGradient = "0";
		Matcher matcher = speedPattern.matcher(status);
		while(matcher.find()) {
			curSpeed = matcher.group(1);
		}
		Matcher matcherGrad = gradientPattern.matcher(status);
		while(matcherGrad.find()) {
			curGradient = matcherGrad.group(1);
		}

		Logger.getInstance().logDebug(
				getClass().getSimpleName(), "ON STATUS RETREIVED : " + status);

		double speed = Double.parseDouble(curSpeed);
		double grad = Double.parseDouble(curGradient);

		Logger.getInstance().logDebug(
				getClass().getSimpleName(), "ON STATUS RETREIVED : Speed=" +
						curSpeed + "   Gradient=" + curGradient);

		mModel.setSpeed(Integer.parseInt("" + ((Double)(speed * 10)).intValue()));
		mModel.setGradient(Integer.parseInt("" + ((Double)(grad * 10)).intValue()));

		final JSONArray data = new JSONArray();
		data.put(mModel.getCountDown());

		if(mModel.getCountDown() == 0) {
			mModel.DisableMaxValueCheck();
		}

		data.put(DecimalsFormatHelper.format(((double)mModel.getGradient()) / 10.0));
		data.put(DecimalsFormatHelper.format(((double)mModel.getSpeed()) / 10.0));
		mListener.onActionUpdate(data.toString());
	}

	@Override
	public void onCalibrationGradientValueMVRetreived(String result) {
		if(!mModel.isExecutedRKADC()){
			Logger.getInstance().logDebug(
				getClass().getSimpleName(), "[@RKADC# Result] \n@RKADC# \n" + result);
			mModel.executedRKADC(Boolean.TRUE);
		}
	}

	@Override
	public void onCalibrationSpeedValueRatioPWMRetreived(String result) {
		if(!mModel.isExecutedRKPWM()){
			Logger.getInstance().logDebug(
				getClass().getSimpleName(), "[@RKPWM# Result] \n@RKPWM# \n" + result);
			mModel.executedRKPWM(Boolean.TRUE);
		}
	}

	@Override
	public void onUpSensorStatusRetreived(String status) {
		if(!mModel.isExecutedRDS_UP()){
			Logger.getInstance().logDebug(
				getClass().getSimpleName(), "[@RDS_UP# Result] \n@RDS_UP# \n" + status);
			mModel.executedRDS_UP(Boolean.TRUE);
		}
	}

	@Override
	public void onDownSensorStatusRetreived(String status) {
//		if(!mModel.isExecutedRDS_DOWN()){
			Logger.getInstance().logDebug(
				getClass().getSimpleName(), "[@RDS_DOWN# Result] \n@RDS_DOWN# \n" + status);
			mModel.executedRDS_DOWN(Boolean.TRUE);
//		}
	}


	// { Private methods

	private void onMinValuesReached() {

		// stoppo il timer dello status per evitare di intasare i canali uart
		mTimer.cancel();
		mTimer.purge();
		mTimer = null;

		// stop workout
		try {
			mWorkoutProxy.stopWorkout();
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
		}

		Logger.getInstance().logDebug(getClass().getSimpleName(), "ON MINIMUM VALUE REACHED");
		Logger.getInstance().logDebug(getClass().getSimpleName(), "minSpeedReached = "+mModel.getSpeed());
		Logger.getInstance().logDebug(getClass().getSimpleName(), "maxGradientReached = "+mModel.getMaxGradientReached());

		String messageError = "";

		try {
			Thread.sleep(3000);
			this.executedRKADC();
			Thread.sleep(3000);
			this.executedRKPWM();
			Thread.sleep(3000);
			this.executedRDS_UP();
			Thread.sleep(3000);
			this.executedRDS_DOWN();
		} catch (Exception e) {
			Logger.getInstance().logError(getClass().getSimpleName(), "Errore durante invio dei comandi executedRKADC, executedRKPWM, executedRDS_UP, executedRIS_VAL");
			return;
		}

		final JSONArray data = new JSONArray();

		Logger.getInstance().logDebug(getClass().getSimpleName(), "max gradient: "+mMaxGradient);
		Logger.getInstance().logDebug(getClass().getSimpleName(), "max speed: "+mMaxSpeed);
		Logger.getInstance().logDebug(getClass().getSimpleName(), "max gradient reached: "+mModel.getMaxGradientReached());
		Logger.getInstance().logDebug(getClass().getSimpleName(), "max speed reached: "+ mModel.getMaxSpeedReached());
		Logger.getInstance().logDebug(getClass().getSimpleName(), "min gradient reached: "+mModel.getMinGradientReached());

		if((mModel.getMaxGradientReached() > (mMaxGradientUpper)) ||
				mModel.getMaxGradientReached() < (mMaxGradientLower)) {

			messageError += String.format(String.valueOf(R.string.Error_MyRun_139_inclination),
					DecimalsFormatHelper.format(((double)mModel.getMaxGradient()) / 10.0) ,
					DecimalsFormatHelper.format(((double)mModel.getMaxGradientReached()) / 10.0));

		}
		if((mModel.getMaxSpeedReached()) > (mMaxSpeedUpper) ||
				mModel.getMaxSpeedReached() < (mMaxSpeedLower)) {

			messageError += String.format(String.valueOf(R.string.Error_MyRun_139_speed),
					DecimalsFormatHelper.format(((double)mModel.getMaxSpeed()) / 10.0),
					DecimalsFormatHelper.format(((double)mModel.getMaxSpeedReached()) / 10.0));

		}

		final String finalMessageError = messageError;

		Logger.getInstance().logDebug(
				getClass().getSimpleName(), "SLEEP before final check to wait for all commands");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Logger.getInstance().logDebug(
						getClass().getSimpleName(), "FINAL CHECK : Speed=" +
								mModel.getMaxSpeedReached() + "   Max Gradient=" + mModel.getMaxGradientReached() +
								"   Min Gradient=" + mModel.getMinGradientReached());


				boolean loggedAllData = mModel.isExecutedRKADC() && mModel.isExecutedRKPWM() && mModel.isExecutedRDS_UP() && mModel.isExecutedRDS_DOWN();

				Logger.getInstance().logDebug(
						getClass().getSimpleName(), "LoggedAllData is "+loggedAllData);
				Logger.getInstance().logDebug(
						getClass().getSimpleName(), "isExecutedRKADC = "+mModel.isExecutedRKADC());
				Logger.getInstance().logDebug(
						getClass().getSimpleName(), "isExecutedRKPWM = "+mModel.isExecutedRKPWM());
				Logger.getInstance().logDebug(
						getClass().getSimpleName(), "isExecutedRDS_UP = "+mModel.isExecutedRDS_UP());
				Logger.getInstance().logDebug(
						getClass().getSimpleName(), "isExecutedRDS_DOWN = "+mModel.isExecutedRDS_DOWN());

				int nTimes = 0;
		while(!loggedAllData && nTimes <= 10) {
			nTimes++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			loggedAllData = mModel.isExecutedRKADC() && mModel.isExecutedRKPWM() && mModel.isExecutedRDS_UP() && mModel.isExecutedRDS_DOWN();

			Logger.getInstance().logDebug(
					getClass().getSimpleName(), "LoggedAllData is "+loggedAllData);
			Logger.getInstance().logDebug(
					getClass().getSimpleName(), "isExecutedRKADC = "+mModel.isExecutedRKADC());
			Logger.getInstance().logDebug(
					getClass().getSimpleName(), "isExecutedRKPWM = "+mModel.isExecutedRKPWM());
			Logger.getInstance().logDebug(
					getClass().getSimpleName(), "isExecutedRDS_UP = "+mModel.isExecutedRDS_UP());
			Logger.getInstance().logDebug(
					getClass().getSimpleName(), "isExecutedRIS_VAL = "+mModel.isExecutedRDS_DOWN());

		}


				if(finalMessageError != "" || nTimes > 10) {
					data.put(finalMessageError);
					mListener.onActionUpdate(data.toString());
					mListener.onActionCompleted(false);
				} else {
					mListener.onActionCompleted(true);
				}

				stopTimers();
				mSystemProxy.deregisterForNotification(Action_139.this);

	}

	private void onMaxValuesReached() {

		Logger.getInstance().logDebug(getClass().getSimpleName(), "ON MAX VALUE REACHED");
		Logger.getInstance().logDebug(getClass().getSimpleName(), "maxSpeedReached = "+mModel.getMaxSpeedReached());
		Logger.getInstance().logDebug(getClass().getSimpleName(), "maxGradientReached = "+mModel.getMaxGradientReached());

		mModel.DisableMaxValueCheck();

		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				try {
					mWorkoutProxy.setGradient(Integer.valueOf(0));
				} catch (WriteNotAllowedException ex) {
					ex.printStackTrace();
					mListener.onActionCompleted(false);
				}
			}
		}, 3000);

		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				try {
					mWorkoutProxy.setSpeed(Integer.valueOf(10));
				} catch (WriteNotAllowedException ex) {
					ex.printStackTrace();
					mListener.onActionCompleted(false);
				}
			}
		}, 6000);

		mModel.setCountDown(REACH_MIN_VALUES_MILLIS / 1000);

		if (mTimer != null) {
			mTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					onMinValuesReached();
				}
			}, REACH_MIN_VALUES_MILLIS);
		}
	}

	private void stopTimers() {

		if (mTimer != null) {
			if (mUpdateTimerTask != null) {
				mUpdateTimerTask.cancel();
			}
			if (mUpdateCurrentValuesTask != null) {
				mUpdateCurrentValuesTask.cancel();
			}
			mTimer.cancel();
			mTimer.purge();
			mTimer = null;
			mUpdateTimerTask = null;
			mUpdateCurrentValuesTask = null;
		}
	}

	@Override
	public void stop() {

		mIsStopping = true;

		stopTimers();

		if (mCoreProxy.isRemoteModeEnabled()) {
			try {
				mWorkoutProxy.stopWorkout();
			} catch (WriteNotAllowedException ex) {
				ex.printStackTrace();
				mListener.onActionCompleted(false);
			}
		}

		super.stop();
	}

	private void startAction() {

			mModel.setMaxSpeed(mMaxSpeed);
				mModel.setMaxGradient(mMaxGradient);
				mModel.EnableMaxValueCheck();

				try {
					// Comandi per avvio del workout
					// 1. simulazione pressione tasto centrale
					// 2. simulazione rilascio tasto centrale
					// 3. set della velocità
					// 4. set del gradient
					mWorkoutProxy.startWorkout(mMaxSpeed, mMaxGradient);
					Logger.getInstance().logDebug(
							getClass().getSimpleName(), "Waiting 3000ms before setting speed..");
					Thread.sleep(3000);
					mWorkoutProxy.setSpeed(mMaxSpeed);
					Logger.getInstance().logDebug(
							getClass().getSimpleName(), "Waiting 3000ms before setting speed..");
					Thread.sleep(3000);
					mWorkoutProxy.setGradient(mMaxGradient);

				} catch (WriteNotAllowedException | InterruptedException ex) {
					ex.printStackTrace();
				mListener.onActionCompleted(false);
			}

			stopTimers();

			mTimer = new Timer();
			mUpdateTimerTask = new TimerTask() {

				@Override
				public void run() {
					mModel.decrementCountDown();

					final JSONArray data = new JSONArray();
					data.put(mModel.getCountDown());
					mListener.onActionUpdate(data.toString());
				}
			};

			mTimer.scheduleAtFixedRate(mUpdateTimerTask, START_COUNTDOWN_DATA_TIME_MILLIS, UPDATE_COUNTDOWN_TIME_MILLIS);

			mModel.setCountDown(REACH_MAX_VALUES_MILLIS / 1000);

			mUpdateCurrentValuesTask = new TimerTask() {

				@Override
				public void run() {
					try {
						mSystemProxy.getStatus();
					} catch (WriteNotAllowedException ex) {
						ex.printStackTrace();
						mListener.onActionCompleted(false);
					}
				}
			};
			mTimer.scheduleAtFixedRate(mUpdateCurrentValuesTask, START_UPDATE_DATA_TIME_MILLIS, UPDATE_DATA_TIME_MILLIS);

			mTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					onMaxValuesReached();
				}
			}, REACH_MAX_VALUES_MILLIS);
//		}
	}

	private void executedRKADC() {
		try {
			this.mSystemProxy.sendCommand(GetCalibrationGradientValueMVCommand.create());
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			Logger.getInstance().logDebug(
					getClass().getSimpleName(), "[executedRKADC - ERROR] " + e.getMessage());
		}
	}

	private void executedRKPWM() {
		try {
			this.mSystemProxy.sendCommand(GetCalibrationSpeedValueRatioPWMCommand.create());
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			Logger.getInstance().logDebug(
					getClass().getSimpleName(), "[executedRKPWM - ERROR] " + e.getMessage());
		}
	}

	private void executedRDS_UP() {
		try {
			this.mSystemProxy.sendCommand(GetUpSensorStatusCommand.create());
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			Logger.getInstance().logDebug(
					getClass().getSimpleName(), "[executedRDS_UP - ERROR] " + e.getMessage());
		}
	}

	private void executedRDS_DOWN() {
		try {
			this.mSystemProxy.sendCommand(GetDownSensorStatusCommand.create());
		} catch (WriteNotAllowedException e) {
			e.printStackTrace();
			Logger.getInstance().logDebug(
					getClass().getSimpleName(), "[executedRDS_DOWN - ERROR] " + e.getMessage());
		}
	}

	// }

	// { Private class

	private final class ActionModel {

		private Boolean doneRKADC;
		private Boolean doneRKPWM;
		private Boolean doneRDS_UP;
		private Boolean doneRDS_DOWN;

		private Integer mSpeed;
		private Integer mGradient;
		private Integer mCountDown;

		private Integer maxSpeedValueReached;
		private Integer maxGradientValueReached;
		private Integer maxSpeed;
		private Integer maxGradient;
		private Boolean enableMaxValueCheck;

		private Integer minGradientValueReached;

		// { Construction

		public ActionModel() {
			super();

			this.doneRKADC = false;
			this.doneRKPWM = false;
			this.doneRDS_UP = false;
			this.doneRDS_DOWN = false;

			mSpeed = 0;
			mGradient = 0;
			mCountDown = 0;

			maxSpeed = 0;
			maxGradient = 0;
			maxSpeedValueReached = 0;
			maxGradientValueReached = 0;
			enableMaxValueCheck = false;

			minGradientValueReached = 12;
		}

		// }

		// { Public getters and setters

		public void executedRKADC(final Boolean value) {
			synchronized (this) {
				this.doneRKADC = value;
			}
		}

		public Boolean isExecutedRKADC() {
			return (this.doneRKADC);
		}

		public void executedRKPWM(final Boolean value) {
			synchronized (this) {
				this.doneRKPWM = value;
			}
		}

		public Boolean isExecutedRKPWM() {
			return (this.doneRKPWM);
		}

		public void executedRDS_UP(final Boolean value) {
			synchronized (this) {
				this.doneRDS_UP = value;
			}
		}

		public Boolean isExecutedRDS_UP() {
			return (this.doneRDS_UP);
		}

		public void executedRDS_DOWN(final Boolean value) {
			synchronized (this) {
				this.doneRDS_DOWN = value;
			}
		}

		public Boolean isExecutedRDS_DOWN() {
			return (this.doneRDS_DOWN);
		}

		public void setMaxSpeed(final Integer speed) {
			synchronized (this) {
				maxSpeed = speed;
			}
		}

		public Integer getMaxSpeed() {
			synchronized (this) {
				return maxSpeed;
			}
		}

		public Integer getMaxSpeedReached() {
			synchronized (this) {
				return maxSpeedValueReached;
			}
		}

		public void setMaxGradient(final Integer gradient) {
			synchronized (this) {
				maxGradient = gradient;
			}
		}

		public Integer getMaxGradient() {
			synchronized (this) {
				return maxGradient;
			}
		}

		public Integer getMaxGradientReached() {
			synchronized (this) {
				return maxGradientValueReached;
			}
		}

		public Integer getMinGradientReached() {
			synchronized (this) {
				return minGradientValueReached;
			}
		}

		public void setSpeed(final Integer speed) {
			synchronized (this) {
				mSpeed = speed;
				if(enableMaxValueCheck){

					if (maxSpeedValueReached < mSpeed) {
						Logger.getInstance().logDebug(
								getClass().getSimpleName(), "MODEL SET SPEED -> Speed from " + maxSpeedValueReached + " to " + mSpeed);

						maxSpeedValueReached = mSpeed;
					}
				}
			}
		}

		public Integer getSpeed() {
			synchronized (this) {
				return mSpeed;
			}
		}

		public void setGradient(final Integer gradient) {
			synchronized (this) {
				mGradient = gradient;
				if(enableMaxValueCheck){
					if (maxGradientValueReached < gradient) {
						Logger.getInstance().logDebug(
								getClass().getSimpleName(), "MODEL SET GRADIENT -> Gradient from " + maxGradientValueReached + " to " + mGradient);

						maxGradientValueReached = mGradient;
					}
				} else {
					Logger.getInstance().logDebug(
							getClass().getSimpleName(), "Trying to set new value: " + mGradient + " to " + minGradientValueReached);
					if  (minGradientValueReached > mGradient && mGradient >= 0) {
						Logger.getInstance().logDebug(
								getClass().getSimpleName(), "MODEL SET MIN GRADIENT -> Gradient from " + minGradientValueReached + " to " + mGradient);
						minGradientValueReached = mGradient;
					}

				}
			}
		}

		public Integer getGradient() {
			synchronized (this) {
				return mGradient;
			}
		}

		public Integer getCountDown() {
			synchronized (this) {
				return mCountDown;
			}
		}

		public void setCountDown(final Integer countDown) {
			synchronized (this) {
				mCountDown = countDown;
			}
		}

		public void decrementCountDown() {
			synchronized (this) {
				if (mCountDown > 0) {
					mCountDown--;
				}
			}
		}

		public void DisableMaxValueCheck() {
			synchronized (this) {
				enableMaxValueCheck = false;
			}
		}

		public void EnableMaxValueCheck() {
			synchronized (this) {
				enableMaxValueCheck = true;
			}
		}

		// }

	}

	// }

}
