<template>
  <div>
    <div class="front-header">
      <div class="front-header-left">
        <img src="@/assets/imgs/logo.png" alt="">
        <div class="title">非物质文化遗产系统</div>
      </div>
      <div class="front-header-center">
        <el-menu :default-active="router.currentRoute.value.path" router mode="horizontal">
          <el-menu-item index="/front/home">首页</el-menu-item>
          <el-menu-item index="/front/heritage">文化遗产</el-menu-item>
          <el-menu-item index="/front/news">文化资讯</el-menu-item>
          <el-menu-item index="/front/promote">非遗宣传</el-menu-item>
          <el-menu-item index="/front/activity">文化活动</el-menu-item>
          <el-menu-item index="/front/feedback">用户反馈</el-menu-item>
          <el-menu-item index="/front/notice">系统公告</el-menu-item>
        </el-menu>
      </div>
      <div class="front-header-right">
        <div v-if="!data.user.id">
          <el-button @click="router.push('/login')">登录</el-button>
          <el-button @click="router.push('/register')">注册</el-button>
        </div>
        <div v-else>
          <el-dropdown style="cursor: pointer; height: 60px">
            <div style="display: flex; align-items: center; color: #fff">
              <img style="width: 40px; height: 40px; border-radius: 50%;" :src="data.user.avatar" alt="">
              <span style="margin-left: 5px;">{{ data.user.name }}</span><el-icon><arrow-down /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="router.push('/front/userFeedback')">我的反馈</el-dropdown-item>
                <el-dropdown-item @click="router.push('/front/userEnroll')">我的报名</el-dropdown-item>
                <el-dropdown-item @click="router.push('/front/userCollect')">我的收藏</el-dropdown-item>
                <el-dropdown-item @click="router.push('/front/UserEvaluate')">活动评价</el-dropdown-item>
                <el-dropdown-item @click="router.push('/front/person')">个人信息</el-dropdown-item>
                <el-dropdown-item @click="router.push('/front/password')">修改密码</el-dropdown-item>
                <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </div>
    <div class="main-body">
      <RouterView @updateUser="updateUser" />
    </div>

    <Footer />
  </div>
</template>

<script setup>
  import router from "@/router/index.js";
  import { reactive } from "vue";
  import request from "@/utils/request.js";
  import {ElMessage} from "element-plus";

  const data = reactive({
    user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  })

  if (data.user.role !== 'USER') {
    ElMessage.error('无权限')
    router.push('/login')
  }

  const logout = () => {
    localStorage.removeItem('xm-user')
    router.push('/login')
  }

  const updateUser = () => {
    data.user =  JSON.parse(localStorage.getItem('xm-user') || '{}')
  }
</script>

<style scoped>
@import "@/assets/css/front.css";
</style>