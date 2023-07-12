package com.technogym.android.myrun.sdk.communication.notificationRules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.technogym.android.myrun.sdk.communication.listeners.IListener;
import com.technogym.android.myrun.sdk.communication.notifiers.INotifier;

/**
 * This is the class which basic implements the interface {@link INotificationRule}. It's abstract because leaves a few
 * things unimplemented so that every single subclass can specify its own behavior.<br/>
 * <br/>
 * It provides a protected method for parsing the message and splitting the parameters.
 * 
 * @author Andrea Rinaldi
 * @author Lorenzo Brasini
 * @version 1.0
 * */
public abstract class NotificationRule<TListener extends IListener> implements INotificationRule<TListener> {

	private final Pattern mPattern;

	// { Construction

	protected NotificationRule() {
		super();
		this.mPattern = Pattern.compile(this.getRegex());
	}

	// }

	@Override
	public abstract String getIdentifier();

	@Override
	public abstract Iterator<INotifier<TListener>> getNotifiers(final String notifyMessage);

	/**
	 * This method returns the patter obtained by the processing of the regular expression associated to the current
	 * {@link INotificationRule}.
	 * 
	 * @return the {@link Pattern}
	 * */
	protected Pattern getRegexPattern() {
		return mPattern;
	}

	/**
	 * This method gets the regular expression related to the current {@link INotificationRule}. It's left abstract
	 * because every type of rule has it's own expression for message recognitions.
	 * 
	 * @return the regular expression
	 * */
	protected abstract String getRegex();

	/**
	 * This method splits the parameters wrapped into the message, only if the message itself matches the rule
	 * {@link Pattern}.
	 * 
	 * @param notifyMessage
	 *            : the message to parse and split
	 * 
	 * @return an iterable list of strings arrays, where each list contains a group of parameters matched
	 * */
	protected Iterator<String[]> splitParams(final String notifyMessage) {
		final Pattern p = this.getRegexPattern();
		final Matcher m = p.matcher(notifyMessage);

		final ArrayList<String[]> list = new ArrayList<String[]>();

		while (m.find()) {
			final String[] params = new String[m.groupCount()];
			for (int i = 0; i < m.groupCount(); i++) {
				params[i] = m.group(i + 1);
			}
			list.add(params);
		}

		return list.iterator();
	}

}
