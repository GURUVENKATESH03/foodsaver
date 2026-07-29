import api from './api'

export const notificationService = {
  getAll() {
    return api.get('/notifications').then(r => r.data)
  },
  
  markAsRead(id) {
    return api.patch(`/notifications/${id}/read`)
  },
  
  markAllAsRead() {
    return api.patch('/notifications/read-all')
  },
  
  getPreferences() {
    return api.get('/notifications/preferences').then(r => r.data)
  },
  
  updatePreferences(data) {
    return api.patch('/notifications/preferences', data)
  }
}