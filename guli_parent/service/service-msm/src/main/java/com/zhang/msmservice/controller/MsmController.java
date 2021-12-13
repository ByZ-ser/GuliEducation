package com.zhang.msmservice.controller;

import com.zhang.commonutils.R;
import com.zhang.commonutils.RandomUtil;
import com.zhang.msmservice.service.MsmService;
import com.zhang.msmservice.utils.EmailUtil;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("/api/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    EmailUtil emailUtil;

    /**
     * 发送邮箱验证码
     * @param email
     * @return
     */

    @PostMapping("/sendAuthCodeEmail/{email}")
    public R sendAuthCodeEmail( @PathVariable("email") String email){
        String authCode = emailUtil.getAuthCode();  //生成随机验证码
        redisTemplate.opsForValue().set(email,authCode,60, TimeUnit.SECONDS);  //将验证码存入缓存,60秒失效
        try {
            emailUtil.sendAuthCodeEmail(email,authCode);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
        return R.ok().data("code",authCode);
    }



}
