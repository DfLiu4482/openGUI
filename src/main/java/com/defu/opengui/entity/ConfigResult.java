package com.defu.opengui.entity;

import org.springframework.stereotype.Component;

/**
 * @author: dfliu
 * @date: 2024/02/07
 **/
@Component
public class ConfigResult {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ConfigResult{" +
                "path='" + path + '\'' +
                '}';
    }
}
