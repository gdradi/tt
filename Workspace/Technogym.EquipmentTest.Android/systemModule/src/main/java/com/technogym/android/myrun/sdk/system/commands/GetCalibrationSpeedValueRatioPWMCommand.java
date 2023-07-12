package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which achieves the retrieval of the values in the
 * speed calibration table vs Ratio PWM of the equipment.<br/>
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public class GetCalibrationSpeedValueRatioPWMCommand extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * */
	protected GetCalibrationSpeedValueRatioPWMCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static GetCalibrationSpeedValueRatioPWMCommand create() {
		return new GetCalibrationSpeedValueRatioPWMCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "RKPWM";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }

}