package com.defu.opengui.service;

import com.defu.opengui.entity.ConfigResult;
import com.defu.opengui.utils.FileTypeChecker;
import com.defu.opengui.utils.PathUtils;
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

    public List<Map<String, String>> analyze(ConfigResult result){
        final long num = System.currentTimeMillis();
        File directory = new File(result.getPath());
        List<Map<String, String>> fileNames = new ArrayList<>();
        // 判断是文件还是路径
        if (directory.isDirectory()){
            final File[] files = directory.listFiles();
            // 遍历文件数组
            for (File file : files) {
                extracted(num, file, fileNames, result);
            }
        }else if (directory.isFile()){
            extracted(num, directory, fileNames, result);
        }
        return fileNames;
    }

    private void extracted(long num, File file, List<Map<String, String>> fileNames, ConfigResult result) {
        Map map = new HashMap();
        if(FileTypeChecker.isImageFile(file)){
            map.put("name", num + file.getName());
            copyResource(file, num);
        }else{
            map.put("name", "/images/file.jpeg");
        }
        map.put("src", file.getAbsolutePath());
        map.put("blockWeight", result.getBlockWeight());
        map.put("blockHeight", result.getBlockHeight());
        fileNames.add(map);
    }


    private void copyResource(File file, Long num){
        Path sourcePath =  Paths.get(file.getAbsolutePath());
        File path =new File(PathUtils.getJarPath()+"/temp");
        if (!path.isDirectory()){
            path.mkdirs();
        }
        // 构建目标文件路径
        try {
            Path targetPath = Paths.get(PathUtils.getJarPath()+"/temp", num+file.getName());
            // 拷贝文件到目标目录
            Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Fetch result exception！");
        }
    }
}
