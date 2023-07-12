package com.technogym.myrow.models.actions;

import com.technogym.myrow.listeners.IActionListener;
import com.technogym.myrow.managers.ITestActionManager;

/**
 * This is the interface for a generic action class required by the equipment test process.
 *
 * @author Federico Foschini
 * @version 1.0
 * */
public interface IAction {

	/**
	 * This method sets a listener for the {@link IAction} state updates.
	 * 
	 * @param listener
	 *            : the {@link IActionListener} that will be notified
	 * */
	public void setListener(final IActionListener listener);

	/**
	 * This method starts the execution of the {@link IAction}.
	 * 
	 * @param data
	 *            : the raw data needed for a proper execution
	 * */
	public void execute(final String data);

	/**
	 * This method executes a command within the {@link IAction} context.
	 * 
	 * @return true if the command has been recognized, otherwise returns false.
	 * */
	public boolean executeCommand(final String commandID);

	/**
	 * This method allows to stop the {@link IAction} pending activities when it gets interrupted. It gets also called
	 * every time from the {@link ITestActionManager} before changing the current action.
	 * */
	public void stop();

}
