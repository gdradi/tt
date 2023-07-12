package com.technogym.mycycling.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.technogym.mycycling.services.TargetServices;
import com.technogym.mycycling.tasks.listeners.ITaskCompleteListener;

public class LooperTask extends AsyncTask<Void, Void, Boolean> {

	// { Internal Fields
	
    protected ITaskCompleteListener mCallback;
    protected TargetServices mTargetService;
    
    // }

	// { Constructors

    /**
     * Constructor
     * @param context: context of the calling activity
     * @param targetService: target service to call
     */
    public LooperTask(Context context, TargetServices targetService) {
    	mCallback = (ITaskCompleteListener)context;
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
        mCallback.onSuccess(true, mTargetService);
		return true;
	}

}
