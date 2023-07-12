package com.technogym.mycycling.commands.factories;

import com.technogym.mycycling.commands.models.ICommandReply;
import com.technogym.mycycling.commands.replies.BootloaderFirmwareVersionReply;
import com.technogym.mycycling.commands.replies.DumpStateReply;
import com.technogym.mycycling.commands.replies.FirmwareVersionReply;
import com.technogym.mycycling.commands.replies.SerialNumberReply;
import com.technogym.mycycling.commands.replies.SetRestPositionReply;
import com.technogym.mycycling.commands.replies.StartBootloaderModeReply;
import com.technogym.mycycling.commands.replies.UpdatedFirmwareVersionReply;

/**
 * Factory to build commands' replies
 */
public class CommandReplyFactory {

    /**
     * Builder for command replies
     */
    public static ICommandReply create(String cmdName, String reply) {
        switch (cmdName) {
            case BootloaderFirmwareVersionReply.CMD_NAME:
                return BootloaderFirmwareVersionReply.create(reply);

            case DumpStateReply.CMD_NAME:
                return DumpStateReply.create(reply);

            case StartBootloaderModeReply.CMD_NAME:
                return StartBootloaderModeReply.create(reply);

            case FirmwareVersionReply.CMD_NAME:
                return FirmwareVersionReply.create(reply);

            case UpdatedFirmwareVersionReply.CMD_NAME:
                return UpdatedFirmwareVersionReply.create(reply);

            case SetRestPositionReply.CMD_NAME:
                return SetRestPositionReply.create(reply);

            case SerialNumberReply.CMD_NAME:
                return SerialNumberReply.create(reply);

            default:
                return null;
        }
    }
}
