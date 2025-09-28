<template>
  <div class="app-list">
    <el-card>
      <div slot="header">
        <h3>应用管理</h3>
      </div>
      
      <div class="toolbar">
        <el-button type="primary" @click="handleCreate">新增应用</el-button>
        <el-input
          v-model="searchForm.name"
          placeholder="搜索应用名称"
          style="width: 200px; margin-left: 20px;"
          @keyup.enter="handleSearch"
        />
        <el-button @click="handleSearch">搜索</el-button>
      </div>
      
      <el-table :data="apps" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="应用名称" />
        <el-table-column prop="code" label="应用编码" />
        <el-table-column prop="description" label="描述" />
        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" />
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
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
    
    <!-- App Form Dialog -->
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px">
      <el-form :model="appForm" :rules="rules" ref="appFormRef" label-width="80px">
        <el-form-item label="应用名称" prop="name">
          <el-input v-model="appForm.name" />
        </el-form-item>
        <el-form-item label="应用编码" prop="code">
          <el-input v-model="appForm.code" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="appForm.description" type="textarea" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch
            v-model="appForm.status"
            :active-value="1"
            :inactive-value="0"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { appApi } from '@/api/modules'

const loading = ref(false)
const dialogVisible = ref(false)
const dialogTitle = ref('')

const apps = ref([])
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

const searchForm = reactive({
  name: ''
})

const appForm = reactive({
  id: 0,
  name: '',
  code: '',
  description: '',
  status: 1
})

const rules = {
  name: [
    { required: true, message: '请输入应用名称', trigger: 'blur' }
  ],
  code: [
    { required: true, message: '请输入应用编码', trigger: 'blur' }
  ]
}

const appFormRef = ref()

// Fetch apps
const fetchApps = async () => {
  try {
    loading.value = true
    const response = await appApi.getApps({
      page: pagination.currentPage,
      size: pagination.pageSize,
      name: searchForm.name
    })
    apps.value = response.data
    pagination.total = response.total
  } catch (error) {
    console.error('Failed to fetch apps:', error)
    ElMessage.error('获取应用列表失败')
  } finally {
    loading.value = false
  }
}

// Handle create
const handleCreate = () => {
  dialogTitle.value = '新增应用'
  Object.assign(appForm, {
    id: 0,
    name: '',
    code: '',
    description: '',
    status: 1
  })
  dialogVisible.value = true
}

// Handle edit
const handleEdit = (row: any) => {
  dialogTitle.value = '编辑应用'
  Object.assign(appForm, row)
  dialogVisible.value = true
}

// Handle delete
const handleDelete = (row: any) => {
  ElMessageBox.confirm(
    `确定要删除应用 ${row.name} 吗？`,
    '确认删除',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await appApi.deleteApp(row.id)
      ElMessage.success('删除成功')
      fetchApps()
    } catch (error) {
      console.error('Failed to delete app:', error)
      ElMessage.error('删除失败')
    }
  })
}

// Handle search
const handleSearch = () => {
  pagination.currentPage = 1
  fetchApps()
}

// Handle size change
const handleSizeChange = (val: number) => {
  pagination.pageSize = val
  fetchApps()
}

// Handle current change
const handleCurrentChange = (val: number) => {
  pagination.currentPage = val
  fetchApps()
}

// Handle submit
const handleSubmit = async () => {
  if (!appFormRef.value) return
  
  await appFormRef.value.validate(async (valid: boolean) => {
    if (valid) {
      try {
        if (appForm.id) {
          // Update app
          await appApi.updateApp(appForm.id, appForm)
          ElMessage.success('更新成功')
        } else {
          // Create app
          await appApi.createApp(appForm)
          ElMessage.success('创建成功')
        }
        dialogVisible.value = false
        fetchApps()
      } catch (error) {
        console.error('Operation failed:', error)
        ElMessage.error('操作失败')
      }
    }
  })
}

onMounted(() => {
  fetchApps()
})
</script>

<style scoped>
.toolbar {
  margin-bottom: 20px;
  display: flex;
  align-items: center;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>