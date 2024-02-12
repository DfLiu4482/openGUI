package com.defu.opengui.controller;

import com.defu.opengui.entity.ConfigJson;
import com.defu.opengui.entity.ConfigList;
import com.defu.opengui.service.IndexService;
import com.defu.opengui.utils.PathUtils;
import jakarta.annotation.Resource;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private ConfigList configList;

    @GetMapping("/index")
    public String index(ModelMap mmap) {
        System.out.println(configList);
        mmap.put("config", configList.getConfigJsonList().get(0));
        return "index";
    }


    @PostMapping("/uploadConfig")
    @ResponseBody
    public Map<String, Object> uploadConfig(@RequestParam("file") MultipartFile file) throws IOException {

        Map<String, Object> retMap = new HashMap<>();
        if (!file.isEmpty()) {
            String path = PathUtils.getJarPath()+"/upload/";
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

    @GetMapping("download/{fileName}")
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable String fileName) throws IOException {
        org.springframework.core.io.Resource resource = new ClassPathResource("/static/images/"+fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName+"");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @PostMapping("/startCal")
    @ResponseBody
    public Map<String, Object> startCal(@RequestBody Map<String, Object> formData){

        return indexService.execute(formData);

    }

    @GetMapping("/getConfigJson")
    @ResponseBody
    public ConfigJson getConfigJson(){
        return configList.getConfigJsonList().get(0);
    }

}
