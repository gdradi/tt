package com.technogym.android.myrun.sdk.system.commands;

import java.util.ArrayList;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which achieves the initialization of the sensor "up" of the
 * equipment.<br/>
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public class InitSensorUpCommand extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor.
	 * */
	protected InitSensorUpCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static InitSensorUpCommand create() {
		return new InitSensorUpCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "initdsens_up";
	}

	@Override
	protected Iterable<String> getParameters() {
		final ArrayList<String> params = new ArrayList<String>();
		params.add(String.valueOf(100));
		params.add(String.valueOf(3));
		params.add(String.valueOf(2));
		params.add(String.valueOf(1));
		return params;
	}

	// }

}
