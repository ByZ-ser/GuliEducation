package com.zhang.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.commonutils.R;
import com.zhang.eduorder.entity.TOrder;
import com.zhang.eduorder.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-17
 */
@RestController
@RequestMapping("/eduorder/t-order")
@CrossOrigin
public class TOrderController {


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TOrderService orderService;
    //创建订单
    @PostMapping("/createdorder/{courseId}")
    public R create(@PathVariable("courseId") String courseId){
        //获取用户手机号码
        String mobile =(String) redisTemplate.opsForValue().get("usermobile");
        //创建订单
        String orderno = orderService.saveOrder(mobile, courseId);
        return R.ok().data("orderno",orderno);
    }

    /**
     * 根据订单编号查询订单
     * @param orderno
     * @return
     */
    @GetMapping("/getOrder/{orderno}")
    public R getOrder(@PathVariable("orderno") String orderno){
        TOrder orderNo = orderService.getOne(new QueryWrapper<TOrder>().eq("order_no", orderno));
        return R.ok().data("order",orderNo);
    }

}

