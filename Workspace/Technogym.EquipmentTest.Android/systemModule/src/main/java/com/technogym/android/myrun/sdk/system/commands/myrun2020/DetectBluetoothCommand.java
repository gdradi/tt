package com.technogym.android.myrun.sdk.system.commands.myrun2020;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class DetectBluetoothCommand extends SystemCommand {
    // { Construction

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing class properties.
     * */
    protected DetectBluetoothCommand() {
        super();
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     * */
    public static DetectBluetoothCommand create() {
        return new DetectBluetoothCommand();
    }

    // }

    // { SystemCommand abstract methods implementation

    @Override
    protected String getName() {
        return "TESTGYMKITBLE";
    }

    @Override
    protected Iterable<String> getParameters() {
        return null;
    }

    // }

}
