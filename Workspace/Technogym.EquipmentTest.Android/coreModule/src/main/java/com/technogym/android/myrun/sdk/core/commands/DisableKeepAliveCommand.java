package com.technogym.android.myrun.sdk.core.commands;

import com.technogym.android.myrun.sdk.communication.commands.ApplicativeCommand;

public class DisableKeepAliveCommand extends ApplicativeCommand {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor.
	 * */
	protected DisableKeepAliveCommand() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static DisableKeepAliveCommand create() {
		return new DisableKeepAliveCommand();
	}

	// }

	// { ICommand implementation

	@Override
	protected String getSource() {
		return "DEV";
	}

	@Override
	protected String getCode() {
		return "060";
	}

	@Override
	protected Iterable<String> getParameters() {
		return null;
	}

	// }
}
