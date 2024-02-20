package com.defu.opengui.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * @author: dfliu
 * @date: 2024/02/07
 **/
@Component
public class ConfigChart {

    // bar line scatter
    private JSONObject xAxis;

    private JSONObject yAxis;

    private JSONArray series;

    private JSONObject chart;

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
                '}';
    }
}
