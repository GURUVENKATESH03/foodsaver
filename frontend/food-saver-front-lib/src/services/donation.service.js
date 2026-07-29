import api from './api'

export const donationService = {
  getAll(params) {
    return api.get('/donations', { params }).then(r => r.data)
  },
  
  getById(id) {
    return api.get(`/donations/${id}`).then(r => r.data)
  },
  
  create(data) {
    return api.post('/donations', data).then(r => r.data)
  },
  
  update(id, data) {
    return api.put(`/donations/${id}`, data).then(r => r.data)
  },
  
  delete(id) {
    return api.delete(`/donations/${id}`)
  },
  
  getNearby(lat, lng, radius = 10) {
    return api.get('/donations/nearby', { 
      params: { lat, lng, radius } 
    }).then(r => r.data)
  },
  
  getCategories() {
    return api.get('/donations/categories').then(r => r.data)
  }
}