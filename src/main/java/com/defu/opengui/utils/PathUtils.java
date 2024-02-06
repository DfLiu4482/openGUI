package com.defu.opengui.utils;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.Resource;

import java.io.File;

/**
 * @author: dfliu
 * @date: 2024/02/06
 **/
public class PathUtils {

    public static String  getJarPath(){
        ApplicationHome home = new ApplicationHome(PathUtils.class);
        File jarFile = home.getSource();
        return jarFile.getParentFile().getPath();
    }

}
