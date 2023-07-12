package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which achieves the retrieval of the error log of the equipment.<br/>
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public class GetErrorLogCommand extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * */
	protected GetErrorLogCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static GetErrorLogCommand create() {
		return new GetErrorLogCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "rerrorlog";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}
