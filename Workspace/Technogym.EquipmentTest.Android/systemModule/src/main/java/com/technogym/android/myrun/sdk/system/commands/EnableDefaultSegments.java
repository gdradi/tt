package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which achieves the auto-calibration of the equipment.<br/>
 * It expects no answer from the equipment.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class EnableDefaultSegments extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor.
	 * */
	protected EnableDefaultSegments() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static EnableDefaultSegments create() {
		return new EnableDefaultSegments();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "SDISP_NORMAL";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}
