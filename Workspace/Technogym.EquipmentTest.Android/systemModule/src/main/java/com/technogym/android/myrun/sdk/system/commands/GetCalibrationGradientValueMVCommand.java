package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which achieves the retrieval of the values in the
 * gradient calibration table vs mV of the equipment.<br/>
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public class GetCalibrationGradientValueMVCommand extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * */
	protected GetCalibrationGradientValueMVCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static GetCalibrationGradientValueMVCommand create() {
		return new GetCalibrationGradientValueMVCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "RKADC";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}