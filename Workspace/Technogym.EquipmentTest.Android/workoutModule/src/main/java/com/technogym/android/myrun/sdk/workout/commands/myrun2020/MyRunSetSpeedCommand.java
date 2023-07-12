package com.technogym.android.myrun.sdk.workout.commands.myrun2020;

import com.technogym.android.myrun.sdk.communication.commands.ApplicativeCommand;
import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;
import com.technogym.android.myrun.sdk.support.DecimalsFormatHelper;

import java.util.ArrayList;

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
public final class MyRunSetSpeedCommand extends SystemCommand {

	private static final int SPEED_MAX_VALUE = 200;
	private static final int SPEED_MIN_VALUE = 8;

	protected final Integer mSpeed;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param speed
	 *            : the speed value to set, multiplied for 10
	 * */
	protected MyRunSetSpeedCommand(final Integer speed) {
		if (speed > SPEED_MAX_VALUE || speed < SPEED_MIN_VALUE) {
			// TODO throw exception
			this.mSpeed = speed;
		} else {
			this.mSpeed = speed;
		}
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param speed
	 *            : the speed value to set, multiplied for 10
	 * */
	public static MyRunSetSpeedCommand create(final Integer speed) {
		return new MyRunSetSpeedCommand(speed);
	}

	// }

	// { ICommand implementation

	@Override
	protected Iterable<String> getParameters() {
		final ArrayList<String> params = new ArrayList<String>();
		params.add(DecimalsFormatHelper.format(this.mSpeed / 10.0));
		return params;
	}

	@Override
	protected String getName() {
		return "SET_SPEED";
	}

	// }

}
