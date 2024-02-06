package com.defu.opengui.entity;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: dfliu
 * @date: 2024/02/06
 **/
@Component
public class ConfigList {
    List<ConfigJson> configJsonList;

    public List<ConfigJson> getConfigJsonList() {
        return configJsonList;
    }

    public void setConfigJsonList(List<ConfigJson> configJsonList) {
        this.configJsonList = configJsonList;
    }

    @Override
    public String toString() {
        return "ConfigList{" +
                "configJsonList=" + configJsonList +
                '}';
    }
}
