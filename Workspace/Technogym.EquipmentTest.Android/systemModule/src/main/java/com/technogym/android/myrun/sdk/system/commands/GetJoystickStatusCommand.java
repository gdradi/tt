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
public class GetJoystickStatusCommand extends SystemCommand {

	private final String param;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 *
	 * @param param
	 * 				: 0,1,2,3
	 *
	 */
	protected GetJoystickStatusCommand(final String param) {
		super();
		
		this.param = param;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param param
	 * 				: 0,1,2,3
	 *
	 * */
	public static GetJoystickStatusCommand create(final String param) {
		return new GetJoystickStatusCommand(param);
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "RIO";
	}

	@Override
	protected Iterable<String> getParameters() {
		final ArrayList<String> params = new ArrayList<String>();
		params.add(this.param);
		return params;
	}

	// }

}