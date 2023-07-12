package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the implementation of a system command which request to the equipment its current status
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public final class StatusCommand extends SystemCommand {
	
	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * */
	protected StatusCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static StatusCommand create() {
		return new StatusCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "status";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}
