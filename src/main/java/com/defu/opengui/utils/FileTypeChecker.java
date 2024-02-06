package com.defu.opengui.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author: dfliu
 * @date: 2024/02/06
 **/
public class FileTypeChecker {
    public static boolean isImageFile(File file) {
        String[] imageExtensions = {"jpg", "jpeg", "png", "gif", "bmp"};
        List<String> imageExtensionsList = Arrays.asList(imageExtensions);

        String fileName = file.getName();
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return false;
        }

        String fileExtension = fileName.substring(lastIndexOfDot + 1).toLowerCase();
        return imageExtensionsList.contains(fileExtension);
    }
}
