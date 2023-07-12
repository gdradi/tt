package com.technogym.android.myrun.sdk.system.commands;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

/**
 * It's the final implementation of a system command which asks for the firmware's bootloader version of the equipment.
 *
 * @author Federico Foschini
 * @version 1.0
 * */
public class BootloaderVersionCommand extends SystemCommand {

    // { Construction

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor.
     * */
    protected BootloaderVersionCommand() {
        super();
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     * */
    public static BootloaderVersionCommand create() {
        return new BootloaderVersionCommand();
    }

    // }

    // { SystemCommand abstract methods implementation

    @Override
    protected String getName() {
        return "BLVER";
    }

    @Override
    protected Iterable<String> getParameters() {
        return null;
    }

    // }

}
