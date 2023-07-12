package com.technogym.android.myrun.sdk.workout.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;

/**
 * This is the {@link INotifier} related to "gradient" notifications.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public final class EquipmentGradientNotifier extends Notifier<IWorkoutListener> {

	private final Integer mGradient;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param version
	 *            : the current gradient received from the equipment
	 * */
	protected EquipmentGradientNotifier(final Integer gradient) {
		super();

		this.mGradient = gradient;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param version
	 *            : the parameter required by class constructor
	 * */
	public static EquipmentGradientNotifier create(final Integer gradient) {
		return new EquipmentGradientNotifier(gradient);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final IWorkoutListener listener) {
		listener.onGradientValueRetrieved(this.mGradient);
	}

	// }

}