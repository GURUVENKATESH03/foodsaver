<template>
  <div class="space-y-6">
    <div>
      <h1 class="text-2xl font-bold text-secondary-900">Receiver Dashboard</h1>
      <p class="text-secondary-500 mt-1">Find and request available food donations</p>
    </div>
    
    <!-- Quick Stats -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
      <router-link to="/donations" class="card p-6 hover:shadow-lg transition-shadow">
        <div class="w-12 h-12 bg-primary-100 rounded-xl flex items-center justify-center mb-4">
          <svg class="w-6 h-6 text-primary-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
        </div>
        <h3 class="text-lg font-semibold text-secondary-900">Browse Food</h3>
        <p class="text-sm text-secondary-500 mt-1">{{ availableCount }} items available</p>
      </router-link>
      
      <div class="card p-6">
        <div class="w-12 h-12 bg-orange-100 rounded-xl flex items-center justify-center mb-4">
          <svg class="w-6 h-6 text-orange-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
        </div>
        <h3 class="text-lg font-semibold text-secondary-900">{{ activeRequests }}</h3>
        <p class="text-sm text-secondary-500 mt-1">Active Requests</p>
      </div>
      
      <div class="card p-6">
        <div class="w-12 h-12 bg-green-100 rounded-xl flex items-center justify-center mb-4">
          <svg class="w-6 h-6 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
        </div>
        <h3 class="text-lg font-semibold text-secondary-900">{{ completedPickups }}</h3>
        <p class="text-sm text-secondary-500 mt-1">Completed Pickups</p>
      </div>
    </div>
    
    <!-- Nearby Donations -->
    <div>
      <div class="flex items-center justify-between mb-4">
        <h3 class="text-lg font-semibold text-secondary-900">Available Near You</h3>
        <router-link to="/map" class="text-sm text-primary-600 hover:text-primary-500 flex items-center">
          <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13l6-3m-6 3V7m6 10l4.553 2.276A1 1 0 0121 18.382V7.618a1 1 0 01-.447-.894L15 7m0 13V7" />
          </svg>
          View Map
        </router-link>
      </div>
      
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <DonationCard 
          v-for="donation in nearbyDonations" 
          :key="donation.id"
          :donation="donation"
          show-request
        />
      </div>
    </div>
    
    <!-- My Requests -->
    <div class="card overflow-hidden">
      <div class="px-6 py-4 border-b border-secondary-200">
        <h3 class="text-lg font-semibold text-secondary-900">My Recent Requests</h3>
      </div>
      <div class="divide-y divide-secondary-200">
        <div 
          v-for="request in myRequests" 
          :key="request.id"
          class="px-6 py-4 flex items-center justify-between"
        >
          <div>
            <h4 class="text-sm font-medium text-secondary-900">{{ request.donationTitle }}</h4>
            <p class="text-sm text-secondary-500">Requested {{ formatDate(request.createdAt) }}</p>
          </div>
          <DonationStatusBadge :status="request.status" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useDonation } from '@/composables/useDonation'
import DonationCard from '@/components/donations/DonationCard.vue'
import DonationStatusBadge from '@/components/donations/DonationStatusBadge.vue'
import { formatDate } from '@/utils/helpers'

const { availableDonations, loadDonations } = useDonation()

const availableCount = ref(24)
const activeRequests = ref(3)
const completedPickups = ref(12)

const nearbyDonations = ref([])
const myRequests = ref([])

onMounted(async () => {
  await loadDonations()
  nearbyDonations.value = availableDonations.value.slice(0, 6)
  
  // Mock data for demo
  myRequests.value = [
    { id: 1, donationTitle: 'Fresh Vegetables Bundle', status: 'APPROVED', createdAt: '2024-01-15' },
    { id: 2, donationTitle: 'Bakery Items', status: 'PENDING', createdAt: '2024-01-14' }
  ]
})
</script>