import api from './api'

export const authService = {
  async login(credentials) {
    const response = await api.post('/auth/login', credentials)
    return response.data
  },

  async register(userData) {
    const response = await api.post('/auth/signup', userData)
    return response.data
  },

  async logout(refreshToken) {
    await api.post('/auth/logout', { refreshToken })
  },

  async getCurrentUser() {
    const response = await api.get('/profile')
    return response.data
  },

  async refreshToken(token) {
    const response = await api.post('/auth/refresh', { refreshToken: token })
    return response.data
  },

  async updateProfile(data) {
    const response = await api.put('/profile', data)
    return response.data
  },

  async changePassword(data) {
    const response = await api.patch('/profile/password', data)
    return response.data
  }
}
