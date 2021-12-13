package com.zhang.eduservice.listen;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.eduservice.entity.EduSubject;
import com.zhang.eduservice.entity.excel.SubjectData;
import com.zhang.eduservice.service.EduSubjectService;
import com.zhang.servicebase.exceptionhandler.GuliException;

public class MyExcelListen extends AnalysisEventListener<SubjectData> {

    //因为MyExcelListen不能交给SpringBoot管理，需要自己new，不能注入其他对象

    public EduSubjectService eduSubjectService;

    public MyExcelListen() {
    }

    public MyExcelListen(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    //读取excel的内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new GuliException(20001,"文件数据为空");
        }
        //一行一行读取,一行中第一个值为一级分类，第二个值为二级分类(比如：前端---》vue)
        //判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        if(existOneSubject==null){
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(existOneSubject);
        }
        //判断二级分类是否重复
        String pid = existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(eduSubjectService, subjectData.getTwoSubjectName(),pid);
        if(existTwoSubject==null){
            existTwoSubject=new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(existTwoSubject);
        }
    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id","0");
        EduSubject one = eduSubjectService.getOne(queryWrapper);
        return one;
    }

    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id",pid);
        EduSubject two = eduSubjectService.getOne(queryWrapper);
        return two;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
