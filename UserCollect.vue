<template>
  <div class="main-content">
    <div style="font-size: 20px; font-weight: 400; margin-bottom: 20px">我收藏的活动（{{ data.total }}）</div>
    <div>
      <el-row :gutter="20">
       <el-col :span="6" v-for="item in data.tableData" :key="item.id">
          <div class="card" style="padding: 0; margin-bottom: 20px;" @click="router.push('/front/activityDetail?id=' + item.id)">
            <img style="width: 100%; height: 240px; border-radius: 5px 5px 0 0; cursor: pointer" :src="item.activityImg" alt="">
            <div style="padding: 10px; font-size: 20px;" class="line1">{{ item.activityName }}</div>
            <div style="padding: 10px; text-align: right"><el-button plain type="danger" @click="delCollect(item.id)">取消收藏</el-button></div>
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

const delCollect = (id) => {
  request.delete('/collect/delete/' + id).then(res => {
    ElMessage.success('取消收藏成功')
    load()
  })
}

// 加载数据
const load = () => {
  request.get('/collect/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      userId: data.user.id
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