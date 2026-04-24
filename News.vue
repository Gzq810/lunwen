<template>
  <div class="main-content" style="width: 60%">
    <div style="margin-bottom: 20px">
      <el-input clearable @clear="load" v-model="data.title" placeholder="请输入标题搜索" style="width: 350px; height: 40px"></el-input>
      <el-button @click="load" type="primary" style="width: 80px; height: 40px; margin-left: 5px">搜 索</el-button>
    </div>
    <div v-for="(news, index) in data.tableData" :key="news.id">
      <div @click="router.push('/front/newsDetail?id=' + news.id)" class="card" style="display: flex; grid-gap: 20px; margin-bottom: 20px; cursor: pointer">
        <img v-if="index % 2 === 0" style="width: 150px; height: 110px; border-radius: 5px" :src="news.img" alt="">
        <div style="flex: 1">
          <div style="font-size: 20px; margin-bottom: 10px">{{ news.title }}</div>
          <div style="color: #666; margin-bottom: 10px" class="line2">{{ news.introduce }}</div>
          <div style="color: #666">发布时间：{{ news.time }}  <span style="margin-left: 20px">浏览量：{{ news.views }}</span></div>
        </div>
        <img v-if="index % 2 === 1" style="width: 150px; height: 110px; border-radius: 5px" :src="news.img" alt="">
      </div>
    </div>
    <div v-if="data.total" style="margin-top: 20px">
      <el-pagination @current-change="load" layout="total,prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total"/>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import request from "@/utils/request.js";
import router from "@/router/index.js";
import {ElMessage} from "element-plus";

const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  tableData: [],
  total: 0,
  pageNum: 1,  // 当前的页码
  pageSize: 8,  // 每页的个数
  title: null
})

// 加载数据
const load = () => {
  request.get('/news/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      title: data.title
    }
  }).then(res => {
    if (res.code === '200') {
      data.tableData = res.data?.list || []
      data.total = res.data?.total
    } else {
      ElMessage.error(res.msg)
    }
  })
}
load()
</script>

<style scoped>

</style>