package com.technogym.android.myrun.sdk.system.commands.bleUart;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class GetMachineTypeCommand extends SystemCommand {

    // { Construction

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing class properties.
     * */
    protected GetMachineTypeCommand() {
        super();
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     * */
    public static GetMachineTypeCommand create() { return new GetMachineTypeCommand(); }

    // }

    // { SystemCommand abstract methods implementation

    @Override
    protected String getName() { return "RMACHINETYPE"; }

    @Override
    protected Iterable<String> getParameters() {
        return null;
    }

    // }

}