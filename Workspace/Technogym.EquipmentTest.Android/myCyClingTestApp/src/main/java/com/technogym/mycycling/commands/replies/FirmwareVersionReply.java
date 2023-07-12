package com.technogym.mycycling.commands.replies;

import com.technogym.mycycling.commands.models.ACommandReply;
import com.technogym.mycycling.commands.models.ICommandReply;

import java.util.ArrayList;

/**
 * FWVER command's reply
 */
public class FirmwareVersionReply extends ACommandReply {

    // { Public constants fields

    public final static String CMD_NAME = "FWVER";

    // }

    // { Constructors

    /***
     * Base constructor
     *
     * @param name          : command's name
     * @param isInfoCommand : command's type informations related
     * @param reply         : command's reply
     */
    private FirmwareVersionReply(String name, boolean isInfoCommand, String reply) {
        super(name, isInfoCommand);
        mReplyValues = retrieveValues(reply);

        // clean reply firmware version in order to keep only the number version
        String fwVersion = mReplyValues.get(0).substring(mReplyValues.get(0).length() - 5);
        mReplyValues = new ArrayList<>();
        mReplyValues.add(fwVersion);
    }

    // }

    // { Public methods

    /***
     * Builder of the command
     * @return an instance of this command
     */
    public static ICommandReply create(String reply) {
        return (new FirmwareVersionReply(CMD_NAME, false, reply));
    }

    // }
}
