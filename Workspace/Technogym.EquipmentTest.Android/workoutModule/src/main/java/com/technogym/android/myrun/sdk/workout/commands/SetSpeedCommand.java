package com.technogym.android.myrun.sdk.workout.commands;

import java.util.ArrayList;

import com.technogym.android.myrun.sdk.communication.commands.ApplicativeCommand;
import com.technogym.android.myrun.sdk.support.DecimalsFormatHelper;

/**
 * It's the final implementation of an applicative work-out command which sets the speed of the equipment to a specific
 * value.<br/>
 * A desired acceleration / deceleration value can be set too: otherwise the speed changes with a default acceleration
 * value.<br/>
 * It expects no direct answer from the equipment: the speed will be updated in the equipment status notification if
 * everything went fine.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class SetSpeedCommand extends ApplicativeCommand {

	private static final int SPEED_MAX_VALUE = 200;
	private static final int SPEED_MIN_VALUE = 8;

	protected final Integer mSpeed;

	protected final Integer mAcceleration;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param speed
	 *            : the speed value to set, multiplied for 10
	 * @param acceleration
	 *            : the acceleration desired for speed change, multiplied for 10
	 * */
	protected SetSpeedCommand(final Integer speed, final Integer acceleration) {
		if (speed > SPEED_MAX_VALUE || acceleration < SPEED_MIN_VALUE) {
			// TODO throw exception
			this.mSpeed = speed;
			this.mAcceleration = acceleration;
		} else {
			this.mSpeed = speed;
			this.mAcceleration = acceleration;
		}
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param speed
	 *            : the speed value to set, multiplied for 10
	 * @param acceleration
	 *            : the acceleration desired for speed change, multiplied for 10
	 * */
	public static SetSpeedCommand create(final Integer speed, final Integer acceleration) {
		return new SetSpeedCommand(speed, acceleration);
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param speed
	 *            : the speed value to set, multiplied for 10
	 * */
	public static SetSpeedCommand create(final Integer speed) {
		return new SetSpeedCommand(speed, 18);
	}

	// }

	// { ICommand implementation

	@Override
	protected String getSource() {
		return "DEV";
	}

	@Override
	protected String getCode() {
		return "024";
	}

	@Override
	protected Iterable<String> getParameters() {
		final ArrayList<String> params = new ArrayList<String>();
		params.add(DecimalsFormatHelper.format(this.mSpeed / 10.0));
		params.add(DecimalsFormatHelper.format(this.mAcceleration / 10.0));
		return params;
	}

	// }

}
