package com.technogym.android.myrun.sdk.communication.commands;

/**
 * It's a common interface for all the types of command that can be sent to an equipment.<br/>
 * <br/>
 * It allows to specify the patterns later, without forcing anything except the fact that the communication is based on
 * messages.
 *
 * @author Federico Foschini
 * @version 1.0
 * */
public interface ICommand {

	/**
	 * Executes the command.<br/>
	 * The implementation of the command itself is left to the specialized classes.
	 * 
	 * @return the outcome of the command execution
	 * */
	public String execute();

}
