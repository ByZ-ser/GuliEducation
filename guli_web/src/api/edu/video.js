import request from '@/utils/request'

export default{
    //添加小节
    addVideo(video){
        return request({
            //url: '/eduservice/edu-video/addvideo',
            url:`/eduservice/edu-video/addvideo`,
            method: 'post',
            data:video
          })
    },
    //删除小节
    deleteVideo(videoId){
        return request({
            //url: '/eduservice/edu-video/addvideo',
            url:`/eduservice/edu-video/deleteVideo/${videoId}`,
            method: 'delete',
          })
    },
    //获取小节信息
    getVideoInfo(videoId){
        return request({
            //url: '/eduservice/edu-video/addvideo',
            url:`/eduservice/edu-video/getVideoInfo/${videoId}`,
            method: 'get',
          })
    },
    //修改小节
    updateVideo(video){
        return request({
            //url: '/eduservice/edu-video/addvideo',
            url:`/eduservice/edu-video/updateVideo`,
            method: 'get',
            data:video
          })
    },
  //删除视频
  deletevideo(videoId){
    return request({
        //url: '/eduservice/edu-video/addvideo',
        url:`/eduvod/video/deleteVideo/${videoId}`,
        method: 'delete',
      })
  }

}

