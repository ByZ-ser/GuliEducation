package com.zhang.eduservice.controller;


import com.zhang.commonutils.R;
import com.zhang.eduservice.client.VodClient;
import com.zhang.eduservice.entity.EduVideo;
import com.zhang.eduservice.service.EduVideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-30
 */
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
@Slf4j
public class EduVideoController {


    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("/addvideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    @DeleteMapping("/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String id){
        //根据小节id查询视频id
        EduVideo eduVideo = eduVideoService.getById(id);

        //先删除视频
        if(!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
            vodClient.deleteVideo(eduVideo.getVideoSourceId());
        }
        eduVideoService.removeById(id);
        return R.ok();
    }

    //查询小节
    @GetMapping("/getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable("videoId") String videoId){
        EduVideo byId = eduVideoService.getById(videoId);
        return R.ok().data("videoInfo",byId);
    }

    //修改小节
    @GetMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

}

