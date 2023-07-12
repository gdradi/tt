package com.technogym.myrow.bridges.android;

import android.app.Activity;
import android.webkit.JavascriptInterface;

public abstract class Bridge implements IBridge {

	protected final Activity mActivity;

	// { Construction

	protected Bridge(final Activity activity) {
		super();

		this.mActivity = activity;
	}

	// }

	// { IBridge implementation

	@Override
	@JavascriptInterface
	public boolean exists() {
		return true;
	}

	// }

}
