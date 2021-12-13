package com.zhang.eduservice.controller;

import com.zhang.commonutils.R;
import com.zhang.eduservice.entity.EduUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/usr")
@CrossOrigin  //解决跨域
@Slf4j
public class EduLoginController {

    @PostMapping("/login")
    public R login(@RequestBody EduUser user)
    {
        log.warn("0000000000000");
       if(user.getUsername().equals("15197944641") && user.getPassword().equals("123456")){
           return R.ok().data("token",user.getUsername());
       }else {
           return R.error();
       }


    }

    @GetMapping("/info")
    public R info()
    {
        return R.ok().data("roles","teacher").data("name","zhangsan").data("avatar","c://user/123.jpg");
    }

    @PostMapping("/logout")
    public R louOut(@RequestBody String username){
        log.warn(username);
        return R.ok();
    }

}
