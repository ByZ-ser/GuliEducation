package com.zhang.eduacl.controller;


import com.zhang.commonutils.R;
import com.zhang.eduacl.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-21
 */
@RestController
@RequestMapping("/eduacl/statistics-daily")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @PostMapping("/getCounts/{day}")
    public R getResult(@PathVariable("day") String day){
        statisticsDailyService.getCounts(day);
        return R.ok();
    }

    //获取前台展示数据
    @GetMapping("/getChartInfo/{type}/{begin}/{end}")
    public R getChartsInfo(@PathVariable("type") String type,@PathVariable("begin") String begin,
                            @PathVariable("end") String end){
        Map<String, Object> data= statisticsDailyService.getChartInfo(type,begin,end);
        return R.ok().data(data);
    }
}

