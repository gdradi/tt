package com.technogym.myrow.bridges.android;

import android.webkit.JavascriptInterface;

import com.technogym.myrow.managers.ITestActionManager;
import com.technogym.myrow.models.actions.Action;

public interface IEquipmentBridge<TActionManager extends ITestActionManager> extends IBridge {

	/**
	 * This methods sends to the {@link ITestActionManager} the informations of the action to execute.
	 * 
	 * @param id
	 *            : is the identifier of the {@link Action}
	 * @param data
	 *            : the raw data that can be passed to the {@link Action} for a proper initialization
	 * */
	@JavascriptInterface
	public void executeAction(final int id, final String data);

	/**
	 * This method sends to the {@link ITestActionManager} a command that needs to be executed within the context of the
	 * current {@link Action}.
	 * 
	 * @param commandID
	 *            : the command identifier, so that the {@link Action} can identify it correctly
	 * */
	@JavascriptInterface
	public void executeCommand(final String commandID);

	// { Info methods

	/**
	 * This method checks if the running device is Unity or not.
	 * 
	 * @return true if the running device is Unity, otherwise false.
	 * */
	@JavascriptInterface
	public boolean isUnity();

	/**
	 * This method checks if the running device is MyRun or not.
	 * 
	 * @return true if the running device is MyRun, otherwise false.
	 * */
	@JavascriptInterface
	public boolean isMyRun();

	/**
	 * This method checks if the running device is MyCycling or not.
	 *
	 * @return true if the running device is MyCycling, otherwise false.
	 * */
	@JavascriptInterface
	public boolean isMyCycling();

	/**
	 * This method checks if the running device is MyRow or not.
	 *
	 * @return true if the running device is MyRow, otherwise false.
	 * */
	@JavascriptInterface
	public boolean isMyRow();

	/**
	 * This method asks for the serial number of the equipment if set.
	 * */
	@JavascriptInterface
	public void askForEquipmentSerialNumber();

	/**
	 * This method sets the serial number on the equipment if not already set.
	 * 
	 * @param serialNumber
	 *            : the serial to set.
	 * */
	@JavascriptInterface
	public void setEquipmentSerialNumber(final String serialNumber);

	// }

}
