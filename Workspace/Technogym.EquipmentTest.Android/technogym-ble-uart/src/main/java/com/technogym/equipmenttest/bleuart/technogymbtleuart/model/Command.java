package com.technogym.equipmenttest.bleuart.technogymbtleuart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.technogym.equipmenttest.bleuart.technogymbtleuart.Exceptions.ExeceptionParamCmdUart;

import java.util.List;

/**
 *
 * Command
 *
 */
public class Command implements Parcelable {
    public static Command _FWVER = new Command("FWVER");
    public static Command _ATLIBVER = new Command("ATLIBVER");
    public static Command _STG_DEV_NAME = new Command("STG_DEV_NAME");
    public static Command _RTG_DEV_NAME = new Command("RTG_DEV_NAME");
    public static Command _STG_ID = new Command("STG_ID");
    public static Command _RTG_ID = new Command("RTG_ID");
    public static Command _SHOMPWM = new Command("SHOMPWM");
    public static Command _RHOMPWM = new Command("RHOMPWM");
    public static Command _SMINPWM = new Command("SMINPWM");
    public static Command _RMINPWM = new Command("RMINPWM");
    public static Command _SMAXPWM = new Command("SMAXPWM");
    public static Command _RMAXPWM = new Command("RMAXPWM");
    public static Command _SCALRESTPOS = new Command("SCALRESTPOS");
    public static Command _RCALRESTPOS = new Command("RCALRESTPOS");
    public static Command _SOLDBRAKE = new Command("SOLDBRAKE");
    public static Command _SPINZA2 = new Command("SPINZA2");
    public static Command _SPINZA3 = new Command("SPINZA3");
    public static Command _RBRAKEVER = new Command("RBRAKEVER");
    public static Command _CTHETA0 = new Command("CTHETA0");
    public static Command _STHETA0 = new Command("STHETA0");
    public static Command _RTHETA0 = new Command("RTHETA0");
    public static Command _SPEDSENSDLY = new Command("SPEDSENSDLY");
    public static Command _RPEDSENSDLY = new Command("RPEDSENSDLY");
    public static Command _SPEDSENSPH = new Command("SPEDSENSPH");
    public static Command _RPEDSENSPH = new Command("RPEDSENSPH");
    public static Command _ENABLE_MANUAL = new Command("ENABLE_MANUAL");
    public static Command _DISABLE_MANUAL = new Command("DISABLE_MANUAL");
    public static Command _MOTOR_STEP_IN = new Command("MOTOR_STEP_IN");
    public static Command _MOTOR_STEP_OUT = new Command("MOTOR_STEP_OUT");
    public static Command _ENABLE_LOG = new Command("ENABLE_LOG");
    public static Command _DISABLE_LOG = new Command("DISABLE_LOG");
    public static Command _ENABLEWORKBENCHTEST = new Command("ENABLEWORKBENCHTEST");
    public static Command _SCALIPERPOSITION = new Command("SCALIPERPOSITION");
    public static Command _STHETATORQUE = new Command("STHETATORQUE");
    public static Command _RTHETATORQUE = new Command("RTHETATORQUE");
    public static Command _SOPTICALSENSCOEFF = new Command("SOPTICALSENSCOEFF");
    public static Command _ROPTICALSENSCOEFF = new Command("ROPTICALSENSCOEFF");
    public static Command _SFRICTIONCOEFF = new Command("SFRICTIONCOEFF");
    public static Command _RFRICTIONCOEFF = new Command("RFRICTIONCOEFF");
    public static Command _SRPMFROMLIB = new Command("SRPMFROMLIB");
    public static Command _SRPMFROMLIBFAST = new Command("SRPMFROMLIBFAST");
    public static Command _SRPMFROMSENSPERIOD = new Command("SRPMFROMSENSPERIOD");
    public static Command _RRPMSOURCE = new Command("RRPMSOURCE");
    public static Command _SRPMMIN = new Command("SRPMMIN");
    public static Command _RRPMMIN = new Command("RRPMMIN");
    public static Command _RMODE = new Command("RMODE");
    public static Command _SGRADEMODE = new Command("SGRADEMODE");
    public static Command _SPOWERMODE = new Command("SPOWERMODE");
    public static Command _SMOTALWAYSON = new Command("SMOTALWAYSON");
    public static Command _ROTALWAYSON = new Command("ROTALWAYSON");
    public static Command _SMOTCOUNTSTOP = new Command("SMOTCOUNTSTOP");
    public static Command _RMOTCOUNTSTOP = new Command("RMOTCOUNTSTOP");
    public static Command _SMOTCOUNTHOM = new Command("SMOTCOUNTHOM");
    public static Command _RMOTCOUNTHOM = new Command("RMOTCOUNTHOM");
    public static Command _SFILTPED = new Command("SFILTPED");
    public static Command _RFILTPED = new Command("RFILTPED");
    public static Command _STARTUSERCAL = new Command("STARTUSERCAL");
    public static Command _STARTFACTORYCAL = new Command("STARTFACTORYCAL");
    public static Command _ABORTCAL = new Command("ABORTCAL");
    public static Command _RCALSTATE = new Command("RCALSTATE");
    public static Command _RCALIBK = new Command("RCALIBK");
    public static Command _RCALIBTHETA0 = new Command("RCALIBTHETA0");
    public static Command _SFACTORYCALTORQUE = new Command("SFACTORYCALTORQUE");
    public static Command _SKOPTICALSENS = new Command("SKOPTICALSENS");
    public static Command _STARTBL = new Command("STARTBL");
    public static Command _RSTATAPP = new Command("RSTATAPP");

    public static Command _RESETERRORLOG = new Command("RESETERRORLOG");
    public static Command _RERRORLOG = new Command("RERRORLOG");
    public static Command _SAPPPARAM = new Command("SAPPPARAM");

    public static Command _UNLOCKBLE = new Command("UNLOCKBLE");
    public static Command _LOCKBLE = new Command("LOCKBLE");
    public static Command _RUNLOCKBLE = new Command("RUNLOCKBLE");

    public static Command _UNDEFINED_ = new Command("_UNDEFINED_");

    private final String mValue;

    private Command(String value) {
        mValue = value;
    }

    @Override
    public int hashCode() {
        return mValue.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Command) {
            return mValue.equals(((Command) o).mValue);

        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return mValue;
    }

    public static Command valueOf(String value) {
        if (value == null) {
            return null;
        }

        if (value.equals("FWVER")) {
            return _FWVER;
        } else if (value.equals("ATLIBVER")) {
            return _ATLIBVER;
        } else if (value.equals("STG_DEV_NAME")) {
            return _STG_DEV_NAME;
        } else if (value.equals("RTG_DEV_NAME")) {
            return _RTG_DEV_NAME;
        } else if (value.equals("STG_ID")) {
            return _STG_ID;
        } else if (value.equals("RTG_ID")) {
            return _RTG_ID;
        } else if (value.equals("SHOMPWM")) {
            return _SHOMPWM;
        } else if (value.equals("RHOMPWM")) {
            return _RHOMPWM;
        } else if (value.equals("SMINPWM")) {
            return _SMINPWM;
        } else if (value.equals("RMINPWM")) {
            return _RMINPWM;
        } else if (value.equals("SMAXPWM")) {
            return _SMAXPWM;
        } else if (value.equals("RMAXPWM")) {
            return _RMAXPWM;
        } else if (value.equals("SCALRESTPOS")) {
            return _SCALRESTPOS;
        } else if (value.equals("RCALRESTPOS")) {
            return _RCALRESTPOS;
        } else if (value.equals("SOLDBRAKE")) {
            return _SOLDBRAKE;
        } else if (value.equals("SPINZA2")) {
            return _SPINZA2;
        } else if (value.equals("SPINZA3")) {
            return _SPINZA3;
        } else if (value.equals("RBRAKEVER")) {
            return _RBRAKEVER;
        } else if (value.equals("CTHETA0")) {
            return _CTHETA0;
        } else if (value.equals("STHETA0")) {
            return _STHETA0;
        } else if (value.equals("RTHETA0")) {
            return _RTHETA0;
        } else if (value.equals("SPEDSENSDLY")) {
            return _SPEDSENSDLY;
        } else if (value.equals("RPEDSENSDLY")) {
            return _RPEDSENSDLY;
        } else if (value.equals("SPEDSENSPH")) {
            return _SPEDSENSPH;
        } else if (value.equals("RPEDSENSPH")) {
            return _RPEDSENSPH;
        } else if (value.equals("ENABLE_MANUAL")) {
            return _ENABLE_MANUAL;
        } else if (value.equals("DISABLE_MANUAL")) {
            return _DISABLE_MANUAL;
        } else if (value.equals("MOTOR_STEP_IN")) {
            return _MOTOR_STEP_IN;
        } else if (value.equals("MOTOR_STEP_OUT")) {
            return _MOTOR_STEP_OUT;
        } else if (value.equals("ENABLE_LOG")) {
            return _ENABLE_LOG;
        } else if (value.equals("DISABLE_LOG")) {
            return _DISABLE_LOG;
        } else if (value.equals("ENABLEWORKBENCHTEST")) {
            return _ENABLEWORKBENCHTEST;
        } else if (value.equals("SCALIPERPOSITION")) {
            return _SCALIPERPOSITION;
        } else if (value.equals("STHETATORQUE")) {
            return _STHETATORQUE;
        } else if (value.equals("RTHETATORQUE")) {
            return _RTHETATORQUE;
        } else if (value.equals("SOPTICALSENSCOEFF")) {
            return _SOPTICALSENSCOEFF;
        } else if (value.equals("ROPTICALSENSCOEFF")) {
            return _ROPTICALSENSCOEFF;
        } else if (value.equals("SFRICTIONCOEFF")) {
            return _SFRICTIONCOEFF;
        } else if (value.equals("RFRICTIONCOEFF")) {
            return _RFRICTIONCOEFF;
        } else if (value.equals("SRPMFROMLIB")) {
            return _SRPMFROMLIB;
        } else if (value.equals("SRPMFROMLIBFAST")) {
            return _SRPMFROMLIBFAST;
        } else if (value.equals("SRPMFROMSENSPERIOD")) {
            return _SRPMFROMSENSPERIOD;
        } else if (value.equals("RRPMSOURCE")) {
            return _RRPMSOURCE;
        } else if (value.equals("SRPMMIN")) {
            return _SRPMMIN;
        } else if (value.equals("RRPMMIN")) {
            return _RRPMMIN;
        } else if (value.equals("RMODE")) {
            return _RMODE;
        } else if (value.equals("SGRADEMODE")) {
            return _SGRADEMODE;
        } else if (value.equals("SPOWERMODE")) {
            return _SPOWERMODE;
        } else if (value.equals("SMOTALWAYSON")) {
            return _SMOTALWAYSON;
        } else if (value.equals("ROTALWAYSON")) {
            return _ROTALWAYSON;
        } else if (value.equals("SMOTCOUNTSTOP")) {
            return _SMOTCOUNTSTOP;
        } else if (value.equals("RMOTCOUNTSTOP")) {
            return _RMOTCOUNTSTOP;
        } else if (value.equals("SMOTCOUNTHOM")) {
            return _SMOTCOUNTHOM;
        } else if (value.equals("RMOTCOUNTHOM")) {
            return _RMOTCOUNTHOM;
        } else if (value.equals("SFILTPED")) {
            return _SFILTPED;
        } else if (value.equals("RFILTPED")) {
            return _RFILTPED;
        } else if (value.equals("STARTUSERCAL")) {
            return _STARTUSERCAL;
        } else if (value.equals("STARTFACTORYCAL")) {
            return _STARTFACTORYCAL;
        } else if (value.equals("ABORTCAL")) {
            return _ABORTCAL;
        } else if (value.equals("RCALSTATE")) {
            return _RCALSTATE;
        } else if (value.equals("RCALIBK")) {
            return _RCALIBK;
        } else if (value.equals("RCALIBTHETA0")) {
            return _RCALIBTHETA0;
        } else if (value.equals("SFACTORYCALTORQUE")) {
            return _SFACTORYCALTORQUE;
        } else if (value.equals("SKOPTICALSENS")) {
            return _SKOPTICALSENS;
        } else if (value.equals("STARTBL")) {
            return _STARTBL;
        } else if (value.equals("RSTATAPP")) {
            return _RSTATAPP;
        } else if (value.equals("RESETERRORLOG")) {
            return _RESETERRORLOG;
        } else if (value.equals("RERRORLOG")) {
            return _RERRORLOG;
        } else if (value.equals("SAPPPARAM")) {
            return _SAPPPARAM;
        } else if (value.equals("UNLOCKBLE")) {
            return _UNLOCKBLE;
        } else if (value.equals("LOCKBLE")) {
            return _LOCKBLE;
        } else if (value.equals("RUNLOCKBLE")) {
            return _RUNLOCKBLE;
        } else {
            return _UNDEFINED_;
        }
    }

    /************************ Parcel Serialization ************************/
    public static final Creator<Command> CREATOR = new Creator<Command>() {
        public Command createFromParcel(Parcel in) {
            return new Command(in);
        }

        public Command[] newArray(int size) {
            return new Command[size];
        }
    };

    private Command(Parcel in) {
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


    public static class RCALSTATE {
        public static final int HOMING_ENDED = 0;
        public static final int NEWPOS = 1;
        public static final int WAITSTART = 2;
        public static final int STARTING = 3;
        public static final int SAMPLE0 = 4;
        public static final int WAITSAMPLE = 5;
        public static final int SAMPLE = 6;
        public static final int ELAB = 7;
        public static final int END = 8;

    }

    public static int getRcalStateFromParam(List<String> params) throws ExeceptionParamCmdUart {
        if (params == null || params.size() < 1) throw new ExeceptionParamCmdUart("Error param not valid. can't find params");
        String param = params.get(0);
        String[] splitParam = param.split(",");
        if (splitParam.length != 2)
            throw new ExeceptionParamCmdUart("Error param not valid. RCALSTATE command");
        try {
            int value = Integer.valueOf(splitParam[0]);
            return value;
        } catch (NumberFormatException e) {
            throw new ExeceptionParamCmdUart("Error param not valid. Cannot convert to int");
        }
    }

    public static double getFirstDoubleValFromParam(List<String> params) throws ExeceptionParamCmdUart {
        if (params == null || params.size() < 1) throw new ExeceptionParamCmdUart("Error param not valid. can't find params");
        String param = params.get(0);
        try {
            return Double.valueOf(param);
        } catch (NumberFormatException e) {
            throw new ExeceptionParamCmdUart("Error param not valid. Cannot convert to double");
        }
    }

}
