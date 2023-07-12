package com.technogym.android.myrun.sdk.workout.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;

/**
 * This is the {@link INotifier} related to "pause count-down" notifications.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public final class PauseCountDownNotifier extends Notifier<IWorkoutListener> {

	private final Integer mCountDown;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor and initializing the class properties.
	 * 
	 * @param countDown
	 *            : the current value of count-down
	 * */
	protected PauseCountDownNotifier(final Integer countDown) {
		super();

		this.mCountDown = countDown;
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static PauseCountDownNotifier create(final Integer countDown) {
		return new PauseCountDownNotifier(countDown);
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final IWorkoutListener listener) {
		listener.onCountDownPaused(this.mCountDown);
	}

	// }

}