package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which achieves the auto-calibration of the equipment.<br/>
 * It expects no answer from the equipment.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class EnableAllSegments extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor.
	 * */
	protected EnableAllSegments() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static EnableAllSegments create() {
		return new EnableAllSegments();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "SDISP_FULL_ON";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}
