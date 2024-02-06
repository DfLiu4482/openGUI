package com.defu.opengui.service;

import com.alibaba.fastjson.JSONObject;
import com.defu.opengui.entity.ConfigInput;
import com.defu.opengui.entity.ConfigJson;
import com.defu.opengui.entity.ConfigList;
import com.defu.opengui.utils.FileTypeChecker;
import com.defu.opengui.utils.PathUtils;
import com.defu.opengui.utils.ReturnMsgUtil;
import com.defu.opengui.utils.TerminalUtil;
import jakarta.annotation.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    private ResourceLoader resourceLoader;

    public Map<String, Object> execute(Map<String, Object> input){



        List<Map<String, Object>> retList = new ArrayList<>();

        final List<ConfigJson> configJsonList = configList.getConfigJsonList();
        configJsonList.forEach(configJson -> {
            Map<String, Object> retData = new HashMap<>();
            String software = configJson.getExecFile();

            // 创建输出路径
            if (!ObjectUtils.isEmpty(configJson.getOutput()) && StringUtils.hasText(configJson.getOutput().getPath())){
                File result = new File(configJson.getOutput().getPath());
                if (!result.exists()){
                    result.mkdirs();
                }
            }

            // 拼接输入参数
            StringBuilder inputParam  = new StringBuilder();
            if (!ObjectUtils.isEmpty(configJson.getInput())){
                for (ConfigInput configInput : configJson.getInput()){
                    if (configInput.getParam()!=null)
                        inputParam.append(" " + configInput.getParam());
                    inputParam.append(" " + input.get(configInput.getName()));
                }
            }

            // 拼接输出参数
            StringBuilder outputParam = new StringBuilder();
            if (configJson.getOutput()!=null){
                outputParam.append(" "+ configJson.getOutput().getParam());
                outputParam.append(" " + configJson.getOutput().getPath() + File.separator);
                outputParam.append(configJson.getOutput().getName());
            }

            // 调用命令
            final ReturnMsgUtil returnMsgUtil;
            try {
                returnMsgUtil = TerminalUtil.execCmd(software +
                        inputParam + outputParam, null);
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
                if (!ObjectUtils.isEmpty(configJson.getResult()) && StringUtils.hasText(configJson.getResult().getPath())){
                    // 将结果文件放在result里面供外部展示或下载
                    File directory = new File(configJson.getResult().getPath());
                    List<String> fileNames = new ArrayList<>();
                    // 判断是文件还是路径
                    if (directory.isDirectory()){
                        final File[] files = directory.listFiles();

                        // 遍历文件数组
                        for (File file : files) {
                            fileNames.add(file.getName());
                            // 构建源文件路径
                            Path sourcePath = Paths.get(file.getAbsolutePath());
                            // 构建目标文件路径
                            try {
                                Path targetPath = Paths.get(resourceLoader.getResource("classpath:/static/images").getURL().getPath(), file.getName());
                                // 拷贝文件到目标目录
                                Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                            } catch (IOException e) {
                                e.printStackTrace();
                                throw new RuntimeException("获取结果异常！");
                            }
                        }
                    }else if (directory.isFile()){
                        if(FileTypeChecker.isImageFile(directory)){
                            fileNames.add(directory.getName());
                        }else{
                            fileNames.add("file.jpeg");
                        }

                        Path sourcePath =  Paths.get(directory.getAbsolutePath());
                        // 构建目标文件路径
                        try {
                            Path targetPath = Paths.get(resourceLoader.getResource("classpath:/static/images").getURL().getPath(), directory.getName());
                            // 拷贝文件到目标目录
                            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException("获取结果异常！");
                        }
                    }

                    retData.put("result", fileNames);
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
