<template>
  <div style="width: 60%; margin: 0 auto; padding: 20px 0; min-height: 100vh">
    <div class="card" style="padding: 20px">
      <div style="font-size: 28px; margin-bottom: 10px; text-align: center; font-weight: bold">【{{ data.heritage.name }}】 </div>
      <div style="margin-bottom: 40px; color: #666; text-align: center">
        发布时间：{{ data.heritage.time }}
        <span style="margin-left: 30px; text-align: center;">浏览量：{{ data.heritage.views }}</span>
      </div>
      <div style="padding: 0 20px" v-html="data.heritage.content"></div>
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
  heritage: {}
})


request.get('/heritage/selectById/' + data.id).then(res => {
  data.heritage = res.data
})
</script>

<style>

</style>