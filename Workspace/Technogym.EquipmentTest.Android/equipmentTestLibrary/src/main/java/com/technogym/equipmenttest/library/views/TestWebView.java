package com.technogym.equipmenttest.library.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TestWebView extends WebView {

	private static final String JAVASCRIPT_EQUIPMENT_INTERFACE_NAME = "EquipmentBridge";
	private static final String JAVASCRIPT_ANDROID_INTERFACE_NAME = "AndroidBridge";

	private Object mAndroidBridge;
	private Object mEquipmentBridge;

	private OnPageLoadedListener mListener;

	// { Construction

	public TestWebView(Context context) {
		super(context);
	}

	public TestWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	// }

	// { Public methods

	/**
	 * This method initializes the {@link TestWebView} with default options needed by the application.
	 * 
	 * @param equipmentBridge
	 *            : the {@link Object} which will be set as Javascript equipment interface.
	 * @param equipmentBridge
	 *            : the {@link Object} which will be set as Javascript android interface.
	 * */
	@SuppressLint("JavascriptInterface")
	public void initializeForTest(final Object equipmentBridge, final Object androidBridge) {
		this.initializeNavigationClients();
		this.initializeWebViewSettings();

		this.clearCache(true);
		this.addJavascriptInterface(equipmentBridge, JAVASCRIPT_EQUIPMENT_INTERFACE_NAME);
		this.addJavascriptInterface(androidBridge, JAVASCRIPT_ANDROID_INTERFACE_NAME);
	}

	public void setOnPageLoadedListener(final OnPageLoadedListener listener) {
		this.mListener = listener;
	}

	// }

	// { Private methods

	/**
	 * This method initializes the clients required by {@link WebView}. {@link WebViewClient} and
	 * {@link WebChromeClient} are both needed.
	 */
	private void initializeNavigationClients() {
		this.setWebViewClient(new WebViewClient() {

			public void onPageFinished(WebView view, String url) {

				if (mListener != null) {
					mListener.onPageLoaded();
				}
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}

		});

		this.setWebChromeClient(new WebChromeClient());
	}

	/**
	 * This method initializes the {@link WebSettings} needed to achieve the wanted behaviours.
	 */
	@SuppressLint("SetJavaScriptEnabled")
	private void initializeWebViewSettings() {
		this.getSettings().setAllowContentAccess(true);
		this.getSettings().setJavaScriptEnabled(true);
		this.getSettings().setUseWideViewPort(true);
		this.getSettings().setAppCacheEnabled(false);
		this.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		this.getSettings().setLoadWithOverviewMode(true);
		this.getSettings().setBuiltInZoomControls(true);
	}

	// }

	// { Inner classes and interfaces

	public interface OnPageLoadedListener {
		void onPageLoaded();
	}

	// }

}
