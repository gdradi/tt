package com.technogym.equipmenttest.photo.app.support;

import android.os.Environment;

import com.technogym.equipmenttest.photo.app.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Manager to manage the file system
 * Created by federico.foschini.
 */
public class FileManager {

    /**
     * Creates the dictories tree related to the specified list
     * @param directories: list of directories to create
     * @return the final directory path
     */
    public static String createDirectoryTree(List<String> directories) {
        String path = Environment.getExternalStorageDirectory() + "";
        for(int i = 0; i < directories.size(); i++) {
            path = path + "/" + directories.get(i);
            File direct = new File(path);
            if(!direct.exists()) {
                direct.mkdirs();
            }
        }
        return path;
    }

    /**
     * Method to create the app images directories tree
     * @return the final images directory path
     */
    public static String createImagesFoldersTree() {
        ArrayList<String> folders = new ArrayList<>();
        folders.add(Configuration.APP_ROOT_FOLDER);
        folders.add(Configuration.APP_IMAGES_FOLDER);
        String path = createDirectoryTree(folders);
        return path;
    }

    /**
     * Method to create the target image file
     * @param imagesFolderPath: images folder path
     * @param imageFilename: target image filename without extension
     * @return the created file
     */
    public static File createImageFile(String imagesFolderPath, String imageFilename) {
        File image = new File(imagesFolderPath, imageFilename + ".png");
        return image;
    }

    /**
     * Method to delete all files in a folder
     * @param folderPath: the target folder
     */
    public static void deleteAllFilesInFolder(String folderPath) {
        File directory = new File(folderPath);
        if(!directory.isDirectory()) {
            return;
        }
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            files[i].delete();
        }
    }

    /**
     * Method to delete all files in the image folder
     */
    public static void cleanImageFolder() {
        String imageFolderPath = createImagesFoldersTree();
        deleteAllFilesInFolder(imageFolderPath);
    }
}
