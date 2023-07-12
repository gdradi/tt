package com.technogym.equipmenttest.library.bridges.web;

import android.webkit.WebView;

import com.technogym.equipmenttest.library.listeners.IActionListener;

/**
 * This interface represents the standard to interact with {@link WebView}'s content through Javascript injection.
 * */
public interface IJavascriptBridge extends IActionListener {

	/**
	 * This method simply notifies the 'onPause' event triggered by {@link Activity}.
	 * */
	public void notifyActivityOnPause();

	/**
	 * This method simply notifies the 'onResume' event triggered by {@link Activity}.
	 * */
	public void notifyActivityOnResume();

	/**
	 * This method simply notifies the 'onStop' event triggered by {@link Activity}.
	 * */
	public void notifyActivityOnStop();

	/**
	 * This method simply notifies the 'onDestroy' event triggered by {@link Activity}.
	 * */
	public void notifyActivityOnDestroy();
	
	/**
	 * This method handles the completion of the scan of a bar code.
	 * 
	 * @param data
	 *            : the content which indicates the result of the scan operation
	 * */
	public void onDataScanned(final String data);
	
    /**
     * This methods sets within the web view the information about the software or hardware nature of the keyboard.
     * 
     * @param enabled
     *            : tells if the keyboard is a hardware one or not
     * */
    public void enableHardwareSource(final Boolean enabled);
}
