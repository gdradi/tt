package com.technogym.android.myrun.sdk.workout.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;

/**
 * This is the {@link INotifier} related to "start/stop" notifications.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public final class MyRunButtonReleaseNotifier extends Notifier<IWorkoutListener> {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor.
	 * */
	protected MyRunButtonReleaseNotifier() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static MyRunButtonReleaseNotifier create() {
		return new MyRunButtonReleaseNotifier();
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final IWorkoutListener listener) {
		listener.onButtonRelease();
	}

	// }

}