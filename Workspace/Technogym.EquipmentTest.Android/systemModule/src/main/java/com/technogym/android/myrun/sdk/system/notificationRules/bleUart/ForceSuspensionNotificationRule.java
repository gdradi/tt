package com.technogym.android.myrun.sdk.system.notificationRules.bleUart;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.bleUart.ForceSuspendNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ForceSuspensionNotificationRule  extends NotificationRule<ISystemListener> {

    protected ForceSuspensionNotificationRule() {
        super();
    }

    public static ForceSuspensionNotificationRule create() {
        return new ForceSuspensionNotificationRule();
    }

    @Override
    public String getIdentifier() { return "FORCESUSPEND";}

    @Override
    public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
        final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
        notifiers.add(ForceSuspendNotifier.create(notifyMessage));
        return notifiers.iterator();
    }

    @Override
    protected String getRegex() {return "";}
}
