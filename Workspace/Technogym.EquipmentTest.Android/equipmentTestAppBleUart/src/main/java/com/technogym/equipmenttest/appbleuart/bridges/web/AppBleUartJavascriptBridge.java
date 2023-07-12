package com.technogym.equipmenttest.appbleuart.bridges.web;

import android.webkit.WebView;

import com.technogym.equipmenttest.library.bridges.web.JavascriptBridge;

public class AppBleUartJavascriptBridge extends JavascriptBridge implements IAppBleUartJavascriptBridge {

	// { Construction

	protected AppBleUartJavascriptBridge(WebView webView) {
		super(webView);
	}

	public static IAppBleUartJavascriptBridge create(final WebView webView) {
		return new AppBleUartJavascriptBridge(webView);
	}

	// }

	// { IMyRunJavascriptBridge implementation

	@Override
	public void onBleUartLowKitUpgraded(boolean result){
		executeJavascript("onBleUartLowKitUpgraded", result+"");
	}

	@Override
	public void onBleUartHighKitUpgraded(boolean result){
		executeJavascript("onBleUartHighKitUpgraded", result+"");
	}

	@Override
	public void onBleUartEquipmentRebooted(boolean connected){
		executeJavascript("onBleUartEquipmentRebooted", connected+"");
	}


	@Override
	public void onLowKitVersionRetrieved(String version) {
		executeJavascript("onLowKitVersionRetrieved", version);
	}

	@Override
	public void onHighKitVersionRetrieved(String version) {
		executeJavascript("onHighKitVersionRetrieved", version);
	}

	@Override
	public void onFirmwareBootloaderVersionRetrieved(String bootloaderVersion) {
		executeJavascript("onFirmwareBootloaderVersionRetrieved", bootloaderVersion);
	}

	@Override
	public void onBluetoothConnectionStateChanged() {
		executeJavascript("onBluetoothConnectionStateChanged", "");
	}

	@Override
	public void onWIDVersionRetrieved(String version) {
		executeJavascript("onWIDVersionRetrieved", version);
	}

	@Override
	public void onMachineTypeRetrieved(String machineType) {
		executeJavascript("onMachineTypeRetrieved", machineType);
	}

	@Override
	public void onPowerTypeRetrieved(String powerType) {
		executeJavascript("onPowerTypeRetrieved", powerType);
	}

	// }

}
