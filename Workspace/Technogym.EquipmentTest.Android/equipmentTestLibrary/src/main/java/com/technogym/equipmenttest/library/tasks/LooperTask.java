package com.technogym.equipmenttest.library.tasks;

import com.technogym.equipmenttest.library.services.TargetServices;

import android.content.Context;
import android.os.AsyncTask;

public class LooperTask extends AsyncTask<Void, Void, Boolean> {

	// { Internal Fields
	
    protected OnTaskCompleteCallable mCallback;
    protected TargetServices mTargetService;
    
    // }

	// { Constructors

    /**
     * Constructor
     * @param context: context of the calling activity
     * @param targetService: target service to call
     */
    public LooperTask(Context context, TargetServices targetService) {
    	mCallback = (OnTaskCompleteCallable)context;
    	mTargetService = targetService;
	}
    
    // }

	// { Overriding Methods
	
	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        mCallback.onTaskComplete(true, mTargetService);
		return true;
	}

}
