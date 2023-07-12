package com.technogym.android.myrun.sdk.workout.commands.myrun2020;

import com.technogym.android.myrun.sdk.communication.commands.ApplicativeCommand;
import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of an applicative work-out command which starts the equipment.<br/>
 * It expects no direct answer from the equipment: the state will be updated in the equipment status notification - and
 * the training data will update as well - if everything went fine.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class MyRunButtonReleaseCommand extends SystemCommand {


	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * */
	protected MyRunButtonReleaseCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class, with default values for initial speed and
	 * gradient.
	 * */
	public static MyRunButtonReleaseCommand createEmpty() {
		return new MyRunButtonReleaseCommand();
	}

	/**
	 * It's a static factory method which returns a new instance of the class, with default value for initial gradient.
	 * 
	 * @param speed
	 *            : the speed the equipment has to reach after starting, multiplied for 10
	 * */
	public static MyRunButtonReleaseCommand createWithSpeed(final Integer speed) {
		return new MyRunButtonReleaseCommand();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static MyRunButtonReleaseCommand create() {
		return new MyRunButtonReleaseCommand();
	}

	// }

	// { ICommand implementation

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	@Override
	protected String getName() {
		return "SSR";
	}

	// }

}