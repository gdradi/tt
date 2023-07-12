package com.technogym.android.myrun.sdk.system.commands.bleUart;;
    
import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class GetCardioricevitoreFwCommand extends SystemCommand {

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing class properties.
     * */
    protected GetCardioricevitoreFwCommand() {
        super();
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     * */
    public static GetCardioricevitoreFwCommand create() {
        return new GetCardioricevitoreFwCommand();
    }

    // }

    // { SystemCommand abstract methods implementation

    @Override
    protected String getName() {
        return "CARDIO_RECEIVER_FWVER";
    }

    @Override
    protected Iterable<String> getParameters() {
        return null;
    }

    // }

}