package com.technogym.android.myrun.sdk.system.notificationRules.myrun2020;

import com.technogym.android.myrun.sdk.communication.notificationRules.NotificationRule;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.android.myrun.sdk.system.listeners.notifiers.myrun2020.DetectBluetoothNotifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DetectBluetoothNotificationRule extends NotificationRule<ISystemListener> {

    protected DetectBluetoothNotificationRule() {
        super();
    }

    public static DetectBluetoothNotificationRule create() {
        return new DetectBluetoothNotificationRule();
    }

    @Override
    public String getIdentifier() { return "TESTGYMKITBLE";}

    @Override
    public Iterator<INotifier<ISystemListener>> getNotifiers(final String notifyMessage) {
        final List<INotifier<ISystemListener>> notifiers = new ArrayList<INotifier<ISystemListener>>();
        notifiers.add(DetectBluetoothNotifier.create(notifyMessage));

        return notifiers.iterator();
    }

    @Override
    protected String getRegex() {return "";}
}