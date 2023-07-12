package com.technogym.mycycling.commands.commands;

import com.technogym.mycycling.commands.models.ACommand;
import com.technogym.mycycling.commands.models.ICommand;

/**
 * SCALRESTPOS command
 */
public class SetRestPositionCommand extends ACommand {

    // { Public constants fields

    public final static String CMD_NAME = "SCALRESTPOS";

    // }

    // { Constructors

    /***
     * Base constructor
     *
     * @param name          : command's name
     * @param parameter     : command's parameter
     * @param isInfoCommand : command's type informations related
     */
    private SetRestPositionCommand(String name, String parameter, boolean isInfoCommand) {
        super(name, isInfoCommand);
        mParameters.add(parameter);
    }

    // }

    // { Public methods

    /***
     * Builder of the command
     * @param parameter : command's parameter
     * @return an instance of this command
     */
    public static ICommand create(String parameter) {
        return (new SetRestPositionCommand(CMD_NAME, parameter, false));
    }

    // }
}
