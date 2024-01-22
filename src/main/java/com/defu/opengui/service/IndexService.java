package com.defu.opengui.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.defu.opengui.entity.ConfigInput;
import com.defu.opengui.entity.ConfigJson;
import com.defu.opengui.utils.ReturnMsgUtil;
import com.defu.opengui.utils.TerminalUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: dfliu
 * @date: 2024/01/09
 **/
@Service
public class IndexService {

    @Resource
    private ConfigJson configJson;

    public Map<String, Object> execute(Map<String, Object> input){

        Map<String, Object> retMap = new HashMap<>();
        retMap.put("success", 1);
        retMap.put("data", null);

        String software = configJson.getExecFile();

        File result = new File(configJson.getOutput().getPath());
        if (!result.exists()){
            result.mkdirs();
        }

        StringBuilder inputParam  = new StringBuilder();
        for (ConfigInput configInput : configJson.getInput()){
            if (configInput.getParam()!=null)
                inputParam.append(" " + configInput.getParam());
            inputParam.append(" " + input.get(configInput.getName()));
        }

        StringBuilder outputParam = new StringBuilder();
        if (configJson.getOutput()!=null){
            outputParam.append(" "+ configJson.getOutput().getParam());
            outputParam.append(" " + configJson.getOutput().getPath() + File.separator);
            outputParam.append(configJson.getOutput().getName());
        }

        final ReturnMsgUtil returnMsgUtil;
        try {
            returnMsgUtil = TerminalUtil.execCmd(software +
                    inputParam + outputParam, null);
        } catch (Exception e) {
            throw new RuntimeException("计算异常！");
        }

        if (returnMsgUtil.getSuccess()){
            //解析画图
            if (!configJson.getChat().isEmpty()){
                final List<JSONObject> chat = configJson.getChat();
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
                retMap.put("data", chatRes);
                return retMap;
            }
            return retMap;
        }
        retMap.put("success", 0);
        return retMap;
    }

}
