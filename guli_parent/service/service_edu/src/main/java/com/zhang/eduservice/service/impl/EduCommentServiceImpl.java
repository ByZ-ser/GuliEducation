package com.zhang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.eduservice.entity.EduComment;
import com.zhang.eduservice.mapper.EduCommentMapper;
import com.zhang.eduservice.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-10-17
 */
@Service
public class EduCommentServiceImpl extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    /**
     * 获取某个课程所有评论
     * @param courseId
     * @return
     */
  /*  @Override
    public List<EduComment> getCommentByCourseId(String courseId) {
        List<EduComment> eduComments = this.list(new QueryWrapper<EduComment>().eq("course_id", courseId));
        return eduComments;
    }*/
}
