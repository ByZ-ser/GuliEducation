package com.zhang.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhang.eduservice.entity.vo.CourseInfoVo;
import com.zhang.eduservice.entity.vo.CourseQueryFrontVo;
import com.zhang.eduservice.entity.vo.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-09-30
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoById(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    void deleteCourseById(String courseId);

    List<EduCourse> getCourseByTeacherId(String teacherId);

    Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryFrontVo courseQuery);

    /**
     * 获取课程信息
     * @param id
     * @return
     */
    CourseWebVo selectInfoWebById(String id);

    /**
     * 更新课程浏览数
     * @param id
     */
    void updatePageViewCount(String id);
}
