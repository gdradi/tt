package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which reset the eeprom error history.<br/>
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public class ResetErrorLogCommand extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * */
	protected ResetErrorLogCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static ResetErrorLogCommand create() {
		return new ResetErrorLogCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "reseterrorlog";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}
