package com.technogym.android.myrun.sdk.workout.commands;

import com.technogym.android.myrun.sdk.communication.commands.ApplicativeCommand;

/**
 * It's the final implementation of an applicative work-out command which achieves the decreasing of equipment speed.<br/>
 * It expects no direct answer from the equipment: the speed value will be updated in the equipment status notification
 * if everything went fine.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class DecreaseSpeedCommand extends ApplicativeCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor.
	 * */
	protected DecreaseSpeedCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static DecreaseSpeedCommand create() {
		return new DecreaseSpeedCommand();
	}

	// }

	// { ICommand implementation

	@Override
	protected String getSource() {
		return "DEV";
	}

	@Override
	protected String getCode() {
		return "005";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}
