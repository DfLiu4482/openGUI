package com.defu.opengui.utils;

import com.alibaba.fastjson.JSONObject;
import com.defu.opengui.service.ReadSourceService;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: dfliu
 * @date: 2024/02/10
 **/
public class AnalyzePlaceholder {

    public static String analyze(String data){
        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()){
            final String group = matcher.group(1);
            try {
                final String dataRes = ReadSourceService.readData(group);
                return dataRes;
            } catch (IOException e) {
                throw new RuntimeException("读取图表数据异常");
            }
        }
        return data;
    }
}
