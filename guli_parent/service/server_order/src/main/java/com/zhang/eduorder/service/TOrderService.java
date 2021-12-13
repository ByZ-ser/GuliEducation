package com.zhang.eduorder.service;

import com.zhang.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-10-17
 */
public interface TOrderService extends IService<TOrder> {

    String saveOrder(String mobile,String courseId);
}
