package com.technogym.android.myrun.sdk.android.adapters;

import java.util.List;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DevicesArrayAdapter extends ArrayAdapter<BluetoothDevice> {

	private final LayoutInflater mInflater;

	// { Construction

	public DevicesArrayAdapter(final Context context, final int textViewResourceId, final List<BluetoothDevice> devices) {
		super(context, textViewResourceId, devices);

		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	// }

	// { ArrayAdapter methods overriding

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		final View view = this.mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);

		final TextView nameTextView = (TextView) view.findViewById(android.R.id.text1);
		nameTextView.setText(this.getItem(position).getName());

		return view;
	}

	// }

}
