package com.defu.opengui.service;

import com.alibaba.fastjson.JSONObject;
import com.defu.opengui.entity.ConfigChart;
import com.defu.opengui.entity.ConfigInput;
import com.defu.opengui.entity.ConfigJson;
import com.defu.opengui.entity.ConfigList;
import com.defu.opengui.utils.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        ConfigList deepClone;
        try {
            deepClone = DeepCloneUtil.deepClone(configList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Create replica object exception!");
        }

        // 指定路径前缀
        final String prefix = PathUtils.getJarPath() + File.separator+"userspace"+File.separator+System.currentTimeMillis();
        try {
            Files.createDirectory(Paths.get(prefix));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Create userspace exception!");
        }

        final List<ConfigJson> configJsonList = deepClone.getConfigJsonList();
        configJsonList.forEach(configJson -> {
            Map<String, Object> retData = new HashMap<>();
            String software = configJson.getExecFile();
            if (!StringUtils.hasText(software)){
                return;
            }

            // 输出路径
            String output = configJson.getOutput();
            if (StringUtils.hasText(output)){
                output = PathUtils.getAbsolute(output, prefix);
                File file = new File(output);
                if (!file.isDirectory()){
                    file.mkdirs();
                }
            }else{
                output = PathUtils.getAbsolute("", prefix);
            }

            // 拼接输入参数
            StringBuilder inputParam  = new StringBuilder();
            if (!ObjectUtils.isEmpty(configJson.getInput()) && !CollectionUtils.isEmpty(configJson.getInput())){
                for (ConfigInput configInput : configJson.getInput()){
                    String value = configInput.getValue();
                    if (configInput.getParam()!=null){
                        inputParam.append(" " + configInput.getParam());
                    }
                    if ("path".equals(configInput.getType()) && StringUtils.hasText(value)){
                        value = PathUtils.getAbsolute(value, prefix);
                    }
                    if (StringUtils.hasText(value)){
                        inputParam.append(" " + value);
                    }else{
                        inputParam.append(" " + input.get(configInput.getName()));
                    }

                }
            }

            // 调用命令
            final ReturnMsgUtil returnMsgUtil;
            try {
                String cmd = "";
                if (StringUtils.hasText(output)){
                    cmd = "cd "+output+" && ";
                }
                cmd += software + inputParam;
                returnMsgUtil = TerminalUtil.execCmd(cmd, null);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Computation exception!");
            }

            // 解析结果
            if (returnMsgUtil.getSuccess()){
                //解析画图
                if (!ObjectUtils.isEmpty(configJson.getCharts()) && !CollectionUtils.isEmpty(configJson.getCharts())){
                    final List<ConfigChart> chart = configJson.getCharts();
                    List<JSONObject> chartRes = analyzeChat.analyze(chart, prefix);
                    retData.put("chart", chartRes);
                }
                // 解析结果
                if (!ObjectUtils.isEmpty(configJson.getResult()) && StringUtils.hasText(configJson.getResult().getPath())){
                    configJson.getResult().setPath(PathUtils.getAbsolute(configJson.getResult().getPath(), prefix));
                    List<Map<String, String>> fileNames = analyzeResult.analyze(configJson.getResult());
                    retData.put("result", fileNames);
                }
                // 解析表格
                if (!ObjectUtils.isEmpty(configJson.getTable()) && StringUtils.hasText(configJson.getTable().getPath())){
                    configJson.getTable().setPath(PathUtils.getAbsolute(configJson.getTable().getPath(), prefix));
                    final Map<String, Object> table = analyzeTable.analyze(configJson.getTable());
                    retData.put("table", table);
                }

            }else{
                throw new RuntimeException("The command line failed to be executed！ "+software);
            }
            retList.add(retData);
        });
        return ResponseResult.success(retList);

    }

}
