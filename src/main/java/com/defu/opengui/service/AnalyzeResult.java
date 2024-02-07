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
import java.util.List;

/**
 * @author: dfliu
 * @date: 2024/02/07
 **/
@Service
public class AnalyzeResult {

    @Resource
    private ResourceLoader resourceLoader;

    public List<String> analyze(String path){
        File directory = new File(path);
        List<String> fileNames = new ArrayList<>();
        // 判断是文件还是路径
        if (directory.isDirectory()){
            final File[] files = directory.listFiles();
            // 遍历文件数组
            for (File file : files) {
                fileNames.add(file.getName());
                copyResource(file);
            }
        }else if (directory.isFile()){
            if(FileTypeChecker.isImageFile(directory)){
                fileNames.add(directory.getName());
            }else{
                fileNames.add("file.jpeg");
            }
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
