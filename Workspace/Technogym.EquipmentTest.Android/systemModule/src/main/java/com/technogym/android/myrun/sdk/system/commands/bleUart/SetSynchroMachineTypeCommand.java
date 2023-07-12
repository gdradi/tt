package com.technogym.android.myrun.sdk.system.commands.bleUart;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class SetSynchroMachineTypeCommand extends SystemCommand {

    // { Construction

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing class properties.
     * */
    protected SetSynchroMachineTypeCommand() {
        super();
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     * */
    public static SetSynchroMachineTypeCommand create() { return new SetSynchroMachineTypeCommand(); }

    // }

    // { SystemCommand abstract methods implementation

    @Override
    protected String getName() { return "SSYNCHRO"; }

    @Override
    protected Iterable<String> getParameters() {
        return null;
    }

    // }

}