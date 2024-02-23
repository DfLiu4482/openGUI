package com.defu.opengui.entity;

import java.io.Serializable;

/**
 * @author: dfliu
 * @date: 2024/02/07
 **/
public class ConfigTable implements Serializable {

    private String path;

    private Integer blockWeight;

    public Integer getBlockWeight() {
        return blockWeight;
    }

    public void setBlockWeight(Integer blockWeight) {
        this.blockWeight = blockWeight;
    }

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
                ", blockWeight=" + blockWeight +
                '}';
    }
}
