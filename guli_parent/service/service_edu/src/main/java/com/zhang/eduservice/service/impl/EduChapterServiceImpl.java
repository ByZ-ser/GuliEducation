package com.zhang.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhang.eduservice.entity.EduChapter;
import com.zhang.eduservice.entity.EduVideo;
import com.zhang.eduservice.entity.chapter.ChapterVo;
import com.zhang.eduservice.entity.chapter.VideoVo;
import com.zhang.eduservice.mapper.EduChapterMapper;
import com.zhang.eduservice.mapper.EduVideoMapper;
import com.zhang.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhang.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-09-30
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduChapterMapper eduChapterMapper;

    @Autowired
    private EduVideoMapper eduVideoMapper;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {


        List<ChapterVo> list = new ArrayList<>();
        //根据课程id获取该课程的所有章节
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = eduChapterMapper.selectList(queryWrapper);

        //遍历所有的章节
        for (EduChapter eduChapter : eduChapters) {
            //封装章节
            ChapterVo chapterVo = new ChapterVo();
            chapterVo.setId(eduChapter.getId());
            chapterVo.setTitle(eduChapter.getTitle());

            List<VideoVo> videoVoList =new ArrayList<>();

            //根据章节id查询该章节下的所有小节
            QueryWrapper<EduVideo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("chapter_id",eduChapter.getId());
            List<EduVideo> eduVideos = eduVideoMapper.selectList(queryWrapper1);

            //遍历小节，封装成vo
            for (EduVideo eduVideo : eduVideos) {
                VideoVo videoVo = new VideoVo();
                videoVo.setId(eduVideo.getId());
                videoVo.setTitle(eduVideo.getTitle());
                videoVo.setVideoSourceId(eduVideo.getVideoSourceId());
                videoVoList.add(videoVo);
            }

            chapterVo.setChildren(videoVoList);
            list.add(chapterVo);
        }
        return list;

    }

    //删除章节
    @Override
    public boolean deleteChapter(String chapterId) {
        //查询该章节下是否有小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        List<EduVideo> eduVideos = eduVideoMapper.selectList(wrapper);
        if(eduVideos.isEmpty()){//如果查询结果为空则表示该章节下没有小节
            int byId = baseMapper.deleteById(chapterId);//删除章节
            return byId>0;
        }else {
            throw new GuliException(200001, "抱歉，请先删除所有小节！");
        }
    }
}
