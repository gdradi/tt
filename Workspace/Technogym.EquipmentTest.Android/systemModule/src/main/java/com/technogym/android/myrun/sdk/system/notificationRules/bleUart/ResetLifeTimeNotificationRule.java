package com.technogym.android.myrun.sdk.system.notificationRules.bleUart;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.bleUart.ResetLifeTimeNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResetLifeTimeNotificationRule extends NotificationRule<ISystemListener> {

    protected ResetLifeTimeNotificationRule() {
        super();
    }

    public static ResetLifeTimeNotificationRule create() {
        return new ResetLifeTimeNotificationRule();
    }

    @Override
    public String getIdentifier() { return "RESETSTAT";}

    @Override
    public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
        final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
        notifiers.add(ResetLifeTimeNotifier.create(notifyMessage));
        return notifiers.iterator();
    }

    @Override
    protected String getRegex() {return "";}
}