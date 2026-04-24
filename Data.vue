<template>
  <div>
    <div style="display: flex; grid-gap: 10px; margin-bottom: 10px">
      <div class="card" style="flex: 1; padding: 20px; text-align: center; font-size: 20px">
        <div style="margin-bottom: 10px">非遗数量</div>
        <div>{{ data.dataCount.heritage }}</div>
      </div>
      <div class="card" style="flex: 1; padding: 20px; text-align: center; font-size: 20px">
        <div style="margin-bottom: 10px">用户数量</div>
        <div>{{ data.dataCount.user }}</div>
      </div>
      <div class="card" style="flex: 1; padding: 20px; text-align: center; font-size: 20px">
        <div style="margin-bottom: 10px">活动数量</div>
        <div>{{ data.dataCount.activity }}</div>
      </div>
      <div class="card" style="flex: 1; padding: 20px; text-align: center; font-size: 20px">
        <div style="margin-bottom: 10px">活动累计参加人数</div>
        <div>{{ data.dataCount.enroll }}</div>
      </div>
    </div>

    <div style="display: flex; grid-gap: 10px">
      <div style="flex: 1" class="card">
        <div id="line" style="height: 500px"></div>
      </div>
      <div style="flex: 1" class="card">
        <div id="pie" style="height: 500px"></div>
      </div>
    </div>

  </div>
</template>

<script setup>
import { reactive, onMounted } from "vue";
import request from "@/utils/request.js";
import * as echarts from 'echarts'

const lineOption = {
  title: {
    text: '近一周活动报名趋势图',
    left: 'center'
  },
  tooltip: {
    trigger: 'axis'
  },
  legend: {
    left: 'left'
  },
  xAxis: {
    name: '日期',
    type: 'category',
    data: []
  },
  yAxis: {
    name: '报名数量',
    type: 'value'
  },
  grid: {
    top: '20%',
    bottom:'10%'
  },
  series: [
    {
      data: [],
      type: 'line',
      smooth: true,
      areaStyle: {
        opacity: 0.8, // 阴影的透明度
        color: 'rgb(185,190,255)' // 阴影的颜色和透明度
      },
      markPoint: {
        data: [
          { type: 'max', name: 'Max' },
          { type: 'min', name: 'Min' }
        ]
      },
      markLine: {
        data: [{ type: 'average', name: 'Avg' }]
      }
    },
  ]
}

const pieOption = {
  title: {
    text: '分类的非遗数量统计',
    subtext: '比例图',
    left: 'center'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    orient: 'vertical',
    left: 'left'
  },
  series: [
    {
      type: 'pie',
      center: ['50%', '60%'],
      radius: '50%',
      data: [],
      label: {
        show: true,
        formatter(param) {
          return param.name + ' (' + param.percent + '%)';
        }
      },
      emphasis: {
        itemStyle: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)'
        }
      }
    }
  ]
}

const data = reactive({
  dataCount: {}
})

request.get('/dataCount').then(res => {
  data.dataCount = res.data
})

// 等页面所有元素加载完成后再设置 echarts图表
onMounted(() => {
  // 请求数据  初始化图表

// 折线图
  let lineDom = document.getElementById('line')
  let lineChart = echarts.init(lineDom)
  request.get('/selectLine').then(res => {
    lineOption.xAxis.data = res.data.date || []
    lineOption.series[0].data = res.data.count || []
    lineChart.setOption(lineOption)
  })

  // 饼图
  let pieDom = document.getElementById('pie')
  let pieChart = echarts.init(pieDom)
  request.get('/selectPie').then(res => {
    pieOption.series[0].data = res.data || []
    pieChart.setOption(pieOption)
  })

})
</script>