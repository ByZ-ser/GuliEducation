package com.zhang.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhang.commonutils.R;
import com.zhang.eduservice.entity.EduTeacher;
import com.zhang.eduservice.entity.vo.TeacherQuery;
import com.zhang.eduservice.service.EduTeacherService;
import com.zhang.servicebase.exceptionhandler.GuliException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-22
 */
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin
public class EduTeacherController {

    private static final Integer PAGESIZE = 4;
    @Resource
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有老师的信息
     * @return
     */
    @GetMapping("/findAll")
    public R findAllTeacher()
    {
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    /**
     * 根据id删除讲师
     * @param id
     * @return
     */
    @DeleteMapping("/deleteTeacher/{id}")
    public R removeTeacher(@PathVariable("id") String id)
    {
        boolean b = eduTeacherService.removeById(id);

        if(b)
        {
            return R.ok();
        }else {
            return R.error();
        }
    }

    /**
     * 根据页码查询数据
     * @param pageNumber
     * @return
     */
    @GetMapping("/findListByPage/{pageNumber}")
    public R getPageList(@PathVariable("pageNumber") Integer pageNumber)
    {
        List<EduTeacher> list = eduTeacherService.list(null);
        if(pageNumber>(list.size()/PAGESIZE+1))
        {
            return R.error();
        }
        Page<EduTeacher> page = new Page<>(pageNumber,PAGESIZE);

        eduTeacherService.page(page,null);

        long total=page.getTotal();  //总记录数

        List<EduTeacher> records = page.getRecords();
        return R.ok().data("currentPageLists",records).data("total",total);
    }

    /**
     * 条件查询
     * @param pageNumber
     * @param teacherQuery
     * @return
     */
    @PostMapping("/fingTeacherByvo/{pageNumber}")
    public R pageTeacherCondition(@PathVariable("pageNumber") Integer pageNumber,
                                  @RequestBody(required = false) TeacherQuery teacherQuery)
    {
        Page<EduTeacher> page=new Page<>(pageNumber,PAGESIZE);
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(teacherQuery.getName()))
        {
            wrapper.like("name",teacherQuery.getName());
        }
        if(teacherQuery.getLevel()!=null)
        {
            wrapper.eq("level",teacherQuery.getLevel());
        }
        if(!StringUtils.isEmpty(teacherQuery.getBegin()))
        {
            wrapper.ge("gmt_create",teacherQuery.getBegin());
        }
        if(!StringUtils.isEmpty(teacherQuery.getEnd()))
        {
            wrapper.le("gmt_create",teacherQuery.getEnd());
        }
        wrapper.orderByDesc("gmt_create");
        eduTeacherService.page(page,wrapper);
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
        return R.ok().data("rows",records).data("total",total);
    }

    /**
     * 添加讲师
     * @param eduTeacher
     * @return
     */
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher)
    {
        boolean save = eduTeacherService.save(eduTeacher);
        if(!save)
        {
            return R.error();
        }
        return R.ok();
    }

    /**
     * 根据id查询讲师
     * @param id
     * @return
     */
    @GetMapping("/getTeacherById/{id}")
    public R getTeacherById(@PathVariable("id") String id)
    {
        EduTeacher byId = eduTeacherService.getById(id);
        return R.ok().data("teacher",byId);
    }

    /**
     * 修改讲师
     * @param eduTeacher
     * @return
     */
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher)
    {
        boolean b = eduTeacherService.updateById(eduTeacher);
        if(!b)
        {
            return R.error();
        }
        return R.ok();
    }

}

