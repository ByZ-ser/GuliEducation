package com.zhang.vod.controller;

import com.zhang.commonutils.R;
import com.zhang.vod.service.VodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
@Slf4j
public class Vodcontroller {

    @Value("${aliyun.oss.file.accessKeyId}")
    private String keyId;

    @Value("${aliyun.oss.file.accessKeySecret}")
    private String keySecret;
    @Autowired
    private VodService vodService;

    //上传视频
    @PostMapping("/uploadvideo")
    public R uploadVideo(MultipartFile file){


        String videoid=vodService.upload(file);

        return R.ok().data("videoId",videoid);
    }

    //删除视频
    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String videoId){
        vodService.deleteVideo(videoId);
        return R.ok();
    }

    //批量删除视频
    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestParam("videoList") List videoList){

        vodService.deleteVideos(videoList);
        return R.ok();
    }

    //根据视频id获取视频凭证
    @GetMapping("/getAuth/{videoId}")
    public R getVieoAuth(@PathVariable("videoId") String videoId){
        String auth=vodService.getAuth(videoId);
        return R.ok().data("auth",auth);
    }

}
