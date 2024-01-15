package com.defu.opengui.entity;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: dfliu
 * @date: 2024/01/11
 **/
@Component
public class ConfigJson {

    private String ExecuFile;
    private List<ConfigInput> input;
    private ConfigOutput output;

    public String getExecuFile() {
        return ExecuFile;
    }

    public void setExecuFile(String execuFile) {
        ExecuFile = execuFile;
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
                "ExecuFile='" + ExecuFile + '\'' +
                ", input=" + input +
                ", output=" + output +
                '}';
    }
}
