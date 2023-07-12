package com.technogym.android.myrun.sdk.system.notificationRules.myrun2020;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.myrun2020.DisableGymkitNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DisableGymkitNotificationRule extends NotificationRule<ISystemListener> {

    protected DisableGymkitNotificationRule() {
        super();
    }

    public static DisableGymkitNotificationRule create() {
        return new DisableGymkitNotificationRule();
    }

    @Override
    public String getIdentifier() { return "DISABLEGYMKIT";}

    @Override
    public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
        final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
        notifiers.add(DisableGymkitNotifier.create(notifyMessage));
        return notifiers.iterator();
    }

    @Override
    protected String getRegex() {return "";}
}