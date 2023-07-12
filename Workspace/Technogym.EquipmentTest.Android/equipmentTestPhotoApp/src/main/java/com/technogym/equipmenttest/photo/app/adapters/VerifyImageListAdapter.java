package com.technogym.equipmenttest.photo.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.technogym.equipmenttest.photo.app.R;
import com.technogym.equipmenttest.photo.app.activities.MainActivity;
import com.technogym.equipmenttest.photo.app.listeners.VerifyImageListItemOnClickListener;
import com.technogym.equipmenttest.photo.app.models.VerifyImageModel;

import java.util.List;

/**
 * Adapter to manage the verify images list items
 * Created by federico.foschini.
 */
public class VerifyImageListAdapter extends ArrayAdapter<VerifyImageModel> {

    public VerifyImageListAdapter(Context context, int textViewResourceId, List<VerifyImageModel> objects) {
        super(context, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.verifyimage_listitem, null);
        TextView actionId = (TextView)convertView.findViewById(R.id.actionId);
        TextView descrizione = (TextView)convertView.findViewById(R.id.description);
        ImageView imgView = (ImageView)convertView.findViewById(R.id.checkOk);
        VerifyImageModel c = getItem(position);
        actionId.setText("[" + c.getActionSequence() + "] " + c.getActionID());
        descrizione.setText(c.getActionDescription());
        imgView.setVisibility(c.hasImage() ? View.VISIBLE : View.INVISIBLE);
        convertView.setBackgroundColor(c.hasImage() ?
                getContext().getColor(R.color.white)
                : getContext().getColor(R.color.colorAccent));
        convertView.setOnClickListener(
                new VerifyImageListItemOnClickListener(
                        getContext(),
                        MainActivity.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE,
                        c.getActionID(),
                        c.getActionSequence(),
                        c.getSerialNumber()));
        return convertView;
    }

    // { Internal Methods

    // }
}
