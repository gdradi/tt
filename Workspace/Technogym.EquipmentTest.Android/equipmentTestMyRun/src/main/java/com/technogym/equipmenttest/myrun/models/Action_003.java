package com.technogym.equipmenttest.myrun.models;

import android.content.Context;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.system.commands.GetCalibrationGradientValueMVCommand;
import com.technogym.android.myrun.sdk.system.commands.SetCalibrationGradientValueMVCommand;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.myrun.proxies.SystemProxy;
import com.technogym.equipmenttest.library.models.Action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.UUID;
import java.util.logging.Handler;

import it.spot.android.logger.domain.Logger;

/**
 * This class has to reset the eeprom values to default settings
 * 
 * @author Federico Foschini
 * @version 2.0
 * */
public class Action_003 extends BaseMyRunAction {

	private static final String TAG = Action_003.class.getSimpleName();
	private final ISystemProxy mSystemProxy;
	private final int N_RETRY = 10;
	private final String RESULT_OK = "OK";
	//private static final String RESULT_ERROR = "ERROR";
	private final ActionModel mModel;
	private Thread timeoutThread;
	private boolean cmdAlreadySent = false;
	private String guid;
	private boolean EEPROMDEFCommandAlreadySent = false;

	// { Construction

	protected Action_003(Context context) {
		super(context);

		Logger.getInstance().logDebug(TAG, "Constructor");

		this.mModel = new ActionModel(N_RETRY);
		this.mSystemProxy = SystemProxy.getInstance(null);
		mSystemProxy.registerForNotification(this);
		guid = UUID.randomUUID().toString();
	}

	public static Action_003 create(final Context context) {
		return new Action_003(context);
	}

	// }

	// { Private methods

	private void doAction() {
		try {

			int delay = 3000;
			Logger.getInstance().logDebug(
					getClass().getSimpleName(),
					"["+guid+"][doAction] waiting "+delay+" ms..");
			Thread.sleep(3000);

			this.mModel.incRetry();
			this.mSystemProxy.setEEPROMDefaultValues();
//			Logger.getInstance().logError(
//					getClass().getSimpleName(), 
//					"DO ACTION WITH RETRY N: " + mModel.curRetry);
			
		} catch (Exception e) {
			timeoutThread.interrupt();
			Logger.getInstance().logError(
					getClass().getSimpleName(), "["+guid+"]ERROR DURING EXECUTION: " + e.getLocalizedMessage());
			mSystemProxy.deregisterForNotification(this);
			this.mListener.onActionCompleted(false);
			e.printStackTrace();
		}
	}
	
	private void backupRKADC() {
		try {
			this.mSystemProxy.sendCommand(GetCalibrationGradientValueMVCommand.create());
		} catch (WriteNotAllowedException e) {
			timeoutThread.interrupt();
			Logger.getInstance().logError(
					getClass().getSimpleName(), "["+guid+"]ERROR DURING BACKUP RKADC: " + e.getLocalizedMessage());
			mSystemProxy.deregisterForNotification(this);
			this.mListener.onActionCompleted(false);
			e.printStackTrace();
		}
	}
	
	private void restoreValuesRKADC() {
		try {
			int delay = 3000;
			Logger.getInstance().logDebug(
					getClass().getSimpleName(),
					"["+guid+"]restoreValuesRKADC waiting "+delay+" ms..");
			Thread.sleep(3000);

			this.mSystemProxy.sendCommand(SetCalibrationGradientValueMVCommand.create(mModel.getValueA(), mModel.getValueB(), mModel.getValueC()));
		} catch (Exception e) {
			timeoutThread.interrupt();
			Logger.getInstance().logError(
					getClass().getSimpleName(), "["+guid+"]ERROR DURING RESTORE RKADC: " + e.getLocalizedMessage());
			mSystemProxy.deregisterForNotification(this);
			this.mListener.onActionCompleted(false);
			e.printStackTrace();
		}
	}
	
	// }

	// { Action abstract method implementation

	@Override
	public void execute(String data) {
		cmdAlreadySent = false;
		Logger.getInstance().logDebug(
				getClass().getSimpleName(), "["+guid+"]EXECUTE");
		backupRKADC();

		timeoutThread = new Thread(new Runnable(){
			@Override
			public void run () {
				try {
					Thread.sleep(20000);
					Logger.getInstance().logError(
							getClass().getSimpleName(), "["+guid+"]timeoutThread - Timeout of 20000ms over. Result: KO");
					mSystemProxy.deregisterForNotification(Action_003.this);
					Action_003.this.mListener.onActionCompleted(false);
				} catch (InterruptedException e) {
					Logger.getInstance().logDebug(getClass().getSimpleName(), "["+guid+"]timeoutThread -  interrupted");
				}
			}
		});
		timeoutThread.start();
		//doAction();
	}

	// }

	@Override
	public void onSetEEPROMDefaultValuesSet(String result) {
		Logger.getInstance().logDebug(
				getClass().getSimpleName(),
				"["+guid+"]onSetEEPROMDefaultValuesSet result: " + result);
		if(result.contains(RESULT_OK)){
			if(!EEPROMDEFCommandAlreadySent){
				EEPROMDEFCommandAlreadySent = true;
				Logger.getInstance().logDebug(
						getClass().getSimpleName(),
						"["+guid+"]onSetEEPROMDefaultValuesSet - Occurrency number 1 - Calling restoreValuesRKADC()");
				restoreValuesRKADC();
			}else{
				Logger.getInstance().logDebug(
						getClass().getSimpleName(),
						"["+guid+"]onSetEEPROMDefaultValuesSet -  Not first occurrency - SKIPPING");
				return;
			}
		} else {
			if(mModel.getCurRetry() < mModel.getMaxRetry()) {
				doAction();
			} else {
				timeoutThread.interrupt();
				mSystemProxy.deregisterForNotification(this);
				this.mListener.onActionCompleted(false);
			}
		}
	}

	@Override
	public synchronized void onCalibrationGradientValueMVRetreived(String result) {

		boolean isSecond = false;
		Logger.getInstance().logDebug(
				getClass().getSimpleName(), 
				"["+guid+"]onCalibrationGradientValueMVRetreived result: " + result);

		try {
			if (!cmdAlreadySent) {
				cmdAlreadySent = true;
				throw new Exception("first");
			} else {
				isSecond = true;
				throw new Exception("second");
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String sStackTrace = sw.toString(); // stack trace as a string
			Logger.getInstance().logDebug(
					getClass().getSimpleName(),
					sStackTrace);
			if (isSecond) {
				Logger.getInstance().logDebug(getClass().getSimpleName(), "["+guid+"]SKIPPED second flow ");
				return;
			}
		}


		/*
		 * Prelievo i valori dal risultato fornito dalla macchina e relativo salvataggio nel modello interno
		 */
		String data = result.replace("A=", "");
		data = data.replace("B=", "_");
		data = data.replace("C=", "_");
		String[] all = data.split("_");

		mModel.setValueA(all[0]);
		mModel.setValueB(all[1]);
		mModel.setValueC(all[2]);
		
		/*
		 * Esecuzione della action
		 */
		doAction();
	}

	@Override
	public void onCalibrationGradientValueMVSet(String result) {

		Logger.getInstance().logDebug(
				getClass().getSimpleName(),
				"["+guid+"]onCalibrationGradientValueMVSet result: " + result);
		/*
		 * Prelievo i valori dal risultato fornito dalla macchina e relativo salvataggio nel modello interno
		 */
		String data = result.replace("A=", "");
		data = data.replace("B=", "_");
		data = data.replace("C=", "_");
		String[] all = data.split("_");
		
		Logger.getInstance().logDebug(
				getClass().getSimpleName(), 
				"ACTION 003" 
				+ "\n ORIGINAL VALUES: \n" 
				+ "A= " + mModel.getValueA() 
				+ "B= " + mModel.getValueB()
				+ "C= " + mModel.getValueC()
				+ "\n RECEIVED VALUES: \n"
				+ "A= " + all[0]
				+ "B= " + all[1]
				+ "C= " + all[2]
				+ "\n");

		timeoutThread.interrupt();
		mSystemProxy.deregisterForNotification(this);
		mListener.onActionCompleted(true);
	}

	// }

	// { Internal Model

	private final class ActionModel {
		
		private String valA, valB, valC;
		
		private Integer curRetry;
		private Integer maxRetry;

		// { Construction
		
		public ActionModel(final int nMaxRetry) {
			super();
			maxRetry = nMaxRetry;
			curRetry = 0;
			
			valA = "";
			valB = "";
			valC = "";
		}

		// }

		// { Public getters and setters

		public int getCurRetry() {
			return curRetry;
		}
		
		public int getMaxRetry() {
			return maxRetry;
		}
		
		public void incRetry() {
			synchronized (this) {
				curRetry++;
			}
		}

		public void setValueA(final String value) {
			synchronized (this) {
				this.valA = value;
			}
		}

		public String getValueA() {
			return (this.valA);
		}
		
		public void setValueB(final String value) {
			synchronized (this) {
				this.valB = value;
			}
		}

		public String getValueB() {
			return (this.valB);
		}
		
		public void setValueC(final String value) {
			synchronized (this) {
				this.valC = value;
			}
		}

		public String getValueC() {
			return (this.valC);
		}
		
		// }

	}

	// }
}