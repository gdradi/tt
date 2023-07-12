package com.technogym.android.myrun.sdk.workout.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;

/**
 * This is the {@link INotifier} related to "speed" notifications.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public final class EquipmentSpeedNotifier extends Notifier<IWorkoutListener> {

	private final Integer mSpeed;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param version
	 *            : the current speed received from the equipment
	 * */
	protected EquipmentSpeedNotifier(final Integer speed) {
		super();

		this.mSpeed = speed;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param version
	 *            : the parameter required by class constructor
	 * */
	public static EquipmentSpeedNotifier create(final Integer speed) {
		return new EquipmentSpeedNotifier(speed);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final IWorkoutListener listener) {
		listener.onSpeedValueRetrieved(this.mSpeed);
	}

	// }

}