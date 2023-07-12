package com.technogym.android.myrun.sdk.workout.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;

/**
 * This is the {@link INotifier} related to "heart rate" notifications.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public final class EquipmentHeartRateNotifier extends Notifier<IWorkoutListener> {

	private final Integer mHeartRate;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param version
	 *            : the current heart rate received from the equipment
	 * */
	protected EquipmentHeartRateNotifier(final Integer heartRate) {
		super();

		this.mHeartRate = heartRate;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * 
	 * @param version
	 *            : the parameter required by class constructor
	 * */
	public static EquipmentHeartRateNotifier create(final Integer heartRate) {
		return new EquipmentHeartRateNotifier(heartRate);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final IWorkoutListener listener) {
		listener.onHeartRateValueRetrieved(this.mHeartRate);
	}

	// }

}