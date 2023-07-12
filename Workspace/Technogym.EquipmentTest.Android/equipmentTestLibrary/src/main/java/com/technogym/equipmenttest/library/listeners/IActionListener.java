package com.technogym.equipmenttest.library.listeners;

import com.technogym.equipmenttest.library.models.IAction;

/**
 * This represents the standard listener interface for actions' success or error events.
 * */
public interface IActionListener {

	/**
	 * This method handles an intermediate update of the state of the current {@link IAction}.
	 * 
	 * @param data
	 *            : the content which indicates the update properties
	 * */
	public void onActionUpdate(final String data);

	/**
	 * This method handles the completion of the current {@link IAction}.
	 * 
	 * @param success
	 *            : a {@code boolean} which indicates the success or the failure of the {@link IAction}
	 * */
	public void onActionCompleted(final boolean success);

	/**
	 * This method handles the error of wrong parameters for the current {@link IAction}.
	 * 
	 * @param data
	 *            : optional details about the errors
	 * */
	public void onWrongActionParameters(final String data);

	/**
	 * This method handles the retrieval of the equipment serial number.
	 * 
	 * @param serialNumber
	 *            : the serial number retrieved
	 * */
	public void onEquipmentSerialNumberRetrieved(final String serialNumber);

	/**
	 * This method handles the set of the equipment serial number.
	 * 
	 * @param result
	 *            : a boolean which is {@code true} if the set goes right, {@code false} otherwise
	 * */
	public void onEquipmentSerialNumberSet(final Boolean result);

	/**
	 * This method handles the retrieval of equipment's firmware version.
	 * 
	 * @param firmwareVersion
	 *            : a @{code String} which is the firmware version received
	 * */
	public void onEquipmentFirmwareVersionRetrieved(final String firmwareVersion);

}
