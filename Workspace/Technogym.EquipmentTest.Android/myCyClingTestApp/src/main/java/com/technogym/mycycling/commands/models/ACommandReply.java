package com.technogym.mycycling.commands.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/***
 * Abstract equipment command's reply
 */
public abstract class ACommandReply implements ICommandReply {

    // { Internal fields

    protected String mName;
    protected boolean mIsInfoCommand;
    protected List<String> mReplyValues;

    // }

    // { Constructors

    /***
     * Base constructor
     * @param name: command's name
     * @param isInfoCommand: command's type informations related
     */
    public ACommandReply(String name, boolean isInfoCommand) {
        mReplyValues = new ArrayList<>();
        mName = name;
        mIsInfoCommand = isInfoCommand;
    }

    // }

    // { Public methods

    @Override
    public String getName() { return mName; }

    @Override
    public List<String> getValues() { return mReplyValues; }

    @Override
    public boolean isEquipmentInfoCommand() { return mIsInfoCommand; }

    // }

    // { Private methods

    /***
     * Gets the values from the reply
     * @param reply: reply
     * @return the values from the reply
     */
    protected List<String> retrieveValues(String reply) {
        reply = reply.replaceAll("(\\r|\\n)", "");
        reply = reply.trim();
        //Log.i("ACommandReply", "retrieveValues -> reply: [" + reply  +"]");
        String startReply = CommandConstants.START_COMMAND_REPLY_TOKEN + mName +
                CommandConstants.COMMAND_RETURN_VALUES_SEPARATOR_TOKEN;
        //Log.i("ACommandReply", "retrieveValues -> start reply: [" + startReply +"]");
        String tmp = reply.replace(startReply, "");

        //Log.i("ACommandReply", "retrieveValues -> tmp1: [" + tmp  +"]");
        tmp = tmp.replace(CommandConstants.END_COMMAND_REPLY_TOKEN, "");
        tmp = tmp.substring(0, tmp.length() - 3);
        Log.i("ACommandReply", "cleaned reply for command [" + mName + "] : [" + tmp  +"]");
        String[] splitted = tmp.split(CommandConstants.COMMAND_RETURN_VALUES_SEPARATOR_TOKEN);
        mReplyValues = new ArrayList<>();
        for (int i = 0; i < splitted.length; i++) {
            mReplyValues.add(splitted[i].trim());
        }
        return mReplyValues;
    }

    // }

}
