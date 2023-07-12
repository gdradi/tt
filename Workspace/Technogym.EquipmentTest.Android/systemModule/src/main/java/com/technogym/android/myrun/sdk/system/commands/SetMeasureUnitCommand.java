package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which set the unit of measure of the equipment
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public final class SetMeasureUnitCommand extends SystemCommand {

	private final Integer mValue;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * 
	 * @param value
	 *            : the value related to the unit of measure
	 * */
	protected SetMeasureUnitCommand(final Integer value) {
		super();

		this.mValue = value;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static SetMeasureUnitCommand create(final Integer value) {
		return new SetMeasureUnitCommand(value);
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return (this.mValue == 1 ? "simpunits" : "ssiunits");
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}
