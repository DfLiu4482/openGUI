package com.defu.opengui.service;

import com.alibaba.fastjson.JSONObject;
import com.defu.opengui.entity.ConfigChart;
import com.defu.opengui.entity.ConfigInput;
import com.defu.opengui.entity.ConfigJson;
import com.defu.opengui.entity.ConfigList;
import com.defu.opengui.utils.ResponseResult;
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
    private AnalyzeChart analyzeChat;

    @Resource
    private AnalyzeResult analyzeResult;

    @Resource
    private AnalyzeTable analyzeTable;


    public ResponseResult execute(Map<String, Object> input){

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
                throw new RuntimeException("Computation exception!");
            }

            // 解析结果
            if (returnMsgUtil.getSuccess()){
                //解析画图
                if (!ObjectUtils.isEmpty(configJson.getCharts()) && !CollectionUtils.isEmpty(configJson.getCharts())){
                    final List<ConfigChart> chart = configJson.getCharts();
                    List<JSONObject> chartRes = analyzeChat.analyze(chart);
                    retData.put("chart", chartRes);
                }
                // 解析结果
                if (!ObjectUtils.isEmpty(configJson.getResult()) && StringUtils.hasText(configJson.getResult().getPath())){
                    List<Map<String, String>> fileNames = analyzeResult.analyze(configJson.getResult().getPath());
                    retData.put("result", fileNames);
                }
                // 解析表格
                if (!ObjectUtils.isEmpty(configJson.getTable()) && StringUtils.hasText(configJson.getTable().getPath())){
                    final Map<String, Object> table = analyzeTable.analyze(configJson.getTable().getPath());
                    retData.put("table", table);
                }

            }else{
                throw new RuntimeException("Computation fail！");
            }
            retList.add(retData);
        });
        return ResponseResult.success(retList);

    }

}
