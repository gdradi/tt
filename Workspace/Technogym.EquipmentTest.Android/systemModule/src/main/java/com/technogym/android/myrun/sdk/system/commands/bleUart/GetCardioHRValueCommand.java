package com.technogym.android.myrun.sdk.system.commands.bleUart;;
    
import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class GetCardioHRValueCommand extends SystemCommand {

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing class properties.
     * */
    protected GetCardioHRValueCommand() {
        super();
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     * */
    public static GetCardioHRValueCommand create() {
        return new GetCardioHRValueCommand();
    }

    // }

    // { SystemCommand abstract methods implementation

    @Override
    protected String getName() {
        return "GETCARDIOHRVALUE";
    }

    @Override
    protected Iterable<String> getParameters() {
        return null;
    }

    // }

}