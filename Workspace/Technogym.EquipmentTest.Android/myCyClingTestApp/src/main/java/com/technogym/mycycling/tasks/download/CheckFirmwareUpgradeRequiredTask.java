package com.technogym.mycycling.tasks.download;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.common.primitives.Booleans;
import com.technogym.mycycling.services.TargetServices;
import com.technogym.mycycling.tasks.listeners.ITaskCompleteListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import it.spot.android.logger.domain.Logger;

/**
 * Task to check if a MyCycling's firmware upgrade is required
 *
 * @author Federico Foschini
 */
public class CheckFirmwareUpgradeRequiredTask extends AsyncTask<String, String, Boolean> {

    // { Internal fields

    private Context mContext;
    private ITaskCompleteListener mTaskListener;
    //private ProgressDialog mProgressDialog;

    // }

    // { Constructors

    /**
     * Constructor
     * @param context
     * @param listener
     */
    public CheckFirmwareUpgradeRequiredTask(Context context, ITaskCompleteListener listener) {
        super();

        mContext = context;
        mTaskListener = listener;
    }

    // }

    // { Public methods

    // }

    // { AsyncTask override methods

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        /*this.mProgressDialog = ProgressDialog.show(
                this.mContext, "Check Firmware Upgrade", "Controllo upgrade necessario...");*/
    }

    /**
     * Executing task method
     *
     * @param params: parameters to execute the task
     *              0 - URL service to check the upgrade required
     *
     * @return true if the upgrade is requied, otherwise false
     */
    @Override
    protected Boolean doInBackground(String... params) {
        boolean upgradeRequired = false;
        try {
            final URL url = new URL(params[0]);

            final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            StringBuffer text = new StringBuffer();
            InputStreamReader in = new InputStreamReader((InputStream) urlConnection.getContent());
            BufferedReader buff = new BufferedReader(in);
            String line;
            do {
                line = buff.readLine();
                if(line != null) {
                    text.append(line);
                }
            } while (line != null);

            String resultData = text.toString();
            Log.i("Check FW Upgrade", "Result Data: " + resultData);
            upgradeRequired = Boolean.parseBoolean(resultData);
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Firmware Upgrade Required: " + upgradeRequired);

        } catch (Exception ex) {
            Logger.getInstance().logError(this.getClass().getSimpleName(), ex.getMessage());
            upgradeRequired = false;
        }
        Log.i("Check FW Upgrade", "Result: " + upgradeRequired);
        return upgradeRequired;
    }

    @Override
    protected void onPostExecute(Boolean upgradeRequired) {
        super.onPostExecute(upgradeRequired);
        /*this.mProgressDialog.dismiss();*/
        mTaskListener.onTaskComplete(upgradeRequired, TargetServices.CHECK_UPGRADE_FIRMWARE_REQUIRED);
    }

    // }
}
