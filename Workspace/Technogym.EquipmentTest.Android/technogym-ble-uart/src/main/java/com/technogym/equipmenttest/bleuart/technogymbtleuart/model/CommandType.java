package com.technogym.equipmenttest.bleuart.technogymbtleuart.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * CommandType
 *
 */
public class CommandType implements Parcelable {
	public static CommandType _TX = new CommandType("TX");
	public static CommandType _RX = new CommandType("RX");
	public static CommandType _UNDEFINED_ = new CommandType("_UNDEFINED_");

	private final String mValue;

	private CommandType(String value) {
		mValue = value;
	}

	@Override
	public int hashCode() {
		return mValue.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof CommandType) {
			return mValue.equals(((CommandType) o).mValue);

		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return mValue;
	}

	public static CommandType valueOf(String value) {
		if (value == null) {
			return null;
		}

		if (value.equals("TX")) {
			return _TX;
		} else if (value.equals("RX")) {
			return _RX;
		} else {
			return _UNDEFINED_;
		}
	}

	/************************ Parcel Serialization ************************/
	public static final Creator<CommandType> CREATOR = new Creator<CommandType>() {
		public CommandType createFromParcel(Parcel in) {
			return new CommandType(in);
		}

		public CommandType[] newArray(int size) {
			return new CommandType[size];
		}
	};

	private CommandType(Parcel in) {
		mValue = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mValue);
	}

	@Override
	public int describeContents() {
		return 0;
	}
}
