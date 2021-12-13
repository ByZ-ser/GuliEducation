package com.zhang.eduservice.mapper;

import com.zhang.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhang.eduservice.entity.vo.CoursePublishVo;
import com.zhang.eduservice.entity.vo.CourseWebVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2021-09-30
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getCoursePublishByCourseId(String id);

    CourseWebVo selectInfoWebById(String courseId);
}
