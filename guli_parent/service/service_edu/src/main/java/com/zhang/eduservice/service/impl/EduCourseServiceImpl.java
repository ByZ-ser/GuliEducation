package com.zhang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.commonutils.R;
import com.zhang.eduservice.client.VodClient;
import com.zhang.eduservice.entity.EduChapter;
import com.zhang.eduservice.entity.EduCourse;
import com.zhang.eduservice.entity.EduCourseDescription;
import com.zhang.eduservice.entity.EduVideo;
import com.zhang.eduservice.entity.vo.CourseInfoVo;
import com.zhang.eduservice.entity.vo.CourseQueryFrontVo;
import com.zhang.eduservice.entity.vo.CourseWebVo;
import com.zhang.eduservice.mapper.EduCourseMapper;
import com.zhang.eduservice.service.EduChapterService;
import com.zhang.eduservice.service.EduCourseDescriptionService;
import com.zhang.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhang.eduservice.service.EduVideoService;
import com.zhang.servicebase.exceptionhandler.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-09-30
 */
@Service
@Slf4j
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduCourseMapper eduCourseMapper;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduVideoService eduVideoService;

  /*  @Resource
    private RestTemplate restTemplate;*/

    @Autowired
    private VodClient vodClient;

    public static final String SERVICE_URL = "http://service-vod";
    @Override
    public String addCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表中添加数据
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert <= 0){
            throw new GuliException(20001,"课程添加失败！！");
        }
        String courseId = eduCourse.getId();

        //向课程简介表中添加数据
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseId);
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);

        return courseId;
    }

    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {

        EduCourse eduCourse = eduCourseMapper.selectById(courseId);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);

        CourseInfoVo courseInfoVo = new CourseInfoVo();
        courseInfoVo.setCover(eduCourse.getCover());
        courseInfoVo.setDescription(eduCourseDescription.getDescription());
        courseInfoVo.setId(eduCourse.getId());
        courseInfoVo.setPrice(eduCourse.getPrice());
        courseInfoVo.setLessonNum(eduCourse.getLessonNum());
        courseInfoVo.setSubjectId(eduCourse.getSubjectId());
        courseInfoVo.setTitle(eduCourse.getTitle());
        courseInfoVo.setSubjectParentId(eduCourse.getSubjectParentId());
        courseInfoVo.setTeacherId(eduCourse.getTeacherId());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if(i==0){
            throw new GuliException(20001,"修改课程信息失败");
        }


        //修改简介表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    /**
     * 删除课程
     * 删除课程本身的同时，还需要删除课程的章节、小节、视频、描述等信息
     * @param courseId
     */
    @Override
    public void deleteCourseById(String courseId) {
        //根据课程id删除课程本身
        eduCourseMapper.deleteById(courseId);
        //根据课程id删除该课程的描述信息
        eduCourseDescriptionService.removeById(courseId);
        //根据课程id查询该课程下的所有章节
        List<EduChapter> chapters = eduChapterService.list(new QueryWrapper<EduChapter>().eq("course_id", courseId));
        //遍历所有的章节
        for (EduChapter chapter : chapters) {
            //根据章节id查询该章节下的所有小节
            List<EduVideo> videos = eduVideoService.list(new QueryWrapper<EduVideo>().eq("chapter_id", chapter.getId()));
            for (EduVideo video : videos) {
                //遍历删除该章节下的所有小节
                eduVideoService.removeById(video.getId());
                //删除小节的同时还需要删除视频资源
                log.warn(video.getVideoSourceId());
                 vodClient.deleteVideo(video.getVideoSourceId());
            }
            //删除该章节
            eduChapterService.removeById(chapter.getId());
        }

    }

    /**
     * 获取讲师讲授的所有课程信息
     * @param teacherId
     * @return
     */
    @Override
    public List<EduCourse> getCourseByTeacherId(String teacherId) {
        List<EduCourse> courses = eduCourseMapper.selectList(new QueryWrapper<EduCourse>().eq("teacher_id", teacherId));
        return courses;
    }


    @Override
    public Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryFrontVo courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQuery.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseQuery.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseQuery.getSubjectId())) {
            queryWrapper.eq("subject_id", courseQuery.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseQuery.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseQuery.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseQuery.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam, queryWrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo selectInfoWebById(String id) {
        this.updatePageViewCount(id);
        return baseMapper.selectInfoWebById(id);
    }

    @Override
    public void updatePageViewCount(String id) {
        EduCourse course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }
}
