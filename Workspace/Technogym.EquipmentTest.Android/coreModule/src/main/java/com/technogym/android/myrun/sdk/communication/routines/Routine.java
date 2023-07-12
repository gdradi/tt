package com.technogym.android.myrun.sdk.communication.routines;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A base implementation of {@link IRoutine} interface.<br/>
 * It provides a {@link Timer} which, on the startup, schedules a {@link TimerTask} accordingly to constructor's
 * parameters.<br/>
 * The task does nothing more than calling the {@link #execute()} abstract method that every subclass needs to implement
 * on its own.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public abstract class Routine implements IRoutine {

	private boolean mIsPeriodic;

	private int mDelay;
	private int mPeriod;

	private Timer mTimer;
	private TimerTask mTimerTask;

	// { Construction

	/**
	 * The class constructor.<br/>
	 * The parameters are all needed for {@link TimerTask}'s scheduling configuration.
	 * 
	 * @param delay
	 *            : the delay before routine's first execution
	 * @param isPeriodic
	 *            : it indicates if the routine has to be executed periodically
	 * @param period
	 *            : if needed, the period after which the routine is executed after previous execution
	 * */
	protected Routine(final int delay, final boolean isPeriodic, final int period) {
		super();

		this.mTimer = null;

		this.mDelay = delay;
		this.mPeriod = period;
		this.mIsPeriodic = isPeriodic;
	}

	// }

	// { IRoutine implementation

	@Override
	public abstract void initRoutine();

	@Override
	public void startRoutine() {
		if (this.mTimer != null) {
			this.cancelRoutine();
		}

		this.mTimer = new Timer();
		this.mTimerTask = new TimerTask() {

			@Override
			public void run() {
				execute();
			}
		};

		if (this.isTaskPeriodic()) {
			this.mTimer.scheduleAtFixedRate(this.mTimerTask, this.getTaskDelay(), this.getTaskPeriod());
		} else {
			this.mTimer.schedule(this.mTimerTask, this.getTaskDelay());
		}
	}

	@Override
	public void cancelRoutine() {

		if (mTimerTask != null) {
			this.mTimerTask.cancel();
		}

		if (mTimer != null) {
			this.mTimer.cancel();
			this.mTimer.purge();
		}
	}

	// }

	// { Private and protected methods

	/**
	 * It executes the core behavior of the routine. It's left abstract because every subclass will implement it on its
	 * own.
	 * */
	protected abstract void execute();

	/**
	 * It tells if the {@link TimerTask} was scheduled for periodical execution.
	 * 
	 * @return {@code true} if it's periodical, {@code false} otherwise
	 * */
	protected boolean isTaskPeriodic() {
		return this.mIsPeriodic;
	}

	/**
	 * It's the getter for the delay set to the {@link TimerTask} execution.
	 * 
	 * @return an {@code int} which represents the delay set
	 * */
	protected int getTaskDelay() {
		return this.mDelay;
	}

	/**
	 * It's the getter for the period set to the {@link TimerTask} execution.
	 * 
	 * @return an {@code int} which represents the period set
	 * */
	protected int getTaskPeriod() {
		return this.mPeriod;
	}

	// }

}
