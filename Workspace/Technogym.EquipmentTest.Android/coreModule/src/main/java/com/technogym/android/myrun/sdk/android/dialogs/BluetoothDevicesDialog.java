package com.technogym.android.myrun.sdk.android.dialogs;

import java.util.ArrayList;
import java.util.List;

import com.technogym.android.myrun.sdk.android.adapters.DevicesArrayAdapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

public abstract class BluetoothDevicesDialog extends DialogFragment {

	private IBluetoothDevicesDialogListener mListener;

	private final List<BluetoothDevice> mDevices;
	private ArrayAdapter<BluetoothDevice> mAdapter;

	private Button mCancelButton;
	private ProgressBar mProgressBar;

	// Construction

	public BluetoothDevicesDialog() {
		super();

		this.mAdapter = null;
		this.mListener = null;
		this.mCancelButton = null;
		this.mProgressBar = null;
		this.mDevices = new ArrayList<BluetoothDevice>();
	}

	// }

	// { DialogFragment methods overriding

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);

		try {
			this.mListener = (IBluetoothDevicesDialogListener) activity;
		} catch (ClassCastException ex) {
			throw new ClassCastException(activity.toString()
					+ " must implement BluetoothDevicesDialog.IBluetoothDevicesDialogListener.");
		}
	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Dialog_NoActionBar);
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		final View view = inflater.inflate(this.getDialogLayoutId(), container, false);

		this.mCancelButton = (Button) view.findViewById(this.getDialogCancelButtonId());
		this.mProgressBar = (ProgressBar) view.findViewById(this.getDialogProgressBarId());

		return view;
	}

	@Override
	public void onViewCreated(final View view, final Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		this.getDialog().setCancelable(false);
		this.getDialog().setCanceledOnTouchOutside(false);

		final ListView listView = (ListView) view.findViewById(this.getDialogListId());
		this.mAdapter = new DevicesArrayAdapter(this.getActivity(), android.R.id.text1, this.mDevices);
		listView.setAdapter(this.mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
				final BluetoothDevice device = (BluetoothDevice) adapter.getItemAtPosition(position);
				mListener.onDevicePicked(device);
			}
		});

		this.mCancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mListener.onCancelButtonPressed();
			}
		});
	}

	// }

	// { Public methods

	public void addBluetoothDevice(final BluetoothDevice device) {
		if (!this.mDevices.contains(device)) {
			this.mDevices.add(device);
			if (this.isVisible()) {
				this.mAdapter.notifyDataSetChanged();
			}
		}
	}

	public void setDevicesScanState(final boolean loading) {
		if (this.isVisible()) {
			if (loading) {
				this.mProgressBar.setVisibility(View.VISIBLE);
			} else {
				this.mProgressBar.setVisibility(View.GONE);
			}
		}
	}

	// }

	// { Private and protected abstract methods

	protected abstract int getDialogLayoutId();

	protected abstract int getDialogListId();

	protected abstract int getDialogCancelButtonId();

	protected abstract int getDialogProgressBarId();

	// }

	// { Public listener interface

	public interface IBluetoothDevicesDialogListener {

		public void onCancelButtonPressed();

		public void onDevicePicked(final BluetoothDevice device);
	}

	// }

}
