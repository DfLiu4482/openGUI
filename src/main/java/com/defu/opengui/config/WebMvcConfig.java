package com.defu.opengui.config;

import com.defu.opengui.utils.PathUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: dfliu
 * @date: 2024/02/21
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String tempPath = PathUtils.getJarPath();
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/","file:"+tempPath+"/temp/","file:"+tempPath+"/data/logo.png","file:"+tempPath+"/data/file.jpeg");
    }
}
