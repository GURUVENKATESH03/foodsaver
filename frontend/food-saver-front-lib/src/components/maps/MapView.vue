<template>
  <div class="space-y-6">
    <!-- Header -->
    <div class="flex flex-col sm:flex-row sm:items-center sm:justify-between gap-4">
      <div>
        <h1 class="font-display text-3xl font-bold text-[var(--color-text-dark)]">Food Map</h1>
        <p class="text-sm text-[var(--color-text-muted)] mt-1 font-body">Find available food donations near you on the live map</p>
      </div>
      <button
        @click="locateAndRefresh"
        class="btn-outline text-sm inline-flex items-center font-body"
        :disabled="locating"
      >
        <svg
          v-if="!locating"
          class="w-4 h-4 mr-2"
          fill="none" stroke="currentColor" viewBox="0 0 24 24"
        >
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
        </svg>
        <svg v-else class="animate-spin w-4 h-4 mr-2" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
          <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" />
          <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
        </svg>
        {{ locating ? 'Locating…' : 'My Location' }}
      </button>
    </div>

    <!-- Map Container -->
    <div class="neu-card overflow-hidden">
      <div ref="mapContainer" class="h-[500px] w-full relative z-0"></div>
    </div>

    <!-- Nearby List -->
    <div v-if="nearbyLoading" class="flex justify-center py-8">
      <div class="w-6 h-6 border-2 border-[var(--color-primary)] border-t-transparent rounded-full animate-spin" />
    </div>

    <div v-else-if="nearbyDonations.length > 0" class="space-y-4">
      <h3 class="font-display text-xl font-bold text-[var(--color-text-dark)]">Nearby Donations</h3>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <FoodCard
          v-for="donation in nearbyDonations"
          :key="donation.id"
          :donation="donation"
          @request="handleRequest"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { donationService } from '@/services/donation.service'
import { useGeolocation } from '@/composables/useGeolocation'
import { useMap } from '@/composables/useMap'
import L from 'leaflet'
import FoodCard from '@/components/donations/FoodCard.vue'

const mapContainer = ref(null)
const locating = ref(false)
const nearbyLoading = ref(false)

const { addMarker, clearMarkers, locateUser: mapLocateUser, setView } = useMap(mapContainer)
const { locateUser } = useGeolocation()

const nearbyDonations = ref([])
const userMarker = ref(null)

onMounted(async () => {
  await locateAndRefresh()
})

async function locateAndRefresh() {
  locating.value = true
  nearbyLoading.value = true

  try {
    const coords = await locateUser()

    clearMarkers()
    setView(coords.lat, coords.lng, 13)

    const userIcon = L.divIcon({
      html: '<div style="width:22px;height:22px;background:#0D9488;border:3px solid white;border-radius:50%;box-shadow:0 3px 8px rgba(0,0,0,0.3)"></div>',
      className: '',
      iconSize: [22, 22],
      iconAnchor: [11, 11]
    })
    userMarker.value = addMarker(coords.lat, coords.lng, '<b>You are here</b>', { icon: userIcon })

    const data = await donationService.getNearby(coords.lat, coords.lng, 10)
    nearbyDonations.value = Array.isArray(data) ? data : data?.data || []

    nearbyDonations.value.forEach(donation => {
      if (donation.latitude && donation.longitude) {
        const popup = `
          <div style="padding:8px;font-family:system-ui,sans-serif">
            <h3 style="font-weight:700;margin:0 0 4px">${donation.title}</h3>
            <p style="font-size:13px;margin:0 0 6px;color:#64748B">${donation.quantity || ''} ${donation.quantityUnit || ''}${donation.distance ? ` · ${donation.distance}km` : ''}</p>
            <a href="/donations/${donation.id}" style="color:#0D9488;font-size:13px;font-weight:600">View Details →</a>
          </div>
        `
        addMarker(donation.latitude, donation.longitude, popup)
      }
    })
  } catch (err) {
    console.warn('Could not load nearby donations:', err.message)
    nearbyDonations.value = []
  } finally {
    locating.value = false
    nearbyLoading.value = false
  }
}

function handleRequest(donationId) {
  console.log('Request donation:', donationId)
}
</script>
