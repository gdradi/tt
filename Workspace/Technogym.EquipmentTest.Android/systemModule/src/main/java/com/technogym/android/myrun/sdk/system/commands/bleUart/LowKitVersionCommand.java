package com.technogym.android.myrun.sdk.system.commands.bleUart;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class LowKitVersionCommand  extends SystemCommand{

        // { Construction

        /**
         * The class constructor.<br/>
         * It does nothing more than calling {@code super} constructor and initializing class properties.
         * */
        protected LowKitVersionCommand() {
            super();
        }

        /**
         * It's a static factory method which returns a new instance of the class.
         * */
        public static LowKitVersionCommand create() {
            return new LowKitVersionCommand();
        }

        // }

        // { SystemCommand abstract methods implementation

        @Override
        protected String getName() {
            return "READLOWKITVERSION";
        }

        @Override
        protected Iterable<String> getParameters() {
            return null;
        }

        // }

}
