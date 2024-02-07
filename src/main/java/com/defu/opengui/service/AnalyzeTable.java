package com.defu.opengui.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: dfliu
 * @date: 2024/02/07
 **/
@Service
public class AnalyzeTable {

    public Map<String, Object> analyze(String path){
        Map<String, Object> table = new HashMap<>();
        try {
            final List<String> lines = ReadSourceService.readLinesData(path);
            //获取表头
            final String head = lines.get(0);
            final String[] headArray = head.split("\\t|\\s+");
            final List<Map<String, String>> headList = Arrays.stream(headArray).map(str -> {
                Map<String, String> h = new HashMap<>();
                h.put("title", str);
                h.put("field", str);
                return h;
            }).collect(Collectors.toList());

            final List<Map<String, String>> dataList = lines.stream().skip(1).map(str -> {
                final String[] split = str.split("\\t|\\s+");
                Map<String, String> h = new HashMap<>();
                for (int i = 0; i < split.length; i++) {
                    h.put(headArray[i], split[i]);
                }
                return h;
            }).collect(Collectors.toList());
            table.put("columns", headList);
            table.put("data", dataList);

            return table;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取表格数据异常");
        }

    }
}
