<template>
  <div class="main-content" style="width: 80%">
    <div style="margin-bottom: 20px">
      <el-input clearable @clear="load" v-model="data.title" placeholder="请输入标题搜索" style="width: 350px; height: 40px"></el-input>
      <el-button @click="load" type="primary" style="width: 80px; height: 40px; margin-left: 5px">搜 索</el-button>
    </div>
    <div style="display: flex; grid-gap: 20px">
      <div style="flex: 1">
        <el-row :gutter="20">
         <el-col @click="router.push('/front/promoteDetail?id=' + item.id)" class="item" style="margin-bottom: 20px; cursor: pointer" :span="12" v-for="item in data.tableData" :key="item.id">
           <img :src="item.img" alt="" style="width: 100%; height: 260px; border-radius: 5px; display: block; margin-bottom: 10px">
           <div class="line1" style="font-size: 20px; font-weight: bold; margin-bottom: 5px">{{ item.title }}</div>
           <div style="color: #666; font-size: 13px">浏览量：{{ item.views }} <span style="margin-left: 20px">发布时间：{{ item.time }}</span></div>
         </el-col>
        </el-row>
      </div>

      <div style="width: 400px">
        <div class="card" style="padding: 20px">
          <div style="display: flex; align-items: center; justify-content: center; margin-bottom: 40px">
            <img style="width: 40px" src="@/assets/imgs/hot.png" alt="">
            <div style="font-size: 24px; font-weight: bold">热度排行榜</div>
          </div>

          <div @click="router.push('/front/promoteDetail?id=' + item.id)" v-for="(item, index) in data.hotList" :key="item.id">
            <div :style="{ color: index <= 2 ? data.colors[index] : '#333'}" style="border-bottom: 1px solid #ddd; padding-bottom: 20px;
             font-size: 18px; cursor: pointer; margin-bottom: 20px; display: flex; align-items: center; grid-gap: 10px">
              <div style="flex: 1">{{ item.title }}</div>
              <div>Top {{ index + 1 }}</div>
            </div>
          </div>
        </div>
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
  pageSize: 4,  // 每页的个数
  title: null,
  hotList: [],
  colors: ['red', 'orange', '#9600e4']
})

const loadHot = () => {
  request.get('/promote/selectAll').then(res => {
    if (res.code === '200') {
      data.hotList = res.data.sort((v1, v2) => v2.views - v1.views)
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadHot()

// 加载数据
const load = () => {
  request.get('/promote/selectPage', {
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