<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card>
          <div class="statistic-card">
            <div class="statistic-title">用户总数</div>
            <div class="statistic-value">1,234</div>
            <div class="statistic-footer">
              <i class="el-icon-arrow-up"></i>
              <span>12% 同比增长</span>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card>
          <div class="statistic-card">
            <div class="statistic-title">应用数量</div>
            <div class="statistic-value">24</div>
            <div class="statistic-footer">
              <i class="el-icon-arrow-up"></i>
              <span>3个新增</span>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card>
          <div class="statistic-card">
            <div class="statistic-title">角色数量</div>
            <div class="statistic-value">156</div>
            <div class="statistic-footer">
              <i class="el-icon-arrow-up"></i>
              <span>8个新增</span>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card>
          <div class="statistic-card">
            <div class="statistic-title">今日操作</div>
            <div class="statistic-value">1,567</div>
            <div class="statistic-footer">
              <i class="el-icon-arrow-down"></i>
              <span>5% 环比下降</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="16">
        <el-card>
          <div slot="header">
            <span>用户增长趋势</span>
          </div>
          <div ref="chartContainer" style="height: 300px;"></div>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card>
          <div slot="header">
            <span>应用分布</span>
          </div>
          <div ref="pieChartContainer" style="height: 300px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import * as echarts from 'echarts'

const chartContainer = ref<HTMLElement | null>(null)
const pieChartContainer = ref<HTMLElement | null>(null)

onMounted(() => {
  if (chartContainer.value) {
    const chart = echarts.init(chartContainer.value)
    chart.setOption({
      xAxis: {
        type: 'category',
        data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [120, 200, 150, 80, 70, 110, 130],
          type: 'bar'
        }
      ]
    })
  }
  
  if (pieChartContainer.value) {
    const pieChart = echarts.init(pieChartContainer.value)
    pieChart.setOption({
      series: [
        {
          type: 'pie',
          data: [
            { value: 335, name: 'Web应用' },
            { value: 310, name: '移动应用' },
            { value: 234, name: 'API服务' },
            { value: 135, name: '其他' }
          ]
        }
      ]
    })
  }
})
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.statistic-card {
  text-align: center;
}

.statistic-title {
  font-size: 14px;
  color: #666;
  margin-bottom: 10px;
}

.statistic-value {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

.statistic-footer {
  font-size: 12px;
  color: #999;
}
</style>