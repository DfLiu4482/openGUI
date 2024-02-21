package com.defu.opengui.config;

import com.defu.opengui.entity.ConfigList;
import com.defu.opengui.service.ReadSourceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author: dfliu
 * @date: 2024/02/21
 **/
@Configuration
public class AppConfig {
    @Bean
    public ConfigList configList() throws IOException {
        ReadSourceService readSourceService  = new ReadSourceService();
        final ConfigList configList = readSourceService.readConfig();
        return configList;
    }
}
