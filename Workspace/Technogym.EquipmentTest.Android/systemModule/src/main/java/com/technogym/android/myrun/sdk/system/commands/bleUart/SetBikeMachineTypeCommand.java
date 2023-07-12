package com.technogym.android.myrun.sdk.system.commands.bleUart;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class SetBikeMachineTypeCommand extends SystemCommand {

    // { Construction

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing class properties.
     * */
    protected SetBikeMachineTypeCommand() {
        super();
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     * */
    public static SetBikeMachineTypeCommand create() { return new SetBikeMachineTypeCommand(); }

    // }

    // { SystemCommand abstract methods implementation

    @Override
    protected String getName() { return "SBIKE"; }

    @Override
    protected Iterable<String> getParameters() {
        return null;
    }

    // }

}