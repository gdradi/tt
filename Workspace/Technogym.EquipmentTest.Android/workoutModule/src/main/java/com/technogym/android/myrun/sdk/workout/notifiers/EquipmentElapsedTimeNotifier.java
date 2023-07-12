package com.technogym.android.myrun.sdk.workout.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;

/**
 * This is the {@link INotifier} related to "elapsed time" notifications.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public final class EquipmentElapsedTimeNotifier extends Notifier<IWorkoutListener> {

	private final Integer mElapsedTime;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param version
	 *            : the total elapsed time received from the equipment
	 * */
	protected EquipmentElapsedTimeNotifier(final Integer elapsedTime) {
		super();

		this.mElapsedTime = elapsedTime;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param version
	 *            : the parameter required by class constructor
	 * */
	public static EquipmentElapsedTimeNotifier create(final Integer elapsedTime) {
		return new EquipmentElapsedTimeNotifier(elapsedTime);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final IWorkoutListener listener) {
		listener.onElapsedTimeValueRetrieved(this.mElapsedTime);
	}

	// }

}