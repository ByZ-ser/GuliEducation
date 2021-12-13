package com.zhang.educms.controller;


import com.zhang.commonutils.R;
import com.zhang.educms.entity.CrmBanner;
import com.zhang.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * 前台显示：用户端显示
 * </p>
 *
 * @author testjava
 * @since 2021-10-09
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;


    @GetMapping("/getAllbanner")
    public R getAll(){
        List<CrmBanner> list=bannerService.selectAll();
         return R.ok().data("list",list);
    }
}

