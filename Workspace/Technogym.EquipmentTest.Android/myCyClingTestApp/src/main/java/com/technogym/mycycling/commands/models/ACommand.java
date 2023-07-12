package com.technogym.mycycling.commands.models;

import java.util.ArrayList;
import java.util.List;

/***
 * Abstract equipment command
 */
public abstract class ACommand implements ICommand {

    // { Internal fields

    protected String mName;
    protected boolean mIsInfoCommand;
    protected List<String> mParameters;

    // }

    // { Constructors

    /***
     * Base constructor
     * @param name: command's name
     * @param isInfoCommand: command's type informations related
     */
    public ACommand(String name, boolean isInfoCommand) {
        mParameters = new ArrayList<>();
        mName = name;
        mIsInfoCommand = isInfoCommand;
    }

    // }

    // { Public methods

    @Override
    public String getName() { return mName; }

    @Override
    public List<String> getParameters() { return mParameters; }

    @Override
    public String serializeAsString() {
        String ser = CommandConstants.START_COMMAND_TOKEN + mName;
        for (String p : mParameters) {
            ser = ser + " " + p;
        }
        ser = ser + CommandConstants.END_COMMAND_TOKEN;
        return ser;
    }

    @Override
    public byte[] serialize() { return (serializeAsString().getBytes()); }

    @Override
    public boolean isEquipmentInfoCommand() { return mIsInfoCommand; }

    // }

    // { Internal methods

    // }
}
