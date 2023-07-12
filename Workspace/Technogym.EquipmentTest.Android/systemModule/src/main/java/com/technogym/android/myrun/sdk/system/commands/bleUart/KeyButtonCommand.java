package com.technogym.android.myrun.sdk.system.commands.bleUart;;
    
import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class KeyButtonCommand extends SystemCommand {

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing class properties.
     * */
    protected KeyButtonCommand() {
        super();
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     * */
    public static KeyButtonCommand create() {
        return new KeyButtonCommand();
    }

    // }

    // { SystemCommand abstract methods implementation

    @Override
    protected String getName() {
        return "KEYBUTTON";
    }

    @Override
    protected Iterable<String> getParameters() {
        return null;
    }

    // }

}