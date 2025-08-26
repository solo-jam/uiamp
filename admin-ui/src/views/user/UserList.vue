<template>
  <div class="user-list">
    <el-card>
      <div slot="header">
        <h3>用户管理</h3>
      </div>
      
      <div class="toolbar">
        <el-button type="primary" @click="handleCreate">新增用户</el-button>
        <el-input
          v-model="searchForm.username"
          placeholder="搜索用户名"
          style="width: 200px; margin-left: 20px;"
          @keyup.enter="handleSearch"
        />
        <el-button @click="handleSearch">搜索</el-button>
      </div>
      
      <el-table :data="users" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="mobile" label="手机号" />
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
    
    <!-- User Form Dialog -->
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px">
      <el-form :model="userForm" :rules="rules" ref="userFormRef" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userForm.email" />
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="userForm.mobile" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch
            v-model="userForm.status"
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
import { userApi } from '@/api/modules'

const loading = ref(false)
const dialogVisible = ref(false)
const dialogTitle = ref('')

const users = ref([])
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

const searchForm = reactive({
  username: ''
})

const userForm = reactive({
  id: 0,
  username: '',
  email: '',
  mobile: '',
  status: 1
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
  ]
}

const userFormRef = ref()

// Fetch users
const fetchUsers = async () => {
  try {
    loading.value = true
    const response = await userApi.getUsers({
      page: pagination.currentPage,
      size: pagination.pageSize,
      username: searchForm.username
    })
    users.value = response.data
    pagination.total = response.total
  } catch (error) {
    console.error('Failed to fetch users:', error)
    ElMessage.error('获取用户列表失败')
  } finally {
    loading.value = false
  }
}

// Handle create
const handleCreate = () => {
  dialogTitle.value = '新增用户'
  Object.assign(userForm, {
    id: 0,
    username: '',
    email: '',
    mobile: '',
    status: 1
  })
  dialogVisible.value = true
}

// Handle edit
const handleEdit = (row: any) => {
  dialogTitle.value = '编辑用户'
  Object.assign(userForm, row)
  dialogVisible.value = true
}

// Handle delete
const handleDelete = (row: any) => {
  ElMessageBox.confirm(
    `确定要删除用户 ${row.username} 吗？`,
    '确认删除',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(async () => {
    try {
      await userApi.deleteUser(row.id)
      ElMessage.success('删除成功')
      fetchUsers()
    } catch (error) {
      console.error('Failed to delete user:', error)
      ElMessage.error('删除失败')
    }
  })
}

// Handle search
const handleSearch = () => {
  pagination.currentPage = 1
  fetchUsers()
}

// Handle size change
const handleSizeChange = (val: number) => {
  pagination.pageSize = val
  fetchUsers()
}

// Handle current change
const handleCurrentChange = (val: number) => {
  pagination.currentPage = val
  fetchUsers()
}

// Handle submit
const handleSubmit = async () => {
  if (!userFormRef.value) return
  
  await userFormRef.value.validate(async (valid: boolean) => {
    if (valid) {
      try {
        if (userForm.id) {
          // Update user
          await userApi.updateUser(userForm.id, userForm)
          ElMessage.success('更新成功')
        } else {
          // Create user
          await userApi.createUser(userForm)
          ElMessage.success('创建成功')
        }
        dialogVisible.value = false
        fetchUsers()
      } catch (error) {
        console.error('Operation failed:', error)
        ElMessage.error('操作失败')
      }
    }
  })
}

onMounted(() => {
  fetchUsers()
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