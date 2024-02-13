package com.defu.opengui.utils;

import com.defu.opengui.service.ReadSourceService;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: dfliu
 * @date: 2024/02/10
 **/
public class AnalyzePlaceholder {

    public static String analyze(String data, String prefix){
        Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher matcher = pattern.matcher(data);
        while (matcher.find()){
            final String group = PathUtils.getAbsolute(matcher.group(1), prefix);
            try {
                if (group.contains("#")){
                    final String substring = group.substring(group.indexOf("#")+1);
                    final Integer integer = Integer.valueOf(substring);
                    final List<String> lines = ReadSourceService.readLinesData(group.substring(0, group.indexOf("#")));
                    final String s = lines.get(integer);
                    String arr = "[" + s + "]";
                    return arr;
                }
                final String dataRes = ReadSourceService.readData(group);
                return dataRes;
            } catch (IOException e) {
                throw new RuntimeException("读取图表数据异常");
            }
        }
        return data;
    }
}
