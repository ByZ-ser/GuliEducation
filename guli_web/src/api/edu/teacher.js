import request from '@/utils/request'

export default{
    //讲师列表(条件查询分页)
    //pageNumber:当前页
    //teacherQuery:查询条件
    getTeacherListPage(pageNumber,teacherQuery){
        return request({
            //url: '/eduservice/edu-teacher/fingTeacherByvo',
            url:`/eduservice/edu-teacher/fingTeacherByvo/${pageNumber}`,
            method: 'post',
            //teacherQuery条件对象，后端使用RequestBody获取数据
            //data表示把对象转化成json进行传递到接口里面
            data:teacherQuery
          })
    },
    deleteTeacherId(id){
      return request({
          //url: '/eduservice/edu-teacher/fingTeacherByvo',
          url:`/eduservice/edu-teacher/deleteTeacher/${id}`,
          method: 'delete'
      })
    },
    saveTeacher(teacher){
      return request({
        url:`/eduservice/edu-teacher/addTeacher`,
        method:'post',
        data:teacher
      })
    },
    getTeacherById(id){
      return request({
        url:`/eduservice/edu-teacher/getTeacherById/${id}`,
        method:'get',
      })
    },
    updateTeacher(teacher){
      return request({
        url:`/eduservice/edu-teacher/updateTeacher`,
        method:'post',
        data:teacher
      })
    }
}

