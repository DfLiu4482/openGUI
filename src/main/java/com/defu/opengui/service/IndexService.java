package com.defu.opengui.service;

import com.defu.opengui.entity.ConfigInput;
import com.defu.opengui.entity.ConfigJson;
import com.defu.opengui.utils.ReturnMsgUtil;
import com.defu.opengui.utils.TerminalUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

/**
 * @author: dfliu
 * @date: 2024/01/09
 **/
@Service
public class IndexService {

    @Resource
    private ConfigJson configJson;

    public void execute(Map<String, Object> input){

        String software = configJson.getExecuFile();

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
            System.out.println("计算成功！");
        }else{
            System.out.println("计算失败！");
        }
    }

}
