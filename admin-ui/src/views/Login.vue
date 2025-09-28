<template>
  <div class="login-container">
    <el-card class="login-card">
      <template #header>
        <h2>统一身份认证与权限管理平台</h2>
      </template>
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" show-password></el-input>
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
import { useUserStore } from '@/store'
import { authApi } from '@/api/modules'
import type { FormInstance } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const loginFormRef = ref<FormInstance>()

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
  if (!loginFormRef.value) return
  
  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        loading.value = true
        // 调用实际的登录API
        const response = await authApi.login({
          username: loginForm.username,
          password: loginForm.password
        })
        
        // 保存token和用户信息
        userStore.setToken(response.access_token)
        
        // 获取用户信息
        const userInfo = await authApi.getCurrentUser()
        userStore.setUser(userInfo)
        
        // 跳转到首页
        router.push('/')
      } catch (error) {
        console.error('Login failed:', error)
        // 显示错误消息
      } finally {
        loading.value = false
      }
    }
  })
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