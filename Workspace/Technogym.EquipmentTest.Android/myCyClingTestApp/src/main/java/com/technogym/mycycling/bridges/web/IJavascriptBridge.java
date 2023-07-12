package com.technogym.mycycling.bridges.web;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.technogym.mycycling.connection.listeners.IMyCyclingListener;
import com.technogym.mycycling.listeners.IActionListener;

/**
 * This interface represents the standard to interact with {@link WebView}'s content through Javascript injection.
 * */
public interface IJavascriptBridge extends IActionListener {

	/**
	 * This method simply notifies the 'onPause' event triggered by {@link Activity}.
	 * */
	@JavascriptInterface
	public void notifyActivityOnPause();

	/**
	 * This method simply notifies the 'onResume' event triggered by {@link Activity}.
	 * */
	@JavascriptInterface
	public void notifyActivityOnResume();

	/**
	 * This method simply notifies the 'onStop' event triggered by {@link Activity}.
	 * */
	@JavascriptInterface
	public void notifyActivityOnStop();

	/**
	 * This method simply notifies the 'onDestroy' event triggered by {@link Activity}.
	 * */
	@JavascriptInterface
	public void notifyActivityOnDestroy();
	
	/**
	 * This method handles the completion of the scan of a bar code.
	 * 
	 * @param data
	 *            : the content which indicates the result of the scan operation
	 * */
	@JavascriptInterface
	public void onDataScanned(final String data);
	
    /**
     * This methods sets within the web view the information about the software or hardware nature of the keyboard.
     * 
     * @param enabled
     *            : tells if the keyboard is a hardware one or not
     * */
	@JavascriptInterface
    public void enableHardwareSource(final Boolean enabled);

	/**
	 * This method handles the equipment firmware version got.
	 *
	 * @param firmwareVersion
	 *            : the equipment firmware version got
	 * */
	@Override
	public void onEquipmentFirmwareVersionRetrieved(String firmwareVersion);

	/**
	 * This method handles the equipment bootloader version got.
	 *
	 * @param bootloaderVersion
	 *            : the equipment bootloader version got
	 * */
	public void onEquipmentBootloaderFirmwareVersionRetrieved(String bootloaderVersion);
}
