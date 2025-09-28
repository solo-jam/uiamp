<template>
  <div class="log-list">
    <el-card>
      <div slot="header">
        <h3>操作日志</h3>
      </div>
      
      <div class="toolbar">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="操作人">
            <el-input v-model="searchForm.username" placeholder="搜索操作人" />
          </el-form-item>
          <el-form-item label="操作类型">
            <el-select v-model="searchForm.operation" placeholder="请选择操作类型" clearable>
              <el-option label="查询" value="SELECT" />
              <el-option label="新增" value="INSERT" />
              <el-option label="修改" value="UPDATE" />
              <el-option label="删除" value="DELETE" />
            </el-select>
          </el-form-item>
          <el-form-item label="操作时间">
            <el-date-picker
              v-model="searchForm.dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="YYYY-MM-DD"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">搜索</el-button>
            <el-button @click="handleReset">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
      
      <el-table :data="logs" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="操作人" />
        <el-table-column prop="operation" label="操作类型" />
        <el-table-column prop="method" label="请求方法" />
        <el-table-column prop="params" label="请求参数" show-overflow-tooltip />
        <el-table-column prop="time" label="耗时(ms)" />
        <el-table-column prop="ip" label="IP地址" />
        <el-table-column prop="createTime" label="操作时间" width="180" />
        <el-table-column label="操作" width="120">
          <template #default="scope">
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <div class="pagination">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pagination.currentPage"
          :page-sizes="[10, 20, 50]"
          :page-size="pagination.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pagination.total"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { logApi } from '@/api/modules'

const loading = ref(false)

const logs = ref([])
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

const searchForm = reactive({
  username: '',
  operation: '',
  dateRange: []
})

// Fetch logs
const fetchLogs = async () => {
  try {
    loading.value = true
    const params: any = {
      page: pagination.currentPage,
      size: pagination.pageSize,
      username: searchForm.username,
      operation: searchForm.operation
    }
    
    if (searchForm.dateRange && searchForm.dateRange.length === 2) {
      params.startDate = searchForm.dateRange[0]
      params.endDate = searchForm.dateRange[1]
    }
    
    const response = await logApi.getLogs(params)
    logs.value = response.data
    pagination.total = response.total
  } catch (error) {
    console.error('Failed to fetch logs:', error)
    ElMessage.error('获取操作日志失败')
  } finally {
    loading.value = false
  }
}

// Handle search
const handleSearch = () => {
  pagination.currentPage = 1
  fetchLogs()
}

// Handle reset
const handleReset = () => {
  Object.assign(searchForm, {
    username: '',
    operation: '',
    dateRange: []
  })
  pagination.currentPage = 1
  fetchLogs()
}

// Handle delete
const handleDelete = (row: any) => {
  ElMessageBox.confirm(
    `确定要删除该操作日志吗？`,
    '确认删除',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await logApi.deleteLog(row.id)
      ElMessage.success('删除成功')
      fetchLogs()
    } catch (error) {
      console.error('Failed to delete log:', error)
      ElMessage.error('删除失败')
    }
  })
}

// Handle size change
const handleSizeChange = (val: number) => {
  pagination.pageSize = val
  fetchLogs()
}

// Handle current change
const handleCurrentChange = (val: number) => {
  pagination.currentPage = val
  fetchLogs()
}

onMounted(() => {
  fetchLogs()
})
</script>

<style scoped>
.toolbar {
  margin-bottom: 20px;
}

.search-form {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}

.search-form .el-form-item {
  margin-bottom: 0;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>