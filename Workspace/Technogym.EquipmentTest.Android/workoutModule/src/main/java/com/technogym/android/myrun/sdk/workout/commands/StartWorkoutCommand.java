package com.technogym.android.myrun.sdk.workout.commands;

import java.util.ArrayList;

import com.technogym.android.myrun.sdk.communication.commands.ApplicativeCommand;
import com.technogym.android.myrun.sdk.support.DecimalsFormatHelper;

/**
 * It's the final implementation of an applicative work-out command which starts the equipment.<br/>
 * It expects no direct answer from the equipment: the state will be updated in the equipment status notification - and
 * the training data will update as well - if everything went fine.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class StartWorkoutCommand extends ApplicativeCommand {

	private final Integer mSpeed;
	private final Integer mGradient;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param speed
	 *            : the speed the equipment has to reach after starting, multiplied for 10
	 * @param gradient
	 *            : the gradient the equipment has to reach after starting, multiplied for 10
	 * */
	protected StartWorkoutCommand(final Integer speed, final Integer gradient) {
		super();

		this.mSpeed = speed;
		this.mGradient = gradient;
	}

	/**
	 * It's a static factory method which returns a new instance of the class, with default values for initial speed and
	 * gradient.
	 * */
	public static StartWorkoutCommand createEmpty() {
		return new StartWorkoutCommand(Integer.valueOf(0), Integer.valueOf(0));
	}

	/**
	 * It's a static factory method which returns a new instance of the class, with default value for initial gradient.
	 * 
	 * @param speed
	 *            : the speed the equipment has to reach after starting, multiplied for 10
	 * */
	public static StartWorkoutCommand createWithSpeed(final Integer speed) {
		return new StartWorkoutCommand(speed, Integer.valueOf(0));
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param speed
	 *            : the speed the equipment has to reach after starting, multiplied for 10
	 * @param gradient
	 *            : the gradient the equipment has to reach after starting, multiplied for 10
	 * */
	public static StartWorkoutCommand create(final Integer speed, final Integer gradient) {
		return new StartWorkoutCommand(speed, gradient);
	}

	// }

	// { ICommand implementation

	@Override
	protected String getSource() {
		return "DEV";
	}

	@Override
	protected String getCode() {
		return "053";
	}

	@Override
	protected Iterable<String> getParameters() {
		final ArrayList<String> params = new ArrayList<String>();
		params.add(DecimalsFormatHelper.format(this.mSpeed / 10.0));
		params.add(DecimalsFormatHelper.format(this.mGradient / 10.0));
		return params;
	}

	// }

}