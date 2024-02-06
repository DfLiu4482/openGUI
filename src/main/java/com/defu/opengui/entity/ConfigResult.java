package com.defu.opengui.entity;

import org.springframework.stereotype.Component;

/**
 * @author: dfliu
 * @date: 2024/02/06
 **/
@Component
public class ConfigResult {
    private String type;
    private String path;
    private Boolean isShow;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }

    @Override
    public String toString() {
        return "ConfigResult{" +
                "type='" + type + '\'' +
                ", path='" + path + '\'' +
                ", isShow=" + isShow +
                '}';
    }
}
