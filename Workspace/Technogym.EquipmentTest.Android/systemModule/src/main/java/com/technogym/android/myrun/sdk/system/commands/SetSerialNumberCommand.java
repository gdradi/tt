package com.technogym.android.myrun.sdk.system.commands;

import java.util.ArrayList;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which achieves the set of the serial number of the equipment.<br/>
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class SetSerialNumberCommand extends SystemCommand {

	private final String mSerialNumber;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * 
	 * @param serialNumber
	 *            : the serial number to send
	 * */
	protected SetSerialNumberCommand(final String serialNumber) {
		super();

		this.mSerialNumber = serialNumber;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static SetSerialNumberCommand create(final String serialNumber) {
		return new SetSerialNumberCommand(serialNumber);
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "stg_id";
	}

	@Override
	protected Iterable<String> getParameters() {
		final ArrayList<String> params = new ArrayList<String>();
		params.add(this.mSerialNumber);
		return params;
	}

	// }

}
