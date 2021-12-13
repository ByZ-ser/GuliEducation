package com.zhang.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.eduservice.entity.EduSubject;
import com.zhang.eduservice.entity.excel.SubjectData;
import com.zhang.eduservice.entity.subject.OneSubject;
import com.zhang.eduservice.entity.subject.TwoSubject;
import com.zhang.eduservice.listen.MyExcelListen;
import com.zhang.eduservice.mapper.EduSubjectMapper;
import com.zhang.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-09-29
 */
@Service
@Slf4j
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    private EduSubjectMapper eduSubjectMapper;

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new MyExcelListen(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获取课程分类的数据
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        List<OneSubject> list=new ArrayList<>();
        //查询所有的一级分类
        QueryWrapper<EduSubject> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("parent_id","0");
        EduSubjectService eduSubjectService = new EduSubjectServiceImpl();
        List<EduSubject> onesubject = eduSubjectMapper.selectList(objectQueryWrapper);
        //遍历所有的一级分类并查找每一个一级分类的二级分类
        for (EduSubject subject : onesubject) {

            OneSubject oneSubject=new OneSubject();
            oneSubject.setId(subject.getId());
            oneSubject.setTitle(subject.getTitle());

            //获取一级分类的所有二级分类
            List<TwoSubject> twoSubjects=new ArrayList<>();
            String id = oneSubject.getId();
            QueryWrapper<EduSubject> childrensubject = new QueryWrapper<>();
            childrensubject.eq("parent_id",id);
            List<EduSubject> child = eduSubjectMapper.selectList(childrensubject);//该一级分类下的所有二级分类


            //遍历查询出来的二级分类并封装
            for (EduSubject eduSubject : child) {

                TwoSubject twoSubject=new TwoSubject();
                twoSubject.setId(eduSubject.getId());
                twoSubject.setTitle(eduSubject.getTitle());
                //封装
                twoSubjects.add(twoSubject);
            }

            //封装一级分类
            oneSubject.setChildren(twoSubjects);
            //封装所有数据
            list.add(oneSubject);
        }

        return list;
    }

    /**
     * 根据一级分类id查询二级分类
     * @param oneSubjectid
     * @return
     */
    @Override
    public List<EduSubject> getTwoSubject(String oneSubjectid) {
        List<EduSubject> subjects = eduSubjectMapper.selectList(new QueryWrapper<EduSubject>().eq("parent_id", oneSubjectid));

        return subjects;
    }


}
