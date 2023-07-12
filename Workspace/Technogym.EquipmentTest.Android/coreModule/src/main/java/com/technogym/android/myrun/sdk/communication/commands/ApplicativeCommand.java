package com.technogym.android.myrun.sdk.communication.commands;

import com.google.common.base.Joiner;

/**
 * This is the base implementation for {@link IApplicativeCommand} interface.<br/>
 * It defines the boundary-characters expected by the equipment for this kind of commands.<br/>
 * Everything else is left to the derived classes.
 *
 * @author Federico Foschini
 * @version 1.0
 * */
public abstract class ApplicativeCommand extends Command implements IApplicativeCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than invoking {@code super} constructor of {@link Object} class.
	 * */
	protected ApplicativeCommand() {
		super();
	}

	// }

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
			formattedParams = String.format(",%s", Joiner.on(',').join(parameters));
		} else {
			formattedParams = "";
		}
		return String.format(
			"%s%s,%s%s%s", this.getStart(), this.getSource(), this.getCode(), formattedParams, this.getEnd());
	}

	/**
	 * This method returns the Source of the command needed by the equipment to recognize the message as a valid
	 * command.
	 * 
	 * @return String
	 * */
	protected abstract String getSource();

	/**
	 * This method returns the Code of the command needed by the equipment to recognize the message as a valid command.
	 * 
	 * @return String
	 * */
	protected abstract String getCode();

	@Override
	protected String getStart() {
		return "!";
	}

	@Override
	protected String getEnd() {
		return "#";
	}

	// }

}
