package com.technogym.android.myrun.sdk.workout.commands;

import com.technogym.android.myrun.sdk.communication.commands.ApplicativeCommand;

/**
 * It's the final implementation of an applicative work-out command which stops the equipment.<br/>
 * It expects no direct answer from the equipment: the state will be updated in the equipment status notification - and
 * the training data will update as well - if everything went fine.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class StopWorkoutCommand extends ApplicativeCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor.
	 * */
	protected StopWorkoutCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static StopWorkoutCommand create() {
		return new StopWorkoutCommand();
	}

	// }

	// { ICommand implementation

	@Override
	protected String getSource() {
		return "DEV";
	}

	@Override
	protected String getCode() {
		return "054";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}
