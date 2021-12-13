package com.zhang.eduacl.service;

import com.zhang.eduacl.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-21
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void getCounts(String day);

    Map<String, Object> getChartInfo(String type, String begin, String end);
}
