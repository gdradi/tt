package com.technogym.android.myrun.sdk.workout.commands.myrun2020;

import com.technogym.android.myrun.sdk.communication.commands.ApplicativeCommand;
import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;
import com.technogym.android.myrun.sdk.support.DecimalsFormatHelper;

import java.util.ArrayList;

/**
 * It's the final implementation of an applicative work-out command which sets the gradient of the equipment to a
 * specific value.<br/>
 * It expects no direct answer from the equipment: the gradient will be updated in the equipment status notification if
 * everything went fine.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class MyRunSetGradientCommand extends SystemCommand {

	private final Integer mGradient;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param gradient
	 *            : the gradient value to set, multiplied for 10
	 * */
	protected MyRunSetGradientCommand(final Integer gradient) {
		super();

		this.mGradient = gradient;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param gradient
	 *            : the gradient value to set, multiplied for 10
	 * */
	public static MyRunSetGradientCommand create(final Integer gradient) {
		return new MyRunSetGradientCommand(gradient);
	}

	// }

	// { ICommand implementation

	@Override
	protected Iterable<String> getParameters() {
		final ArrayList<String> params = new ArrayList<String>();
		params.add(DecimalsFormatHelper.format(this.mGradient / 10.0));
		return params;
	}

	@Override
	protected String getName() {
		return "SET_GRADIENT";
	}

	// }

}