package com.technogym.android.myrun.sdk.system.commands.myrun2020;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class MyRunBtVersionCommand extends SystemCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing class properties.
	 * */
	protected MyRunBtVersionCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static MyRunBtVersionCommand create() {
		return new MyRunBtVersionCommand();
	}

	// }

	// { SystemCommand abstract methods implementation

	@Override
	protected String getName() {
		return "RGYMKIT_VERSION";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }
}
