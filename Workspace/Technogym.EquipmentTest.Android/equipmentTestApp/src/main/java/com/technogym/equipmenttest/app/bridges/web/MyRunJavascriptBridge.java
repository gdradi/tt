package com.technogym.equipmenttest.app.bridges.web;

import android.webkit.WebView;

import com.technogym.equipmenttest.library.bridges.web.JavascriptBridge;

public class MyRunJavascriptBridge extends JavascriptBridge implements IMyRunJavascriptBridge {

	// { Construction

	protected MyRunJavascriptBridge(WebView webView) {
		super(webView);
	}

	public static IMyRunJavascriptBridge create(final WebView webView) {
		return new MyRunJavascriptBridge(webView);
	}

	// }

	// { IMyRunJavascriptBridge implementation

	@Override
	public void onFirmwareBootloaderVersionRetrieved(String bootloaderVersion) {
		executeJavascript("onFirmwareBootloaderVersionRetrieved", bootloaderVersion);
	}

	@Override
	public void onBluetoothConnectionStateChanged() {
		executeJavascript("onBluetoothConnectionStateChanged", "");
	}

	// }

}
