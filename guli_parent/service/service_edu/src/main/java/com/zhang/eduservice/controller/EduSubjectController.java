package com.zhang.eduservice.controller;


import com.zhang.commonutils.R;
import com.zhang.eduservice.entity.EduSubject;
import com.zhang.eduservice.entity.subject.OneSubject;
import com.zhang.eduservice.entity.subject.TwoSubject;
import com.zhang.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-29
 */
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    //添加课程分类
    //获取上传过来的文件，把文件内容进行读取
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){

        eduSubjectService.saveSubject(file,eduSubjectService);

        return R.ok();
    }

    //课程分类列表(树形结构)
    @GetMapping("/getSubject")
    public R getAllSubject(){

        List<OneSubject> data = eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",data);
    }
    //根据一级分类查询二级分类
    @GetMapping("/getTwoSubject/{oneSubjectid}")
    public R getTwoSubject(@PathVariable("oneSubjectid")  String oneSubjectid){

        List<EduSubject> twoSubjects=eduSubjectService.getTwoSubject(oneSubjectid);
        return R.ok().data("twosubject",twoSubjects);

    }
}

