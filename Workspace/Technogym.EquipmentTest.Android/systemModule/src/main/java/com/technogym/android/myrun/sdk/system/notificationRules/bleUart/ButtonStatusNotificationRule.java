package com.technogym.android.myrun.sdk.system.notificationRules.bleUart;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.bleUart.ButtonStatusNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ButtonStatusNotificationRule extends NotificationRule<ISystemListener> {

    protected ButtonStatusNotificationRule() {
        super();
    }

    public static ButtonStatusNotificationRule create() {
        return new ButtonStatusNotificationRule();
    }

    @Override
    public String getIdentifier() { return "BUTTONSTATUS";}

    @Override
    public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
        final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
        notifiers.add(ButtonStatusNotifier.create(notifyMessage));
        return notifiers.iterator();
    }

    @Override
    protected String getRegex() {return "";}
}
