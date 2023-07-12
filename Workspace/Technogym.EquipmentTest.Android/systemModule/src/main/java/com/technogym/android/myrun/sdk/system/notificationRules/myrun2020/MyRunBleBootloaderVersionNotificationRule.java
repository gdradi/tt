package com.technogym.android.myrun.sdk.system.notificationRules.myrun2020;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.BootloaderVersionNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyRunBleBootloaderVersionNotificationRule extends NotificationRule<ISystemListener> {

    // { Construction

    protected MyRunBleBootloaderVersionNotificationRule() {
        super();
    }

    public static MyRunBleBootloaderVersionNotificationRule create() {
        return new MyRunBleBootloaderVersionNotificationRule();
    }

    // }

    // { NotificationRule abstract methods implementation

    @Override
    public String getIdentifier() {
        return "BLVER";
    }

    @Override
    public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
        final Iterator<String[]> result = this.splitParams(notifyMessage);

        final String[] statusData = result.next();

        final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
        notifiers.add(BootloaderVersionNotifier.create(statusData[0]));

        return notifiers.iterator();
    }

    @Override
    protected String getRegex() {
        // WFW0699_AA01.01, WFW0699_AB01.01
        return "WFW0699_([A-Z][A-Z][0-9]+\\.[0-9]+)";
    }

    // }

}
