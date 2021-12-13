import request from '@/utils/request'

export default{
   //根据课程id获取课程列表
    getCourseList(courseId){
        return request({
            //url: '/eduservice/edu-teacher/fingTeacherByvo',
            url:`/eduservice/edu-chapter/getChapterVIdeo/${courseId}`,
            method: 'get'
          })
    },
    //添加章节
    addChapter(eduChapter){
        return request({
            //url: '/eduservice/edu-teacher/fingTeacherByvo',
            url:`/eduservice/edu-chapter/addchapter`,
            method: 'post',
            data:eduChapter
          })
    },
    //查询章节
    getChapterById(chapterId){
        return request({
            //url: '/eduservice/edu-teacher/fingTeacherByvo',
            url:`/eduservice/edu-chapter/getChapterInfo/${chapterId}`,
            method: 'get',
          })
    },
    //修改章节
    updateChapter(eduChapter){
        return request({
            //url: '/eduservice/edu-teacher/fingTeacherByvo',
            url:`/eduservice/edu-chapter/updateChapter`,
            method: 'post',
            data:eduChapter
          })
    },
    //删除章节
    deleteChapter(chapterId){
        return request({
            //url: '/eduservice/edu-teacher/fingTeacherByvo',
            url:`/eduservice/edu-chapter/deletechapter/${chapterId}`,
            method: 'delete',
          })
    }

}

