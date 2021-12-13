package com.zhang.eduacl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.commonutils.R;
import com.zhang.eduacl.entity.StatisticsDaily;
import com.zhang.eduacl.mapper.StatisticsDailyMapper;
import com.zhang.eduacl.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-21
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public void getCounts(String day) {

        R object = restTemplate.getForObject("http://localhost:8006/ucenterservice/ucenter-member/getRegister/" + day, R.class);
        Integer count =(Integer) object.getData().get("count");
        //如果表中已经有该天的数据则先删除旧的再插入新的
        baseMapper.delete(new QueryWrapper<StatisticsDaily>().eq("date_calculated",day));

        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(count);
        statisticsDaily.setDateCalculated(day);
        statisticsDaily.setLoginNum(44);
        statisticsDaily.setVideoViewNum(11);
        statisticsDaily.setCourseNum(99);
        baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map<String, Object> getChartInfo(String type, String begin, String end) {

        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.select(type, "date_calculated");
        dayQueryWrapper.between("date_calculated", begin, end);

        List<StatisticsDaily> dayList = baseMapper.selectList(dayQueryWrapper);

        Map<String, Object> map = new HashMap<>();
        List<Integer> dataList = new ArrayList<Integer>();
        List<String> dateList = new ArrayList<String>();
        map.put("dataList", dataList);
        map.put("dateList", dateList);


        for (int i = 0; i < dayList.size(); i++) {
            StatisticsDaily daily = dayList.get(i);
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        return map;
    }
}
