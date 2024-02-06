package com.defu.opengui;

import com.defu.opengui.entity.ConfigList;
import com.defu.opengui.service.ReadSourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author: dfliu
 * @date: 2024/01/11
 **/
@Configuration
public class MyConfiguration {

    @Bean
    public ConfigList configList() throws IOException {
        ReadSourceService readSourceService  = new ReadSourceService();
        final ConfigList configList = readSourceService.readConfig();
        return configList;
    }
}
