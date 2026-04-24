<template>
  <div class="main-content">
    <div style="margin-bottom: 20px">
      <el-input clearable @clear="load" v-model="data.name" placeholder="请输入名称搜索" style="width: 350px; height: 40px"></el-input>
      <el-button @click="load" type="primary" style="width: 80px; height: 40px; margin-left: 5px">搜 索</el-button>
    </div>
    <div>
      <el-row :gutter="20">
       <el-col @click="router.push('/front/activityDetail?id=' + item.id)" class="item" style="cursor: pointer; margin-bottom: 20px" v-for="item in data.tableData" :key="item.id" :span="6">
         <img style="width: 100%; height: 200px; margin-bottom: 5px; display: block; border-radius: 5px 5px 0 0" :src="item.img" alt="">
         <div style="font-size: 20px; font-weight: 400; margin-bottom: 5px;" class="line1">{{ item.name }}</div>
         <div style="display: flex; align-items: center">
           <el-tag type="success" v-if="item.status === '报名中'">{{ item.status }}</el-tag>
           <el-tag type="primary" v-if="item.status === '进行中'">{{ item.status }}</el-tag>
           <el-tag type="danger" v-if="item.status === '已结束'">{{ item.status }}</el-tag>
           <span style="margin-left: 20px; color: #666; font-size: 13px">{{ item.startTime.substring(0, 10) }} ~ {{ item.endTime.substring(0, 10) }}</span>
         </div>
       </el-col>
      </el-row>

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
  name: null
})

// 加载数据
const load = () => {
  request.get('/activity/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name
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