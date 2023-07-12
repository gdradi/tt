package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

import java.util.ArrayList;

/**
 * It's the final implementation of a system command which update the values in the
 * gradient calibration table vs mV of the equipment.<br/>
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public class SetCalibrationGradientValueCCommand extends SystemCommand {

	private final String valC;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 *
	 * @param valueC
	 * 				: the third value of the calibration corner of the mV ADC gradient
	 */
	protected SetCalibrationGradientValueCCommand(final String valueC) {
		super();
		this.valC = valueC;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 *
	 * @param valueC
	 * 				: the third value of the calibration corner of the mV ADC gradient
	 * */
	public static SetCalibrationGradientValueCCommand create(final String valueC) {
		return new SetCalibrationGradientValueCCommand(valueC);
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "SKADC_C";
	}

	@Override
	protected Iterable<String> getParameters() {
		final ArrayList<String> params = new ArrayList<String>();
		params.add(this.valC);
		return params;
	}

	// }

}