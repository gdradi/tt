package com.technogym.android.myrun.sdk.system.listeners.notifiers.bleUart;

    import com.technogym.android.myrun.sdk.communication.notifiers.Notifier;
    import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
    
    public class SetGradientNotifier extends Notifier<ISystemListener> {
        private final String mStatus;
    
        // { Construction
    
        /**
         * The class constructor.<br/>
         * It does nothing more than calling {@code super} constructor and initializing the class properties.
         *
         * @param status
         *            : the down sensor status retreived from the equipment
         * */
        protected SetGradientNotifier(final String status) {
            super();
    
            this.mStatus = status;
        }
    
        /**
         * It's a static factory method which returns a new instance of the class.
         *
         * @param status
         *            : the parameter required by class constructor
         * */
        public static SetGradientNotifier create(final String status) {
            return new SetGradientNotifier(status);
        }
    
        // }
    
        // { INotifier implementation
    
        @Override
        public void notify(final ISystemListener listener) {
            listener.onSetGradientResponse(this.mStatus);
        }
    
        // }
    }    
    