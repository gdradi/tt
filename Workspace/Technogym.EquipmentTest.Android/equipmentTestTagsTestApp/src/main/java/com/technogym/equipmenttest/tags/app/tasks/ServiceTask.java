package com.technogym.equipmenttest.tags.app.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.technogym.equipmenttest.tags.app.Configuration;
import com.technogym.equipmenttest.tags.app.services.RequestType;
import com.technogym.equipmenttest.tags.app.services.TargetServices;
import com.technogym.equipmenttest.tags.app.services.requests.AuthenticateUserRequest;
import com.technogym.equipmenttest.tags.app.services.requests.GetEquipmentCodesRequest;
import com.technogym.equipmenttest.tags.app.services.requests.IRequest;
import com.technogym.equipmenttest.tags.app.services.requests.SetNFCVerificationRequest;
import com.technogym.equipmenttest.tags.app.services.requests.SetQRCodeVerificationRequest;
import com.technogym.equipmenttest.tags.app.services.responses.AuthenticateUserResponse;
import com.technogym.equipmenttest.tags.app.services.responses.GetEquipmentCodesResponse;
import com.technogym.equipmenttest.tags.app.services.responses.IResponse;
import com.technogym.equipmenttest.tags.app.services.responses.SetNFCVerificationResponse;
import com.technogym.equipmenttest.tags.app.services.responses.SetQRCodeVerificationResponse;
import com.technogym.equipmenttest.tags.app.tasks.listeners.ITaskCompleteListener;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import it.spot.android.logger.domain.Logger;

import static com.technogym.equipmenttest.tags.app.services.TargetServices.AUTH_USER;

/**
 * Task to call a web service
 * @author Federico Foschini
 *
 */
public class ServiceTask extends AsyncTask<Void, Void, Boolean> {

	// { Internal Fields
	
    protected ITaskCompleteListener mCallback;
    protected TargetServices mTargetService;
    protected IRequest mRequest;
    protected IResponse mResponse;

    // }

	// { Constructors

    /**
     * Constructor
     * @param context: context of the calling activity
     * @param targetService: target service to call
     * @param request: request to do to the service
     */
    public ServiceTask(Context context, TargetServices targetService, IRequest request) {
    	mCallback = (ITaskCompleteListener)context;
    	mTargetService = targetService;
    	
    	switch(mTargetService) {
    		case AUTH_USER: 
    			mRequest = (AuthenticateUserRequest)request;
    			break;
            case GET_EQUIPMENT_CODES:
                mRequest = (GetEquipmentCodesRequest)request;
                break;
            case SET_EQUIPMENT_QRCODE_VERIFICATION:
                mRequest = (SetQRCodeVerificationRequest)request;
                break;
            case SET_EQUIPMENT_NFC_VERIFICATION:
                mRequest = (SetNFCVerificationRequest)request;
                break;
			default:
				mRequest = null;
				mCallback.onTaskComplete(null, null);
				break;
    	}
	}
    
    // }

	// { Overriding Methods
	
	@Override
	protected Boolean doInBackground(Void... params) {
		String receivedResponse = "";
        try {
            URL url = null;
            if(mRequest.getRequestType() == RequestType.HTTPGET) {
                url = new URL(
                		Configuration.EQUIPMENT_TEST_SERVICES_URL + mRequest.getServiceUrl().trim()
                		+ "?" + mRequest.getRequestQueryString().trim());
                receivedResponse = doHttpGet(url);
            } else if(mRequest.getRequestType() == RequestType.HTTPPOST) {
                url = new URL(
                		Configuration.EQUIPMENT_TEST_SERVICES_URL
                		+ mRequest.getServiceUrl());
                receivedResponse = doHttpPost(url, mRequest.getRequestBodyString());
            }

        } catch(Exception ex) {
			ex.printStackTrace();
            return false;
        }

        try {
            switch (mTargetService) {
            	case AUTH_USER: 
            		mResponse = new AuthenticateUserResponse(receivedResponse);
        			break;
                case GET_EQUIPMENT_CODES:
                    mResponse = new GetEquipmentCodesResponse(receivedResponse);
                    break;
                case SET_EQUIPMENT_QRCODE_VERIFICATION:
                    mResponse = new SetQRCodeVerificationResponse(receivedResponse);
                    break;
                case SET_EQUIPMENT_NFC_VERIFICATION:
                    mResponse = new SetNFCVerificationResponse(receivedResponse);
                    break;
    			default:
    				return false;
            }
        } catch(Exception ex) {
			ex.printStackTrace();
            return false;
        }

		return true;
	}

    @Override
    protected void onPostExecute(final Boolean success) {
        if(success && mCallback != null) {
            mCallback.onTaskComplete(mResponse, mTargetService);
        }
    }

    // }

	// { Internal Methods
	
    /**
     * Method to do a HTTP POST request
     * @param url: target url
     * @param postParams: JSON parameters
     * @return the service's response as string
     * @throws IOException
     */
    protected String doHttpPost(URL url, String postParams) throws IOException {
        Log.i(this.getClass().getSimpleName(),"[doHttpPost] Target URL: " + url.toString());
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[doHttpPost] Target URL: " + url.toString());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setConnectTimeout(10000);
        urlConnection.setReadTimeout(10000);
        urlConnection.setRequestProperty("Content-Type","application/json");
        urlConnection.setRequestProperty("Host", Configuration.EQUIPMENT_TEST_SERVICES_DOMAIN);
        urlConnection.connect();

        String responseStr = "";
        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
        out.write(postParams);
        out.close();
        int responseCode = urlConnection.getResponseCode();
        if (responseCode == 200) {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while ((line = br.readLine()) != null) {
                responseStr += line;
            }
        }
        return responseStr;
    }

    /**
     * Method to do a HTTP GET request
     * @param url: target url
     * @return the service's response as string
     * @throws IOException
     */
    protected String doHttpGet(URL url) throws IOException {
        Log.i(this.getClass().getSimpleName(),"[doHttpGet] Target URL: " + url.toString());
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "[doHttpGet] Target URL: " + url.toString());
        int resCode;
        StringBuffer response = new StringBuffer();
        BufferedReader reader;
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setRequestProperty("Content-Type","application/json");
        urlConnection.connect();
        resCode = urlConnection.getResponseCode();
        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
        if(resCode == 200){
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine())!= null){
                response.append(line);
            }
        }
        return response.toString();
    }

    // }

}
