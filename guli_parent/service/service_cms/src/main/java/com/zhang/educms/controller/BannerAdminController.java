package com.zhang.educms.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.commonutils.R;
import com.zhang.educms.entity.CrmBanner;
import com.zhang.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * 管理管管理banner的接口
 * </p>
 *
 * @author testjava
 * @since 2021-10-09
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService crmBannerService;

    //分页查询banner
    @GetMapping("/pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable("page") long page, @PathVariable("limit") long limit){
        Page<CrmBanner> page1 = new Page<>(page,limit);
        crmBannerService.page(page1, null);
        return R.ok().data("items",page1.getRecords()).data("total",page1.getTotal());
    }

    //添加banner
    @PostMapping("/addbanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        crmBannerService.save(crmBanner);
        return R.ok();
    }

    //修改
    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        crmBannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        crmBannerService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = crmBannerService.getById(id);
        return R.ok().data("item", banner);
    }

}

