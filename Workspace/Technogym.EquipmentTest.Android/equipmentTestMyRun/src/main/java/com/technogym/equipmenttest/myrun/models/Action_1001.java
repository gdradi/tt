package com.technogym.equipmenttest.myrun.models;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.proxies.ISystemProxy;
import com.technogym.equipmenttest.myrun.proxies.SystemProxy;
import com.technogym.equipmenttest.library.models.Action;
import it.spot.android.logger.domain.Logger;
import android.content.Context;

import java.util.Arrays;
import java.util.HashMap;

public class Action_1001 extends BaseMyRunAction {

	private final ISystemProxy mSystemProxy;

	// Qual'è la phase corrente
	private int currentPhase = 0;

	// Quanti RIO devi mandare
	private final int maxRioToSend = 3;

	// Quale RIO stai mandando
	private int currentRio;

	// Risposte ricevute dei 4 RIO
	private HashMap<Integer, Integer> rioResults = new HashMap<Integer, Integer>();




//	private int commandResultToCheckTrue;
//	private boolean allCommandsOk;

	/*

	RIO 0: DX DOWN
	RIO 1: DX UP
	RIO 2: SX DOWN
	RIO 3: SX UP

	RX: 0 (false) oppure 1 (true)

	 */

	protected Action_1001(final Context context) {
		super(context);
		this.mSystemProxy = SystemProxy.getInstance(null);
	}

	public static Action_1001 create(final Context context) {
		return new Action_1001(context);
	}


	@Override
	public void execute(String data) {
		executeCommand(Integer.toString(this.currentPhase));
	}

	/*
	 Questo metodo viene chiamato dall'app web e prende come
	 parametro in ingresso lo stato (phase) corrente (0-4).
	 Chiama successivamente sendRioCommand sempre con parametro 0 e
	 innesca quindi l'invio in successione dei comandi
	 RIO 0, RIO 1, RIO 2, RIO 3.
	 */
	@Override
	public boolean executeCommand(final String phase) {
		mSystemProxy.registerForNotification(this);

		this.currentPhase = Integer.parseInt(phase);
		Logger.getInstance().logDebug(getClass().getSimpleName(),
				"executeCommand - currentPhase: "+currentPhase);
		sendRioCommand(0);
		return true;
	}

	/*
	Chiamato iterativamente ogni volta che si ottiene la risposta dal comando RIO.
	Ad ogni iterazione viene passato un parametro incrementato (0-3).
	 */
	private void sendRioCommand(int param){
		this.currentRio = param;
		try {
			Logger.getInstance().logDebug(getClass().getSimpleName(),
					"sendRioCommand: "+this.currentRio);
			this.mSystemProxy.getJoystickStatus(this.currentRio);
		} catch (WriteNotAllowedException e) {
			Logger.getInstance().logDebug(getClass().getSimpleName(),
					"exception while calling getJoystickStatus with param: "+this.currentRio);
			e.printStackTrace();
			notifyPollingCycleEnded("error");
		}
	}

	/*
	RIO 0: DX DOWN
	RIO 1: DX UP
	RIO 2: SX DOWN
	RIO 3: SX UP

	RX: 0 (false) oppure 1 (true)
	 */
	@Override
	public void onJoystickStatusRetrieved(String mStatus) {
		// la risposta al comando è IO:0 oppure IO:1
		Logger.getInstance().logDebug(getClass().getSimpleName(),
				"onJoystickStatusRetrieved - currentRio "+ currentRio +", response: "+mStatus);

		try {
			String response = mStatus.split(":")[1];
			rioResults.put(currentRio, Integer.valueOf(response));

			if (currentRio < maxRioToSend) {
				sendRioCommand(currentRio +1);
			}
			else {
				boolean phaseResult = false;
				switch(currentPhase) {
					case 0:
						phaseResult = rioResults.get(0) == 0
								&& rioResults.get(1) == 1
								&& rioResults.get(2) == 0
								&& rioResults.get(3) == 0;
						break;
					case 1:
						phaseResult = rioResults.get(0) == 1
								&& rioResults.get(1) == 0
								&& rioResults.get(2) == 0
								&& rioResults.get(3) == 0;
						break;
					case 2:
						phaseResult = rioResults.get(0) == 0
								&& rioResults.get(1) == 0
								&& rioResults.get(2) == 0
								&& rioResults.get(3) == 1;
						break;
					case 3:
						phaseResult = rioResults.get(0) == 0
								&& rioResults.get(1) == 0
								&& rioResults.get(2) == 1
								&& rioResults.get(3) == 0;
						break;
					case 4:
						phaseResult = rioResults.get(0) == 0
								&& rioResults.get(1) == 0
								&& rioResults.get(2) == 0
								&& rioResults.get(3) == 0;
						break;
					default:
						Logger.getInstance().logDebug(getClass().getSimpleName(),
								"onJoystickStatusRetrieved - invalid phase: "+currentPhase);
						notifyPollingCycleEnded("error");
				}
				Logger.getInstance().logDebug(getClass().getSimpleName(),
						"onJoystickStatusRetrieved - Polling cycle ended. rioResults="+ Arrays.asList(rioResults)+", phaseResult="+phaseResult);
				notifyPollingCycleEnded(String.valueOf(phaseResult));
			}
		} catch (Exception e) {
			Logger.getInstance().logDebug(getClass().getSimpleName(),
					"onJoystickStatusRetrieved - "+e.getLocalizedMessage());
			notifyPollingCycleEnded("error");
			return;
		}
	}

	// Può essere true, false o error
	private void notifyPollingCycleEnded(String phaseResult) {
		Logger.getInstance().logDebug(getClass().getSimpleName(),
				"notifyPollingCycleEnded("+phaseResult+")");
		mListener.onActionUpdate(phaseResult);
		mSystemProxy.deregisterForNotification(this);
	}



	@Override
	public void stop() {
		this.mSystemProxy.deregisterForNotification(this);
		super.stop();
	}

	// }

}
