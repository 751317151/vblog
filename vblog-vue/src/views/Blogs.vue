<template>
  <div class="m-container">
    <Header></Header>
    <div class="block">
      <el-timeline>
        <el-timeline-item 
        v-bind:timestamp="blog.created" 
        placement="top" 
        v-for="(blog,key) in blogs" 
        :key="key"
        color="blue"
        >
          <el-card shadow="hover">
            <h4><router-link :to="{name: 'BlogDetail', params: {blogId: blog.id}}">{{blog.title}}</router-link></h4>
            <p>{{blog.description}}</p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
     
    </div>
    <!-- <el-pagination class="mpage"
      background
      layout="prev, pager, next"
      :current-page=currentPage
      :page-size=pageSize
      @current-change=page
      :total="total">
    </el-pagination> -->
    <el-pagination class="mpage"
      @size-change=handleSizeChange
      @current-change=handleCurrentChange
      :current-page=currentPage
      :page-sizes="[1, 2, 3, 4, 5]"
      :page-size=pageSize
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
    <el-backtop></el-backtop>
  </div>
</template>
<script>
  import Header from "@/components/Header";
  export default {
    name: "Blogs.vue",
    components: {Header},
    data() {
      return {
        blogs: {},
        currentPage: 1,
        total: 0,
        pageSize: 5
      }
    },
    methods: {
      handleCurrentChange(currentPage) {
        const _this = this
        this.$axios.get('/blogs?currentPage=' + currentPage+'&currentSize='+this.pageSize,{
          headers : {
            "Authorization": localStorage.getItem("vblogtoken")
          },
          }).then((res) => {
          console.log(res.data.data.records)
          _this.blogs = res.data.data.records
          _this.currentPage = res.data.data.current
          _this.total = res.data.data.total
          _this.pageSize = res.data.data.size
        }).catch((e) => {
          console.log(e)
        })
      },
      handleSizeChange(currentSize) {
        const _this = this
        this.$axios.get('/blogs?currentPage=' + this.currentPage+'&currentSize='+currentSize).then((res) => {
          console.log(res.data.data.records)
          _this.blogs = res.data.data.records
          _this.currentPage = res.data.data.current
          _this.total = res.data.data.total
          _this.pageSize = res.data.data.size
        })
      }
    },
    mounted () {
      this.handleCurrentChange(1);
      this.$notify({
        title: '欢迎回来',
        message: '2333333333333333',
        duration: 1500
      });
    }
  }
</script>
<style scoped>
  .mpage {
    margin: 0 auto;
    text-align: center;
  }
</style>