package com.zhang.eduservice.client;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zhang.commonutils.R;
import com.zhang.eduservice.client.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-vod",fallback = VodClientImpl.class)
@Component
public interface VodClient {
    //删除视频
    @DeleteMapping("/eduvod/video/deleteVideo/{videoId}")
    public R deleteVideo(@PathVariable("videoId") String videoId);

}
