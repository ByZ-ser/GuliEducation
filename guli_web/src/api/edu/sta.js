import request from '@/utils/request'

export default{
   //生成统计数据
    createData(day){
        return request({
            //url: '/eduservice/edu-teacher/fingTeacherByvo',
            url:`/eduacl/statistics-daily/getCounts/${day}`,
            method: 'post'
          })
    },
    showChart(searchObj) {
        return request({
            url: `/eduacl/statistics-daily/getChartInfo/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
            method: 'get'
        })
    }
}

