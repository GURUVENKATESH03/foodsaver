import api from './api'

export const authService = {
  async login(credentials) {
    const response = await api.post('/auth/login', credentials)
    return response.data
  },

  async register(userData) {
    const response = await api.post('/auth/register', userData)
    return response.data
  },

  async logout() {
    await api.post('/auth/logout')
  },

  async getCurrentUser() {
    const response = await api.get('/auth/me')
    return response.data
  },

  async refreshToken(token) {
    const response = await api.post('/auth/refresh', { refreshToken: token })
    return response.data
  },

  async updateProfile(data) {
    const response = await api.patch('/auth/profile', data)
    return response.data
  },

  async changePassword(data) {
    const response = await api.post('/auth/change-password', data)
    return response.data
  }
}
