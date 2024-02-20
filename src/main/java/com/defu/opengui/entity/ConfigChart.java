package com.defu.opengui.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * @author: dfliu
 * @date: 2024/02/07
 * 解析echarts数据
 * 优先级
 * 1：file,直接从文本文件中读取chart的完整数据
 * 2：chart,从配置文件中的json解析，用${filepath}站位获取文件中的数据
 * 3：xAxis,yAxis,series仅定义这三个数据从已经定义的模版中画最简单的图
 **/
@Component
public class ConfigChart {

    // bar line scatter
    private JSONObject xAxis;

    private JSONObject yAxis;

    private JSONArray series;

    private JSONObject chart;

    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }



    public JSONObject getyAxis() {
        return yAxis;
    }

    public void setyAxis(JSONObject yAxis) {
        this.yAxis = yAxis;
    }

    public JSONObject getxAxis() {
        return xAxis;
    }

    public void setxAxis(JSONObject xAxis) {
        this.xAxis = xAxis;
    }

    public JSONArray getSeries() {
        return series;
    }

    public void setSeries(JSONArray series) {
        this.series = series;
    }

    public JSONObject getChart() {
        return chart;
    }

    public void setChart(JSONObject chart) {
        this.chart = chart;
    }

    @Override
    public String toString() {
        return "ConfigChart{" +
                "xAxis=" + xAxis +
                ", yAxis=" + yAxis +
                ", series=" + series +
                ", chart=" + chart +
                ", file='" + file + '\'' +
                '}';
    }
}
