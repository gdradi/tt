package com.technogym.myrow.tasks.download;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Task to download PDF files
 *
 * @author Federico Foschini
 */
public class DownloaderAsyncTask extends AsyncTask<String, Void, File> {

	public static final String DEFAULT_LOCAL_PATH = "file_downloaded.pdf";
	public static final int DOWNLOAD_PROGRESS_UPDATE = 0;

	// the downloaded amount of the file
	private Integer mDownloadedFileSize = 0;
	// the total size of the file to download
	private Integer mFileSize = 0;
	// the progress of the download, between 0 and 100
	private Float mDownloadProgress = 0f;

	private ProgressDialog mProgressDialog;

	private final Context mContext;
	private final Listener mListener;
	private final Handler mProgressHandler;

	private String mErrorMessage;

	// { Construction

	public DownloaderAsyncTask(final Context context, final Listener listener, final Handler progressHandler) {
		super();

		this.mContext = context;
		this.mListener = listener;
		this.mProgressHandler = progressHandler;
	}

	// }

	// { AsyncTask implementation

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		this.mProgressDialog = ProgressDialog.show(
			this.mContext, "Scaricando il PDF", "Attendere il termine del download...");
	}

	@Override
	protected File doInBackground(String... params) {

		File file = null;

		try {
			final URL url = new URL(params[0]);

			final HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();

			// create a new file, to save the downloaded file
			file = new File(params[1], params[2]);
			// if the file already exists, it gets overwritten
			FileOutputStream fileOutput = new FileOutputStream(file, false);

			// Stream used for reading the data from the internet
			InputStream inputStream = urlConnection.getInputStream();

			// this is the total size of the file which we are downloading
			mFileSize = urlConnection.getContentLength();

			// create a buffer...
			byte[] buffer = new byte[1024 * 1024];
			int bufferLength = 0;

			while ((bufferLength = inputStream.read(buffer)) > 0) {
				fileOutput.write(buffer, 0, bufferLength);
				this.mDownloadedFileSize += bufferLength;
				this.mDownloadProgress = ((float) this.mDownloadedFileSize / this.mFileSize) * 100;

				this.notifyDownloadProgress();
			}

			// close the output stream when complete
			fileOutput.close();

		} catch (final Exception e) {
			this.mErrorMessage = "Failed to download file. Please check your internet connection.";
			return null;
		}

		return file;
	}

	@Override
	protected void onPostExecute(File file) {
		super.onPostExecute(file);

		this.mProgressDialog.dismiss();

		if (file != null) {
			this.mListener.onSuccess(file);
		} else {
			this.mListener.onError(this.mErrorMessage);
		}
	}

	// }

	// { Private methods

	private void notifyDownloadProgress() {

		if (this.mProgressHandler != null) {

			final Message msg = Message.obtain();
			msg.what = DOWNLOAD_PROGRESS_UPDATE;
			msg.arg1 = this.mDownloadProgress.intValue();
			msg.arg2 = this.mFileSize / 1024;

			this.mProgressHandler.sendMessage(msg);
		}
	}

	// }

	// { Public inner interface

	public interface Listener {

		public void onError(final String error);

		public void onSuccess(final File file);

	}

	// }
}
