package com.technogym.android.myrun.sdk.workout.proxies;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.communication.proxies.EquipmentProxy;
import com.technogym.android.myrun.sdk.connection.controllers.IEquipmentController;
import com.technogym.android.myrun.sdk.workout.commands.DecreaseGradientCommand;
import com.technogym.android.myrun.sdk.workout.commands.DecreaseSpeedCommand;
import com.technogym.android.myrun.sdk.workout.commands.IncreaseGradientCommand;
import com.technogym.android.myrun.sdk.workout.commands.IncreaseSpeedCommand;
import com.technogym.android.myrun.sdk.workout.commands.PauseCommand;
import com.technogym.android.myrun.sdk.workout.commands.ResumeFromPauseCommand;
import com.technogym.android.myrun.sdk.workout.commands.SetGradientCommand;
import com.technogym.android.myrun.sdk.workout.commands.SetJoystickModeCommand;
import com.technogym.android.myrun.sdk.workout.commands.SetSpeedCommand;
import com.technogym.android.myrun.sdk.workout.commands.StartCooldownCommand;
import com.technogym.android.myrun.sdk.workout.commands.StartWorkoutCommand;
import com.technogym.android.myrun.sdk.workout.commands.StopWorkoutCommand;
import com.technogym.android.myrun.sdk.workout.listeners.IWorkoutListener;
import com.technogym.android.myrun.sdk.workout.notificationRules.EquipmentDistanceNotificationRule;
import com.technogym.android.myrun.sdk.workout.notificationRules.EquipmentElapsedTimeNotificationRule;
import com.technogym.android.myrun.sdk.workout.notificationRules.EquipmentGradientNotificationRule;
import com.technogym.android.myrun.sdk.workout.notificationRules.EquipmentHeartRateNotificationRule;
import com.technogym.android.myrun.sdk.workout.notificationRules.EquipmentSpeedNotificationRule;
import com.technogym.android.myrun.sdk.workout.notificationRules.JoystickGradientDownNotificationRule;
import com.technogym.android.myrun.sdk.workout.notificationRules.JoystickGradientDownReleasedNotificationRule;
import com.technogym.android.myrun.sdk.workout.notificationRules.JoystickGradientUpNotificationRule;
import com.technogym.android.myrun.sdk.workout.notificationRules.JoystickGradientUpReleasedNotificationRule;
import com.technogym.android.myrun.sdk.workout.notificationRules.JoystickSpeedDownNotificationRule;
import com.technogym.android.myrun.sdk.workout.notificationRules.JoystickSpeedDownReleasedNotificationRule;
import com.technogym.android.myrun.sdk.workout.notificationRules.JoystickSpeedUpNotificationRule;
import com.technogym.android.myrun.sdk.workout.notificationRules.JoystickSpeedUpReleasedNotificationRule;
import com.technogym.android.myrun.sdk.workout.notificationRules.PauseCountDownNotificationRule;
import com.technogym.android.myrun.sdk.workout.notificationRules.StartStopNotificationRule;
import com.technogym.android.myrun.sdk.workout.notificationRules.WorkoutCompletedNotificationRule;

/**
 * This is a final implementation of {@link IWorkoutProxy} interface.<br/>
 * <br/>
 * It supports the following notification rules:
 * <ul>
 * <li>the {@link JoystickGradientUpReleasedNotificationRule}</li>
 * <li>the {@link JoystickGradientUpNotificationRule}</li>
 * <li>the {@link JoystickGradientDownNotificationRule}</li>
 * <li>the {@link JoystickGradientDownReleasedNotificationRule}</li>
 * <li>the {@link JoystickSpeedUpNotificationRule}</li>
 * <li>the {@link JoystickSpeedUpReleasedNotificationRule}</li>
 * <li>the {@link JoystickSpeedDownNotificationRule}</li>
 * <li>the {@link JoystickSpeedDownReleasedNotificationRule}</li>
 * <li>the {@link EquipmentSpeedNotificationRule}</li>
 * <li>the {@link EquipmentGradientNotificationRule}</li>
 * <li>the {@link EquipmentHeartRateNotificationRule}</li>
 * <li>the {@link EquipmentDistanceNotificationRule}</li>
 * <li>the {@link EquipmentElapsedTimeNotificationRule}</li>
 * <li>the {@link PauseCountDownNotificationRule}</li>
 * <li>the {@link StartStopNotificationRule}</li>
 * <li>the {@link WorkoutCompletedNotificationRule}</li>
 * </ul>
 * */
public final class WorkoutProxy extends EquipmentProxy<IWorkoutListener> implements IWorkoutProxy {

	private static WorkoutProxy mInstance = null;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * It instantiates a new {@link EquipmentProxy} for the management of applicative work-out commands.<br/>
	 * It simply calls the {@code super} constructor.
	 * 
	 * @param connectionController
	 *            : a reference to the controller of the connection with the equipment
	 * */
	protected WorkoutProxy(final IEquipmentController connectionController) {
		super(connectionController);
	}

	/**
	 * It's a static factory method.<br/>
	 * It achieves the implementation of Singleton pattern.
	 * 
	 * @param connectionController
	 *            : the connection controller required by the class constructor
	 * */
	public static IWorkoutProxy getInstance(final IEquipmentController connectionController) {
		if (mInstance == null) {
			mInstance = new WorkoutProxy(connectionController);
		}
		return mInstance;
	}

	// }

	// { IWorkoutProxy

	@Override
	public void setSpeed(final Integer speed) throws WriteNotAllowedException {
		this.sendCommand(SetSpeedCommand.create(speed));
	}

	@Override
	public void setSpeed(final Integer speed, final Integer acceleration) throws WriteNotAllowedException {
		this.sendCommand(SetSpeedCommand.create(speed, acceleration));
	}

	@Override
	public void increaseSpeed() throws WriteNotAllowedException {
		this.sendCommand(IncreaseSpeedCommand.create());
	}

	@Override
	public void decreaseSpeed() throws WriteNotAllowedException {
		this.sendCommand(DecreaseSpeedCommand.create());
	}

	@Override
	public void increaseGradient() throws WriteNotAllowedException {
		this.sendCommand(IncreaseGradientCommand.create());
	}

	@Override
	public void decreaseGradient() throws WriteNotAllowedException {
		this.sendCommand(DecreaseGradientCommand.create());
	}

	@Override
	public void setGradient(final Integer gradient) throws WriteNotAllowedException {
		this.sendCommand(SetGradientCommand.create(gradient));
	}

	@Override
	public void pause() throws WriteNotAllowedException {
		this.sendCommand(PauseCommand.create());
	}

	@Override
	public void resumeFromPause() throws WriteNotAllowedException {
		this.sendCommand(ResumeFromPauseCommand.create());
	}

	@Override
	public void startCooldown() throws WriteNotAllowedException {
		this.sendCommand(StartCooldownCommand.create());
	}

	@Override
	public void stopWorkout() throws WriteNotAllowedException {
		this.sendCommand(StopWorkoutCommand.create());
	}

	@Override
	public void startWorkout(final Integer speed, final Integer gradient) throws WriteNotAllowedException {
		this.sendCommand(StartWorkoutCommand.create(speed, gradient));
	}

	@Override
	public void setJoystickMode(final Boolean enable) throws WriteNotAllowedException {
		this.sendCommand(SetJoystickModeCommand.create(enable));
	}

	// IEquipmentProxy

	@Override
	public void initializeNotificationRules() {
		if (this.getInitializationState() == false) {
			this.addNotificationRule(JoystickGradientUpNotificationRule.create());
			this.addNotificationRule(JoystickGradientUpReleasedNotificationRule.create());
			this.addNotificationRule(JoystickGradientDownNotificationRule.create());
			this.addNotificationRule(JoystickGradientDownReleasedNotificationRule.create());
			this.addNotificationRule(JoystickSpeedUpNotificationRule.create());
			this.addNotificationRule(JoystickSpeedUpReleasedNotificationRule.create());
			this.addNotificationRule(JoystickSpeedDownNotificationRule.create());
			this.addNotificationRule(JoystickSpeedDownReleasedNotificationRule.create());
			this.addNotificationRule(EquipmentSpeedNotificationRule.create());
			this.addNotificationRule(EquipmentGradientNotificationRule.create());
			this.addNotificationRule(EquipmentHeartRateNotificationRule.create());
			this.addNotificationRule(EquipmentDistanceNotificationRule.create());
			this.addNotificationRule(EquipmentElapsedTimeNotificationRule.create());
			this.addNotificationRule(PauseCountDownNotificationRule.create());
			this.addNotificationRule(StartStopNotificationRule.create());
			this.addNotificationRule(WorkoutCompletedNotificationRule.create());
		}
	}

	// }

	// }

}
