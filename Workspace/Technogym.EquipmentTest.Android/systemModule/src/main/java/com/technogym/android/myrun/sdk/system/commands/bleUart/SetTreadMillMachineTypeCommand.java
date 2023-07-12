package com.technogym.android.myrun.sdk.system.commands.bleUart;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class SetTreadMillMachineTypeCommand extends SystemCommand {

    // { Construction

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing class properties.
     * */
    protected SetTreadMillMachineTypeCommand() {
        super();
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     * */
    public static SetTreadMillMachineTypeCommand create() { return new SetTreadMillMachineTypeCommand(); }

    // }

    // { SystemCommand abstract methods implementation

    @Override
    protected String getName() { return "STREADMILL"; }

    @Override
    protected Iterable<String> getParameters() {
        return null;
    }

    // }

}
