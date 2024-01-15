package com.defu.opengui.entity;

import org.springframework.stereotype.Component;

/**
 * @author: dfliu
 * @date: 2024/01/11
 **/
@Component
public class ConfigInput {

    private String name;
    private String type;
    private String param;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "ConfigInput{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                '}';
    }
}
