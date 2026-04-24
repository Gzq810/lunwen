<template>
  <div class="main-content" style="width: 50%">
    <div class="card">
      <div style="margin: 20px 0; text-align: center; font-size: 20px; font-weight: 400">请留下您的反馈问题</div>
      <el-form ref="formRef" :model="data.form" :rules="data.rules" label-width="90px" style="padding: 20px">
        <el-form-item prop="title" label="反馈标题">
          <el-input v-model="data.form.title" placeholder="请输入反馈标题"></el-input>
        </el-form-item>
        <el-form-item prop="content" label="反馈内容">
          <el-input type="textarea" :rows="3" v-model="data.form.content" placeholder="请输入反馈内容"></el-input>
        </el-form-item>
        <el-form-item prop="img" label="反馈图片">
          <el-upload
              :action="baseUrl + '/files/upload'"
              :headers="{ 'token': data.user.token }"
              :on-success="handleFileUpload"
              list-type="picture"
              :show-file-list="false"
          >
            <img v-if="data.form.img" style="width: 200px; height: 100px; border-radius: 5px; " :src="data.form.img" alt="">
            <div v-else style="width: 200px; height: 120px; border-radius: 5px; border: 1px dashed #ccc; display: flex; align-items: center; justify-content: center">上传图片</div>
          </el-upload>
        </el-form-item>

        <div style="text-align: center; margin-top: 40px; margin-bottom: 20px">
          <el-button size="large" type="primary" @click="add">提交反馈</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import request from "@/utils/request.js";
import router from "@/router/index.js";
import {ElMessage} from "element-plus";

const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  form: {},
  rules: {
    title: {
      required: true, message: '请输入反馈标题', trigger: 'blur'
    },
    content: {
      required: true, message: '请输入反馈内容', trigger: 'blur'
    },
  }
})

const baseUrl = import.meta.env.VITE_BASE_URL
const handleFileUpload = (res) => {
  data.form.img = res.data
}

const formRef = ref()

const add = () => {
  formRef.value.validate(valid => {
    if (valid) {
      data.form.userId = data.user.id
      request.post('/feedback/add', data.form).then(res => {
        if (res.code === '200') {
          ElMessage.success('操作成功')
          data.form = {}
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}
</script>

<style scoped>

</style>