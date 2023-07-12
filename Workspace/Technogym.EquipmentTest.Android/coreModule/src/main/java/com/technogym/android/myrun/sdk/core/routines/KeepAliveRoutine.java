package com.technogym.android.myrun.sdk.core.routines;

import com.technogym.android.myrun.sdk.communication.enums.MyRunStatusType;
import com.technogym.android.myrun.sdk.communication.routines.IRoutine;
import com.technogym.android.myrun.sdk.communication.routines.Routine;
import com.technogym.android.myrun.sdk.core.proxies.ICoreProxy;
import com.technogym.android.myrun.sdk.status.listeners.IStatusListener;
import com.technogym.android.myrun.sdk.status.proxies.IStatusProxy;

public class KeepAliveRoutine extends Routine implements IRoutine, IStatusListener {

	//private static final int KEEP_ALIVE_TIMER = 5000;
	private static final int KEEP_ALIVE_TIMER = 10000000;

	//private final ICoreProxy mCoreProxy;
	private final IStatusProxy mStatusProxy;
	//private final IKeepAliveExpiredListener mListener;

	// { Construction

	protected KeepAliveRoutine(final ICoreProxy coreProxy, final IStatusProxy statusProxy,
			final IKeepAliveExpiredListener listener) {
		super(KEEP_ALIVE_TIMER, false, 0);

		//this.mCoreProxy = coreProxy;
		this.mStatusProxy = statusProxy;
		//this.mListener = listener;
	}

	public static KeepAliveRoutine create(final ICoreProxy coreProxy, final IStatusProxy statusProxy,
			final IKeepAliveExpiredListener listener) {
		return new KeepAliveRoutine(coreProxy, statusProxy, listener);
	}

	// }

	// { Period abstract methods

	@Override
	public void initRoutine() {
		this.mStatusProxy.registerForNotification(this);
	}

	@Override
	protected void execute() {
		//this.mListener.onKeepAliveExpired();
	}

	// }

	// { IStatusListener implementation

	@Override
	public void onEquipmentStatusChanged(MyRunStatusType status) {
//		this.startRoutine();
//		try {
//			this.mCoreProxy.sendKeepAlive();
//		} catch (WriteNotAllowedException ex) {
//			ex.printStackTrace();
//		}
	}

	// }

	// { Private classes and interfaces

	public interface IKeepAliveExpiredListener {
		public void onKeepAliveExpired();
	}

	// }

}
