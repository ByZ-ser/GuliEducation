<template>

    <div class="app-container">
        <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.name" placeholder="课程名称"/>
      </el-form-item>

      

      <el-form-item label="添加时间">
        <el-date-picker
          v-model="courseQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="courseQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
     <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * 4 + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="课程名称" width="200" align="center"/>

      <el-table-column label="课程状态" width="80" align="center">
        <template slot-scope="scope">
          {{ scope.row.status==='Normal'?'已发布':'未发布' }}
        </template>
      </el-table-column>

      <el-table-column prop="lessonNum" label="课时数" width="80" align="center"/>

      <el-table-column prop="gmtCreate" label="添加时间" width="160" align="center"/>

      <el-table-column prop="viewCount" label="浏览数量" width="80" align="center"/>

      <el-table-column label="操作" width="500" align="center">
        <template slot-scope="scope">
          <router-link :to="'/course/info/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程基本信息</el-button>
          </router-link>
           <router-link :to="'/course/chapter/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit" @click="editCourse(scope.row.id)">编辑课程大纲</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
      <!-- 分页 -->
   <el-pagination
      :current-page="page"
      :page-size="4"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
    </div>
</template>
<script>
import course from '@/api/edu/course'

export default({
  // data:{
   //},
   data(){//定义变量和初始值
       return {
           list:null,//查询之后接口返回的集合
           page:1,//当前页
           total:0,//总记录数
           courseQuery:{
              
           }//查询条件对象
       }
   },
   created(){//页面渲染之前执行，一般调用methods定义的方法
        this.getList()
   },
   methods:{//创建具体方法，调用teacher.js定义的方法
        getList(page=1){
            this.page=page
            course.getCourseListPage(this.page,this.courseQuery)
                   .then(response=>{//请求成功
                        this.list=response.data.volist
                        this.total=response.data.total
                        console.log(this.list)
                        console.log(this.total)
                        console.log(response)
                   })
                   .catch(error=>{//请求失败
                        console.log(error)
                   })
        },
        resetData(){//清空数据
            //清空查询条件
            this.courseQuery={}
            //查询所有数据
            this.getList()
        },
        //删除课程
        removeDataById(couseId){
             this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {//点击确定
        course.deleteCourse(couseId)
                  .then(response=>{
                     this.$message({
                     type: 'success',
                     message: '删除成功!'
                        });
                     this.getList()
                  })
        })        
        },
        editCourse(courseId){
         this.$router.push({ path: '/course/chapter/'+courseId })
        }
   }
})
</script>
