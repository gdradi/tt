package com.technogym.android.myrun.sdk.core.commands;

import com.technogym.android.myrun.sdk.communication.commands.ApplicativeCommand;
import com.technogym.android.myrun.sdk.communication.commands.IApplicativeCommand;

/**
 * It's the final implementation of a core command which makes the equipment exiting from remote mode, so that it can be
 * no more controlled with {@link IApplicativeCommand}s.<br/>
 * It expects an answer from the equipment.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public class ExitRemoteModeCommand extends ApplicativeCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor.
	 * */
	protected ExitRemoteModeCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static ExitRemoteModeCommand create() {
		return new ExitRemoteModeCommand();
	}

	// }

	// { ICommand implementation

	@Override
	protected String getSource() {
		return "DEV";
	}

	@Override
	protected String getCode() {
		return "029";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}
