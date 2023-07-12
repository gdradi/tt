package com.technogym.myrow.models.actions;

import android.content.Context;
import android.content.Intent;

import com.technogym.myrow.listeners.IActionListener;

/**
 * This is the base class for every action required by the equipment test process. It's abstract because some behaviour
 * is specific to the given action itself.
 *
 * @author Federico Foschini
 * @version 1.0
 * */
public abstract class Action implements IAction {

	protected static final String OPEN_APPLICATION_COMMAND = "open_external_app";

	protected final Context mContext;
	protected IActionListener mListener;

	// { Construction

	/**
	 * Base constructor for an {@link Action}.
	 * 
	 * @param context
	 *            : the application's {@link Context} within it gets executed.
	 * */
	protected Action(final Context context) {
		super();

		this.mContext = context;
		this.mListener = null;
	}

	// }

	// { IAction implementation

	@Override
	public void setListener(final IActionListener listener) {
		this.mListener = listener;
	}

	@Override
	public abstract void execute(final String data);

	@Override
	public boolean executeCommand(final String commandID) {
		if (commandID.equals(OPEN_APPLICATION_COMMAND)) {
			this.mContext.startActivity(new Intent(commandID));
			return true;
		}

		return false;
	}

	@Override
	public void stop() {
		// INF: empty
	}

	// }

}
