package com.zhang.ucenterservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.commonutils.R;
import com.zhang.ucenterservice.entity.UcenterMember;
import com.zhang.ucenterservice.entity.vo.LoginVo;
import com.zhang.ucenterservice.entity.vo.RegisterVo;
import com.zhang.ucenterservice.service.UcenterMemberService;
import com.zhang.ucenterservice.service.impl.UcenterMemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-13
 */
@RestController
@RequestMapping("/ucenterservice/ucenter-member")
@CrossOrigin
@Slf4j
public class UcenterMemberController {



    @Autowired
    private UcenterMemberService ucenterMemberService;

    //注册
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return R.ok();
    }

    //登录
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo){
        UcenterMember ucenterMember=ucenterMemberService.login(loginVo);
        if(ucenterMember==null){
            return R.error();
        }
        return R.ok().data("user",ucenterMember);
    }

    //根据手机号查询用户
    @GetMapping("/findUser/{phone}")
    public R getUser(@PathVariable("phone") String phone){
        log.warn(phone);
        //UcenterMember ucenterMember=ucenterMemberService.getUserByPhone(phone);
        UcenterMember memberServiceOne = ucenterMemberService.getOne(new QueryWrapper<UcenterMember>().eq("mobile", phone));

        return R.ok().data("user",memberServiceOne);
    }


    //根据手机号查询用户2
    @GetMapping("/findUser2/{phone}")
    public UcenterMember getUser2(@PathVariable("phone") String phone){
        log.warn(phone);
        //UcenterMember ucenterMember=ucenterMemberService.getUserByPhone(phone);
        UcenterMember memberServiceOne = ucenterMemberService.getOne(new QueryWrapper<UcenterMember>().eq("mobile", phone));
        log.warn(memberServiceOne.toString());
        return memberServiceOne;
    }

    //退出登录
    @GetMapping("/loginout")
    public R lgout(){
        ucenterMemberService.loginout();
        return R.ok();
    }
}

