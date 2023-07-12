package com.technogym.android.myrun.sdk.system.listeners.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

/**
 * This is the {@link INotifier} related to "calibration gradient values vs mV" notifications.
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public class GetCalibrationGradientValueMVNotifier extends Notifier<ISystemListener> {

	private final String mResult;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param result
	 *            : the calibration gradient values vs mV
	 * */
	protected GetCalibrationGradientValueMVNotifier(final String result) {
		super();

		this.mResult = result;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param result
	 *            : the parameter required by class constructor
	 * */
	public static GetCalibrationGradientValueMVNotifier create(final String result) {
		return new GetCalibrationGradientValueMVNotifier(result);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final ISystemListener listener) {
		listener.onCalibrationGradientValueMVRetreived(this.mResult);
	}

	// }

}
