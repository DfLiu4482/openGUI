package com.defu.opengui.service;

import com.defu.opengui.utils.FileTypeChecker;
import jakarta.annotation.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: dfliu
 * @date: 2024/02/07
 **/
@Service
public class AnalyzeResult {

    @Resource
    private ResourceLoader resourceLoader;

    public List<Map<String, String>> analyze(String path){
        File directory = new File(path);
        List<Map<String, String>> fileNames = new ArrayList<>();
        // 判断是文件还是路径
        if (directory.isDirectory()){
            final File[] files = directory.listFiles();
            // 遍历文件数组
            for (File file : files) {
                Map map = new HashMap();
                map.put("name", file.getName());
                map.put("src", file.getName());
                fileNames.add(map);
                copyResource(file);
            }
        }else if (directory.isFile()){
            Map map = new HashMap();
            if(FileTypeChecker.isImageFile(directory)){
                map.put("name", directory.getName());
            }else{
                map.put("name", "file.jpeg");
            }
            map.put("src", directory.getName());
            fileNames.add(map);
            copyResource(directory);

        }
        return fileNames;
    }

    private void copyResource(File file){
        Path sourcePath =  Paths.get(file.getAbsolutePath());
        // 构建目标文件路径
        try {
            Path targetPath = Paths.get(resourceLoader.getResource("classpath:/static/images").getURL().getPath(), file.getName());
            // 拷贝文件到目标目录
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("获取结果异常！");
        }
    }
}
