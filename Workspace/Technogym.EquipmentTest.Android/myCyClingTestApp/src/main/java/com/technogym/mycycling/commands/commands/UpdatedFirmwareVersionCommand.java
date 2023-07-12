package com.technogym.mycycling.commands.commands;

import com.technogym.mycycling.commands.models.ACommand;
import com.technogym.mycycling.commands.models.ICommand;

/**
 * FWVER command for updated firmware version
 */
public class UpdatedFirmwareVersionCommand extends ACommand {

    // { Public constants fields

    public final static String CMD_NAME = "UPDATED_FWVER";

    // }

    // { Internal constants fields

    private final static String EQUIPMENT_CMD = "FWVER";

    // }

    // { Constructors

    /***
     * Base constructor
     *
     * @param name          : command's name
     * @param isInfoCommand : command's type informations related
     */
    private UpdatedFirmwareVersionCommand(String name, boolean isInfoCommand) {
        super(name, isInfoCommand);
    }

    // }

    // { Public methods

    /***
     * Builder of the command
     * @return an instance of this command
     */
    public static ICommand create() {
        return (new UpdatedFirmwareVersionCommand(EQUIPMENT_CMD, false));
    }

    // }
}
