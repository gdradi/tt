package com.technogym.myrow.bridges.android;

import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * This is the very basic interface for a Javascript interface that will be injected within the hosted {@link WebView}.
 *
 * @author Federico Foschini
 * */
public interface IBridge {

	/**
	 * Clears all the resources the bridge is using.
	 * */
	@JavascriptInterface
	public void clearResources();
	
	/**
	 * This is a placeholder method, needed only to check if the Javascript interface has been correctly injected within
	 * the {@link WebView}.
	 * 
	 * @return a simple {@code boolean}
	 * */
	@JavascriptInterface
	public boolean exists();

}
