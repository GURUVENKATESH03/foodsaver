import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { donationService } from '@/services/donation.service'

export const useDonationStore = defineStore('donation', () => {
  // State
  const donations = ref([])
  const currentDonation = ref(null)
  const filters = ref({
    category: '',
    status: '',
    distance: 10,
    expiryDays: 7,
    search: ''
  })
  const pagination = ref({
    page: 1,
    limit: 12,
    total: 0,
    totalPages: 0
  })
  const loading = ref(false)
  const error = ref(null)
  
  // Getters
  const availableDonations = computed(() => 
    donations.value.filter(d => d.status === 'AVAILABLE' && !isExpired(d.expiryDate))
  )
  
  const myDonations = computed(() => 
    donations.value.filter(d => d.donorId === user.value?.id)
  )
  
  const donationsByCategory = computed(() => {
    const grouped = {}
    donations.value.forEach(d => {
      grouped[d.category] = (grouped[d.category] || 0) + 1
    })
    return grouped
  })
  
  // Actions
  async function fetchDonations(params = {}) {
    loading.value = true
    error.value = null
    try {
      const response = await donationService.getAll({
        ...filters.value,
        ...pagination.value,
        ...params
      })
      donations.value = response.data
      pagination.value = {
        ...pagination.value,
        total: response.total,
        totalPages: response.totalPages
      }
    } catch (err) {
      error.value = err.message || 'Failed to fetch donations'
    } finally {
      loading.value = false
    }
  }
  
  async function fetchDonationById(id) {
    loading.value = true
    try {
      const response = await donationService.getById(id)
      currentDonation.value = response
      return response
    } catch (err) {
      error.value = err.message
      throw err
    } finally {
      loading.value = false
    }
  }
  
  async function createDonation(data) {
    loading.value = true
    try {
      const response = await donationService.create(data)
      donations.value.unshift(response)
      return response
    } catch (err) {
      error.value = err.message
      throw err
    } finally {
      loading.value = false
    }
  }
  
  async function updateDonation(id, data) {
    loading.value = true
    try {
      const response = await donationService.update(id, data)
      const index = donations.value.findIndex(d => d.id === id)
      if (index !== -1) donations.value[index] = response
      if (currentDonation.value?.id === id) {
        currentDonation.value = response
      }
      return response
    } catch (err) {
      error.value = err.message
      throw err
    } finally {
      loading.value = false
    }
  }
  
  async function deleteDonation(id) {
    try {
      await donationService.delete(id)
      donations.value = donations.value.filter(d => d.id !== id)
    } catch (err) {
      error.value = err.message
      throw err
    }
  }
  
  function setFilters(newFilters) {
    filters.value = { ...filters.value, ...newFilters }
    pagination.value.page = 1
  }
  
  function resetFilters() {
    filters.value = {
      category: '',
      status: '',
      distance: 10,
      expiryDays: 7,
      search: ''
    }
    pagination.value.page = 1
  }
  
  function isExpired(dateString) {
    return new Date(dateString) < new Date()
  }
  
  return {
    donations,
    currentDonation,
    filters,
    pagination,
    loading,
    error,
    availableDonations,
    myDonations,
    donationsByCategory,
    fetchDonations,
    fetchDonationById,
    createDonation,
    updateDonation,
    deleteDonation,
    setFilters,
    resetFilters
  }
})