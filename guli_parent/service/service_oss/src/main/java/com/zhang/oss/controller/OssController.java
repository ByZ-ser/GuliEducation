package com.zhang.oss.controller;


import com.zhang.commonutils.R;
import com.zhang.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;
    //上传头像的方法
    @PostMapping("/upload")
    public R uploadOssFile(MultipartFile file){
        //获取上传文件
        //返回头像的url路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
