package com.pg.backend.controller;

import com.pg.backend.common.Result;
import com.pg.backend.constant.FilePath;
import com.pg.backend.constant.FileSize;
import com.pg.backend.constant.FileType;
import com.pg.backend.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/oss")
public class Oss {
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result upload(MultipartFile multipartFile) throws IOException {
        if(FileSize.LV1 <multipartFile.getSize()){
            return Result.error("file is too lage");
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        if(FileType.IMG != ext){
            return Result.error("file type not satisfied");
        }
        String objectName = new StringBuilder()
                .append(FilePath.PATH1)
                .append(UUID.randomUUID())
                .append(ext)
                .toString();
        //使用aliOssUtils上云可能更好
        String filePath = aliOssUtil.upload(multipartFile.getBytes(), objectName);
        return Result.success(filePath);
    }
}
