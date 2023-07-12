package com.technogym.android.myrun.sdk.bluetooth.threads;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.technogym.android.myrun.sdk.connection.utils.Constants;
import com.technogym.android.myrun.sdk.support.Logger;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;

public class BluetoothConnectedThread extends Thread {

	private static final String LOGTAG = "BluetoothConnectedThread";

	private static final String DATA_CHUNK_REGEX = "!([^!#]+)#";
	private static final int DATA_CHUNK_SIZE = 128;

	private final Handler mHandler;
	private final BluetoothSocket mSocket;
	private final InputStream mInStream;
	private final OutputStream mOutStream;

	private String mBufferOutput;
	private Pattern mPattern;

	private boolean mIsRunning;

	// { Construction

	protected BluetoothConnectedThread(final Handler handler, final BluetoothSocket socket) {
		super();

		this.mHandler = handler;
		this.mSocket = socket;

		this.mIsRunning = false;

		InputStream tmpIn = null;
		OutputStream tmpOut = null;

		try {
			tmpIn = socket.getInputStream();
			tmpOut = socket.getOutputStream();
		} catch (IOException e) {
			Logger.e(LOGTAG, "Input and output streams not created successfully.");
		}

		this.mInStream = tmpIn;
		this.mOutStream = tmpOut;

		this.mPattern = Pattern.compile(DATA_CHUNK_REGEX);

		this.mBufferOutput = "";
	}

	public static BluetoothConnectedThread create(final Handler handler, final BluetoothSocket socket) {
		return new BluetoothConnectedThread(handler, socket);
	}

	// }

	// { Thread methods override

	@Override
	public void run() {
		this.mIsRunning = true;

		final byte[] buffer = new byte[DATA_CHUNK_SIZE];

		while (this.mIsRunning) {
			try {
				int bytes = this.mInStream.read(buffer);
				this.onMessageRead(buffer, bytes);
			} catch (Exception e) {
				e.printStackTrace();
				this.mHandler.sendEmptyMessage(Constants.CONNECTION_INTERRUPTED);
				break;
			}
		}
	}

	// }

	// { Public methods

	public void write(final byte[] buffer) {
		synchronized (this) {
			try {
				this.mOutStream.write(buffer);
				this.mHandler.sendEmptyMessage(Constants.MESSAGE_SENT);
			} catch (IOException e) {
				Logger.e(LOGTAG, "Exception during write", e);
			}
		}
	}

	public void write(final int out) {
		synchronized (this) {
			try {
				mOutStream.write(out);
				mHandler.sendEmptyMessage(Constants.MESSAGE_SENT);
			} catch (IOException e) {
				Logger.e(LOGTAG, "Exception during write", e);
			}
		}
	}

	public void write(final String message) {
		if (message.length() > 0) {
			byte[] send = message.getBytes();
			this.write(send);
		}
	}

	public void cancel() {
		try {
			Logger.d(LOGTAG, "Closing streams");
			this.mIsRunning = false;
			this.mInStream.close();
			this.mOutStream.close();
			this.mSocket.close();
		} catch (IOException e) {
			Logger.e(LOGTAG, "close() of connect socket failed");
		}
	}

	// }

	// { Private and protected methods

	private void onMessageRead(final byte[] bytes, final int nBytes) throws UnsupportedEncodingException {
		synchronized (this) {
			final String messageRead = new String(bytes, 0, nBytes, "UTF-8");
			this.mBufferOutput += messageRead;

			final Matcher m = this.mPattern.matcher(this.mBufferOutput);

			while (m.find()) {
				onChunkFound(m.group());
			}
		}
	}

	private void onChunkFound(final String chunk) {
		synchronized (this) {
			this.cleanBufferOutput(chunk);

			final Message msg = new Message();
			msg.what = Constants.MESSAGE_RECEIVED;
			msg.obj = chunk.substring(1, chunk.length() - 1);

			this.mHandler.sendMessage(msg);
		}
	}

	private void cleanBufferOutput(final String chunk) {
		synchronized (this) {
			this.mBufferOutput = this.mBufferOutput.substring(this.mBufferOutput.indexOf("#") + 1);
		}
	}

	// }

}
