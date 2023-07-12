package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which get the eeprom error logs.<br/>
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public class CheckErrorLogNumberCommand  extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * */
	protected CheckErrorLogNumberCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static CheckErrorLogNumberCommand create() {
		return new CheckErrorLogNumberCommand();
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
