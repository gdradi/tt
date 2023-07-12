package com.technogym.myrow.bridges.android;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

public interface IAndroidBridge extends IBridge {

	/**
	 * This method closes the application itself.
	 * */
	@JavascriptInterface
	public void closeApplication();

	/**
	 * This method allows the {@link WebView} to open an activity through the Javascript interface.
	 * 
	 * @param namespace
	 *            the {@code String} identifier for the desired {@link Activity}
	 * @param jsonData
	 *            the JSON data that will be parsed and passed to the awakened {@link Activity}
	 * */
	@JavascriptInterface
	public void startActivity(final String namespace, final String jsonData);

	/**
	 * This method allows the {@link WebView} to start a {@link Service} through the Javascript interface.
	 * 
	 * @param namespace
	 *            the {@code String} identifier for the desired {@link Service}
	 * @param jsonData
	 *            the JSON data that will be parsed and passed to the awakened {@link Service}
	 * */
	@JavascriptInterface
	public void startService(final String namespace, final String jsonData);

	/**
	 * This method allows the {@link WebView} to send a broadcast {@link Intent} through the Javascript interface.
	 * 
	 * @param namespace
	 *            the {@code String} that identifies the broadcast action to call
	 * */
	@JavascriptInterface
	public void broadcast(final String namespace);

	/**
	 * This method opens a document within another {@link Activity}. Currently only PDF documents are supported.
	 * 
	 * @param type
	 *            : indicates the type of the document to open
	 * @param url
	 *            : the remote path where the document can be found
	 * */
	@JavascriptInterface
	public void openDocument(final String type, final String url);

	/**
	 * This method allows to show a {@link Toast} notification from within the {@link WebView}.
	 * 
	 * @param message
	 *            : the content of the notification
	 * */
	@JavascriptInterface
	public void showToastNotification(final String message);

	/**
	 * This method allows to log a message through the Android console.
	 * 
	 * @param level
	 *            : the priority level of the log
	 * @param message
	 *            : the content to log
	 * */
	@JavascriptInterface
	public void log(final String log, final String message);

}