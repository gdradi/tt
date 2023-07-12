package com.technogym.android.myrun.sdk.system.commands.bleUart;;
    
import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

import java.util.ArrayList;

public class TextXScreenCommand extends SystemCommand {

    private final String color;

    /**
     * The class constructor.<br/>
     * It does nothing more than calling {@code super} constructor and initializing class properties.
     *
     * @param color*/
    protected TextXScreenCommand(String color) {
        super();
        this.color = color;
    }

    /**
     * It's a static factory method which returns a new instance of the class.
     * */
    public static TextXScreenCommand create(final String color) { return new TextXScreenCommand(color); }

    // }

    // { SystemCommand abstract methods implementation

    @Override
    protected String getName() {
        return "TESTXSCR";
    }

    @Override
    protected Iterable<String> getParameters() {
        final ArrayList<String> params = new ArrayList<String>();
        params.add(this.color);
        return params;
    }

    // }

}