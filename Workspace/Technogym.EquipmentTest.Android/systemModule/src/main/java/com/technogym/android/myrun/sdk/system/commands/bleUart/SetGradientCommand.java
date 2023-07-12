package com.technogym.android.myrun.sdk.system.commands.bleUart;;
    
import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

import java.util.ArrayList;

public class SetGradientCommand extends SystemCommand {

    final String percentage;

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing class properties.
     * */
    protected SetGradientCommand(String percentage) {
        super();
        this.percentage = percentage;
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     * */
    public static SetGradientCommand create(String percentage) {
        return new SetGradientCommand(percentage);
    }

    // }

    // { SystemCommand abstract methods implementation

    @Override
    protected String getName() {
        return "SET_GRADIENT";
    }

    @Override
    protected Iterable<String> getParameters() {
        final ArrayList<String> params = new ArrayList<String>();
        params.add(this.percentage);
        return params;
    }

    // }

}