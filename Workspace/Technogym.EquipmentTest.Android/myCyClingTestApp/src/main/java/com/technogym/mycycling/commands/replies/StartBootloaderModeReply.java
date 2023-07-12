package com.technogym.mycycling.commands.replies;

import com.technogym.mycycling.commands.models.ACommandReply;
import com.technogym.mycycling.commands.models.ICommandReply;

/**
 * STARTBL command reply
 */
public class StartBootloaderModeReply extends ACommandReply {

    // { Public constants fields

    public final static String CMD_NAME = "STARTBL";

    // }

    // { Constructors

    /***
     * Base constructor
     *
     * @param name          : command's name
     * @param isInfoCommand : command's type informations related
     * @param reply         : command's reply
     */
    private StartBootloaderModeReply(String name, boolean isInfoCommand, String reply) {
        super(name, isInfoCommand);
        mReplyValues = retrieveValues(reply);
    }

    // }

    // { Public methods

    /***
     * Builder of the command
     * @return an instance of this command
     */
    public static ICommandReply create(String reply) {
        return (new StartBootloaderModeReply(CMD_NAME, false, reply));
    }

    // }
}
