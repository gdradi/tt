package com.technogym.android.myrun.sdk.system.commands.myrun2020;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class MyRunMainboardBtVersionCommand extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * */
	protected MyRunMainboardBtVersionCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static MyRunMainboardBtVersionCommand create() {
		return new MyRunMainboardBtVersionCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "NRFFWVER";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }
}
