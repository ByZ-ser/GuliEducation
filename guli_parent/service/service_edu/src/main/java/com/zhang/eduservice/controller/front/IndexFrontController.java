package com.zhang.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.commonutils.R;
import com.zhang.eduservice.entity.EduCourse;
import com.zhang.eduservice.entity.EduTeacher;
import com.zhang.eduservice.service.EduCourseService;
import com.zhang.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService eduCourseService;

    @Autowired
    private EduTeacherService eduTeacherService;

    //查询课程表中的前8个课程和讲师表中的前四条讲师用于首页的显示
    @GetMapping("/index")
    public R index(){
        //查询前8个课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduCourses = eduCourseService.list(wrapper);
        //查询前四个讲师
        QueryWrapper<EduTeacher> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByDesc("id");
        wrapper1.last("limit 4");
        List<EduTeacher> eduTeachers = eduTeacherService.list(wrapper1);
        return R.ok().data("indexcourse",eduCourses).data("teachers",eduTeachers);
    }

}
