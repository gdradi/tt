package com.technogym.android.myrun.sdk.workout.commands;

import java.util.ArrayList;

import com.technogym.android.myrun.sdk.communication.commands.ApplicativeCommand;

/**
 * It's the final implementation of an applicative work-out command which pause the equipment.<br/>
 * It expects no direct answer from the equipment: the state will be updated in the equipment status notification if
 * everything went fine.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public final class PauseCommand extends ApplicativeCommand {

	protected final Integer mSeconds;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param seconds
	 *            : the total seconds during whose the equipment pause
	 * */
	protected PauseCommand(final Integer seconds) {
		super();
		this.mSeconds = seconds;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param seconds
	 *            : the total seconds during whose the equipment pause
	 * */
	public static PauseCommand create(final Integer seconds) {
		return new PauseCommand(seconds);
	}

	/**
	 * It's a static factory method which returns a new instance of the class.<br/>
	 * The paused status will endure for sixty seconds.
	 * */
	public static PauseCommand create() {
		return new PauseCommand(60);
	}

	// }

	// { ICommand implementation

	@Override
	protected String getSource() {
		return "DEV";
	}

	@Override
	protected String getCode() {
		return "088";
	}

	@Override
	protected Iterable<String> getParameters() {
		ArrayList<String> params = new ArrayList<String>();
		params.add(mSeconds.toString());
		return params;
	}

	// }

}
