<template>
  <div class="space-y-8">
    <!-- Header -->
    <div>
      <h1 class="font-display text-2xl font-bold text-[var(--color-text-dark)]">Receiver Dashboard</h1>
      <p class="text-sm text-[var(--color-text-muted)] mt-1">Find and request available food donations near you</p>
    </div>

    <!-- Quick Stats -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-5">
      <router-link to="/donations" class="bg-white rounded-2xl border border-[var(--color-border)] p-6 hover:shadow-[var(--shadow-card-hover)] hover:-translate-y-0.5 transition-all duration-200">
        <div class="w-12 h-12 rounded-xl bg-[var(--color-primary)]/12 flex items-center justify-center mb-4">
          <svg class="w-6 h-6 text-[var(--color-primary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
        </div>
        <p class="font-display text-3xl font-bold text-[var(--color-text-dark)]">{{ availableCount }}</p>
        <p class="text-sm text-[var(--color-text-muted)] mt-1">Items Available</p>
      </router-link>

      <div class="bg-white rounded-2xl border border-[var(--color-border)] p-6">
        <div class="w-12 h-12 rounded-xl bg-[var(--color-urgent)]/8 flex items-center justify-center mb-4">
          <svg class="w-6 h-6 text-[var(--color-urgent)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
        </div>
        <p class="font-display text-3xl font-bold text-[var(--color-text-dark)]">{{ activeRequests }}</p>
        <p class="text-sm text-[var(--color-text-muted)] mt-1">Active Requests</p>
      </div>

      <div class="bg-white rounded-2xl border border-[var(--color-border)] p-6">
        <div class="w-12 h-12 rounded-xl bg-[var(--color-secondary)]/12 flex items-center justify-center mb-4">
          <svg class="w-6 h-6 text-[var(--color-secondary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
        </div>
        <p class="font-display text-3xl font-bold text-[var(--color-text-dark)]">{{ completedPickups }}</p>
        <p class="text-sm text-[var(--color-text-muted)] mt-1">Completed Pickups</p>
      </div>
    </div>

    <!-- Available Near You (powered by real-time geolocation) -->
    <div>
      <div class="flex items-center justify-between mb-5">
        <div class="flex items-center gap-3">
          <h3 class="font-display text-lg font-bold text-[var(--color-text-dark)]">Available Near You</h3>
          <button
            v-if="!nearbyLoading"
            @click="refreshNearby"
            class="p-1.5 rounded-lg text-[var(--color-text-muted)] hover:text-[var(--color-primary)] hover:bg-[var(--color-primary)]/8 transition-all"
            :title="locatingMessage ? 'Locating…' : 'Refresh location'"
          >
            <svg
              class="w-4 h-4"
              :class="{ 'animate-spin': locatingMessage }"
              fill="none" stroke="currentColor" viewBox="0 0 24 24"
            >
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
            </svg>
          </button>
        </div>
        <router-link to="/map" class="text-sm font-medium text-[var(--color-secondary)] hover:text-[var(--color-secondary-hover)] flex items-center gap-1 transition-colors">
          <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13l6-3m-6 3V7m6 10l4.553 2.276A1 1 0 0121 18.382V7.618a1 1 0 01-.447-.894L15 7m0 13V7" />
          </svg>
          View Map
        </router-link>
      </div>

      <!-- Loading state -->
      <div v-if="nearbyLoading" class="bg-white rounded-2xl border border-[var(--color-border)] p-12 text-center">
        <div class="w-8 h-8 border-2 border-[var(--color-primary)] border-t-transparent rounded-full animate-spin mx-auto mb-3" />
        <p class="text-sm text-[var(--color-text-muted)]">{{ locatingMessage || 'Finding food near you…' }}</p>
      </div>

      <!-- Location permission prompt -->
      <div v-else-if="locationError && !nearbyDonations.length" class="bg-white rounded-2xl border border-[var(--color-border)] p-12 text-center">
        <div class="w-14 h-14 rounded-full bg-[var(--color-bg-warm)] flex items-center justify-center mx-auto mb-3">
          <svg class="w-7 h-7 text-[var(--color-text-muted)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
          </svg>
        </div>
        <p class="text-sm text-[var(--color-text-muted)] mb-3">{{ locationError }}</p>
        <button @click="refreshNearby" class="btn-primary text-sm">
          Enable Location
        </button>
      </div>

      <!-- Empty state -->
      <div v-else-if="nearbyDonations.length === 0" class="bg-white rounded-2xl border border-[var(--color-border)] p-12 text-center">
        <div class="w-14 h-14 rounded-full bg-[var(--color-bg-warm)] flex items-center justify-center mx-auto mb-3">
          <svg class="w-7 h-7 text-[var(--color-text-muted)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4" />
          </svg>
        </div>
        <p class="text-sm text-[var(--color-text-muted)]">Nothing posted near you (10km radius) — be the first to request.</p>
        <button @click="refreshNearby" class="btn-outline text-xs mt-4">Refresh location</button>
      </div>

      <!-- Nearby cards -->
      <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
        <DonationCard
          v-for="donation in nearbyDonations"
          :key="donation.id"
          :donation="donation"
          :show-request="true"
          @request="handleRequest"
        />
      </div>
    </div>

    <!-- My Recent Requests -->
    <div class="bg-white rounded-2xl border border-[var(--color-border)] overflow-hidden">
      <div class="px-6 py-4 border-b border-[var(--color-border)]">
        <h3 class="font-display text-lg font-bold text-[var(--color-text-dark)]">My Recent Requests</h3>
      </div>
      <div v-if="myRequests.length === 0" class="p-8 text-center text-sm text-[var(--color-text-muted)]">
        No requests yet. Browse available food to get started.
      </div>
      <div v-else class="divide-y divide-[var(--color-border)]">
        <div
          v-for="request in myRequests"
          :key="request.id"
          class="px-6 py-4 flex items-center justify-between"
        >
          <div>
            <h4 class="text-sm font-semibold text-[var(--color-text-dark)]">{{ request.donationTitle }}</h4>
            <p class="text-xs text-[var(--color-text-muted)] mt-0.5">Requested {{ request.createdAt }}</p>
          </div>
          <DonationStatusBadge :status="request.status" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { donationService } from '@/services/donation.service'
import { useGeolocation } from '@/composables/useGeolocation'
import DonationCard from '@/components/donations/DonationCard.vue'
import DonationStatusBadge from '@/components/donations/DonationStatusBadge.vue'

const availableCount = ref(0)
const activeRequests = ref(3)
const completedPickups = ref(12)

const nearbyDonations = ref([])
const nearbyLoading = ref(false)
const locatingMessage = ref('')
const locationError = ref('')

const myRequests = ref([])

const { userCoords, locateUser } = useGeolocation()

onMounted(async () => {
  // Try to get nearby donations based on real-time location
  await refreshNearby()

  // Mock data (will be replaced when connected to real store)
  myRequests.value = [
    { id: 1, donationTitle: 'Fresh Vegetables Bundle', status: 'APPROVED', createdAt: '2024-01-15' },
    { id: 2, donationTitle: 'Bakery Items', status: 'PENDING', createdAt: '2024-01-14' }
  ]
})

async function refreshNearby() {
  nearbyLoading.value = true
  locationError.value = ''

  try {
    // Step 1: Get user's current location
    locatingMessage.value = 'Getting your location…'
    const coords = await locateUser()
    locatingMessage.value = 'Searching nearby meals…'

    // Step 2: Fetch donations within 10km
    const data = await donationService.getNearby(coords.lat, coords.lng, 10)
    nearbyDonations.value = Array.isArray(data) ? data : data?.data || []

    availableCount.value = nearbyDonations.value.length
  } catch (err) {
    console.warn('Nearby search failed:', err.message)
    locationError.value = err.message || 'Could not find nearby donations.'

    // Fallback to empty array
    nearbyDonations.value = []
    availableCount.value = 0
  } finally {
    nearbyLoading.value = false
    locatingMessage.value = ''
  }
}

function handleRequest(donationId) {
  console.log('Request donation:', donationId)
}
</script>
