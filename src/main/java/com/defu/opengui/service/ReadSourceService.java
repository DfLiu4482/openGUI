package com.defu.opengui.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.defu.opengui.entity.ConfigJson;
import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * @author: dfliu
 * @date: 2024/01/09
 **/
public class ReadSourceService {

    public ConfigJson readConfig() throws IOException {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        final String path = jarFile.getParentFile().getPath();

        String jsonStr = new String(Files.readAllBytes(Paths.get(path+"/config.json")));
        return JSONObject.parseObject(jsonStr, new TypeReference<ConfigJson>() {});

    }

    public static String readData(String path) throws IOException {

        String data = new String(Files.readAllBytes(Paths.get(path)));
        return data;
    }

}
