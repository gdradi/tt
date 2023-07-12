package com.technogym.android.myrun.sdk.communication.routines;

/**
 * Basic interface for the implementation of a routine.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public interface IRoutine {

	/**
	 * This method has to be implemented with all the operations needed for routine initialization.
	 * */
	public void initRoutine();

	/**
	 * This method simply starts the routine.
	 * */
	public void startRoutine();

	/**
	 * This method simply cancels the routine.
	 * */
	public void cancelRoutine();

}
