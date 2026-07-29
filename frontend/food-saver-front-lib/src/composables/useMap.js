import { ref, onMounted, onUnmounted, watch } from 'vue'
import L from 'leaflet'

export function useMap(containerRef, options = {}) {
  const map = ref(null)
  const markers = ref([])
  const userLocation = ref(null)
  
  const defaultOptions = {
    center: [40.7128, -74.0060],
    zoom: 12,
    ...options
  }
  
  onMounted(() => {
    if (!containerRef.value) return
    
    map.value = L.map(containerRef.value).setView(
      defaultOptions.center, 
      defaultOptions.zoom
    )
    
    L.tileLayer(import.meta.env.VITE_MAP_TILE_URL || 'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors'
    }).addTo(map.value)
  })
  
  onUnmounted(() => {
    markers.value.forEach(m => m.remove())
    if (map.value) {
      map.value.remove()
      map.value = null
    }
  })
  
  function addMarker(lat, lng, popupContent, options = {}) {
    if (!map.value) return null
    
    const marker = L.marker([lat, lng], options).addTo(map.value)
    if (popupContent) marker.bindPopup(popupContent)
    markers.value.push(marker)
    return marker
  }
  
  function clearMarkers() {
    markers.value.forEach(m => m.remove())
    markers.value = []
  }
  
  function setView(lat, lng, zoom) {
    map.value?.setView([lat, lng], zoom)
  }
  
  function locateUser() {
    return new Promise((resolve, reject) => {
      if (!navigator.geolocation) {
        reject(new Error('Geolocation not supported'))
        return
      }
      
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords
          userLocation.value = { lat: latitude, lng: longitude }
          setView(latitude, longitude, 14)
          resolve(userLocation.value)
        },
        (error) => reject(error),
        { enableHighAccuracy: true, timeout: 10000 }
      )
    })
  }
  
  return {
    map,
    markers,
    userLocation,
    addMarker,
    clearMarkers,
    setView,
    locateUser
  }
}