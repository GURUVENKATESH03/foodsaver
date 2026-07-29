import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { requestService } from '@/services/request.service'

export const useRequestStore = defineStore('request', () => {
  const requests = ref([])
  const currentRequest = ref(null)
  const loading = ref(false)
  const stats = ref({
    pending: 0,
    approved: 0,
    completed: 0,
    rejected: 0
  })
  
  const pendingRequests = computed(() => 
    requests.value.filter(r => r.status === 'PENDING')
  )
  
  const activeRequests = computed(() => 
    requests.value.filter(r => ['PENDING', 'APPROVED', 'PICKUP_SCHEDULED'].includes(r.status))
  )
  
  async function fetchRequests(params = {}) {
    loading.value = true
    try {
      const response = await requestService.getAll(params)
      requests.value = response.data
      calculateStats()
    } finally {
      loading.value = false
    }
  }
  
  async function createRequest(donationId, message) {
    loading.value = true
    try {
      const response = await requestService.create({ donationId, message })
      requests.value.unshift(response)
      return response
    } finally {
      loading.value = false
    }
  }
  
  async function updateRequestStatus(id, status, notes = '') {
    loading.value = true
    try {
      const response = await requestService.updateStatus(id, { status, notes })
      const index = requests.value.findIndex(r => r.id === id)
      if (index !== -1) requests.value[index] = response
      calculateStats()
      return response
    } finally {
      loading.value = false
    }
  }
  
  function calculateStats() {
    stats.value = {
      pending: requests.value.filter(r => r.status === 'PENDING').length,
      approved: requests.value.filter(r => r.status === 'APPROVED').length,
      completed: requests.value.filter(r => r.status === 'COMPLETED').length,
      rejected: requests.value.filter(r => r.status === 'REJECTED').length
    }
  }
  
  return {
    requests,
    currentRequest,
    loading,
    stats,
    pendingRequests,
    activeRequests,
    fetchRequests,
    createRequest,
    updateRequestStatus
  }
})