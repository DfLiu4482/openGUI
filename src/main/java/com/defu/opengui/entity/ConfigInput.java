package com.defu.opengui.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author: dfliu
 * @date: 2024/01/11
 **/
@Component
public class ConfigInput implements Serializable {

    // 参数的唯一标识
    private String name;
    // 参数类型 file / string / path 需要拼接
    private String type;
    // 执行命令时的参数如：-c
    private String param;
    // 参数值如果不为空则取页面输入
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

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
                ", value='" + value + '\'' +
                '}';
    }
}
