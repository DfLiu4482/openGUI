package com.defu.opengui.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.defu.opengui.entity.ConfigChart;
import com.defu.opengui.utils.AnalyzePlaceholder;
import com.defu.opengui.utils.PathUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: dfliu
 * @date: 2024/02/06
 **/
@Service
public class AnalyzeChart {

    public List<JSONObject>  analyze(List<ConfigChart> charts) {

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
            if (ObjectUtils.isEmpty(chart.getChart())){
                // 解析series
                chart.getSeries().forEach(value->{
                    final JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(value));
                    final String data = jsonObject.getString("data");
                    final String analyze = AnalyzePlaceholder.analyze(data);
                    jsonObject.put("data", JSONObject.parseArray(analyze));
                    seriesArray.add(jsonObject);
                });

                final String data = chart.getxAxis().getString("data");
                final String analyze = AnalyzePlaceholder.analyze(data);
                chart.getxAxis().put("data", JSONObject.parseArray(analyze));
                xAxisArray.add(chart.getxAxis());
                templateJson.put("series", seriesArray);
                templateJson.put("xAxis", chart.getxAxis());
                chartRes.add(templateJson);

            }else {
                chart.getChart().getJSONArray("series").forEach(value->{
                    final JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(value));
                    final String data = jsonObject.getString("data");
                    final String analyze = AnalyzePlaceholder.analyze(data);
                    jsonObject.put("data", JSONObject.parseArray(analyze));
                    seriesArray.add(jsonObject);
                });
                chart.getChart().put("series", seriesArray);

                if (!ObjectUtils.isEmpty(chart.getChart().getJSONObject("xAxis"))&&
                        StringUtils.hasText(chart.getChart().getJSONObject("xAxis").getString("data"))){
                    final String data = chart.getChart().getJSONObject("xAxis").getString("data");
                    final String analyze = AnalyzePlaceholder.analyze(data);
                    chart.getxAxis().put("data", analyze);
                    xAxisArray.add(chart.getxAxis());
                    chart.getChart().put("series", seriesArray);
                    chart.getChart().put("xAxis", chart.getxAxis());
                }
                chartRes.add(chart.getChart());

            }
        });
        return chartRes;
    }
}
