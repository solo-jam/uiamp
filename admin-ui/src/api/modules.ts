// User API
import api from './index'

export const userApi = {
  // Get user list
  getUsers(params: any) {
    return api.get('/user/users', { params })
  },
  
  // Get user by ID
  getUser(id: number) {
    return api.get(`/user/users/${id}`)
  },
  
  // Create user
  createUser(data: any) {
    return api.post('/user/users', data)
  },
  
  // Update user
  updateUser(id: number, data: any) {
    return api.put(`/user/users/${id}`, data)
  },
  
  // Delete user
  deleteUser(id: number) {
    return api.delete(`/user/users/${id}`)
  }
}

// App API
export const appApi = {
  // Get app list
  getApps(params: any) {
    return api.get('/app/apps', { params })
  },
  
  // Get app by ID
  getApp(id: number) {
    return api.get(`/app/apps/${id}`)
  },
  
  // Create app
  createApp(data: any) {
    return api.post('/app/apps', data)
  },
  
  // Update app
  updateApp(id: number, data: any) {
    return api.put(`/app/apps/${id}`, data)
  },
  
  // Delete app
  deleteApp(id: number) {
    return api.delete(`/app/apps/${id}`)
  }
}

// Role API
export const roleApi = {
  // Get role list
  getRoles(params: any) {
    return api.get('/permission/roles', { params })
  },
  
  // Get role by ID
  getRole(id: number) {
    return api.get(`/permission/roles/${id}`)
  },
  
  // Create role
  createRole(data: any) {
    return api.post('/permission/roles', data)
  },
  
  // Update role
  updateRole(id: number, data: any) {
    return api.put(`/permission/roles/${id}`, data)
  },
  
  // Delete role
  deleteRole(id: number) {
    return api.delete(`/permission/roles/${id}`)
  }
}

// Auth API
export const authApi = {
  // Login
  login(credentials: { username: string; password: string }) {
    return api.post('/auth/oauth2/token', credentials)
  },
  
  // Get current user info
  getCurrentUser() {
    return api.get('/auth/user')
  }
}