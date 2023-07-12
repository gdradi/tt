package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which achieves the retrieval of the serial number of the equipment.<br/>
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class GetSerialNumberCommand extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * */
	protected GetSerialNumberCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static GetSerialNumberCommand create() {
		return new GetSerialNumberCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "rtg_id";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}
