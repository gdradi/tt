package com.technogym.android.myrun.sdk.workout.notifiers;

import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;

/**
 * This is the {@link INotifier} related to "gradient-down joy-stick pressed" notifications.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public final class JoystickGradientDownNotifier extends Notifier<IWorkoutListener> {

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It does nothing more than calling {@code super} constructor.
	 * */
	protected JoystickGradientDownNotifier() {
		super();
	}

	/**
	 * It's a static factory method which returns a new instance of the class.
	 * */
	public static JoystickGradientDownNotifier create() {
		return new JoystickGradientDownNotifier();
	}

	// }

	// { INotifier implementation

	@Override
	public void notify(final IWorkoutListener listener) {
		listener.onJoystickGradientDownPressed();
	}

	// }

}