package com.technogym.android.myrun.sdk.workout.commands;

import java.util.ArrayList;

import com.technogym.android.myrun.sdk.communication.commands.ApplicativeCommand;
import com.technogym.android.myrun.sdk.support.DecimalsFormatHelper;

/**
 * It's the final implementation of an applicative work-out command which sets the gradient of the equipment to a
 * specific value.<br/>
 * It expects no direct answer from the equipment: the gradient will be updated in the equipment status notification if
 * everything went fine.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class SetGradientCommand extends ApplicativeCommand {

	private final Integer mGradient;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param gradient
	 *            : the gradient value to set, multiplied for 10
	 * */
	protected SetGradientCommand(final Integer gradient) {
		super();

		this.mGradient = gradient;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param gradient
	 *            : the gradient value to set, multiplied for 10
	 * */
	public static SetGradientCommand create(final Integer gradient) {
		return new SetGradientCommand(gradient);
	}

	// }

	// { ICommand implementation

	@Override
	protected String getSource() {
		return "DEV";
	}

	@Override
	protected String getCode() {
		return "025";
	}

	@Override
	protected Iterable<String> getParameters() {
		final ArrayList<String> params = new ArrayList<String>();
		params.add(DecimalsFormatHelper.format(this.mGradient / 10.0));
		return params;
	}

	// }

}