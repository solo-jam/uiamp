import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import {useUserStore} from '@/store'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/Login.vue'),
        meta: {requiresAuth: false}
    },
    {
        path: '/',
        component: () => import('@/views/Layout.vue'),
        meta: {requiresAuth: true},
        children: [
            {
                path: '',
                name: 'Dashboard',
                component: () => import('@/views/Dashboard.vue')
            },
            {
                path: 'users',
                name: 'Users',
                component: () => import('@/views/user/UserList.vue')
            },
            {
              path: 'apps',
              name: 'Apps',
              component: () => import('@/views/app/AppList.vue')
            },
            {
              path: 'roles',
              name: 'Roles',
              component: () => import('@/views/role/RoleList.vue')
            },
            {
              path: 'logs',
              name: 'Logs',
                  component: () => import('@/views/log/LogList.vue')
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

// Navigation guard
router.beforeEach((to, from, next) => {
    const userStore = useUserStore()
    const isAuthenticated = userStore.isLoggedIn

    if (to.meta.requiresAuth && !isAuthenticated) {
        next('/login')
    } else if (to.name === 'Login' && isAuthenticated) {
        next('/')
    } else {
        next()
    }
})

export default router