package com.technogym.android.myrun.sdk.workout.proxies;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.communication.proxies.IEquipmentProxy;
import com.technogym.android.myrun.sdk.core.proxies.ICoreProxy;
import com.technogym.android.myrun.sdk.status.proxies.IStatusProxy;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;

/**
 * It's the interface for an {@link IEquipmentProxy} which manages the applicative training functionalities and allows
 * to communicate properly with the equipment.<br/>
 * <br/>
 * It resolves the generic {@code TListener} with an appropriate {@link IWorkoutListener}, so that external components
 * can register themselves for work-out notification events.<br/>
 * <br/>
 * All the commands available through this kind of proxy need the equipment to be in remote mode. Have a look at
 * {@link ICoreProxy#enterRemoteMode()}.<br/>
 * If there's the need to know the current work-out's data - such as speed, gradient, heart rate... - just register to
 * {@link IStatusProxy} for equipment status notifications.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public interface IWorkoutProxy extends IEquipmentProxy<IWorkoutListener> {

	/**
	 * This method sends to the equipment a request for speed increase.<br/>
	 * This command does not expect any response.
	 * */
	public void increaseSpeed() throws WriteNotAllowedException;

	/**
	 * This method sends to the equipment a request for speed decrease. <br/>
	 * This command does not expect any response.
	 * */
	public void decreaseSpeed() throws WriteNotAllowedException;

	/**
	 * This method sends to the equipment a request for speed set.<br/>
	 * It's equivalent to {@link #setSpeed(Integer, Integer)}, but here a default acceleration value is sent.<br/>
	 * This command does not expect any response.
	 * 
	 * @param speed
	 *            : the speed to set, multiplied for 10
	 * */
	public void setSpeed(final Integer speed) throws WriteNotAllowedException;

	/**
	 * This method sends to the equipment a request for speed set.<br/>
	 * It differs from the {@link #setSpeed(Integer)} method because it allows to specify the acceleration to use for
	 * reaching the target speed.<br/>
	 * This command does not expect any response.
	 * 
	 * @param speed
	 *            : the speed to set, multiplied for 10
	 * @param acceleration
	 *            : the acceleration to set, multiplied for 10
	 * */
	public void setSpeed(final Integer speed, final Integer acceleration) throws WriteNotAllowedException;

	/**
	 * This method sends to the equipment a request for gradient increase.<br/>
	 * This command does not expect any response.
	 * */
	public void increaseGradient() throws WriteNotAllowedException;

	/**
	 * This method sends to the equipment a request for gradient decrease.<br/>
	 * This command does not expect any response.
	 * */
	public void decreaseGradient() throws WriteNotAllowedException;

	/**
	 * This method sends to the equipment a request for gradient set.<br/>
	 * This command does not expect any response.
	 * 
	 * @param gradient
	 *            : the gradient to set, multiplied for 10
	 * */
	public void setGradient(final Integer gradient) throws WriteNotAllowedException;

	/**
	 * This method sends to the equipment a request for pausing the work-out.<br/>
	 * This command does not expect any response.
	 * */
	public void pause() throws WriteNotAllowedException;

	/**
	 * This method sends to the equipment a request for resuming the work-out from paused status.<br/>
	 * This command does not expect any response.
	 * */
	public void resumeFromPause() throws WriteNotAllowedException;

	/**
	 * This method sends to the equipment a request for starting the cool-down phase.<br/>
	 * This command does not expect any response.
	 * */
	public void startCooldown() throws WriteNotAllowedException;

	/**
	 * This method sends to the equipment a request for stopping the work-out.<br/>
	 * This command does not expect any response.
	 * */
	public void stopWorkout() throws WriteNotAllowedException;

	/**
	 * This method sends to the equipment a request for starting the work-out with the desired speed and gradient.<br/>
	 * This command does not expect any response.
	 * 
	 * @param speed
	 *            : the value of the desired speed, multiplied for 10
	 * @param gradient
	 *            : the value of the desired gradient, multiplied for 10
	 * */
	public void startWorkout(final Integer speed, final Integer gradient) throws WriteNotAllowedException;

	/**
	 * This method sends to the equipment a request for enabling or disabling the joy-sticks.<br/>
	 * This command does not expect any response.
	 * 
	 * @param enable
	 *            : if {@code true} it should enable joy-sticks
	 * */
	public void setJoystickMode(final Boolean enable) throws WriteNotAllowedException;
}
