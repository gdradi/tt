package com.technogym.equipmenttest.appbleuart.manager;

import android.content.Context;

import com.technogym.equipmenttest.appbleuart.models.Action_579;
import com.technogym.equipmenttest.appbleuart.models.Action_580;
import com.technogym.equipmenttest.appbleuart.models.Action_587;
import com.technogym.equipmenttest.appbleuart.models.Action_588;
import com.technogym.equipmenttest.appbleuart.models.Action_589;
import com.technogym.equipmenttest.appbleuart.models.Action_591;
import com.technogym.equipmenttest.appbleuart.models.Action_593;
import com.technogym.equipmenttest.appbleuart.models.Action_594;
import com.technogym.equipmenttest.appbleuart.models.Action_599;
import com.technogym.equipmenttest.appbleuart.models.Action_614;
import com.technogym.equipmenttest.appbleuart.models.Action_615;
import com.technogym.equipmenttest.library.bridges.web.IJavascriptBridge;
import com.technogym.equipmenttest.library.managers.TestActionManager;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.appbleuart.models.Action_1002;
import com.technogym.equipmenttest.appbleuart.models.Action_1003;
import com.technogym.equipmenttest.appbleuart.models.Action_1004;
import com.technogym.equipmenttest.appbleuart.models.Action_1005;
import com.technogym.equipmenttest.appbleuart.models.Action_1007;
import com.technogym.equipmenttest.appbleuart.models.Action_1010;
import com.technogym.equipmenttest.appbleuart.models.Action_1011;
import com.technogym.equipmenttest.appbleuart.models.Action_1015;
import com.technogym.equipmenttest.appbleuart.models.Action_1016;
import com.technogym.equipmenttest.appbleuart.models.Action_1018;
import com.technogym.equipmenttest.appbleuart.models.Action_1019;
import com.technogym.equipmenttest.appbleuart.models.Action_1020;
import com.technogym.equipmenttest.appbleuart.models.Action_1022;
import com.technogym.equipmenttest.appbleuart.models.Action_1023;
import com.technogym.equipmenttest.appbleuart.models.Action_1024;

import it.spot.android.logger.domain.Logger;

/**
 * This class initializes the "device-up" sensors:
 * <ul>
 * <li>sends the proper command</li>
 * <li>checks the response correctness</li>
 * </ul>
 * It's an automatic action.
 * 
 * @author Andrea Rinaldi
 * @version 1.0
 * */
public class BleUartActionManager extends TestActionManager {

	// { Construction

	/**
	 * The class constructor: it calls the constructor of the parent base class {@link TestActionManager} and doesn't do
	 * anything specific.
	 * 
	 * @param context
	 *            : the execution context of the application
	 * @param javascriptBridge
	 *            : a bridge class needed to communicate with webView content
	 * */
	protected BleUartActionManager(final Context context, final IJavascriptBridge javascriptBridge) {
		super(context, javascriptBridge);
	}

	/**
	 * This is a static method which initializes an instance of the given class. It replaces - in a very trivial way - a
	 * factory class and can implement specific behavior to check the correctness of input parameters.
	 * 
	 * @param context
	 *            : the execution context of the application
	 * @param javascriptBridge
	 *            : a bridge class needed to communicate with webView content
	 * 
	 * @return an instance of {@link BleUartActionManager} if everything goes fine, {@code null} otherwise.
	 * */
	public static BleUartActionManager create(final Context context, final IJavascriptBridge javascriptBridge) {
		return new BleUartActionManager(context, javascriptBridge);
	}

	// }

	// { ITestActionManager abstract methods implementation

	@Override
	protected Action initializeAction(final int id) {
		switch (id) {
			case 1002:
				return Action_1002.create(this.mContext);
			case 1003:
				return Action_1003.create(this.mContext);
			case 1004:
				return Action_1004.create(this.mContext);
			case 1005:
				return Action_1005.create(this.mContext);
			case 1007:
				return Action_1007.create(this.mContext);
			case 1010:
				return Action_1010.create(this.mContext);
			case 1011:
				return Action_1011.create(this.mContext);
			case 1015:
				return Action_1015.create(this.mContext);
			case 1016:
				return Action_1016.create(this.mContext);
			case 1018:
				return Action_1018.create(this.mContext);
			case 1019:
				return Action_1019.create(this.mContext);
			case 1020:
				return Action_1020.create(this.mContext);
			case 1022:
				return Action_1022.create(this.mContext);
			case 1023:
				return Action_1023.create(this.mContext);
			case 1024:
				return Action_1024.create(this.mContext);

				// Live essential
			case 579:
				return Action_579.create(this.mContext);
			case 587:
				return Action_587.create(this.mContext);
			case 580:
				return Action_580.create(this.mContext);
			case 588:
				return Action_588.create(this.mContext);
			case 589:
				return Action_589.create(this.mContext);
			case 591:
				return Action_591.create(this.mContext);
			case 593:
				return Action_593.create(this.mContext);
			case 594:
				return Action_594.create(this.mContext);
			case 599:
				return Action_599.create(this.mContext);
			case 614:
				return Action_614.create(this.mContext);
			case 615:
				return Action_615.create(this.mContext);
		}

		return null;
	}

	@Override
	public void execute(final int id, final String data) {
		Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Executing action " + id);
		super.execute(id, data);
	}

	// }

}
