package com.technogym.android.myrun.sdk.workout.commands;

import java.util.ArrayList;

import com.technogym.android.myrun.sdk.communication.commands.ApplicativeCommand;

/**
 * It's the final implementation of an applicative work-out command which enables the joy-sticks of the equipment during
 * the remote mode.<br/>
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class SetJoystickModeCommand extends ApplicativeCommand {

	protected final Boolean mIsEnabled;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor.
	 * */
	protected SetJoystickModeCommand(final Boolean isEnabled) {
		super();
		this.mIsEnabled = isEnabled;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static SetJoystickModeCommand create(final Boolean isEnabled) {
		return new SetJoystickModeCommand(isEnabled);
	}

	// }

	// { ICommand implementation

	@Override
	protected String getSource() {
		return "DEV";
	}

	@Override
	protected String getCode() {
		return "032";
	}

	@Override
	protected Iterable<String> getParameters() {
		ArrayList<String> params = new ArrayList<String>();
		if (this.mIsEnabled) {
			params.add("0");
		} else {
			params.add("1");
		}
		return params;
	}

	// }

}