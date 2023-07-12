package com.technogym.android.myrun.sdk.system.commands.bleUart;

import com.technogym.android.myrun.sdk.communication.commands.SystemCommand;

public class HighKitVersionCommand extends SystemCommand{

        // { Construction

        /**
         * The class constructor.<br/>
         * It does nothing more than calling {@code super} constructor and initializing class properties.
         * */
        protected HighKitVersionCommand() {
            super();
        }

        /**
         * It's a static factory method which returns a new instance of the class.
         * */
        public static HighKitVersionCommand create() {
            return new HighKitVersionCommand();
        }

        // }

        // { SystemCommand abstract methods implementation

        @Override
        protected String getName() {
            return "READHIGHKITVERSION";
        }

        @Override
        protected Iterable<String> getParameters() {
            return null;
        }

        // }

}
