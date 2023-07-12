package com.technogym.android.myrun.sdk.system.notificationRules.bleUart;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.bleUart.LowKitVersionNotifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LowKitVersionNotificationRule extends NotificationRule<ISystemListener> {

    // { Construction

    protected LowKitVersionNotificationRule() {
        super();
    }

    public static LowKitVersionNotificationRule create() {
        return new LowKitVersionNotificationRule();
    }

    // }

    // { NotificationRule abstract methods implementation

    @Override
    public String getIdentifier() {
        return "READLOWKITVERSION";
    }

    @Override
    public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
        //final Iterator<String[]> result = this.splitParams(notifyMessage);
        //final String[] statusData = result.next();

        final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
        notifiers.add(LowKitVersionNotifier.create(notifyMessage));

        return notifiers.iterator();
    }

    @Override
    protected String getRegex() {return "";}

    // }

}
