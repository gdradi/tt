package com.technogym.equipmenttest.appbleuart.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.technogym.equipmenttest.appbleuart.connectors.BleUartConnector;
import com.technogym.sdk.fitnessmachineservice.interfaces.OTAListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import it.spot.android.logger.domain.Logger;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.technogym.equipmenttest.appbleuart.Configuration.EQUIPMENT_TEST_SERVICES_URL;
import static com.technogym.equipmenttest.appbleuart.Configuration.HIGHKIT_FILE_DOWNLOAD_PATH;
import static com.technogym.equipmenttest.appbleuart.Configuration.FIRMWARE_DOWNLOAD_FOLDER;
import static com.technogym.equipmenttest.appbleuart.Configuration.LOWKIT_FILE_DOWNLOAD_PATH;

public class UpgradeManager implements IUpgradeManager, OTAListener {

    private static final String TAG = UpgradeManager.class.getSimpleName();
    private BleUartConnector uartBridgeConnector;
    private boolean writeFailed = false;
    private boolean isHighKitUpgrade = false;
    private boolean isLowKitUpgrade = false;
    private final Context context;

    public UpgradeManager(final BleUartConnector uartBridgeConnector, Context context){
        this.uartBridgeConnector = uartBridgeConnector;
        this.context = context;
    }

    @Override
    public String downloadLowKitUpgradeFile(String itemCode, String widVersion, String token){
        return downloadUpgradeFile(itemCode, widVersion, token, EQUIPMENT_TEST_SERVICES_URL + LOWKIT_FILE_DOWNLOAD_PATH);
    }

    @Override
    public String downloadHighKitUpgradeFile(String itemCode, String widVersion, String token) {
        return downloadUpgradeFile(itemCode, widVersion, token, EQUIPMENT_TEST_SERVICES_URL + HIGHKIT_FILE_DOWNLOAD_PATH);
    }


    private String downloadUpgradeFile(String itemCode, String widVersion, String token, String url) {
        Logger.getInstance().logDebug(TAG, "downloadFirmwareUpgradeFile");
        Logger.getInstance().logDebug(TAG, "itemCode: "+itemCode);
        Logger.getInstance().logDebug(TAG, "token " + token);
        Logger.getInstance().logDebug(TAG, "WIDVersion " + widVersion);

        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("ItemCode", itemCode)
                .add("WIDVersion", widVersion)
                .add("Token", token)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Call call = client.newCall(request);

        String localUpgradeFilePath;
        try {
            Response response = call.execute();
            //Log.w(TAG,"Response: " + response);

            File downloadFolder = new File(FIRMWARE_DOWNLOAD_FOLDER);
            if(!downloadFolder.exists()) {
                if(downloadFolder.mkdir()){
                    Log.w(TAG, "Folder " + FIRMWARE_DOWNLOAD_FOLDER +" created.");
                }
            }
            localUpgradeFilePath = downloadFolder.getAbsolutePath() + "/upgradeFirmware.myb";
            InputStream input = response.body().byteStream();
            OutputStream output = new FileOutputStream(localUpgradeFilePath);

            byte data[] = new byte[1024];
            int count;
            while ((count = input.read(data)) != -1) {
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Log.w(TAG,"FILE SIZE: " + Integer.parseInt(String.valueOf(new File(localUpgradeFilePath).length()/1024))+" kb");
        return localUpgradeFilePath;
    }

    @Override
    public void copyLowKitFileToEquipmentMemory(String path) {
        isLowKitUpgrade = true;
        isHighKitUpgrade = false;
        copyFileToEquipmentMemory(path);
    }

    @Override
    public void copyHighKitFileToEquipmentMemory(String path) {
        isHighKitUpgrade = true;
        isLowKitUpgrade = false;
        copyFileToEquipmentMemory(path);
    }

    private void copyFileToEquipmentMemory(String path) {
        writeFailed = false;
        uartBridgeConnector.writeFirmwareData(path,this);
    }


    // region OTAListener

    @Override
    public void onOTAProgress(int progress) {
        Logger.getInstance().logDebug(TAG, "******** [Copying file in equipment flash memory] Progress: "+progress+" ********");
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "Copia file su memoria equipment: "+progress+"%", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onOTAFinish(Boolean result) {
        Logger.getInstance().logDebug(TAG, "onOTAFinish. Result: "+result);
        if(!result){
            if(!writeFailed){
                writeFailed = true;
                if (isLowKitUpgrade) {
                    uartBridgeConnector.onLowKitUpgraded(result);
                } else if (isHighKitUpgrade) {
                    uartBridgeConnector.onHighKitUpgraded(result);
                }
            }
        } else {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Copia file su memoria equipment: 100%", Toast.LENGTH_LONG).show();
                }
            });

            if (isLowKitUpgrade) {
                uartBridgeConnector.onLowKitUpgraded(result);
            } else if (isHighKitUpgrade) {
                uartBridgeConnector.onHighKitUpgraded(result);
            }
        }
    }

    //end region
}
