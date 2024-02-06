package com.defu.opengui.entity;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: dfliu
 * @date: 2024/01/11
 **/
@Component
public class ConfigJson {

    private Integer order;
    private String execFile;
    private List<ConfigInput> input;
    private ConfigOutput output;
    private List<JSONObject> chat;
    private ConfigResult result;

    public ConfigResult getResult() {
        return result;
    }

    public void setResult(ConfigResult result) {
        this.result = result;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<JSONObject> getChat() {
        return chat;
    }

    public void setChat(List<JSONObject> chat) {
        this.chat = chat;
    }

    public String getExecFile() {
        return execFile;
    }

    public void setExecFile(String execFile) {
        this.execFile = execFile;
    }

    public List<ConfigInput> getInput() {
        return input;
    }

    public void setInput(List<ConfigInput> input) {
        this.input = input;
    }

    public ConfigOutput getOutput() {
        return output;
    }

    public void setOutput(ConfigOutput output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "ConfigJson{" +
                "order=" + order +
                ", execFile='" + execFile + '\'' +
                ", input=" + input +
                ", output=" + output +
                ", chat=" + chat +
                ", result=" + result +
                '}';
    }
}
