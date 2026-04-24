<template>
  <div class="main-content" style="width: 60%">
    <div class="card" style="padding: 20px">
      <div style="text-align: center; font-size: 20px; font-weight: bold; margin-bottom: 10px">{{ data.promote.title }}</div>
      <div style="text-align: center; color: #666; margin-bottom: 40px">
        浏览量：{{ data.promote.views }}
        <span style="margin-left: 20px">发布时间：{{ data.promote.time }}</span>
      </div>
      <div style="text-align: center">
        <video style="width: 90%" controls :src="data.promote.video"></video>
      </div>
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
  promote: {}
})


request.get('/promote/selectById/' + data.id).then(res => {
  data.promote = res.data
})
</script>

<style>

</style>