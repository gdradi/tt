package com.technogym.android.myrun.sdk.system.commands.bleUart;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

import java.util.ArrayList;

public class ResistanceLevelCommand extends SystemCommand {

    private final String resistanceLevel;

    protected ResistanceLevelCommand(final String resistanceLevel) {
        super();
        this.resistanceLevel = resistanceLevel;
    }

    public static ResistanceLevelCommand create(final String resistanceLevel) {
        return new ResistanceLevelCommand(resistanceLevel);
    }


    @Override
    protected String getName() {
        return "SET_RESISTANCE_LEVEL";
    }

    @Override
    protected Iterable<String> getParameters() {
        final ArrayList<String> params = new ArrayList<String>();
        params.add(this.resistanceLevel);
        return params;
    }

}
