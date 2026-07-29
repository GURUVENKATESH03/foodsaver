<template>
  <div class="space-y-6">
    <div>
      <h1 class="text-2xl font-bold text-secondary-900">Donor Dashboard</h1>
      <p class="text-secondary-500 mt-1">Manage your donations and track impact</p>
    </div>
    
    <!-- Quick Actions -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
      <router-link to="/donations/create" class="card p-6 hover:shadow-lg transition-shadow group">
        <div class="w-12 h-12 bg-primary-100 rounded-xl flex items-center justify-center mb-4 group-hover:bg-primary-200 transition-colors">
          <svg class="w-6 h-6 text-primary-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4" />
          </svg>
        </div>
        <h3 class="text-lg font-semibold text-secondary-900">Create Donation</h3>
        <p class="text-sm text-secondary-500 mt-1">List available food items</p>
      </router-link>
      
      <div class="card p-6">
        <div class="w-12 h-12 bg-blue-100 rounded-xl flex items-center justify-center mb-4">
          <svg class="w-6 h-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
          </svg>
        </div>
        <h3 class="text-lg font-semibold text-secondary-900">{{ stats.activeDonations }}</h3>
        <p class="text-sm text-secondary-500 mt-1">Active Donations</p>
      </div>
      
      <div class="card p-6">
        <div class="w-12 h-12 bg-green-100 rounded-xl flex items-center justify-center mb-4">
          <svg class="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3.055 11H5a2 2 0 012 2v1a2 2 0 002 2 2 2 0 012 2v2.945M8 3.935V5.5A2.5 2.5 0 0010.5 8h.5a2 2 0 012 2 2 2 0 104 0 2 2 0 012-2h1.064M15 20.488V18a2 2 0 012-2h3.064M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
        </div>
        <h3 class="text-lg font-semibold text-secondary-900">{{ stats.totalImpact }} kg</h3>
        <p class="text-sm text-secondary-500 mt-1">Total Food Shared</p>
      </div>
    </div>
    
    <!-- Donation Management -->
    <div class="card overflow-hidden">
      <div class="px-6 py-4 border-b border-secondary-200 flex items-center justify-between">
        <h3 class="text-lg font-semibold text-secondary-900">My Donations</h3>
        <router-link to="/donations" class="text-sm text-primary-600 hover:text-primary-500">View all</router-link>
      </div>
      
      <div v-if="loading" class="p-8">
        <LoadingSpinner size="lg" full-screen={false} />
      </div>
      
      <div v-else-if="donations.length === 0" class="p-8 text-center">
        <p class="text-secondary-500">No donations yet. Create your first donation!</p>
      </div>
      
      <div v-else class="divide-y divide-secondary-200">
        <div 
          v-for="donation in donations" 
          :key="donation.id"
          class="px-6 py-4 flex items-center justify-between hover:bg-secondary-50 transition-colors"
        >
          <div class="flex items-center space-x-4">
            <img 
              :src="donation.image || '/placeholder-food.jpg'" 
              class="w-16 h-16 rounded-lg object-cover"
            />
            <div>
              <h4 class="text-sm font-medium text-secondary-900">{{ donation.title }}</h4>
              <p class="text-sm text-secondary-500">{{ donation.quantity }} {{ donation.unit }}</p>
              <DonationStatusBadge :status="donation.status" />
            </div>
          </div>
          <div class="flex items-center space-x-2">
            <span class="text-sm text-secondary-500">{{ donation.requestsCount }} requests</span>
            <router-link 
              :to="`/donations/${donation.id}`"
              class="p-2 text-secondary-400 hover:text-primary-600"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
              </svg>
            </router-link>
          </div>
        </div>
      </div>
    </div>
    
    <!-- Pending Requests -->
    <div class="card p-6">
      <h3 class="text-lg font-semibold text-secondary-900 mb-4">Pending Requests</h3>
      <div class="space-y-3">
        <RequestCard 
          v-for="request in pendingRequests" 
          :key="request.id"
          :request="request"
          @approve="handleApprove"
          @reject="handleReject"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useDonation } from '@/composables/useDonation'
import { useRequestStore } from '@/stores/request'
import LoadingSpinner from '@/components/common/LoadingSpinner.vue'
import DonationStatusBadge from '@/components/donations/DonationStatusBadge.vue'
import RequestCard from '@/components/requests/RequestCard.vue'

const { donations, loading, loadDonations } = useDonation()
const requestStore = useRequestStore()

const stats = ref({
  activeDonations: 5,
  totalImpact: 1250,
  pendingRequests: 3
})

const pendingRequests = ref([])

onMounted(async () => {
  await loadDonations()
  await requestStore.fetchRequests({ status: 'PENDING' })
  pendingRequests.value = requestStore.pendingRequests
})

function handleApprove(requestId) {
  requestStore.updateRequestStatus(requestId, 'APPROVED')
}

function handleReject(requestId) {
  requestStore.updateRequestStatus(requestId, 'REJECTED')
}
</script>