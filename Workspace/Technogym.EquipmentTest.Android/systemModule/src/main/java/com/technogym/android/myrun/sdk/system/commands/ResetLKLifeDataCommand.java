package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class ResetLKLifeDataCommand extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * */
	protected ResetLKLifeDataCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static ResetLKLifeDataCommand create() {
		return new ResetLKLifeDataCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "RESETLKLIFEDATA";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }
}
