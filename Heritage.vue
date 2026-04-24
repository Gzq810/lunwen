<template>
  <div class="main-content">
    <div style="margin-bottom: 20px">
      <el-input clearable @clear="load" v-model="data.name" placeholder="请输入非遗名称搜索" style="width: 350px; height: 40px"></el-input>
      <el-button @click="load" type="primary" style="width: 80px; height: 40px; margin-left: 5px">搜 索</el-button>
    </div>

    <div>
      <el-tabs v-model="data.activeName" class="demo-tabs" @tab-click="handleClick">
        <el-tab-pane label="全部" :name="0" style="padding-top: 10px">
          <el-row :gutter="20">
           <el-col :span="12" v-for="heritage in data.heritageList" :key="heritage.id">
             <div @click="router.push('/front/heritageDetail?id=' + heritage.id)" class="card" style="display: flex; grid-gap: 20px; margin-bottom: 20px; cursor: pointer">
               <img style="width: 150px; height: 100px; border-radius: 5px" :src="heritage.img" alt="">
               <div style="flex: 1">
                 <div style="font-size: 20px; margin-bottom: 10px">【{{ heritage.name }}】</div>
                 <div style="color: #666" class="line2">{{ heritage.introduce }}</div>
               </div>
             </div>
           </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane v-for="item in data.classifyList" :key="item.id" :label="item.name" :name="item.id">
          <div style="display: flex; grid-gap: 20px; margin-bottom: 20px">
            <img style="width: 300px; height: 160px; border-radius: 5px" :src="data.classify.img" alt="">
            <div>
              <div style="font-size: 30px; margin-bottom: 20px">{{ data.classify.name }}</div>
              <div style="padding: 10px; border-radius: 5px; background-color: #eee; font-size: 16px; line-height: 30px">{{ data.classify.introduce }}</div>
            </div>
          </div>
          <el-row :gutter="20">
            <el-col :span="12" v-for="heritage in data.heritageList" :key="heritage.id">
              <div @click="router.push('/front/heritageDetail?id=' + heritage.id)" class="card" style="display: flex; grid-gap: 20px; margin-bottom: 20px; cursor: pointer">
                <img style="width: 150px; height: 100px; border-radius: 5px" :src="heritage.img" alt="">
                <div style="flex: 1">
                  <div style="font-size: 20px; margin-bottom: 10px">【{{ heritage.name }}】</div>
                  <div style="color: #666" class="line2">{{ heritage.introduce }}</div>
                </div>
              </div>
            </el-col>
          </el-row>
        </el-tab-pane>
      </el-tabs>
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
  heritageList: [],
  total: 0,
  pageNum: 1,  // 当前的页码
  pageSize: 8,  // 每页的个数
  name: null,
  classifyList: [],
  activeName: 0,
  classifyId: null,
  classify: {}
})

const handleClick = (tab) => {
  data.classifyId = tab.paneName
  if (tab.paneName === 0) {
    data.classifyId = null
  }
  load()

  request.get('/classify/selectById/' + data.classifyId).then(res => {
    data.classify = res.data
  })
}

request.get('/classify/selectAll').then(res => {
  data.classifyList = res.data
})

// 加载数据
const load = () => {
  request.get('/heritage/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name,
      classifyId: data.classifyId
    }
  }).then(res => {
    if (res.code === '200') {
      data.heritageList = res.data?.list || []
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