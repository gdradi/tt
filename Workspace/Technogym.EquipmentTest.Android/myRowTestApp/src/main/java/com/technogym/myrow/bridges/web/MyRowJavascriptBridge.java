package com.technogym.myrow.bridges.web;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.technogym.myrow.commands.models.ICommandReply;
import com.technogym.myrow.connection.models.EquipmentConnectionState;

import java.util.Arrays;

public class MyRowJavascriptBridge extends JavascriptBridge implements IMyRowJavascriptBridge {

    // { Construction

    protected MyRowJavascriptBridge(WebView webView) {
        super(webView);
    }

    @JavascriptInterface
    public static IMyRowJavascriptBridge create(final WebView webView) {
        return new MyRowJavascriptBridge(webView);
    }

    // }

    // { IMyCyclingJavascriptBridge implementation

    @Override
    @JavascriptInterface
    public void onBleDeviceFound(String data) {
        this.executeJavascript("onBleDeviceFound", data);
    }

    @Override
    @JavascriptInterface
    public void onBleConnectionStateChanged() {
        executeJavascript("onBleConnectionStateChanged", "");
    }

    @Override
    @JavascriptInterface
    public void onEquipmentFirmwareUpgradeChecked(final Boolean upgradeRequired) {
        executeJavascript("onEquipmentFirmwareUpgradeChecked", upgradeRequired.toString());
    }

    @Override
    @JavascriptInterface
    public void onEquipmentFirmwareDownloaded(String downloadFilePath) {
        executeJavascript("onEquipmentFirmwareDownloaded", downloadFilePath.toString());
    }

    @JavascriptInterface
    public void onBootloaderModeStarted(final Boolean bootloaderModeStarted) {
        executeJavascript("onBootloaderModeStarted", bootloaderModeStarted.toString());
    }

    @Override
    @JavascriptInterface
    public void onEquipmentFirmwareVersionUpdated(String firmwareVersion) {
        executeJavascript("onEquipmentFirmwareVersionUpdated", firmwareVersion.toString());
    }

    // }

    // { IMyCyclingListener implementation

    @Override
    public void onValueReceived(ICommandReply reply) {
        Log.i("onMessageReceived", "Received msg for tag: " + reply.getName() + " with content: " + Arrays.toString(reply.getValues().toArray()));
        switch (reply.getName()) {

            /*
            // TODO : manage command reply values

            case StartBootloaderModeReply.CMD_NAME:
                onBootloaderModeStarted(true);
                break;

            case SerialNumberReply.CMD_NAME:
                onEquipmentSerialNumberRetrieved(reply.getValues().get(0));
                break;

            case FirmwareVersionReply.CMD_NAME:
                onEquipmentFirmwareVersionRetrieved(reply.getValues().get(0));
                break;

            case UpdatedFirmwareVersionReply.CMD_NAME:
                onEquipmentFirmwareVersionUpdated(reply.getValues().get(0));
                break;

            case BootloaderFirmwareVersionReply.CMD_NAME:
                onEquipmentBootloaderFirmwareVersionRetrieved(reply.getValues().get(0));
                break;
                */
        }
    }

    @Override
    public void onTargetDeviceFound(String data) {
        this.onBleDeviceFound(data);
    }

    @Override
    public void onConnectionStateChanged(EquipmentConnectionState state) {
        this.onBleConnectionStateChanged();
    }

    @Override
    public void onUpgradeDone(Boolean result) {
        executeJavascript("onEquipmentFirmwareUpgraded", result.toString());
    }

    // }
}
