package com.technogym.android.myrun.sdk.workout.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;

/**
 * This is the {@link INotifier} related to "distance" notifications.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public final class EquipmentDistanceNotifier extends Notifier<IWorkoutListener> {

	private final Integer mDistance;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param version
	 *            : the covered distance received from the equipment
	 * */
	protected EquipmentDistanceNotifier(final Integer distance) {
		super();

		this.mDistance = distance;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param version
	 *            : the parameter required by class constructor
	 * */
	public static EquipmentDistanceNotifier create(final Integer distance) {
		return new EquipmentDistanceNotifier(distance);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final IWorkoutListener listener) {
		listener.onDistanceValueRetrieved(this.mDistance);
	}

	// }

}