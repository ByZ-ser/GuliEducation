package com.zhang.eduacl.Schedule;

import com.zhang.eduacl.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * 定时任务
 */
@Component
public class ScheduleTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //每天凌晨1点获取前一天的数据并更新
    @Scheduled(cron = "0 0 1 * * ?") //定时任务，cron:cron表达式
    public void getDataTask(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        statisticsDailyService.getCounts(sdf.format(date));
    }
}
