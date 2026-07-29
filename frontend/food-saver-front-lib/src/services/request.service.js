import api from './api'

export const requestService = {
  getAll(params) {
    return api.get('/requests', { params }).then(r => r.data)
  },
  
  getById(id) {
    return api.get(`/requests/${id}`).then(r => r.data)
  },
  
  create(data) {
    return api.post('/requests', data).then(r => r.data)
  },
  
  updateStatus(id, data) {
    return api.patch(`/requests/${id}/status`, data).then(r => r.data)
  },
  
  getPickupDetails(id) {
    return api.get(`/requests/${id}/pickup`).then(r => r.data)
  }
}