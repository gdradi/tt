package com.technogym.android.myrun.sdk.system.notificationRules.bleUart;

    import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
    import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
    import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
    import com.technogym.android.myrun.sdk.system.listeners.notifiers.bleUart.ReadWifiConfigNotifier;
    
    import java.util.ArrayList;
    import java.util.Iterator;
    import java.util.List;
    
    public class ReadWifiConfigNotificationRule extends NotificationRule<ISystemListener> {
    
        // { Construction
    
        protected ReadWifiConfigNotificationRule() {
            super();
        }
    
        public static ReadWifiConfigNotificationRule create() {
            return new ReadWifiConfigNotificationRule();
        }
    
        // }
    
        // { NotificationRule abstract methods implementation
    
        @Override
        public String getIdentifier() {
            return "RWIFICONFIG";
        }
    
        @Override
        public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
            //final Iterator<String[]> result = this.splitParams(notifyMessage);
            //final String[] statusData = result.next();
    
            final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
            notifiers.add(ReadWifiConfigNotifier.create(notifyMessage));
    
            return notifiers.iterator();
        }
    
        @Override
        protected String getRegex() {
            return "";
        }
    
        // }
        
    }      
    