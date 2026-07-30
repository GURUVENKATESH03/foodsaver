import api from './api'

export const requestService = {
  create(data) {
    return api.post('/requests', data).then(r => r.data)
  },

  approve(id) {
    return api.patch(`/requests/${id}/approve`).then(r => r.data)
  },

  reject(id) {
    return api.patch(`/requests/${id}/reject`).then(r => r.data)
  },

  complete(id) {
    return api.patch(`/requests/${id}/complete`).then(r => r.data)
  },

  getForDonation(donationId) {
    return api.get(`/requests/donation/${donationId}`).then(r => r.data)
  },

  getMine(page = 1, limit = 12) {
    return api.get('/requests/mine', { params: { page, limit } }).then(r => r.data)
  }
}
