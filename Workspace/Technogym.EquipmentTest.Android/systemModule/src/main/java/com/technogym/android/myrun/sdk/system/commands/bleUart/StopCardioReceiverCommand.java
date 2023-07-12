package com.technogym.android.myrun.sdk.system.commands.bleUart;;
    
import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class StopCardioReceiverCommand extends SystemCommand {

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing class properties.
     * */
    protected StopCardioReceiverCommand() {
        super();
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     * */
    public static StopCardioReceiverCommand create() {
        return new StopCardioReceiverCommand();
    }

    // }

    // { SystemCommand abstract methods implementation

    @Override
    protected String getName() {
        return "STOPCARDIORECEIVER";
    }

    @Override
    protected Iterable<String> getParameters() {
        return null;
    }

    // }

}