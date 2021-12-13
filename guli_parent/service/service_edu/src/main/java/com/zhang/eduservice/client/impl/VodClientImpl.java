package com.zhang.eduservice.client.impl;

import com.zhang.commonutils.R;
import com.zhang.eduservice.client.VodClient;
import org.springframework.stereotype.Component;

@Component
public class VodClientImpl implements VodClient {
    @Override
    public R deleteVideo(String videoId) {
        return R.error().message("删除视频出错，降级方法");
    }
}
