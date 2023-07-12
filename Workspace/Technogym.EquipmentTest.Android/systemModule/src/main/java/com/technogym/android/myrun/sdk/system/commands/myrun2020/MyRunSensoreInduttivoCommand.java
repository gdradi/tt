package com.technogym.android.myrun.sdk.system.commands.myrun2020;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which achieves the retrieval of the status of
 * the equipment's down sensor.<br/>
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public class MyRunSensoreInduttivoCommand extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * */
	protected MyRunSensoreInduttivoCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static MyRunSensoreInduttivoCommand create() {
		return new MyRunSensoreInduttivoCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "RIS_VAL";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }
}
