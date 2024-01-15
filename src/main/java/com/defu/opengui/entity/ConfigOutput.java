package com.defu.opengui.entity;

import org.springframework.stereotype.Component;

/**
 * @author: dfliu
 * @date: 2024/01/11
 **/
@Component
public class ConfigOutput {

    private String name;
    private String param;
    private String type;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ConfigOutput{" +
                "name='" + name + '\'' +
                ", param='" + param + '\'' +
                ", type='" + type + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
