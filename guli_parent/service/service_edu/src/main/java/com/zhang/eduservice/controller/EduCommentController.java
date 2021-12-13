package com.zhang.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.commonutils.R;
import com.zhang.eduservice.client.GetMemberInfoClient;
import com.zhang.eduservice.entity.EduComment;
import com.zhang.eduservice.entity.vo.UcenterMember;
import com.zhang.eduservice.service.EduCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-10-17
 */
@RestController
@RequestMapping("/eduservice/edu-comment")
@CrossOrigin
@Slf4j
public class EduCommentController {

    public static final String UCENTER = "http://SERVICE-UCENTER/";

    private static final Integer PAGE_SIZE=15;
    @Autowired
    private EduCommentService eduCommentService;


    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private RestTemplate restTemplate;

    @Autowired
    //@Resource
    private GetMemberInfoClient memberInfoClients;

    //根据课程id获取课程的所有评论
    @GetMapping("/getComment/{courseId}/{page}")
    public R getAllComment(@PathVariable("courseId") String courseId,@PathVariable("page") Integer page){

        Page<EduComment> pages = new Page<>(page,PAGE_SIZE);
        IPage<EduComment> ipages = eduCommentService.page(pages, new QueryWrapper<EduComment>().eq("course_id", courseId));
        List<EduComment> comments = ipages.getRecords();
        log.warn(comments.toString());
        //log.warn(usermobile);
        return R.ok().data("comment",comments).data("total",comments.size());
    }

    //发表评论
    @PostMapping("/publishComment")
    public R AddComment(@RequestBody EduComment comment){
        String mobile =(String) redisTemplate.opsForValue().get("usermobile");
        UcenterMember user = restTemplate.getForObject("http://localhost:8006/ucenterservice/ucenter-member/findUser2/" + mobile, UcenterMember.class);
        comment.setNickname(user.getNickname());
        comment.setAvatar(user.getAvatar());
        comment.setMemberId(user.getId());

        eduCommentService.save(comment);

        return R.ok();

    }
}

