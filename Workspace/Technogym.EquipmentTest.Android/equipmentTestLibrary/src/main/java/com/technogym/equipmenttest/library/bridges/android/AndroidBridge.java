package com.technogym.equipmenttest.library.bridges.android;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;

import com.technogym.equipmenttest.library.tasks.DownloaderAsyncTask;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;
import android.support.v4.content.FileProvider;

public class AndroidBridge extends Bridge implements IAndroidBridge {

	protected boolean mIsUnity = true;

	// { Construction

	protected AndroidBridge(final Activity activity) {
		super(activity);
	}

	public static IAndroidBridge create(final Activity activity) {
		return new AndroidBridge(activity);
	}

	// }

	// { IAndroidProxy implementation

	/**
	 * {@inheritDoc}
	 * 
	 * It directly calls the {@link Activity#finish} method, because it should be a single Activity application with an
	 * embedded web application.
	 * */
	@Override
	@JavascriptInterface
	public void closeApplication() {
		this.mActivity.finish();
	}

	@Override
	@JavascriptInterface
	public void clearResources() {
		final File file = new File(Environment.getExternalStorageDirectory(), DownloaderAsyncTask.DEFAULT_LOCAL_PATH);
		if (file.exists()) {
			file.delete();
		}
	}

	@Override
	@JavascriptInterface
	public void startActivity(String namespace, String jsonData) {
		final Intent intent = new Intent(namespace);

		final Bundle args = new Bundle();
		try {
			// TODO - try to think at some way to pass different data types
			// Right now we receive only strings.
			final JSONArray data = new JSONArray(jsonData);
			if (data.length() > 0) {
				args.putString(data.getString(0), data.getString(1));
				if (data.length() > 2) {
					args.putString(data.getString(2), data.getString(3));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		intent.putExtras(args);

		this.mActivity.startActivity(intent);
	}

	@Override
	@JavascriptInterface
	public void startService(String namespace, String jsonData) {
		final Intent intent = new Intent(namespace);

		JSONArray data;
		try {
			data = new JSONArray(jsonData);
			if (data.length() >= 2) {
				intent.putExtra(data.getString(0), data.getInt(1));
				if (data.length() >= 4) {
					intent.putExtra(data.getString(2), data.getString(3));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		this.mActivity.startService(intent);
	}

	@Override
	@JavascriptInterface
	public void broadcast(final String namespace) {
		final Intent intent = new Intent(namespace);
		this.mActivity.sendBroadcast(intent);
	}

	@Override
	@JavascriptInterface
	public void showToastNotification(final String message) {
		Toast.makeText(this.mActivity, message, Toast.LENGTH_LONG).show();
	}

	@Override
	@JavascriptInterface
	public void log(final String level, final String message) {
		Log.i(this.getClass().getName(), message);
	}

	@Override
	@JavascriptInterface
	public void openDocument(final String type, final String url) {
		new DownloaderAsyncTask(this.mActivity, new DownloaderAsyncTask.Listener() {

			@Override
			public void onSuccess(File file) {
				openPDF(file);
			}

			@Override
			public void onError(String error) {
				// do nothing
			}
		}, null).execute(
			url, Environment.getExternalStorageDirectory().getPath(), DownloaderAsyncTask.DEFAULT_LOCAL_PATH);
	}

	// }

	// { Protected methods

	protected void openPDF(final File file) {

		//Uri fullPath = Uri.fromFile(file);
		Uri fullPath = FileProvider.getUriForFile(mActivity.getApplicationContext(), mActivity.getApplicationContext().getPackageName() + ".provider", file);

		try {

			final Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(fullPath, "application/pdf");
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

			this.mActivity.startActivity(intent);

		} catch (ActivityNotFoundException e) {
			// do nothing
		}
	}

	// }

}
