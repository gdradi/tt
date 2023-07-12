package com.technogym.android.myrun.sdk.system.commands;

import java.util.ArrayList;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which update the values in the
 * gradient calibration table vs mV of the equipment.<br/>
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public class SetCalibrationGradientValueMVCommand extends SystemCommand {

	private final String valA;
	private final String valB;
	private final String valC;
	
	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * 
	 * @param valueA
	 * 				: the first value of the calibration corner of the mV ADC gradient
	 * @param valueB
	 * 				: the second value of the calibration corner of the mV ADC gradient
	 * @param valueC
	 * 				: the third value of the calibration corner of the mV ADC gradient
	 */
	protected SetCalibrationGradientValueMVCommand(final String valueA, final String valueB, final String valueC) {
		super();
		
		this.valA = valueA;
		this.valB = valueB;
		this.valC = valueC;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param valueA
	 * 				: the first value of the calibration corner of the mV ADC gradient
	 * @param valueB
	 * 				: the second value of the calibration corner of the mV ADC gradient
	 * @param valueC
	 * 				: the third value of the calibration corner of the mV ADC gradient
	 * */
	public static SetCalibrationGradientValueMVCommand create(final String valueA, final String valueB, final String valueC) {
		return new SetCalibrationGradientValueMVCommand(valueA, valueB, valueC);
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "SKADC";
	}

	@Override
	protected Iterable<String> getParameters() {
		final ArrayList<String> params = new ArrayList<String>();
		params.add(this.valA);
		params.add(this.valB);
		params.add(this.valC);
		return params;
	}

	// }

}