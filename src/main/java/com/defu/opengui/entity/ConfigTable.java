package com.defu.opengui.entity;

/**
 * @author: dfliu
 * @date: 2024/02/07
 **/
public class ConfigTable {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ConfigTable{" +
                "path='" + path + '\'' +
                '}';
    }
}
