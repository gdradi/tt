package com.technogym.android.myrun.sdk.system.commands.bleUart;;
    
import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

import java.util.ArrayList;

public class SetSpeedCommand extends SystemCommand {

    private final String value;

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing class properties.
     * */
    protected SetSpeedCommand(String value) {
        super();
        this.value = value;
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     * */
    public static SetSpeedCommand create(String value) {
        return new SetSpeedCommand(value);
    }

    // }

    // { SystemCommand abstract methods implementation

    @Override
    protected String getName() {
        return "SET_SPEED";
    }

    @Override
    protected Iterable<String> getParameters() {
        final ArrayList<String> params = new ArrayList<String>();
        params.add(this.value);
        return params;
    }

    // }

}