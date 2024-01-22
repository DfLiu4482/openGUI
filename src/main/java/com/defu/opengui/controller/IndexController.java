package com.defu.opengui.controller;

import com.defu.opengui.entity.ConfigJson;
import com.defu.opengui.service.IndexService;
import com.defu.opengui.utils.ReturnMsgUtil;
import jakarta.annotation.Resource;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: dfliu
 * @date: 2024/01/09
 **/
@Controller
public class IndexController {

    @Resource
    private IndexService indexService;

    @Resource
    private ConfigJson configJson;

    @GetMapping("/index")
    public String index(ModelMap mmap) {
        System.out.println(configJson);
        mmap.put("config", configJson);
        return "index";
    }


    @PostMapping("/uploadConfig")
    @ResponseBody
    public Map<String, Object> uploadConfig(@RequestParam("file") MultipartFile file) throws IOException {

        Map<String, Object> retMap = new HashMap<>();
        if (!file.isEmpty()) {
            ApplicationHome home = new ApplicationHome(getClass());
            File jarFile = home.getSource();
            String path = jarFile.getParentFile().getPath()+"/upload/";
            File dir = new File(path);
            if (!dir.exists()) dir.mkdirs();

            file.transferTo(new File(path+file.getOriginalFilename()));
            // store the bytes somewhere
            retMap.put("code", 200);
            retMap.put("path", path + file.getOriginalFilename());
            return retMap;
        }
        retMap.put("code", 500);
        return retMap;
    }

    @PostMapping("/startCal")
    @ResponseBody
    public Map<String, Object> startCal(@RequestBody Map<String, Object> formData){

        return indexService.execute(formData);

    }

    @GetMapping("/getConfigJson")
    @ResponseBody
    public ConfigJson getConfigJson(){
        return configJson;
    }

}
