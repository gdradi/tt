package com.technogym.android.myrun.sdk.system.notificationRules.bleUart;

    import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
    import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
    import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
    import com.technogym.android.myrun.sdk.system.listeners.notifiers.bleUart.SetGradientNotifier;
    
    import java.util.ArrayList;
    import java.util.Iterator;
    import java.util.List;
    
    public class SetGradientNotificationRule extends NotificationRule<ISystemListener> {
    
        // { Construction
    
        protected SetGradientNotificationRule() {
            super();
        }
    
        public static SetGradientNotificationRule create() {
            return new SetGradientNotificationRule();
        }
    
        // }
    
        // { NotificationRule abstract methods implementation
    
        @Override
        public String getIdentifier() {
            return "SET_GRADIENT";
        }
    
        @Override
        public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
            //final Iterator<String[]> result = this.splitParams(notifyMessage);
            //final String[] statusData = result.next();
    
            final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
            notifiers.add(SetGradientNotifier.create(notifyMessage));
    
            return notifiers.iterator();
        }
    
        @Override
        protected String getRegex() {
            return "";
        }
    
        // }
        
    }      
    