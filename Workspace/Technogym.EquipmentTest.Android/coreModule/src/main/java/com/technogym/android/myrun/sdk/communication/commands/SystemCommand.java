package com.technogym.android.myrun.sdk.communication.commands;

import com.google.common.base.Joiner;
import com.technogym.android.myrun.sdk.support.Logger;

/**
 * This is an abstract class which implements the basic interface for system commands.<br/>
 * It defines the first and last characters needed by the equipment to recognize the boundaries of every single sent
 * command.<br/>
 * The other methods inherited by {@link Command} base class are left to specific implementations.
 *
 * @author Federico Foschini
 * @version 1.0
 * */
public abstract class SystemCommand extends Command implements ISystemCommand {

	// { Command abstract methods implementation

	/**
	 * This method has got a standard implementation and it should be left as is.<br/>
	 * It composes the message properly to parameters (optionals) of the command and wraps it between two characters
	 * which define the boundaries of the message itself, so that it can be recognized when reading from a connection
	 * stream.
	 * */
	@Override
	public String execute() {
		final Iterable<String> parameters = this.getParameters();
		String formattedParams;
		if (parameters != null) {
			formattedParams = String.format("%s", Joiner.on(' ').join(parameters));
		} else {
			formattedParams = "";
		}

		String msg = String.format("%s%s %s%s", this.getStart(), this.getName(), formattedParams, this.getEnd());
		if (parameters == null) {
			Logger.e("SYSTEM_COMMAND", msg);
			return msg.trim();
		}

		Logger.e("SYSTEM_COMMAND", msg);
		return msg;
	}

	/**
	 * This method returns the name of the command needed by the equipment to recognize the message as a valid command.
	 * 
	 * @return String
	 * */
	protected abstract String getName();

	@Override
	protected String getStart() {
		return "@";
	}

	@Override
	protected String getEnd() {
		return "#";
	}

	// }

}
