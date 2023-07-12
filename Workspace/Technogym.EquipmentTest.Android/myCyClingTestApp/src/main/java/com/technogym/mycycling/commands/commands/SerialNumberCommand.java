package com.technogym.mycycling.commands.commands;

import com.technogym.mycycling.commands.models.ACommand;
import com.technogym.mycycling.commands.models.ICommand;

/**
 * Get Serial Number command
 */
public class SerialNumberCommand extends ACommand {

    // { Public constants fields

    public final static String CMD_NAME = "RTG_ID";

    // }

    // { Constructors

    /***
     * Base constructor
     *
     * @param name          : command's name
     * @param isInfoCommand : command's type informations related
     */
    private SerialNumberCommand(String name, boolean isInfoCommand) {
        super(name, isInfoCommand);
    }

    // }

    // { Public methods

    /***
     * Builder of the command
     * @return an instance of this command
     */
    public static ICommand create() {
        return (new SerialNumberCommand(CMD_NAME, true));
    }

    // }
}
