package com.technogym.equipmenttest.library.tasks;

import com.technogym.equipmenttest.library.services.TargetServices;

/**
 * Interface related to the callback made by a task to the activity that called it
 * @author Federico Foschini
 *
 */
public interface OnTaskCompleteCallable {

	/**
	 * Method to pass the task's returned data to the calling activity
	 * @param data: data to return
	 * @param targetService: called service
	 */
	public void onTaskComplete(Object data, TargetServices targetService);
}
