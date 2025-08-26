<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <el-menu
        :default-active="$route.path"
        class="el-menu-vertical"
        router
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
      >
        <el-menu-item index="/">
          <i class="el-icon-menu"></i>
          <span>仪表盘</span>
        </el-menu-item>
        <el-menu-item index="/users">
          <i class="el-icon-user"></i>
          <span>用户管理</span>
        </el-menu-item>
        <el-menu-item index="/apps">
          <i class="el-icon-s-platform"></i>
          <span>应用管理</span>
        </el-menu-item>
        <el-menu-item index="/roles">
          <i class="el-icon-s-custom"></i>
          <span>角色管理</span>
        </el-menu-item>
        <el-menu-item index="/logs">
          <i class="el-icon-document"></i>
          <span>操作日志</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    
    <el-container>
      <el-header>
        <div class="header-content">
          <h2>统一身份认证与权限管理平台</h2>
          <div class="user-info">
            <el-dropdown>
              <span class="el-dropdown-link">
                {{ user?.username || '管理员' }}
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </div>
      </el-header>
      
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { useStore } from '@/store'
import { useRouter } from 'vue-router'

const store = useStore()
const router = useRouter()

const user = store.state.user

const handleLogout = () => {
  store.logout()
  router.push('/login')
}
</script>

<style scoped>
.layout-container {
  height: 100vh;
}

.el-header {
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

.user-info {
  cursor: pointer;
}

.el-aside {
  background-color: #545c64;
}

.el-menu-vertical {
  height: 100%;
}

.el-main {
  background-color: #f5f5f5;
  padding: 20px;
}
</style>