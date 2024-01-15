package com.defu.opengui;

import com.defu.opengui.entity.ConfigJson;
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
    public ConfigJson configJson() throws IOException {
        ReadSourceService readSourceService  = new ReadSourceService();
        final ConfigJson configJson = readSourceService.readConfig();
        return configJson;
    }
}
