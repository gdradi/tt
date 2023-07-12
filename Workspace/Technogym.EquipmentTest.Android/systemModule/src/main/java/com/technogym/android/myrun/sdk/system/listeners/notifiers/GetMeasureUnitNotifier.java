package com.technogym.android.myrun.sdk.system.listeners.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;

/**
 * This is the {@link INotifier} related to "set the unit of measure" notifications.
 * 
 * @author Federico Foschini
 * @version 1.0
 * */
public final class GetMeasureUnitNotifier extends Notifier<ISystemListener> {

	private final String mMeasureUnit;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param measureUnit
	 *            : the measure unit retreived from the equipment
	 * */
	protected GetMeasureUnitNotifier(final String measureUnit) {
		super();

		this.mMeasureUnit = measureUnit;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param measureUnit
	 *            : the parameter required by class constructor
	 * */
	public static GetMeasureUnitNotifier create(final String measureUnit) {
		return new GetMeasureUnitNotifier(measureUnit);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final ISystemListener listener) {
		listener.onMeasureUnitRetreived(this.mMeasureUnit);
	}

	// }

}
