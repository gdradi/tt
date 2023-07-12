package com.technogym.mycycling.commands.factories;

import com.technogym.mycycling.commands.commands.BootloaderFirmwareVersionCommand;
import com.technogym.mycycling.commands.commands.DumpStateCommand;
import com.technogym.mycycling.commands.commands.FirmwareVersionCommand;
import com.technogym.mycycling.commands.commands.SerialNumberCommand;
import com.technogym.mycycling.commands.commands.SetRestPositionCommand;
import com.technogym.mycycling.commands.commands.StartingBootloaderModeCommand;
import com.technogym.mycycling.commands.commands.UpdatedFirmwareVersionCommand;
import com.technogym.mycycling.commands.models.ICommand;

import java.util.List;

/**
 * Factory to build commands
 */
public class CommandFactory {

    /**
     * Builder for command replies
     */
    public static ICommand create(String cmdName, List<String> parameters) {
        switch (cmdName) {
            case BootloaderFirmwareVersionCommand.CMD_NAME:
                return BootloaderFirmwareVersionCommand.create();

            case SetRestPositionCommand.CMD_NAME:
                return SetRestPositionCommand.create(parameters.get(0));

            case DumpStateCommand.CMD_NAME:
                return DumpStateCommand.create();

            case FirmwareVersionCommand.CMD_NAME:
                return FirmwareVersionCommand.create();

            case UpdatedFirmwareVersionCommand.CMD_NAME:
                return UpdatedFirmwareVersionCommand.create();

            case StartingBootloaderModeCommand.CMD_NAME:
                return StartingBootloaderModeCommand.create();

            case SerialNumberCommand.CMD_NAME:
                return SerialNumberCommand.create();

            default:
                return null;
        }
    }
}
