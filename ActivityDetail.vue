<template>
  <div style="width: 60%; margin: 0 auto; padding: 20px 0; min-height: 100vh">
    <div class="card" style="margin-bottom: 20px">
      <div style="display: flex; grid-gap: 20px">
        <img :src="data.activity.img" alt="" style="width: 300px; height: 300px; border-radius: 5px">
        <div style="flex: 1">
          <div style="font-size: 24px; margin-bottom: 20px; display: flex; align-items: center">
            <div style="flex: 1">{{ data.activity.name }}</div>
            <div style="cursor: pointer" @click="addCollect">
              <span v-if="!data.activity.userCollect" style="font-size: 14px"><el-icon style="position: relative; top: 3px" size="18" color="#666"><Star /></el-icon>收藏</span>
              <span v-else style="font-size: 14px; color: red"><el-icon style="position: relative; top: 3px" size="18" color="red"><StarFilled /></el-icon>取消收藏</span>
            </div>
          </div>
          <div style="font-size: 16px; color: #666; display: flex; align-items: center; margin-bottom: 20px">
            <span style="margin-right: 20px">时间：{{ data.activity.startTime }} ~ {{ data.activity.endTime }}</span>
            <el-tag type="success" v-if="data.activity.status === '报名中'">{{ data.activity.status }}</el-tag>
            <el-tag type="primary" v-if="data.activity.status === '进行中'">{{ data.activity.status }}</el-tag>
            <el-tag type="danger" v-if="data.activity.status === '已结束'">{{ data.activity.status }}</el-tag>
          </div>
          <div style="margin-bottom: 20px; color: #666">报名人数：{{ data.activity.num }}</div>
          <div style="line-height: 24px; font-size: 16px; margin-bottom: 40px">{{ data.activity.introduce }}</div>
          <div>
            <el-button @click="handleAdd" :disabled="data.activity.status !== '报名中'" style="width: 100px; height: 40px" type="primary">我要报名</el-button>
          </div>
        </div>
      </div>
    </div>

    <div>
      <el-tabs v-model="data.activeName">
        <el-tab-pane label="活动详情" name="活动详情">
          <div v-html="data.activity.content"></div>
        </el-tab-pane>
        <el-tab-pane label="用户评价" name="用户评价">
          <div class="card">
            <div v-for="item in data.evaluateList" :key="item.id" style="margin-bottom: 20px; padding-bottom: 20px; border-bottom: 1px solid #eee">
              <div style="display: flex; align-items: flex-start; grid-gap: 20px">
                <img style="width: 50px; height: 50px; border-radius: 50%; display: block" :src="item.userAvatar" alt="">
                <div style="flex: 1">
                  <div style="color: #666; margin-bottom: 10px">{{ item.userName }} <span style="margin-left: 10px">{{ item.time }}</span></div>
                  <div>{{ item.content }}</div>
                </div>
              </div>
            </div>

            <div v-if="data.total">
              <el-pagination @current-change="loadEvaluate" layout="total, prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total" />
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>


    <el-dialog title="报名信息" v-model="data.formVisible" width="40%" destroy-on-close>
      <el-form ref="formRef" :model="data.form" :rules="data.rules" label-width="70px" style="padding: 20px">
        <el-form-item label="报名时间">
          <div>{{ data.activity.startTime }} ~ {{ data.activity.endTime }}</div>
        </el-form-item>
        <el-form-item prop="name" label="姓名">
          <el-input v-model="data.form.name" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="电话">
          <el-input v-model="data.form.phone" placeholder="请输入电话"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible = false">取 消</el-button>
          <el-button type="primary" @click="add">确 定</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import request from "@/utils/request.js";
import router from "@/router/index.js";
import {ElMessage} from "element-plus";

const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  id: router.currentRoute.value.query.id,
  activity: {},
  activeName: '活动详情',
  formVisible: false,
  form: {},
  rules: {
    name: [
      { required: true, message: '请输入姓名', trigger: 'blur' }
    ],
    phone: [
      { required: true, message: '请输入电话', trigger: 'blur' }
    ]
  },
  pageNum: 1,
  pageSize: 5,
  total: 0,
  evaluateList: []
})

const formRef = ref()

const addCollect = () => {
  request.post('/collect/add', { userId: data.user.id, activityId: data.id }).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      loadActivity()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const loadEvaluate = () => {
  request.get('/evaluate/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      activityId: data.id
    }
  }).then(res => {
    data.evaluateList = res.data?.list
    data.total = res.data.total
  })
}
loadEvaluate()

const loadActivity = () => {
  request.get('/activity/selectById/' + data.id).then(res => {
    data.activity = res.data
  })
}
loadActivity()

const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}

const add = () => {
  formRef.value.validate(valid => {
    if (valid) {
      data.form.userId = data.user.id
      data.form.activityId = data.activity.id
      request.post('/enroll/add', data.form).then(res => {
        if (res.code === '200') {
          ElMessage.success('操作成功')
          data.formVisible = false
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}
</script>

<style>

</style>