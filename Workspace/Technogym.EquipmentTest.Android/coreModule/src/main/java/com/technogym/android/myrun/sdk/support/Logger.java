package com.technogym.android.myrun.sdk.support;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import android.util.Log;

public class Logger {

	public static File LOG_FILE = null;
	public static boolean WRITE_ON_FILE_ENABLED = false;

	public static void e(final String key, final String msg) {
		Log.e(key, msg);
		writeOnFile(key, msg);
	}

	public static void e(final String key, final String msg, final Exception exception) {
		Log.e(key, msg, exception);
		writeOnFile(key, msg, exception);
	}

	public static void d(final String key, final String msg) {
		Log.d(key, msg);
		writeOnFile(key, msg);
	}

	public static void i(final String key, final String msg) {
		Log.i(key, msg);
		writeOnFile(key, msg);
	}

	public static void w(final String key, final String msg) {
		Log.w(key, msg);
		writeOnFile(key, msg);
	}

	public static void v(final String key, final String msg) {
		Log.v(key, msg);
		writeOnFile(key, msg);
	}

	private static void writeOnFile(final String key, final String msg) {
		if (WRITE_ON_FILE_ENABLED) {
			checkFile();
			try {
				BufferedWriter buf = new BufferedWriter(new FileWriter(LOG_FILE, true));
				buf.append(msg);
				buf.newLine();
				buf.flush();
				buf.close();
			} catch (IOException e) {
				// INF: Empty
			}
		}
	}

	private static void writeOnFile(final String key, final String msg, final Exception exception) {
		if (WRITE_ON_FILE_ENABLED) {
			checkFile();
			try {
				exception.printStackTrace(new PrintStream(LOG_FILE));
			} catch (FileNotFoundException e) {
				// INF: Empty
			}

			writeOnFile(key, msg);
		}
	}

	private static void checkFile() {
		if (LOG_FILE == null) {
			LOG_FILE = new File("sdcard/mylog.file");
		}

		if (!LOG_FILE.exists()) {
			try {
				LOG_FILE.createNewFile();
			} catch (IOException e) {
				// INF: Empty
			}
		}
	}
}
