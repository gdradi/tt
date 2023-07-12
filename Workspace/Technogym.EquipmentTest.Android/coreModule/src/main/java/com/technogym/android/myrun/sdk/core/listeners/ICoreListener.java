package com.technogym.android.myrun.sdk.core.listeners;

import com.technogym.android.myrun.sdk.communication.listeners.IListener;
import com.technogym.android.myrun.sdk.core.proxies.ICoreProxy;

/**
 * This is the interface that a listener must implement if it needs to be notified by the {@link ICoreProxy}.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public interface ICoreListener extends IListener {

	/**
	 * This method gets called when the equipment enters remote mode.
	 * 
	 * @param success
	 *            {@link Boolean} which indicates if the equipment entered successfully the remote mode.
	 * */
	public void onEnterRemoteModeResult(final Boolean success);

	/**
	 * This method gets called when the equipment exits remote mode.
	 * 
	 * @param success
	 *            {@link Boolean} which indicates if the equipment exited correctly the remote mode.
	 * */
	public void onExitRemoteModeResult(final Boolean success);

	/**
	 * This method notifies of keep alive expiration.<br/>
	 * It generally means that the equipment hasn't received the "keep alive" command for more than five seconds, so
	 * that it exited from remote mode.
	 * */
	public void onKeepAliveExpired();

}
