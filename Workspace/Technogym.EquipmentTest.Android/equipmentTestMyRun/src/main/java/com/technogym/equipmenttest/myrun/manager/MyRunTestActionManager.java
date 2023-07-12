package com.technogym.equipmenttest.myrun.manager;

import android.content.Context;

import com.technogym.equipmenttest.library.bridges.web.IJavascriptBridge;
import com.technogym.equipmenttest.library.managers.TestActionManager;
import com.technogym.equipmenttest.library.models.Action;
import com.technogym.equipmenttest.myrun.models.Action_002;
import com.technogym.equipmenttest.myrun.models.Action_003;
import com.technogym.equipmenttest.myrun.models.Action_007;
import com.technogym.equipmenttest.myrun.models.Action_009;
import com.technogym.equipmenttest.myrun.models.Action_1001;
import com.technogym.equipmenttest.myrun.models.Action_1005;
import com.technogym.equipmenttest.myrun.models.Action_1010;
import com.technogym.equipmenttest.myrun.models.Action_1011;
import com.technogym.equipmenttest.myrun.models.Action_1018;
import com.technogym.equipmenttest.myrun.models.Action_1019;
import com.technogym.equipmenttest.myrun.models.Action_1022;
import com.technogym.equipmenttest.myrun.models.Action_139;
import com.technogym.equipmenttest.myrun.models.Action_157;
import com.technogym.equipmenttest.myrun.models.Action_158;
import com.technogym.equipmenttest.myrun.models.Action_195;
import com.technogym.equipmenttest.myrun.models.Action_245;
import com.technogym.equipmenttest.myrun.models.Action_279;
import com.technogym.equipmenttest.myrun.models.Action_328;
import com.technogym.equipmenttest.myrun.models.Action_331;
import com.technogym.equipmenttest.myrun.models.Action_336;
import com.technogym.equipmenttest.myrun.models.Action_337;
import com.technogym.equipmenttest.myrun.models.Action_1021;

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
public class MyRunTestActionManager extends TestActionManager {

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
	protected MyRunTestActionManager(final Context context, final IJavascriptBridge javascriptBridge) {
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
	 * @return an instance of {@link MyRunTestActionManager} if everything goes fine, {@code null} otherwise.
	 * */
	public static MyRunTestActionManager create(final Context context, final IJavascriptBridge javascriptBridge) {
		return new MyRunTestActionManager(context, javascriptBridge);
	}

	// }

	// { ITestActionManager abstract methods implementation

	@Override
	protected Action initializeAction(final int id) {
		switch (id) {
			case 2:
				return Action_002.create(this.mContext);
			case 3:
				return Action_003.create(this.mContext);
			case 7:
				return Action_007.create(this.mContext);
			case 9:
				return Action_009.create(this.mContext);
			case 139:
				return Action_139.create(this.mContext);
			case 157:
				return Action_157.create(this.mContext);
			case 158:
				return Action_158.create(this.mContext);
			case 195:
				return Action_195.create(this.mContext);
			case 245:
				return Action_245.create(this.mContext);
			case 279:
				return Action_279.create(this.mContext);
			case 328:
				return Action_328.create(this.mContext);
			case 331:
				return Action_331.create(this.mContext);
			case 336:
				return Action_336.create(this.mContext);
			case 337:
				return Action_337.create(this.mContext);
			case 1001:
				return Action_1001.create(this.mContext);
			case 1005:
				return Action_1005.create(this.mContext);
			case 1018:
				return Action_1018.create(this.mContext);
			case 1019:
				return Action_1019.create(this.mContext);
			case 1010:
				return Action_1010.create(this.mContext);
			case 1011:
				return Action_1011.create(this.mContext);
			case 1021:
				return Action_1021.create(this.mContext);
			case 1022:
				return Action_1022.create(this.mContext);
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
