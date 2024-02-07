package com.defu.opengui.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.defu.opengui.entity.ConfigJson;
import com.defu.opengui.entity.ConfigList;
import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author: dfliu
 * @date: 2024/01/09
 **/
public class ReadSourceService {

    public ConfigList readConfig() throws IOException {
        ApplicationHome home = new ApplicationHome(getClass());
        File jarFile = home.getSource();
        final String path = jarFile.getParentFile().getPath();

        ConfigList configList = new ConfigList();
        List<ConfigJson> configJsons = new ArrayList<>();
        String jsonStr = new String(Files.readAllBytes(Paths.get(path+"/config/config.json")));
        final JSONArray jsonArray = JSONArray.parseArray(jsonStr);
        jsonArray.stream().forEach(obj->{
            configJsons.add(JSONObject.parseObject(obj.toString(), new TypeReference<>() {}));
        });
        List<ConfigJson> collect = configJsons.stream().sorted(Comparator.comparing(ConfigJson::getOrder)).collect(Collectors.toList());
        configList.setConfigJsonList(collect);
        return configList;

    }

    public static String readData(String path) throws IOException {

        String data = new String(Files.readAllBytes(Paths.get(path)));
        return data;
    }

    public static List<String> readLinesData(String path) throws IOException {
        final List<String> strings = Files.readAllLines(Paths.get(path));
        return strings;
    }

}
