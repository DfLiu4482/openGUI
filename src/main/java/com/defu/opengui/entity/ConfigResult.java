package com.defu.opengui.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author: dfliu
 * @date: 2024/02/07
 **/
@Component
public class ConfigResult implements Serializable {

    private String path;

    private Integer blockWeight;

    private Integer blockHeight;

    public Integer getBlockWeight() {
        return blockWeight;
    }

    public void setBlockWeight(Integer blockWeight) {
        this.blockWeight = blockWeight;
    }

    public Integer getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(Integer blockHeight) {
        this.blockHeight = blockHeight;
    }

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
                ", blockWeight=" + blockWeight +
                ", blockHeight=" + blockHeight +
                '}';
    }
}
