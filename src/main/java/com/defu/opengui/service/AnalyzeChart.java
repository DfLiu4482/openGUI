package com.defu.opengui.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.defu.opengui.entity.ConfigChart;
import com.defu.opengui.utils.AnalyzePlaceholder;
import com.defu.opengui.utils.PathUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: dfliu
 * @date: 2024/02/06
 **/
@Service
public class AnalyzeChart {

    public List<JSONObject> analyze(List<ConfigChart> charts, String prefix) {

        String path = PathUtils.getJarPath()+"/config/chat.json";
        final JSONObject templateJson;
        try {
            templateJson = JSONObject.parseObject(ReadSourceService.readData(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Abnormal reading of chart data!");
        }

        List<JSONObject> chartRes = new ArrayList<>();
        charts.forEach(chart->{
            JSONArray seriesArray = new JSONArray();
            JSONArray xAxisArray = new JSONArray();
            if (!ObjectUtils.isEmpty(chart.getFile())){
                // 读取文件
                final String chartPath = PathUtils.getAbsolute(chart.getFile(), prefix);
                File file = new File(chartPath);
                if (file.isDirectory()){
                    final File[] files = file.listFiles();
                    for (File f : files){
                        extracted(chartRes, f, chart);
                    }

                }else if (file.isFile()){
                    extracted(chartRes, file, chart);
                }else{
                    throw new RuntimeException("图表解析失败");
                }

            }else if (ObjectUtils.isEmpty(chart.getChart())){
                // 解析series
                chart.getSeries().forEach(value->{
                    final JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(value));
                    final String data = jsonObject.getString("data");
                    final String analyze = AnalyzePlaceholder.analyze(data, prefix);
                    jsonObject.put("data", JSONObject.parseArray("["+analyze+"]"));
                    seriesArray.add(jsonObject);
                });

                if (!ObjectUtils.isEmpty(chart.getxAxis())&& StringUtils.hasText(chart.getxAxis().getString("data"))){
                    final String data = chart.getxAxis().getString("data");
                    final String analyze = AnalyzePlaceholder.analyze(data, prefix);
                    chart.getxAxis().put("data", JSONObject.parseArray("["+analyze+"]"));
                    xAxisArray.add(chart.getxAxis());
                }
                templateJson.put("series", seriesArray);
                templateJson.put("xAxis", chart.getxAxis());
                templateJson.put("blockWeight", chart.getBlockWeight());
                templateJson.put("blockHeight", chart.getBlockHeight());
                chartRes.add(templateJson);
            }else {
                chart.getChart().getJSONArray("series").forEach(value->{
                    final JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(value));
                    final String data = jsonObject.getString("data");
                    final String analyze = AnalyzePlaceholder.analyze(data, prefix);
                    jsonObject.put("data", JSONObject.parseArray("["+analyze+"]"));
                    seriesArray.add(jsonObject);
                });
                chart.getChart().put("series", seriesArray);

                if (!ObjectUtils.isEmpty(chart.getChart().getJSONObject("xAxis"))&&
                        StringUtils.hasText(chart.getChart().getJSONObject("xAxis").getString("data"))){
                    final JSONObject xAxis = chart.getChart().getJSONObject("xAxis");
                    final String data = chart.getChart().getJSONObject("xAxis").getString("data");
                    final String analyze = AnalyzePlaceholder.analyze(data, prefix);
                    xAxis.put("data", JSONObject.parseArray("["+analyze+"]"));
                    xAxisArray.add(chart.getxAxis());
                    chart.getChart().put("series", seriesArray);
                    chart.getChart().put("xAxis", xAxis);
                }
                if (!ObjectUtils.isEmpty(chart.getChart().getJSONObject("yAxis"))&&
                        StringUtils.hasText(chart.getChart().getJSONObject("yAxis").getString("data"))){
                    final JSONObject yAxis = chart.getChart().getJSONObject("yAxis");
                    final String data = chart.getChart().getJSONObject("yAxis").getString("data");
                    final String analyze = AnalyzePlaceholder.analyze(data, prefix);
                    yAxis.put("data", JSONObject.parseArray("["+analyze+"]"));
                    xAxisArray.add(chart.getyAxis());
                    chart.getChart().put("series", seriesArray);
                    chart.getChart().put("yAxis", yAxis);
                }
                chart.getChart().put("blockWeight", chart.getBlockWeight());
                chart.getChart().put("blockHeight", chart.getBlockHeight());
                chartRes.add(chart.getChart());
            }
        });
        return chartRes;
    }

    private void extracted(List<JSONObject> chartRes, File file, ConfigChart chart) {
        final String s;
        try {
            s = ReadSourceService.readData(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("图表解析失败");
        }
        final JSONObject jsonObject = JSONObject.parseObject(s);
        jsonObject.put("blockWeight", chart.getBlockWeight());
        jsonObject.put("blockHeight", chart.getBlockHeight());
        chartRes.add(jsonObject);
    }

    public JSONObject cleanJson(JSONObject json){
        final String s = json.toJSONString();
        return null;
    }
}
