package com.technogym.equipmenttest.bleuart.technogymbtleuart.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;


import com.technogym.equipmenttest.bleuart.fitnessmachineservice.Utils;
import com.technogym.equipmenttest.bleuart.technogymbtleuart.Exceptions.ExceptionCommandUart;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * CommandModel
 */
public class CommandModel implements Parcelable {

    public class Uart {
        public static final byte START_CMD_TX = (byte) '@';
        public static final byte END_CMD_TX = (byte) '#';
        public static final byte START_CMD_RX = (byte) '!';
        public static final byte END_CMD_RX_0 = (byte) '\r';
        public static final byte END_CMD_RX_1 = (byte) '\n';
        public static final byte END_CMD_RX_2 = (byte) '#';

        public static final byte TX_PARAM_SEPARATOR = (byte) ' ';
        public static final byte RX_PARAM_SEPARATOR = (byte) ':';
    }

    public static final String CMD = "cmd";
    public static final String PARAMS = "params";
    public static final String TYPE = "type";

    protected String mCmd;
    protected java.util.List<String> mParams = new ArrayList<String>();
    protected CommandType mType;

    public CommandModel() {
    }

    public CommandModel(String cmd, CommandType type, ArrayList<String> params) {
        mCmd = cmd;
        mType = type;
        mParams = params;
    }

    public CommandModel setCmd(String value) {
        mCmd = value;
        return this;
    }

    public String getCmd() {
        return mCmd;
    }

    public CommandModel setParams(java.util.List<String> value) {
        mParams = value;
        return this;
    }

    public java.util.List<String> getParams() {
        return mParams;
    }

    public CommandModel setType(
            CommandType value) {
        mType = value;
        return this;
    }

    public CommandType getType() {
        return mType;
    }

    public void addParam(String param) {
        if (mParams == null) mParams = new ArrayList<String>();
        mParams.add(param);
    }

    /************************
     * Parcel Serialization
     ************************/
    public static final Creator<CommandModel> CREATOR = new Creator<CommandModel>() {
        public CommandModel createFromParcel(Parcel in) {
            return new CommandModel(in);
        }

        public CommandModel[] newArray(int size) {
            return new CommandModel[size];
        }
    };

    private CommandModel(Parcel in) {
        mCmd = (String) in.readValue(String.class
                .getClassLoader());
        int mParamsCount0 = in.readInt();
        if (mParamsCount0 >= 0) {
            java.util.List<String> mParams0 = new java.util.LinkedList<String>();
            mParams = mParams0;
            for (int i0 = 0; i0 < mParamsCount0; i0++) {
                mParams0.add((String) in
                        .readValue(String.class.getClassLoader()));
            }
        }
        mType = (CommandType) in
                .readValue(CommandType.class
                        .getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(mCmd);
        java.util.List<String> mParams1 = mParams;
        if (mParams1 != null) {
            int mParamsCount1 = mParams1.size();
            dest.writeInt(mParamsCount1);
            for (String mParams0 : mParams1) {
                dest.writeValue(mParams0);
            }
        } else {
            dest.writeInt(-1);
        }
        dest.writeValue(mType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public CommandModel decode(String rawCmd, CommandType type) throws ExceptionCommandUart {
        if (rawCmd == null) throw new ExceptionCommandUart("raw rx command is null");
        mType = type;

        if (CommandType._RX.equals(mType)) {
            if (rawCmd.length() <= 7) throw new ExceptionCommandUart("rx command has no content");
            if (rawCmd.charAt(0) != (char) Uart.START_CMD_RX)
                throw new ExceptionCommandUart("rx first char is not conform to protocol");
            if (rawCmd.charAt(rawCmd.length() - 3) != (char) Uart.END_CMD_RX_0)
                throw new ExceptionCommandUart("rx end char is not conform to protocol");
            if (rawCmd.charAt(rawCmd.length() - 2) != (char) Uart.END_CMD_RX_1)
                throw new ExceptionCommandUart("rx end char is not conform to protocol");
            if (rawCmd.charAt(rawCmd.length() - 1) != (char) Uart.END_CMD_RX_2)
                throw new ExceptionCommandUart("rx end char is not conform to protocol");

            String checkSum = rawCmd.subSequence(rawCmd.length() - 6, rawCmd.length() - 3).toString();
            String bodyCmd = "";
            rawCmd = rawCmd.subSequence(1, rawCmd.length() - 6).toString();

            String[] rawCmdArray = rawCmd.split(String.valueOf((char) Uart.RX_PARAM_SEPARATOR));

            mParams.clear();
            for (int i = 0; i < rawCmdArray.length; i++) {
                if (i == 0) {
                    mCmd = rawCmdArray[i];
                    continue;
                }
                bodyCmd += rawCmdArray[i];
                if (i < rawCmdArray.length - 1)
                    bodyCmd = bodyCmd + (char) (Uart.RX_PARAM_SEPARATOR);
                mParams.add(rawCmdArray[i]);
            }

            if (!Utils.checkSumModule(bodyCmd, checkSum, 256)) {
                Log.d(TAG, "Error body = " + bodyCmd + " - checksum = " + checkSum);
                throw new ExceptionCommandUart("rx command error checksum. Body = " + bodyCmd + " - checksum = " + checkSum);
            }

        } else {
            throw new ExceptionCommandUart("can't decode TX command type. Decode is only for RX command type");
        }

        return this;
    }

    public String encode() throws ExceptionCommandUart {
        String encoded = "";
        if (mCmd == null)
            throw new ExceptionCommandUart("No valid command. Please set a valid cmd");

        if (CommandType._TX.equals(mType)) {
            encoded += (String.valueOf((char) Uart.START_CMD_TX) + mCmd);
            if (mParams != null) {
                for (String par : mParams) {
                    encoded += (String.valueOf((char) Uart.TX_PARAM_SEPARATOR) + par);
                }
            }
            encoded += String.valueOf((char) Uart.END_CMD_TX);

        } else {
            throw new ExceptionCommandUart("can't encode RX command type. Encode is only for TX type");

        }
        return encoded;
    }
}
