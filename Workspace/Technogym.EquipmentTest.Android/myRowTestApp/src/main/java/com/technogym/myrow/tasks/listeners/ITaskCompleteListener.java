package com.technogym.myrow.tasks.listeners;

import com.technogym.myrow.services.TargetServices;

/**
 * Interface related to the callback made by a task to the activity that called it
 *
 * @author Federico Foschini
 *
 */
public interface ITaskCompleteListener {

	/**
	 * Method to pass the task's returned data to the calling activity
	 * @param data: data to return
	 * @param targetService: called service
	 */
	public void onTaskComplete(Object data, TargetServices targetService);

	/**
	 * Method to pass the task's success returned data to the calling activity
	 * @param data: data to return
	 * @param targetService: called service
	 */
	public void onSuccess(Object data, TargetServices targetService);

	/**
	 * Method to pass the task's fail returned data to the calling activity
	 * @param errorMessage: error message
	 * @param targetService: called service
	 */
	public void onError(String errorMessage, TargetServices targetService);
}
