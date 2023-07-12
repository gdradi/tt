package com.technogym.myrow.bridges.web;

import android.os.Handler;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class JavascriptBridge implements IJavascriptBridge {

	private static final String EXECUTE_JAVASCRIPT_CONTENT = "javascript:JSBridge.%s('%s')";

	private final WebView mWebView;
	private final Handler mHandler;

	// { Construction

	protected JavascriptBridge(final WebView webView) {
		super();
		this.mWebView = webView;
		this.mHandler = new Handler();
	}

	@JavascriptInterface
	public static IJavascriptBridge create(final WebView webView) {
		return new JavascriptBridge(webView);
	}

	// }

	// { IJavascriptBridge implementation

	@Override
	@JavascriptInterface
	public void notifyActivityOnPause() {
		this.executeJavascript("notifyActivityOnPause", "");
	}

	@Override
	@JavascriptInterface
	public void notifyActivityOnResume() {
		this.executeJavascript("notifyActivityOnResume", "");
	}

	@Override
	public void notifyActivityOnStop() {
		this.executeJavascript("notifyActivityOnStop", "");
	}

	@Override
	@JavascriptInterface
	public void notifyActivityOnDestroy() {
		this.executeJavascript("notifyActivityOnDestroy", "");
	}

	@JavascriptInterface
	public void onDataScanned(final String data) {
		this.executeJavascript("onDataScanned", data);
	}
    
    @Override
	@JavascriptInterface
    public void enableHardwareSource(final Boolean enabled) {
        this.executeJavascript("enableHardwareSource", enabled.toString());
    }

	@Override
	@JavascriptInterface
	public void onActionUpdate(final String data) {
		this.executeJavascript("onActionUpdate", data);
	}

	@Override
	@JavascriptInterface
	public void onActionCompleted(final boolean success) {
		this.executeJavascript("onActionCompleted", Boolean.toString(success));
	}

	@Override
	@JavascriptInterface
	public void onWrongActionParameters(final String data) {
		this.executeJavascript("onWrongActionParameters", data);
	}

	@Override
	@JavascriptInterface
	public void onEquipmentSerialNumberRetrieved(final String serialNumber) {
		this.executeJavascript("onEquipmentSerialRetrieved", serialNumber);
	}

	@Override
	@JavascriptInterface
	public void onEquipmentSerialNumberSet(final Boolean result) {
		this.executeJavascript("onEquipmentSerialSet", String.valueOf(result));
	}

	@Override
	@JavascriptInterface
	public void onEquipmentBootloaderFirmwareVersionRetrieved(String bootloaderVersion){
		executeJavascript("onFirmwareBootloaderVersionRetrieved", bootloaderVersion);
	}


	@Override
	@JavascriptInterface
	public void onEquipmentFirmwareVersionRetrieved(String firmwareVersion) {
		this.executeJavascript("onEquipmentFirmwareVersionRetrieved", firmwareVersion);
	}

	// }

	// { Private and protected methods

	protected void executeJavascript(final String method, final String data) {
		Log.e("JSBRIDGE", "executing " + String.format(EXECUTE_JAVASCRIPT_CONTENT, method, data));
		this.mHandler.post(new Runnable() {

			@Override
			public void run() {
				mWebView.loadUrl(String.format(EXECUTE_JAVASCRIPT_CONTENT, method, data));
			}
		});
	}

	// }

}
