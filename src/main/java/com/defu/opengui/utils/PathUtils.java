package com.defu.opengui.utils;

import org.springframework.boot.system.ApplicationHome;

import java.io.File;

/**
 * @author: dfliu
 * @date: 2024/02/06
 **/
public class PathUtils {

    public static String getJarPath(){
        ApplicationHome home = new ApplicationHome(PathUtils.class);
        File jarFile = home.getSource();
        if(jarFile.isDirectory()){
            return jarFile.getParentFile().getParentFile().getPath();
        }
        return jarFile.getParentFile().getPath();
    }

    public static String getAbsolute(String path, String prefix){
        File f = new File(path);
        if (f.isAbsolute()) return path;
        else{
            File file = new File(prefix + File.separator + path);
            return file.getAbsolutePath();
        }
    }

}
