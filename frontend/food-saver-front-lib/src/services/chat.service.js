import api from './api'

export const chatService = {
  getConversations(page = 1, limit = 20) {
    return api.get('/conversations', { params: { page, limit } }).then(r => r.data)
  },

  getMessages(conversationId, page = 1, limit = 50) {
    return api.get(`/conversations/${conversationId}/messages`, { params: { page, limit } }).then(r => r.data)
  },

  sendMessage(conversationId, content) {
    return api.post(`/conversations/${conversationId}/messages`, { content }).then(r => r.data)
  },

  startConversation(donationId) {
    return api.post('/conversations', { donationId }).then(r => r.data)
  }
}
