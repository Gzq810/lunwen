<template>
  <div style="width: 60%; margin: 0 auto; padding: 20px 0; min-height: 100vh">
    <div class="card" style="padding: 20px">
      <div style="font-size: 28px; margin-bottom: 10px; text-align: center; font-weight: bold">{{ data.news.title }} </div>
      <div style="margin-bottom: 40px; color: #666; text-align: center">
        发布时间：{{ data.news.time }}
        <span style="margin-left: 30px; text-align: center;">浏览量：{{ data.news.views }}</span>
      </div>
      <div style="padding: 0 20px" v-html="data.news.content"></div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import request from "@/utils/request.js";
import router from "@/router/index.js";

const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  id: router.currentRoute.value.query.id,
  news: {}
})


request.get('/news/selectById/' + data.id).then(res => {
  data.news = res.data
})
</script>

<style>

</style>