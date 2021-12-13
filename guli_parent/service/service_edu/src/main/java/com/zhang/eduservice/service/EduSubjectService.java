package com.zhang.eduservice.service;

import com.zhang.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhang.eduservice.entity.subject.OneSubject;
import com.zhang.eduservice.entity.subject.TwoSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-09-29
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService eduSubjectService);

    List<OneSubject> getAllOneTwoSubject();

    List<EduSubject> getTwoSubject(String oneSubjectid);
}
