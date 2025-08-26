<template>
  <div class="login-container">
    <el-card class="login-card">
      <div slot="header">
        <h2>统一身份认证与权限管理平台</h2>
      </div>
      <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleLogin" :loading="loading">登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from '@/store'
import { authApi } from '@/api/modules'

const router = useRouter()
const store = useStore()

const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: ''
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

const handleLogin = async () => {
  try {
    loading.value = true
    const response = await authApi.login({
      username: loginForm.username,
      password: loginForm.password
    })
    
    // Save token and user info
    store.setToken(response.access_token)
    
    // Get user info
    const userInfo = await authApi.getCurrentUser()
    store.setUser(userInfo)
    
    // Redirect to dashboard
    router.push('/')
  } catch (error) {
    console.error('Login failed:', error)
    // Show error message
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}

.login-card {
  width: 400px;
}
</style>