package com.zhang.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.commonutils.R;
import com.zhang.eduservice.entity.EduCourse;
import com.zhang.eduservice.entity.EduTeacher;
import com.zhang.eduservice.entity.chapter.ChapterVo;
import com.zhang.eduservice.entity.vo.*;
import com.zhang.eduservice.mapper.EduCourseMapper;
import com.zhang.eduservice.service.EduChapterService;
import com.zhang.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-30
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
@Slf4j
public class EduCourseController {
    private static final Integer PAGESIZE = 4;
    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduCourseMapper eduCourseMapper;

    @Autowired
    private EduChapterService chapterService;

    //添加课程的基本信息
    @PostMapping("/addCourse")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo){
        String id = eduCourseService.addCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseinfo(@PathVariable("courseId") String courseId){

        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfoById(courseId);
        return R.ok().data("courseInfo",courseInfoVo);
    }


    @PostMapping("/updateCourseInfo")
    public R updateCourse(@RequestBody CourseInfoVo courseInfoVo){

            eduCourseService.updateCourseInfo(courseInfoVo);
            return R.ok();
    }

    @GetMapping("/getPublishinfo/{courseId}")
    public R getPublishInfo(@PathVariable("courseId") String courseId){
        CoursePublishVo coursePublishByCourseId = eduCourseMapper.getCoursePublishByCourseId(courseId);
        return R.ok().data("publishinfo",coursePublishByCourseId);
    }

    //最终发布课程
    @PostMapping("/publishCourse/{id}")
    public R publishCourse(@PathVariable("id") String id){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    //查询所有的课程列表
    @GetMapping("/getAllCourse")
    public R getAll(){
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list",list);
    }

    //条件查询课程列表
    @PostMapping("/findCourseByVo/{pageNumber}")
    public R findCourseByVo(@PathVariable("pageNumber") Integer pageNumber,
                            @RequestBody(required = false) CourseQuery courseQuery)
    {
        Page<EduCourse> objects = new Page<>(pageNumber,PAGESIZE);
        QueryWrapper<EduCourse> objectQueryWrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(courseQuery.getName()))
        {
            objectQueryWrapper.like("title",courseQuery.getName());
        }

        if(!StringUtils.isEmpty(courseQuery.getBegin())){
            objectQueryWrapper.ge("gmt_create",courseQuery.getBegin());
        }
        if(!StringUtils.isEmpty(courseQuery.getEnd())){
            objectQueryWrapper.le("gmt_create",courseQuery.getEnd());
        }
        objectQueryWrapper.orderByDesc("gmt_create");
        eduCourseService.page(objects,objectQueryWrapper);
        List<EduCourse> records = objects.getRecords();
        long total = objects.getTotal();
        return R.ok().data("volist",records).data("total",total);
    }

    //根据页码查询
    @GetMapping("/findBypagenum/{pageNum}")
    public R getInfoByPage(@PathVariable("pageNum") Integer pageNum){
        List<EduCourse> list = eduCourseService.list(null);
        if(pageNum>(list.size()/PAGESIZE+1))
        {
            return R.error();
        }
        Page<EduCourse> page = new Page<>(pageNum,PAGESIZE);

        eduCourseService.page(page,null);

        long total=page.getTotal();  //总记录数

        List<EduCourse> records = page.getRecords();
        return R.ok().data("currentPageLists",records).data("total",total);

    }

    //删除课程
    @DeleteMapping("/deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable("courseId") String courseId){

        eduCourseService.deleteCourseById(courseId);
        return R.ok();

    }
    //通过讲师id获取该讲师所讲授的所有课程
    @GetMapping("/getAllCourseByTeacher/{teacherId}")
    public R getCourseByTeacher(@PathVariable("teacherId") String teacherId){
        List<EduCourse> courses=eduCourseService.getCourseByTeacherId(teacherId);
        return R.ok().data("teachercourses",courses).data("total",courses.size());
    }

    @ApiOperation(value = "分页课程列表")
    @PostMapping(value = "{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseQueryFrontVo courseQuery){
        Page<EduCourse> pageParam = new Page<EduCourse>(page, limit);
        Map<String, Object> map = eduCourseService.pageListWeb(pageParam, courseQuery);
        List<EduCourse> courses = eduCourseService.list(null);
        return  R.ok().data(map).data("total",courses.size());
    }

    //查询一级分类下的所有课程
    @GetMapping("/getCourseInfoOne/{onesubject}")
    public R getCourseOneSubjetcId(@PathVariable("onesubject") String onesubject){
        List<EduCourse> eduCourses = eduCourseService.list(new QueryWrapper<EduCourse>().eq("subject_parent_id", onesubject));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", eduCourses);
        return R.ok().data(map).data("total",eduCourses.size());
    }

    //查询二级分类下的所有课程
    @GetMapping("/getCourseInfoTwo/{twosubject}")
    public R getCourseTwoSubjetcId(@PathVariable("twosubject") String twosubject){
        List<EduCourse> eduCourses = eduCourseService.list(new QueryWrapper<EduCourse>().eq("subject_id", twosubject));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", eduCourses);
        return R.ok().data(map).data("total",eduCourses.size());
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "{courseId}")
    public R getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){

        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = eduCourseService.selectInfoWebById(courseId);

        log.warn(courseWebVo.toString());
        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courseId);

        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList);
    }


    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "/getInfo/{courseId}")
    public CourseWebVo getById2(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId){
        log.warn("课程id:"+courseId);
        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = eduCourseService.selectInfoWebById(courseId);

        log.warn(courseWebVo.toString());
        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoByCourseId(courseId);

        return courseWebVo;
    }
}

