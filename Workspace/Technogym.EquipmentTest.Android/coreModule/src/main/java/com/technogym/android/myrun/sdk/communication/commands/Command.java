package com.technogym.android.myrun.sdk.communication.commands;

/**
 * This class is a base implementation of {@link ICommand} interface.<br/>
 * It provides default behaviors for the construction and recognition of a command message.
 *
 * @author Federico Foschini
 * @version 1.0
 * */
public abstract class Command implements ICommand {

	// { Construction

	/**
	 * The class constructor. It does nothing more than calling {@code super} constructor of {@link Object} class.
	 * */
	protected Command() {
		super();
	}

	// }

	// { ICommand implementation

	/**
	 * This method built the string message which represents the command itself. It should parse all the parameters and
	 * join them with every other needed information.
	 * 
	 * @see #getParameters()
	 * */
	@Override
	public abstract String execute();

	// }

	// { protected methods

	/**
	 * This method gets the string which mark the beginning of the related command message.
	 * 
	 * @return String
	 * */
	protected abstract String getStart();

	/**
	 * This method returns the parameters required by the equipment to execute the command correctly.
	 * 
	 * @return String
	 * */
	protected abstract Iterable<String> getParameters();

	/**
	 * This method gets the string which mark the end of the related command message.
	 * 
	 * @return String
	 * */
	protected abstract String getEnd();

	// }
}
