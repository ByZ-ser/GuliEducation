<template>

  <div class="app-container">
    <!-- 添加和修改章节表单 -->
<el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
    <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
            <el-input v-model="chapter.title"/>
        </el-form-item>
        <el-form-item label="章节排序">
            <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
        </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
    </div>
</el-dialog>
<!-- 添加和修改课时表单 -->
<el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
  <el-form :model="video" label-width="120px">
    <el-form-item label="课时标题">
      <el-input v-model="video.title"/>
    </el-form-item>
    <el-form-item label="课时排序">
      <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
    </el-form-item>
    <el-form-item label="是否免费">
      <el-radio-group v-model="video.free">
        <el-radio :label="true">免费</el-radio>
        <el-radio :label="false">默认</el-radio>
      </el-radio-group>
    </el-form-item>
   <el-form-item label="上传视频">
    <el-upload
           :on-success="handleVodUploadSuccess"
           :on-remove="handleVodRemove"
           :before-remove="beforeVodRemove"
           :on-exceed="handleUploadExceed"
           :file-list="fileList"
           :action="BASE_API+'/eduvod/video/uploadvideo'"
           :limit="1"
           class="upload-demo">
    <el-button size="small" type="primary">上传视频</el-button>
    <el-tooltip placement="right-end">
        <div slot="content">最大支持1G，<br>
            支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
            GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
            MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
            SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
        <i class="el-icon-question"/>
    </el-tooltip>
    </el-upload>
</el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
    <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
  </div>
</el-dialog>

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>
    <el-button type="text" @click="openChapterDialog()">添加章节</el-button>
   <!-- 章节 -->
<ul class="chanpterList">
    <li
        v-for="chapter in chaptervideoList"
        :key="chapter.id">
        <p>
            {{ chapter.title }}
              <span class="acts">
                <el-button type="text" @click="openVideo(chapter.id)">添加课时</el-button>
                <el-button style="" type="text" @click="openEditchapter(chapter.id)">编辑</el-button>
                <el-button type="text" @click="openDeletechapter(chapter.id)">删除</el-button>
            </span>
        </p>

        <!-- 视频 -->
        <ul class="chanpterList videoList">
            <li
                v-for="video in chapter.children"
                :key="video.id">
                <p>{{ video.title }}
                   <span class="acts">
                <el-button style="" type="text" @click="openEditvideo(video.id)">编辑</el-button>
                <el-button type="text" @click="openDeletevideo(video.id)">删除</el-button>
            </span>
                </p>
            </li>
        </ul>
    </li>
</ul>
<div>
    <el-button @click="previous">上一步</el-button>
    <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
</div>
  </div>
</template>

<script>
import chapter from '@/api/edu/chapter'
import video from '@/api/edu/video'

export default {
  data() {
    return {
      chaptervideoList:[],
      saveBtnDisabled: false, // 保存按钮是否禁用
      courseId:'',
      dialogChapterFormVisible:false,
      dialogVideoFormVisible:false,
      chapter:{//封装章节的属性
          title: '',
          sort: 0
      },
      video:{
          title: '',
          sort: 0,
          free: 0,
          videoSourceId: ''
      },
      BASE_API: process.env.BASE_API,
      fileList: []
    }
  },

  created() {
    //获取路由中的课程id
    if(this.$route.params && this.$route.params.id){
        this.courseId = this.$route.params.id
        this.getAllchapter()
    }
    console.log('chapter created')
  },

  methods: {

//========================================小节操作==========================================//
//视频上传成功
handleVodUploadSuccess(response,file,fileList){
  this.video.videoSourceId=response.data.videoId
  this.video.videoOriginalName=file.name
},

//文件大小限制
handleUploadExceed(){
    this.$message.warning('想要重新上传视频，请先删除已上传的视频')
},

//点击确定时，删除视频
handleVodRemove(){
    video.deletevideo(this.video.videoSourceId)
        .then(response=>{
               //提示信息
               this.$message({
                 type:'success',
                 message:'删除视频成功'
                })
                this.fileList=[]
                this.video.videoSourceId=''
                this.video.videoOriginalName=''
        })
},
//删除之前的确认框
beforeVodRemove(file,fileList){
  return this.$confirm(`确定移除 ${ file.name }?`)
},
openVideo(chapterId){
    this.dialogVideoFormVisible=true
    this.video.chapterId=chapterId
    this.video.title=''
    this.video.sort=''
    this.video.free=''
    this.videoSourceId=''
    this.fileList=[]
},

//添加小节
addVideo(){
  this.video.courseId=this.courseId
  video.addVideo(this.video)
    .then(response=>{
         //关闭弹框
              this.dialogVideoFormVisible=false
            //提示信息
               this.$message({
                 type:'success',
                message:'添加小节成功'
                })
            //刷新页面
            this.getAllchapter()
    })
},

openEditvideo(videoId){
    this.dialogVideoFormVisible=true
    video.getVideoInfo(videoId)
      .then(response=>{
          this.video=response.data.videoInfo
      })
},
//修改小节
updatevideo(){
  video.updateVideo(this.video)
    .then(response=>{
          //关闭弹框
              this.dialogVideoFormVisible=false
            //提示信息
               this.$message({
                 type:'success',
                message:'修改小节成功'
                })
            //刷新页面
            this.getAllchapter()
    })

},
saveOrUpdateVideo(){
  if(!this.video.id){
      this.addVideo() 
  }else{
    this.updatevideo()
  }
  
},
//删除小节
 openDeletevideo(videoId){
         this.$confirm('此操作将永久删除该小节, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {//点击确定
        video.deleteVideo(videoId)
                  .then(response=>{
                     this.$message({
                     type: 'success',
                     message: '删除成功!'
                        });
                     this.getAllchapter()
                  })
        })         
    },



//========================================章节操作==========================================//
    //删除章节
    openDeletechapter(chapterId){
         this.$confirm('此操作将永久删除该章节, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {//点击确定
        chapter.deleteChapter(chapterId)
                  .then(response=>{
                     this.$message({
                     type: 'success',
                     message: '删除成功!'
                        });
                     this.getAllchapter()
                  })
        })         
    },
    //修改章节弹框
    openEditchapter(chapterId){
      this.dialogChapterFormVisible=true
        chapter.getChapterById(chapterId)
                .then(response=>{
                  this.chapter=response.data.chapterinfo
                })
    },
    //添加课程弹框
    openChapterDialog(){
      this.dialogChapterFormVisible=true
      this.chapter.title=''
      this.chapter.sort=''
    },
    //添加章节
    addChapter(){
      this.chapter.courseId = this.courseId
      console.log(this.chapter.courseId)
      console.log(this.chapter.title)
      console.log(this.chapter.sort)
      chapter.addChapter(this.chapter)
        .then(response=>{
            //关闭弹框
              this.dialogChapterFormVisible=false
            //提示信息
               this.$message({
                 type:'success',
                message:'添加章节成功'
                })
            //刷新页面
            this.getAllchapter()
        })
    },
    //修改章节
    updateChapter(){
        chapter.updateChapter(this.chapter)
            .then(response=>{
                //关闭弹框
              this.dialogChapterFormVisible=false
            //提示信息
               this.$message({
                 type:'success',
                message:'修改章节成功'
                })
            //刷新页面
            this.getAllchapter()
            })
    },
    //添加章节
    saveOrUpdate(){
      if(!this.chapter.id){
         this.addChapter()
      }else{
          this.updateChapter()
      }
        
    },
    previous() {
      console.log('previous')
      this.$router.push({ path: '/course/info/'+this.courseId })
    },

    next() {
      console.log('next')
      this.$router.push({ path: '/course/publish/'+this.courseId })
    },

    getAllchapter(){
      chapter.getCourseList(this.courseId)
            .then(response=>{
                this.chaptervideoList=response.data.allVideo
            })
    }
  }
}
</script>
<style scoped>
.chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
}
.chanpterList li{
  position: relative;
}
.chanpterList p{
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}
.chanpterList .acts {
    float: right;
    font-size: 14px;
}

.videoList{
  padding-left: 50px;
}
.videoList p{
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
}

</style>