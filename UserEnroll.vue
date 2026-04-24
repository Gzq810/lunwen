<template>
  <div class="main-content">
    <div style="font-size: 20px; font-weight: 400; margin-bottom: 20px">我报名的活动（{{ data.total }}）</div>
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.activityName" prefix-icon="Search" style="width: 240px; margin-right: 10px" placeholder="请输入活动名称查询"></el-input>
      <el-button type="info" plain @click="load">查询</el-button>
      <el-button type="warning" plain style="margin: 0 10px" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-table stripe tooltip-effect="dark myEff" :data="data.tableData">
        <el-table-column prop="activityName" label="活动名称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="userName" label="用户名称"></el-table-column>
        <el-table-column prop="time" label="报名时间"></el-table-column>
        <el-table-column prop="status" label="审核状态">
          <template v-slot="scope">
            <el-tag type="danger" v-if="scope.row.status === '已取消'">已取消</el-tag>
            <el-tag type="warning" v-if="scope.row.status === '待审核'">待审核</el-tag>
            <el-tag type="success" v-if="scope.row.status === '审核通过'">审核通过</el-tag>
            <el-tag type="danger" v-if="scope.row.status === '审核拒绝'">审核拒绝</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="reason" label="审核原因"></el-table-column>
        <el-table-column prop="name" label="姓名"></el-table-column>
        <el-table-column prop="phone" label="联系方式"></el-table-column>

        <el-table-column label="操作" width="120" fixed="right">
          <template v-slot="scope">
            <el-button type="primary" v-if="scope.row.status === '审核通过'" @click="handleAddEvaluate(scope.row)">评价</el-button>
            <el-button type="danger" v-if="scope.row.status === '待审核'" @click="cancel(scope.row)">取消报名</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="card" v-if="data.total">
      <el-pagination @current-change="load" background layout="total, prev, pager, next" :page-size="data.pageSize" v-model:current-page="data.pageNum" :total="data.total" />
    </div>

    <el-dialog title="活动评价" v-model="data.formVisible" width="40%" destroy-on-close>
      <el-form ref="formRef" :model="data.form" :rules="data.rules" label-width="90px" style="padding: 20px">
        <el-form-item prop="content" label="评价内容">
          <el-input type="textarea" :rows="3" v-model="data.form.content" placeholder="请输入评价内容"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="data.formVisible = false">取 消</el-button>
          <el-button type="primary" @click="addEvaluate">确 定</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>

import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {Delete, Edit} from "@element-plus/icons-vue";


const data = reactive({
  user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
  formVisible: false,
  form: {},
  tableData: [],
  pageNum: 1,
  pageSize: 10,
  total: 0,
  activityName: null,
  ids: [],
  rules: {
    content: [
      { required: true, message: '请输入内容', trigger: 'change' }
    ]
  }
})

const formRef = ref()

const load = () => {
  request.get('/enroll/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      activityName: data.activityName,
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

const handleAddEvaluate = (row) => {
  data.form = { activityId: row.activityId, userId: data.user.id }
  data.formVisible = true
}

const addEvaluate = () => {
  formRef.value.validate(valid => {
    if (valid) {
      request.post('/evaluate/add', data.form).then(res => {
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

const cancel = (row) => {
  ElMessageBox.confirm('您确定取消报名吗？', '取消确认', { type: 'warning' }).then(res => {
    row.status = '已取消'
    request.put('/enroll/update', row).then(res => {
      if (res.code === '200') {
        ElMessage.success("取消成功")
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {
    console.error(err)
  })
}

const delBatch = () => {
  if (!data.ids.length) {
    ElMessage.warning("请选择数据")
    return
  }
  ElMessageBox.confirm('删除后数据无法恢复，您确定删除吗？', '删除确认', { type: 'warning' }).then(res => {
    request.delete("/enroll/delete/batch", {data: data.ids}).then(res => {
      if (res.code === '200') {
        ElMessage.success('操作成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {
    console.error(err)
  })
}
const handleSelectionChange = (rows) => {
  data.ids = rows.map(v => v.id)
}

const reset = () => {
  data.activityName = null
  load()
}

load()
</script>