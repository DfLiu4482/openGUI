package com.defu.opengui.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: dfliu
 * @date: 2024/02/06
 **/
@Service
public class AnalyzeChat {

    public List<JSONObject>  analyze(List<JSONObject> chat){
        List<JSONObject> chatRes = new ArrayList<>();
        chat.forEach(obj->{
            JSONArray seriesArray = new JSONArray();
            obj.getJSONArray("series").forEach(value->{

                final JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(value));
                final String data = jsonObject.getString("data");
                Pattern pattern = Pattern.compile("\\$\\{(.*?)\\}");
                Matcher matcher = pattern.matcher(data);
                while (matcher.find()){
                    final String group = matcher.group(1);
                    try {
                        final String dataRes = ReadSourceService.readData(group);
                        jsonObject.put("data", JSONObject.parseArray(dataRes));
                    } catch (IOException e) {
                        throw new RuntimeException("读取图表数据异常");
                    }
                }
                seriesArray.add(jsonObject);
            });
            obj.put("series", seriesArray);
            chatRes.add(obj);
        });
        return chatRes;
    }
}
