package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which achieves the retrieval of the measure unit of the equipment.<br/>
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public final class GetMeasureUnitCommand extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * */
	protected GetMeasureUnitCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static GetMeasureUnitCommand create() {
		return new GetMeasureUnitCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "ssiunits";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}
