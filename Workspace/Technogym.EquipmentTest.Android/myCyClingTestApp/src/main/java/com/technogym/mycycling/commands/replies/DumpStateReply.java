package com.technogym.mycycling.commands.replies;

import android.util.Log;

import com.technogym.mycycling.commands.models.ACommandReply;
import com.technogym.mycycling.commands.models.ICommandReply;

import java.util.HashMap;
import java.util.List;

/**
 * DUMPLK command's reply
 */
public class DumpStateReply extends ACommandReply {

    // { Public constants fields

    public final static String CMD_NAME = "DUMPLK";

    // }

    // { Internal fields

    protected HashMap<String, String> mDumpValues;

    // }

    // { Constructors

    /***
     * Base constructor
     *
     * @param name          : command's name
     * @param isInfoCommand : command's type informations related
     * @param reply         : command's reply
     */
    private DumpStateReply(String name, boolean isInfoCommand, String reply) {
        super(name, isInfoCommand);
        mDumpValues = new HashMap<>();
        mReplyValues = retrieveValues(reply);
    }

    // }

    // { Public methods

    /***
     * Builder of the command
     * @return an instance of this command
     */
    public static ICommandReply create(String reply) {
        return (new DumpStateReply(CMD_NAME, false, reply));
    }

    /**
     * Gets the dump reply's values
     * @return the dump reply's values
     */
    public HashMap<String, String> getDumpReplyValues() { return mDumpValues; }

    // }

    // { Internal methods

    /***
     * Gets the values from the reply
     * @param reply: reply
     * @return the values from the reply
     */
    @Override
    protected List<String> retrieveValues(String reply) {
        List<String> cleanedBaseReply = super.retrieveValues(reply);

        /*
         * DUMPLK reply structure (after base clean):
         * TGRADE=0.000000 TPOWER=0.000000 RPM=0.000000 POWER=0.000000 THETAMOT=248.000000 TORQUEANGLE=0.000000 BLECON=N
         */
        String[] splittedVals = cleanedBaseReply.get(0).split(" ");
        for (int i = 0; i < splittedVals.length; i++) {
            String[] splittedTmp = splittedVals[i].split("=");
            Log.i(this.getClass().getSimpleName(), "[retrieveValues] Current Reply Value: " + splittedVals[i]);
            if(splittedTmp.length >= 2) {
                mDumpValues.put(splittedTmp[0], splittedTmp[1]);
            }
        }
        return cleanedBaseReply;
    }

    // }
}
