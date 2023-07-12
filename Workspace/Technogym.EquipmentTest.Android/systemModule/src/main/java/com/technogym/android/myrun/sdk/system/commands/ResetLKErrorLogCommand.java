package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class ResetLKErrorLogCommand extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * */
	protected ResetLKErrorLogCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static ResetLKErrorLogCommand create() {
		return new ResetLKErrorLogCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "RESETLKERRORLOG";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }
}
