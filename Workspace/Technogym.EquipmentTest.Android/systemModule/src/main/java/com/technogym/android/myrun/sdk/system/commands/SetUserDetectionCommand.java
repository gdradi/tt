package com.technogym.android.myrun.sdk.system.commands;

import java.util.ArrayList;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which achieves the set of user detection on the equipment.<br/>
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class SetUserDetectionCommand extends SystemCommand {

	private final Integer mValue;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * 
	 * @param value
	 *            : the user detection sensibility
	 * */
	protected SetUserDetectionCommand(final Integer value) {
		super();

		this.mValue = value;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static SetUserDetectionCommand create(final Integer value) {
		return new SetUserDetectionCommand(value);
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "srundetminsp";
	}

	@Override
	protected Iterable<String> getParameters() {
		final ArrayList<String> params = new ArrayList<String>();
		params.add(String.valueOf(this.mValue));
		return params;
	}

	// }

}
