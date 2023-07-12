package com.technogym.equipmenttest.photo.app.tasks;

import android.app.Service;
import android.os.AsyncTask;
import android.util.Log;

import com.technogym.equipmenttest.photo.app.Configuration;
import com.technogym.equipmenttest.photo.app.listeners.ITaskCompleteListener;
import com.technogym.equipmenttest.photo.app.service.ServiceData;
import com.technogym.equipmenttest.photo.app.service.TargetServices;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import it.spot.android.logger.domain.Logger;

/**
 * Task to upload a file to a web service.
 * Created by federico.foschini.
 */
public class HttpVerifyImageUploadTask extends AsyncTask<Void, Void, Boolean> {

    // { Internal Fields

    protected ITaskCompleteListener mCallback;
    protected TargetServices mTargetService;
    protected String mTargetFilePath;
    protected String mSerialNumber;
    protected String mActionID;
    protected String mActionSequence;
    protected URL connectURL;

    // }

    // { Constructors

    /**
     * Constructor
     * @param listener: task complete listener
     * @param targetService: target service related to the upload
     * @param targetFilepath: path of the file to be uploaded
     * @param serialNumber: target serial number
     * @param actionID: target action ID
     * @param actionSequence: target action sequence
     */
    public HttpVerifyImageUploadTask(ITaskCompleteListener listener,
                                     TargetServices targetService,
                                     String targetFilepath,
                                     String serialNumber,
                                     String actionID,
                                     String actionSequence) {
        mCallback = listener;
        mTargetService = targetService;
        mTargetFilePath = targetFilepath;
        mSerialNumber = serialNumber;
        mActionID = actionID;
        mActionSequence = actionSequence;

        // sets the target URL basing on the target service
        try {
            switch (targetService) {
                case UPLOAD_PHOTO_VERIFICATION_ACTIONS_SERVICE:
                    connectURL = new URL(Configuration.EQUIPMENT_TEST_SERVICES_URL
                            + ServiceData.ACTION_UPLOAD_PHOTO_VERIFICATION_ACTIONS_SERVICE);
                    break;
            }
        } catch (MalformedURLException e) {
            Log.i(this.getClass().getSimpleName(), "[HttpVerifyImageUploadTask] Error: " + e.getMessage());
            Logger.getInstance().logError(this.getClass().getSimpleName(), "[HttpVerifyImageUploadTask] Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // }

    // { AsyncTask Implementation

    @Override
    protected Boolean doInBackground(Void... params) {
        Boolean respResult = false;
        try {
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            FileInputStream fstrm = new FileInputStream(mTargetFilePath);
            Log.e(this.getClass().getSimpleName(),"[doInBackground] Starting Http File Sending to URL");

            // Open a HTTP connection to the URL and configure the connection
            HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

            // write headers
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"ACTIONID\""+ lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(mActionID);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"ACTIONSEQUENCE\""+ lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(mActionSequence);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"SERIALNUMBER\""+ lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(mSerialNumber);
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + mTargetFilePath +"\"" + lineEnd);
            dos.writeBytes(lineEnd);

            Log.e(this.getClass().getSimpleName(),"Headers are written");

            // create a buffer of maximum size
            int bytesAvailable = fstrm.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[ ] buffer = new byte[bufferSize];

            // read file and write it
            int bytesRead = fstrm.read(buffer, 0, bufferSize);
            while (bytesRead > 0)
            {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fstrm.available();
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                bytesRead = fstrm.read(buffer, 0,bufferSize);
            }
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // close streams
            fstrm.close();
            dos.flush();
            Log.i(this.getClass().getSimpleName(),
                    "[doInBackground] File Sent - Response Code: " + String.valueOf(conn.getResponseCode()));
            Logger.getInstance().logDebug(
                    this.getClass().getSimpleName(),
                    "[doInBackground] File Sent - Response Code: " + String.valueOf(conn.getResponseCode()));

            // retrieve the response from server
            InputStream is = conn.getInputStream();
            int ch;

            StringBuffer b =new StringBuffer();
            while( ( ch = is.read() ) != -1 ){ b.append( (char)ch ); }
            String s=b.toString();
            Log.i(this.getClass().getSimpleName(), "[doInBackground] Response: " + s);
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[doInBackground] File Sent - Response: " + s);
            dos.close();

            JSONObject respObj = new JSONObject(s);
            respResult = respObj.getBoolean("Success");
            Log.i(this.getClass().getSimpleName(), "[doInBackground] Response Feedback: " + respResult);
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[doInBackground] File Sent - Response Feedback: " + respResult);

        } catch (Exception e) {
            respResult = false;
            Log.i(this.getClass().getSimpleName(), "[doInBackground] Error: " + e.getMessage());
            Logger.getInstance().logError(this.getClass().getSimpleName(), "[doInBackground] Error: " + e.getMessage());
            e.printStackTrace();
        }
        return respResult;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if(mCallback != null) {
            mCallback.onTaskComplete(success, mTargetService);
        }
    }

    // }
}
