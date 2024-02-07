package com.defu.opengui.service;

import com.alibaba.fastjson.JSONObject;
import com.defu.opengui.entity.ConfigInput;
import com.defu.opengui.entity.ConfigJson;
import com.defu.opengui.entity.ConfigList;
import com.defu.opengui.utils.ReturnMsgUtil;
import com.defu.opengui.utils.TerminalUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: dfliu
 * @date: 2024/01/09
 **/
@Service
public class IndexService {

    @Resource
    private ConfigList configList;

    @Resource
    private AnalyzeChat analyzeChat;

    @Resource
    private AnalyzeResult analyzeResult;

    @Resource
    private AnalyzeTable analyzeTable;


    public Map<String, Object> execute(Map<String, Object> input){

        List<Map<String, Object>> retList = new ArrayList<>();

        final List<ConfigJson> configJsonList = configList.getConfigJsonList();
        configJsonList.forEach(configJson -> {
            Map<String, Object> retData = new HashMap<>();
            String software = configJson.getExecFile();
            if (!StringUtils.hasText(software)){
                return;
            }

            // 创建输出路径
            if (StringUtils.hasText(configJson.getOutput())){
                File result = new File(configJson.getOutput());
                if (!result.exists()){
                    result.mkdirs();
                }
            }

            // 拼接输入参数
            StringBuilder inputParam  = new StringBuilder();
            if (!ObjectUtils.isEmpty(configJson.getInput()) && !CollectionUtils.isEmpty(configJson.getInput())){
                for (ConfigInput configInput : configJson.getInput()){
                    if (configInput.getParam()!=null){
                        inputParam.append(" " + configInput.getParam());
                    }
                    if (StringUtils.hasText(configInput.getValue())){
                        inputParam.append(" " + configInput.getValue());
                    }else{
                        inputParam.append(" " + input.get(configInput.getName()));
                    }

                }
            }

            // 调用命令
            final ReturnMsgUtil returnMsgUtil;
            try {
                String cmd = "";
                if (StringUtils.hasText(configJson.getOutput())){
                    cmd = "cd "+configJson.getOutput()+" && ";
                }
                cmd += software + inputParam;
                returnMsgUtil = TerminalUtil.execCmd(cmd, null);
            } catch (Exception e) {
                throw new RuntimeException("计算异常！");
            }

            // 解析结果
            if (returnMsgUtil.getSuccess()){
                //解析画图
                if (!ObjectUtils.isEmpty(configJson.getChat()) && !configJson.getChat().isEmpty()){
                    final List<JSONObject> chat = configJson.getChat();
                    List<JSONObject> chatRes = analyzeChat.analyze(chat);
                    retData.put("chat", chatRes);
                }
                // 解析结果
                if (StringUtils.hasText(configJson.getResult())){
                    List<String> fileNames = analyzeResult.analyze(configJson.getResult());
                    retData.put("result", fileNames);
                }
                // 解析表格
                if (StringUtils.hasText(configJson.getTable())){
                    final Map<String, Object> table = analyzeTable.analyze(configJson.getTable());
                    retData.put("table", table);
                }

            }else{
                throw new RuntimeException("计算失败！");
            }
            retList.add(retData);
        });

        Map<String, Object> retMap = new HashMap<>();
        retMap.put("success", 1);
        retMap.put("data",retList);
        return retMap;
    }

}
