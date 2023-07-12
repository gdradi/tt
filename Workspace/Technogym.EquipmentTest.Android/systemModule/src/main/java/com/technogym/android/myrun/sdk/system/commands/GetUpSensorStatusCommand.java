package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which achieves the retrieval of the status of
 * the equipment's up sensor.<br/>
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public class GetUpSensorStatusCommand extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * */
	protected GetUpSensorStatusCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static GetUpSensorStatusCommand create() {
		return new GetUpSensorStatusCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "RDS_UP";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }
}
