package com.technogym.mycycling.tasks.download;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.technogym.mycycling.services.TargetServices;
import com.technogym.mycycling.tasks.listeners.ITaskCompleteListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import it.spot.android.logger.domain.Logger;

/**
 * Task to download MyCycling's firmware to do the upgrade
 *
 * @author Federico Foschini
 */
public class DownloadFirmwareTask extends AsyncTask<String, String, String> {

    // { Public fields

    /**
     * Response code related to the skip of the upgrade
     */
    public static final int HTTP_RESPONSE_CODE_CONTINUE = 100;

    /**
     * Response code related to the upgrade firmware's file not found
     */
    public static final int HTTP_RESPONSE_CODE_NOT_FOUND = 404;

    /**
     * Response code related to the upgrade firmware's service internal error
     */
    public static final int HTTP_RESPONSE_CODE_SERVICE_ERROR = 500;

    // }

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
    public DownloadFirmwareTask(Context context, ITaskCompleteListener listener) {
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
                this.mContext, "Scaricando il Firmware", "Attendere il termine del download...");*/
    }

    /**
     * Executing task method
     *
     * @param params: parameters to execute the task
     *              0 - URL service to download the task
     *              1 - destination folder path
     *              2 - destination filename
     *
     * @return null if the download has been OK, otherwise the error message
     */
    @Override
    protected String doInBackground(String... params) {
        File downloadedFile;
        try {
            final URL url = new URL(params[0]);

            final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // create a new file, to save the downloaded file
            downloadedFile = new File(params[1], params[2]);
            // if the file already exists, it gets overwritten
            FileOutputStream fileOutput = new FileOutputStream(downloadedFile, false);

            // Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            // this is the total size of the file which we are downloading
            Integer mFileSize = urlConnection.getContentLength();

            // create a buffer...
            byte[] buffer = new byte[1024 * 1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                //this.mDownloadedFileSize += bufferLength;
                //this.mDownloadProgress = ((float) this.mDownloadedFileSize / this.mFileSize) * 100;
            }

            // close the output stream when complete
            fileOutput.close();

        } catch (Exception ex) {
            Logger.getInstance().logError(this.getClass().getSimpleName(), ex.getMessage());
            return null;
        }
        return downloadedFile.getAbsolutePath();
    }

    @Override
    protected void onPostExecute(String filePath) {
        super.onPostExecute(filePath);
        //this.mProgressDialog.dismiss();

        if (filePath != null) {
            mTaskListener.onSuccess(filePath, TargetServices.DOWNLOAD_FIRMWARE);
        } else {
            mTaskListener.onError("Error during firmware download", TargetServices.DOWNLOAD_FIRMWARE);
        }
    }

    // }
}
