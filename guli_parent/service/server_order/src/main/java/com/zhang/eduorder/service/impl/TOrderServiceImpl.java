package com.zhang.eduorder.service.impl;

import com.zhang.eduorder.entity.TOrder;
import com.zhang.eduorder.entity.vo.CourseWebVo;
import com.zhang.eduorder.entity.vo.UcenterMember;
import com.zhang.eduorder.mapper.TOrderMapper;
import com.zhang.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhang.eduorder.util.OrderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-17
 */
@Service
@Slf4j
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 创建订单
     * @param mobile
     * @param courseId
     */
    @Override
    public String saveOrder(String mobile,String courseId) {
        //根据手机号码获取下单的用户
        UcenterMember user = restTemplate.getForObject("http://localhost:8006/ucenterservice/ucenter-member/findUser2/" + mobile, UcenterMember.class);
        //根据课程id获取用户购买的课程
        CourseWebVo course = restTemplate.getForObject("http://localhost:8001/eduservice/course/getInfo/" + courseId, CourseWebVo.class);
        log.warn(courseId);
        log.warn(course.toString());
        TOrder tOrder = new TOrder();
        tOrder.setOrderNo(OrderUtil.getOrderIdByUUId());
        tOrder.setCourseId(course.getId());
        tOrder.setCourseCover(course.getCover());
        tOrder.setCourseTitle(course.getTitle());
        tOrder.setTeacherName(course.getTeacherName());
        tOrder.setTotalFee(course.getPrice());
        tOrder.setMemberId(user.getId());
        tOrder.setNickname(user.getNickname());
        tOrder.setMobile(user.getMobile());
        tOrder.setStatus(0);
        tOrder.setPayType(1);
        baseMapper.insert(tOrder);
        return tOrder.getOrderNo();
    }
}
