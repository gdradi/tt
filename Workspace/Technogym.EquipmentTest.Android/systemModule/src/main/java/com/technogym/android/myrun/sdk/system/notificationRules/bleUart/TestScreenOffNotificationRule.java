package com.technogym.android.myrun.sdk.system.notificationRules.bleUart;

    import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
    import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
    import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
    import com.technogym.android.myrun.sdk.system.listeners.notifiers.bleUart.TestScreenOffNotifier;
    
    import java.util.ArrayList;
    import java.util.Iterator;
    import java.util.List;
    
    public class TestScreenOffNotificationRule extends NotificationRule<ISystemListener> {
    
        // { Construction
    
        protected TestScreenOffNotificationRule() {
            super();
        }
    
        public static TestScreenOffNotificationRule create() {
            return new TestScreenOffNotificationRule();
        }
    
        // }
    
        // { NotificationRule abstract methods implementation
    
        @Override
        public String getIdentifier() {
            return "TESTSCROFF";
        }
    
        @Override
        public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
            //final Iterator<String[]> result = this.splitParams(notifyMessage);
            //final String[] statusData = result.next();
    
            final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
            notifiers.add(TestScreenOffNotifier.create(notifyMessage));
    
            return notifiers.iterator();
        }
    
        @Override
        protected String getRegex() {
            return "";
        }
    
        // }
        
    }      
    