package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which asks for the firmware version of the equipment.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class FirmwareVersionCommand extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor.
	 * */
	protected FirmwareVersionCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static FirmwareVersionCommand create() {
		return new FirmwareVersionCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "fwver";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}
