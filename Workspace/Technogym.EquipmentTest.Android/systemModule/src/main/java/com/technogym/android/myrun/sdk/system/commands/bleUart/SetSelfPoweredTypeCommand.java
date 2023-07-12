package com.technogym.android.myrun.sdk.system.commands.bleUart;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class SetSelfPoweredTypeCommand extends SystemCommand {

    // { Construction

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing class properties.
     * */
    protected SetSelfPoweredTypeCommand() {
        super();
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     * */
    public static SetSelfPoweredTypeCommand create() { return new SetSelfPoweredTypeCommand(); }

    // }

    // { SystemCommand abstract methods implementation

    @Override
    protected String getName() { return "SSELFPOWERED"; }

    @Override
    protected Iterable<String> getParameters() {
        return null;
    }

    // }

}
