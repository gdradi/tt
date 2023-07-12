package com.technogym.equipmenttest.photo.app.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import com.technogym.equipmenttest.photo.app.activities.MainActivity;
import com.technogym.equipmenttest.photo.app.support.FileManager;

import java.io.File;

/**
 * Listener related to the VerifyImage ListView item OnClick event
 * Created by federico.foschini.
 */
public class VerifyImageListItemOnClickListener implements View.OnClickListener {

    // { Internal Fields

    private String _actionID;       // target action ID
    private String _actionSequence; // target action sequence
    private String _serialNumber;   // target serial number
    private Context _context;       // app context
    private int _targetResultID;    // target result ID for activity result

    // }

    // { Constructors

    /**
     * Constructor
     * @param context: target context
     * @param targetResultID: target activity result ID
     * @param actionID: target action ID
     * @param actionSequence: target action sequence
     * @param serialNumber: target serial number
     */
    public VerifyImageListItemOnClickListener(Context context, int targetResultID, String actionID, String actionSequence, String serialNumber) {
        _actionID = actionID;
        _actionSequence = actionSequence;
        _serialNumber = serialNumber;
        _context = context;
        _targetResultID = targetResultID;
    }

    // }

    // { OnClickListener Implementation

    @Override
    public void onClick(View v) {
        // images file system management
        String imageFolderPath = FileManager.createImagesFoldersTree();
        File imageFile = FileManager.createImageFile(imageFolderPath, _actionID);
        Uri targetImageUri = Uri.fromFile(imageFile);

        // update the target activity data to upload
        MainActivity mainActivity = ((MainActivity) _context);
        MainActivity.CurrentActionID = _actionID;
        MainActivity.CurrentActionSequence = _actionSequence;
        MainActivity.CurrentSerialNumber = _serialNumber;
        MainActivity.CurrentImageFilePath = imageFile.getAbsolutePath();
        MainActivity.CurrentVerifyImageIndex =  mainActivity.getVerifyImageModelIndex(_actionID);

        // take picture
        Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, targetImageUri);
        mainActivity.startActivityForResult(imageIntent, _targetResultID);
    }

    // }
}
