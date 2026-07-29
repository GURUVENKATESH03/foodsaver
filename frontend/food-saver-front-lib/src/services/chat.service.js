import api from './api'

export const chatService = {
  getConversations() {
    return api.get('/chat/conversations').then(r => r.data)
  },
  
  getMessages(conversationId, params) {
    return api.get(`/chat/conversations/${conversationId}/messages`, { params })
      .then(r => r.data)
  },
  
  sendMessage(conversationId, content) {
    return api.post(`/chat/conversations/${conversationId}/messages`, { content })
      .then(r => r.data)
  },
  
  createConversation(participantId, donationId) {
    return api.post('/chat/conversations', { participantId, donationId })
      .then(r => r.data)
  }
}