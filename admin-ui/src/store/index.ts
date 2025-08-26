import { createStore } from 'pinia'

export default createStore({
  state: () => ({
    user: null,
    token: localStorage.getItem('token') || ''
  }),
  
  getters: {
    isLoggedIn: (state) => !!state.token
  },
  
  actions: {
    setUser(user: any) {
      this.user = user
    },
    
    setToken(token: string) {
      this.token = token
      localStorage.setItem('token', token)
    },
    
    logout() {
      this.user = null
      this.token = ''
      localStorage.removeItem('token')
    }
  }
})