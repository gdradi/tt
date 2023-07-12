package com.technogym.equipmenttest.app.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BluetoothScanAdapter extends ArrayAdapter<BluetoothDevice> {

	public BluetoothScanAdapter(Context context, int resource) {
		super(context, resource);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) super.getView(position, convertView, parent);
		view.setText(getItem(position).getName());
		return view;
	}
}
