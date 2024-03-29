package com.defu.opengui.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @author: dfliu
 * @date: 2024/01/11
 **/
@Component
public class ConfigJson implements Serializable {

    // 命令排序 0 为固定的，决定页面显示
    private Integer order;
    // 需要执行的可执行文件
    private String execFile;
    // 命令入参
    private List<ConfigInput> input;
    // 结果输出位置，如果命令行工具没有指定输出位置的功能，则可以用这个参数来指定命令行执行的位置
    private String output;
    // echarts图表的json
    private List<ConfigChart> charts;
    // 结果文件的位置或者结果文件本身，如果是文件页面显示文件图片，如果是图片页面直接显示图片
    private ConfigResult result;
    // 需要显示的表格，目前是txt格式的数据
    private ConfigTable table;

    public List<ConfigChart> getCharts() {
        return charts;
    }

    public void setChats(List<ConfigChart> charts) {
        this.charts = charts;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public ConfigResult getResult() {
        return result;
    }

    public void setResult(ConfigResult result) {
        this.result = result;
    }

    public ConfigTable getTable() {
        return table;
    }

    public void setTable(ConfigTable table) {
        this.table = table;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    @Override
    public String toString() {
        return "ConfigJson{" +
                "order=" + order +
                ", execFile='" + execFile + '\'' +
                ", input=" + input +
                ", output='" + output + '\'' +
                ", charts=" + charts +
                ", result=" + result +
                ", table=" + table +
                '}';
    }
}
