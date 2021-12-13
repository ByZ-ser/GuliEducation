import request from '@/utils/request'

export default{
   //课程分类
    getSubjectList(){
        return request({
            //url: '/eduservice/edu-teacher/fingTeacherByvo',
            url:`/eduservice/edu-subject/getSubject`,
            method: 'get'
          })
    }
}

