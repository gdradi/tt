package com.technogym.android.myrun.sdk.workout.listeners;

import com.technogym.android.myrun.sdk.communication.listeners.IListener;
import com.technogym.android.myrun.sdk.workout.proxies.IWorkoutProxy;

/**
 * This is the interface that a listener must implement if it needs to be notified by the {@link IWorkoutProxy}.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public interface IWorkoutListener extends IListener {

	// { Workout

	/**
	 * This method gets called when the equipment speed is retrieved.
	 * 
	 * @param speed
	 *            : the retrieved speed
	 * */
	public void onSpeedValueRetrieved(final Integer speed);

	/**
	 * This method gets called when the equipment gradient is retrieved.
	 * 
	 * @param gradient
	 *            : the retrieved gradient
	 * */
	public void onGradientValueRetrieved(final Integer gradient);

	/**
	 * This method gets called when the equipment heart rate is retrieved.
	 * 
	 * @param heartRate
	 *            : the retrieved heart rate
	 * */
	public void onHeartRateValueRetrieved(final Integer heartRate);

	/**
	 * This method gets called when the equipment distance is retrieved.
	 * 
	 * @param distance
	 *            : the retrieved distance
	 * */
	public void onDistanceValueRetrieved(final Integer distance);

	/**
	 * This method gets called when the equipment elapsed time is retrieved.
	 * 
	 * @param elapsedTime
	 *            : the retrieved elapsed time
	 * */
	public void onElapsedTimeValueRetrieved(final Integer elapsedTime);

	/**
	 * This method gets called when the work-out status turn to "completed".
	 * */
	public void onWorkoutCompleted();

	/**
	 * This method gets called when the count-down gets paused.
	 * 
	 * @param countDown
	 *            : the current count-down value
	 * */
	public void onCountDownPaused(final Integer countDown);

	// }

	// { Physical joystick and buttons

	/**
	 * This method gets called when the "gradient-up" joy-stick gets pressed.
	 * */
	public void onJoystickGradientUpPressed();

	/**
	 * This method gets called when the "gradient-up" joy-stick is released.
	 * */
	public void onJoystickGradientUpReleased();

	/**
	 * This method gets called when the "gradient-down" joy-stick gets pressed.
	 * */
	public void onJoystickGradientDownPressed();

	/**
	 * This method gets called when the "gradient-down" joy-stick is released.
	 * */
	public void onJoystickGradientDownReleased();

	/**
	 * This method gets called when the "speed-up" joy-stick gets pressed.
	 * */
	public void onJoystickSpeedUpPressed();

	/**
	 * This method gets called when the "speed-up" joy-stick is released.
	 * */
	public void onJoystickSpeedUpReleased();

	/**
	 * This method gets called when the "speed-down" joy-stick gets pressed.
	 * */
	public void onJoystickSpeedDownPressed();

	/**
	 * This method gets called when the "speed-down" joy-stick is released.
	 * */
	public void onJoystickSpeedDownReleased();

	/**
	 * This method gets called when the "start / stop" button gets pressed.
	 * */
	public void onStartStopButtonPressed();

    void onButtonPress();

	void onButtonRelease();

	// }

}
