package com.technogym.equipmenttest.library.managers;

import android.content.Context;
import android.os.Handler;

import com.technogym.equipmenttest.library.bridges.web.IJavascriptBridge;
import com.technogym.equipmenttest.library.models.Action;

/**
 * This class implements {@link ITestActionManager} and provides the default behaviour needed to correctly run the test
 * of an equipment.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public abstract class TestActionManager implements ITestActionManager {

	protected final Context mContext;
	protected int mExecutionTimeout;
	protected final IJavascriptBridge mJavascriptBridge;

	protected Action mCurrentAction;

	// { Construction

	protected TestActionManager(final Context context, final IJavascriptBridge javascriptBridge) {
		super();

		this.mContext = context;
		this.mJavascriptBridge = javascriptBridge;

		this.mCurrentAction = null;
	}

	// }

	// { ITestActionManager implementation

	@Override
	public void execute(final int id, final String data) {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (mCurrentAction != null) {
					mCurrentAction.stop();
				}

				mCurrentAction = initializeAction(id);
				if (mCurrentAction != null) {
					mCurrentAction.setListener(mJavascriptBridge);
					mCurrentAction.execute(data);
				}
			}
		}, 300);

	}

	@Override
	public void executeCommand(final String commandID) {
		if (this.mCurrentAction != null) {
			this.mCurrentAction.executeCommand(commandID);
		}
	}

	@Override
	public void killTest() {
		if (this.mCurrentAction != null) {
			this.mCurrentAction.stop();
		}
	}

	// }

	// { Private and protected methods

	/**
	 * This is a {@code protected} method which creates the proper {@link Action} corresponding to the input parameter.
	 * 
	 * @param id
	 *            : the identification number of the {@link Action}
	 * 
	 * @return the related {@link Action} if the id is profiled within the application, {@code null} otherwise.
	 * */
	protected abstract Action initializeAction(final int id);

	// }

}
