<template>
  <div class="main-content" style="width: 50%">
    <div class="card">
      <div style="font-weight: bold; font-size: 18px; padding: 10px 0 30px 10px">系统公告</div>
      <el-timeline style="max-width: 600px">
        <el-timeline-item
            color="#1A374D"
            v-for="(item, index) in data.noticeData"
            :key="index"
            :timestamp="item.time"
        >
          {{ item.content }}
        </el-timeline-item>
      </el-timeline>
    </div>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";

const data = reactive({
  noticeData: []
})

const loadNotice = () => {
  request.get('/notice/selectAll').then(res => {
    if (res.code === '200') {
      data.noticeData = res.data
    } else {
      ElMessage.error(res.msg)
    }
  })
}
loadNotice()
</script>