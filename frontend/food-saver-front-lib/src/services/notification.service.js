import api from './api'

export const notificationService = {
  getAll(page = 1, limit = 20) {
    return api.get('/notifications', { params: { page, limit } }).then(r => r.data)
  },

  getUnreadCount() {
    return api.get('/notifications/unread-count').then(r => r.data)
  },

  markAsRead(id) {
    return api.patch(`/notifications/${id}/read`)
  },

  markAllAsRead() {
    return api.patch('/notifications/read-all')
  }
}
