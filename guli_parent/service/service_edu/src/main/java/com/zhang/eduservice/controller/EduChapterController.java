package com.zhang.eduservice.controller;


import com.zhang.commonutils.R;
import com.zhang.eduservice.entity.EduChapter;
import com.zhang.eduservice.entity.chapter.ChapterVo;
import com.zhang.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-30
 */
@RestController
@RequestMapping("/eduservice/edu-chapter")
@CrossOrigin
public class EduChapterController {
    @Autowired
    private EduChapterService eduChapterService;

    //返回课程大纲列表
    @GetMapping("/getChapterVIdeo/{courseId}")
    public R getChapterVideo(@PathVariable("courseId") String courseId){
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allVideo",list);
    }

    //添加章节
    @PostMapping("/addchapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

   //查询章节
    @GetMapping("/getChapterInfo/{chaptreId}")
    public R getChapterInfo(@PathVariable("chaptreId") String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapterinfo",eduChapter);
    }

    //修改章节
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    //删除章节
    @DeleteMapping("/deletechapter/{chapterId}")
    public R deleteChapter(@PathVariable("chapterId") String chapterId){
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}

