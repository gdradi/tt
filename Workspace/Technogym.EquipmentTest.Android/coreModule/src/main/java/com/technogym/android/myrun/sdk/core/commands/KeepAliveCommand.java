package com.technogym.android.myrun.sdk.core.commands;

import com.technogym.android.myrun.sdk.communication.commands.ApplicativeCommand;
import com.technogym.android.myrun.sdk.communication.commands.IApplicativeCommand;
import com.technogym.android.myrun.sdk.core.routines.KeepAliveRoutine;

/**
 * It's the final implementation of a core command which sends something similar to an ACK to the equipment: if the
 * equipment doesn't receive it for more than five seconds, it exits remote mode and it can be no more controller by
 * {@link IApplicativeCommand}s; otherwise, it keeps sending the equipment status.<br/>
 * There's a routine that does everything: it's enough to initialize and start it after being entered in remote mode.<br/>
 * It expects no answer from the equipment.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * 
 * @see KeepAliveRoutine
 * @see EnterRemoteModeCommand
 * */
public class KeepAliveCommand extends ApplicativeCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor.
	 * */
	protected KeepAliveCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static KeepAliveCommand create() {
		return new KeepAliveCommand();
	}

	// }

	// { ICommand implementation

	@Override
	protected String getSource() {
		return "DEV";
	}

	@Override
	protected String getCode() {
		return "055";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}
