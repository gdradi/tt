package com.technogym.equipmenttest.app.models;

import it.spot.android.logger.domain.Logger;
import android.content.Context;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.system.commands.GetCalibrationGradientValueMVCommand;
import com.technogym.android.myrun.sdk.system.commands.SetCalibrationGradientValueMVCommand;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.android.myrun.sdk.system.proxies.SystemProxy;
import com.technogym.equipmenttest.library.models.Action;

/**
 * This class has to reset the eeprom values to default settings
 * 
 * @author Federico Foschini
 * @version 2.0
 * */
public class Action_003 extends BaseAction {

	private final ISystemProxy mSystemProxy;
	private final int N_RETRY = 10;
	private final String RESULT_OK = "OK";
	//private static final String RESULT_ERROR = "ERROR";
	private final ActionModel mModel;
	
	// { Construction

	protected Action_003(Context context) {
		super(context);
		
		this.mModel = new ActionModel(N_RETRY);
		this.mSystemProxy = SystemProxy.getInstance(null);
		mSystemProxy.registerForNotification(this);
	}

	public static Action_003 create(final Context context) {
		return new Action_003(context);
	}

	// }

	// { Private methods

	private void doAction() {
		try {
			this.mModel.incRetry();
			this.mSystemProxy.setEEPROMDefaultValues();
//			Logger.getInstance().logError(
//					getClass().getSimpleName(), 
//					"DO ACTION WITH RETRY N: " + mModel.curRetry);
			
		} catch (WriteNotAllowedException e) {
			Logger.getInstance().logError(
					getClass().getSimpleName(), "ERROR DURING EXECUTION: " + e.getLocalizedMessage());
			mSystemProxy.deregisterForNotification(this);
			this.mListener.onActionCompleted(false);
			e.printStackTrace();
		}
	}
	
	private void backupRKADC() {
		try {
			this.mSystemProxy.sendCommand(GetCalibrationGradientValueMVCommand.create());
		} catch (WriteNotAllowedException e) {
			Logger.getInstance().logError(
					getClass().getSimpleName(), "ERROR DURING BACKUP RKADC: " + e.getLocalizedMessage());
			mSystemProxy.deregisterForNotification(this);
			this.mListener.onActionCompleted(false);
			e.printStackTrace();
		}
	}
	
	private void restoreValuesRKADC() {
		try {
			this.mSystemProxy.sendCommand(SetCalibrationGradientValueMVCommand.create(mModel.getValueA(), mModel.getValueB(), mModel.getValueC()));
		} catch (WriteNotAllowedException e) {
			Logger.getInstance().logError(
					getClass().getSimpleName(), "ERROR DURING RESTORE RKADC: " + e.getLocalizedMessage());
			mSystemProxy.deregisterForNotification(this);
			this.mListener.onActionCompleted(false);
			e.printStackTrace();
		}
	}
	
	// }

	// { Action abstract method implementation

	@Override
	public void execute(String data) {
		Logger.getInstance().logDebug(
				getClass().getSimpleName(), "EXECUTE");
		backupRKADC();
		//doAction();
	}

	// }

	// { ISystemListener implementation

	@Override
	public void onSetEEPROMDefaultValuesSet(String result) {
//		Logger.getInstance().logDebug(
//				getClass().getSimpleName(), 
//				"onSetEEPROMDefaultValuesSet result: " + result);
		
		if(result.contains(RESULT_OK)){
			restoreValuesRKADC();
			//this.mListener.onActionCompleted(true);
		} else {
			if(mModel.getCurRetry() < mModel.getMaxRetry()) {
				doAction();
			} else {
				mSystemProxy.deregisterForNotification(this);
				this.mListener.onActionCompleted(false);
			}
		}
	}


	@Override
	public void onCalibrationGradientValueMVRetreived(String result) {
		Logger.getInstance().logDebug(
				getClass().getSimpleName(), 
				"onCalibrationGradientValueMVRetreived result: " + result);
		
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