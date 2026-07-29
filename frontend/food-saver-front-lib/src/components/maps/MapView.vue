<template>
  <div class="space-y-4">
    <div class="flex items-center justify-between">
      <div>
        <h1 class="text-2xl font-bold text-secondary-900">Food Map</h1>
        <p class="text-secondary-500">Find available donations near you</p>
      </div>
      <button 
        @click="locateUser"
        class="btn-secondary flex items-center"
        :disabled="locating"
      >
        <svg v-if="!locating" class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
        </svg>
        {{ locating ? 'Locating...' : 'My Location' }}
      </button>
    </div>
    
    <div class="card overflow-hidden">
      <div ref="mapContainer" class="h-[600px] w-full relative z-0"></div>
    </div>
    
    <!-- Nearby List -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      <DonationCard 
        v-for="donation in nearbyDonations" 
        :key="donation.id"
        :donation="donation"
        show-request
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useMap } from '@/composables/useMap'
import { useDonation } from '@/composables/useDonation'
import DonationCard from '@/components/donations/DonationCard.vue'

const mapContainer = ref(null)
const locating = ref(false)

const { addMarker, clearMarkers, locateUser: mapLocateUser, setView } = useMap(mapContainer)
const { availableDonations, loadDonations } = useDonation()

const nearbyDonations = ref([])

onMounted(async () => {
  await loadDonations()
  nearbyDonations.value = availableDonations.value
  
  // Add markers for donations
  availableDonations.value.forEach(donation => {
    if (donation.latitude && donation.longitude) {
      const popup = `
        <div class="p-2">
          <h3 class="font-bold">${donation.title}</h3>
          <p class="text-sm">${donation.quantity} ${donation.unit}</p>
          <a href="/donations/${donation.id}" class="text-primary-600 text-sm">View Details</a>
        </div>
      `
      addMarker(donation.latitude, donation.longitude, popup)
    }
  })
})

async function locateUser() {
  locating.value = true
  try {
    const location = await mapLocateUser()
    addMarker(location.lat, location.lng, '<b>Your Location</b>', { color: 'blue' })
  } catch (error) {
    alert('Could not get your location. Please enable location services.')
  } finally {
    locating.value = false
  }
}
</script>