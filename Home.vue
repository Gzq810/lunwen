<template>
  <div class="main-content">
    <div style="margin-bottom: 50px">
      <el-carousel height="400px">
        <el-carousel-item v-for="item in data.carouselList" :key="item">
          <img :src="item.img" alt="" style="width: 100%" @click="router.push('/front/heritageDetail?id=' + item.id)">
        </el-carousel-item>
      </el-carousel>
    </div>

    <div style="margin-bottom: 50px">
      <div style="margin-bottom: 10px">
        <div style="font-size: 30px; text-align: center; margin-bottom: 10px">热 门 非 遗</div>
        <div style="font-size: 16px; font-weight: 300; color: #666; text-align: center">匠韵千年 薪火相传</div>
      </div>
      <div style="text-align: right; color: #666; cursor: pointer; margin-bottom: 10px"><span @click="router.push('/front/heritage')">查看全部</span></div>
      <el-row :gutter="20">
       <el-col :span="6" v-for="item in data.heritageList" :key="item.id">
         <div @click="router.push('/front/heritageDetail?id=' + item.id)" class="item" style="margin-bottom: 20px; cursor: pointer">
           <img style="width: 100%; height: 260px; border-radius: 5px 5px 0 0; margin-bottom: 5px" :src="item.img" alt="">
           <div class="line1" style="font-weight: bold; font-size: 20px; margin-bottom: 5px">【{{ item.name }}】</div>
           <div style="color: #666" class="line2">{{ item.introduce }}</div>
         </div>
       </el-col>
      </el-row>
    </div>

    <div style="margin-bottom: 50px">
      <div style="margin-bottom: 10px">
        <div style="font-size: 30px; text-align: center; margin-bottom: 10px">热 门 活 动</div>
        <div style="font-size: 16px; font-weight: 300; color: #666; text-align: center">潮动九州 活力共生</div>
      </div>
      <div style="text-align: right; color: #666; cursor: pointer; margin-bottom: 10px"><span @click="router.push('/front/activity')">查看全部</span></div>
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in data.activityList" :key="item.id">
          <div @click="router.push('/front/activityDetail?id=' + item.id)" class="item" style="margin-bottom: 20px; cursor: pointer">
            <img style="width: 100%; height: 260px; border-radius: 5px 5px 0 0; margin-bottom: 5px" :src="item.img" alt="">
            <div class="line1" style="font-weight: bold; font-size: 20px; margin-bottom: 5px">{{ item.name }}</div>
            <div style="color: #666" class="line2">{{ item.introduce }}</div>
          </div>
        </el-col>
      </el-row>
    </div>

  </div>
</template>

<script setup>
import { reactive } from "vue";
import request from "@/utils/request.js";
import router from "@/router/index.js";

const data = reactive({
  carouselList: [],
  heritageList: [],
  activityList: [],
})

request.get('/carousel/selectAll').then(res => {
  data.carouselList = res.data
})

request.get('/heritage/selectAll').then(res => {
  data.heritageList = res.data
  data.heritageList = data.heritageList.sort((h1, h2) => h2.views - h1.views)
})

request.get('/activity/selectAll').then(res => {
  data.activityList = res.data
  data.activityList = data.activityList.sort((a1, a2) => a2.num - a1.num)
})
</script>