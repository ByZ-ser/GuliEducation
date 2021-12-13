import request from '@/utils/request'

export default{
   //添加课程信息
    addCourseInfo(courseInfo){
        return request({
            //url: '/eduservice/edu-teacher/fingTeacherByvo',
            url:`/eduservice/course/addCourse`,
            method: 'post',
            data:courseInfo
          })
    },
    //查询所有的讲师
    getListTeacher(){
        return request({
            //url: '/eduservice/edu-teacher/fingTeacherByvo',
            url:`/eduservice/edu-teacher/findAll`,
            method: 'get'
          })
    },
    //根据课程id查询课程信息
    getCourseById(id){
        return request({
            //url: '/eduservice/edu-teacher/fingTeacherByvo',
            url:`/eduservice/course/getCourseInfo/${id}`,
            method: 'get'
          })
    },
    //修改课程信息
    updateCourse(courseInfoVo){
        return request({
            //url: '/eduservice/edu-teacher/fingTeacherByvo',
            url:`/eduservice/course/updateCourseInfo`,
            method: 'post',
            data:courseInfoVo
          })
    },
    //课程发布信息显示
    getPublishInfo(courseId){
        return request({
            //url: '/eduservice/course/getPublishinfo',
            url:`/eduservice/course/getPublishinfo/${courseId}`,
            method: 'get',
          })
    },
    //课程最终发布
    publishCourse(courseId){
        return request({
            //url: '/eduservice/course/getPublishinfo',
            url:`/eduservice/course/publishCourse/${courseId}`,
            method: 'post',
          })
    },
    //查询所有的课程
    getAllCourse(courseId){
        return request({
            //url: '/eduservice/course/getPublishinfo',
            url:`/eduservice/course/getAllCourse`,
            method: 'get',
          })
    },
     //讲师列表(条件查询分页)
    //pageNumber:当前页
    //teacherQuery:查询条件
    getCourseListPage(pageNumber,courseQuery){
        return request({
            //url: '/eduservice/edu-teacher/fingTeacherByvo',
            url:`/eduservice/course/findCourseByVo/${pageNumber}`,
            method: 'post',
            //teacherQuery条件对象，后端使用RequestBody获取数据
            //data表示把对象转化成json进行传递到接口里面
            data:courseQuery
          })
    },
    //删除课程
    deleteCourse(courseId){
        return request({
            //url: '/eduservice/edu-teacher/fingTeacherByvo',
            url:`/eduservice/course/deleteCourse/${courseId}`,
            method: 'delete'
          })
    }
}

