package com.technogym.mycycling.managers;

import com.technogym.mycycling.models.actions.Action;

/**
 * This interface declares the basic methods that a test manager has to implement.<br/>
 * <br/>
 * Its implementation allows to centralize the equipment test management in a single class:
 * <ul>
 * <li>check of the availability of the requested {@link Action}</li>
 * <li>start of an {@link Action}</li>
 * <li>stop of an {@link Action}</li>
 * </ul>
 *
 * @author Federico Foschini
 * @version 1.0
 * */
public interface ITestActionManager {

	/**
	 * It stops the execution of the previous {@link Action} and, if allowed, starts the next one which is identified by
	 * the given input parameters.
	 * 
	 * @param id
	 *            : the identification number of the {@link Action} to execute
	 * @param data
	 *            : the content data needed by the {@link Action} for a correct execution
	 * */
	public void execute(final int id, final String data);

	/**
	 * It tells to the current {@link Action} to execute a specific command.
	 * 
	 * @param commandID
	 *            : the {@code String} which identifies the command to execute
	 * */
	public void executeCommand(final String commandID);

	/**
	 * This method kills the test, stopping every kind of execution still place.
	 * */
	public void killTest();

}
