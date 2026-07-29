import { computed } from 'vue'
import { useDonationStore } from '@/stores/donation'
import { useToastStore } from '@/stores/toast'
import { useRouter } from 'vue-router'

export function useDonation() {
  const store = useDonationStore()
  const toast = useToastStore()
  const router = useRouter()
  
  async function loadDonations(params = {}) {
    await store.fetchDonations(params)
  }
  
  async function submitDonation(formData) {
    try {
      await store.createDonation(formData)
      toast.success('Donation created successfully!')
      router.push('/donations')
    } catch (error) {
      toast.error(error.message || 'Failed to create donation')
      throw error
    }
  }
  
  async function modifyDonation(id, data) {
    try {
      await store.updateDonation(id, data)
      toast.success('Donation updated successfully!')
    } catch (error) {
      toast.error(error.message || 'Failed to update donation')
      throw error
    }
  }
  
  return {
    donations: computed(() => store.donations),
    loading: computed(() => store.loading),
    filters: computed(() => store.filters),
    pagination: computed(() => store.pagination),
    availableDonations: computed(() => store.availableDonations),
    loadDonations,
    submitDonation,
    modifyDonation,
    deleteDonation: store.deleteDonation,
    setFilters: store.setFilters,
    resetFilters: store.resetFilters
  }
}