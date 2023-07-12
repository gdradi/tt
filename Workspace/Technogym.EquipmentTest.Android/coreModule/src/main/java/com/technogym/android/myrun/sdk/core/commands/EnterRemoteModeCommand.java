package com.technogym.android.myrun.sdk.core.commands;

import com.technogym.android.myrun.sdk.communication.commands.ApplicativeCommand;
import com.technogym.android.myrun.sdk.communication.commands.IApplicativeCommand;
import com.technogym.android.myrun.sdk.core.routines.KeepAliveRoutine;

/**
 * It's the final implementation of a core command which makes the equipment entering in remote mode, so that it can be
 * controlled with {@link IApplicativeCommand}s.<br/>
 * It expects an answer from the equipment.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * 
 * @see KeepAliveCommand
 * @see KeepAliveRoutine
 * */
public class EnterRemoteModeCommand extends ApplicativeCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor.
	 * */
	protected EnterRemoteModeCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static EnterRemoteModeCommand create() {
		return new EnterRemoteModeCommand();
	}

	// }

	// { ICommand implementation

	@Override
	protected String getSource() {
		return "DEV";
	}

	@Override
	protected String getCode() {
		return "027";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}
