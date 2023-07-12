package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which reset the eeprom to default values.<br/>
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public class SetEEPROMDefaultValuesCommand extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * */
	protected SetEEPROMDefaultValuesCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static SetEEPROMDefaultValuesCommand create() {
		return new SetEEPROMDefaultValuesCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "eepromdef";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}
