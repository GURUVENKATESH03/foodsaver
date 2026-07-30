import { ref } from 'vue'

/**
 * useGeolocation
 *
 * Lightweight composable to get the user's real-time geographic position
 * (no map dependency — works anywhere).
 */
export function useGeolocation() {
  const userCoords = ref(null)    // { lat, lng }
  const locating = ref(false)
  const error = ref(null)

  /**
   * Request the user's current position via the browser's Geolocation API.
   * Returns a Promise that resolves with { lat, lng }.
   */
  function locateUser(options = {}) {
    return new Promise((resolve, reject) => {
      if (!navigator.geolocation) {
        const msg = 'Geolocation is not supported by this browser.'
        error.value = msg
        reject(new Error(msg))
        return
      }

      locating.value = true
      error.value = null

      navigator.geolocation.getCurrentPosition(
        (position) => {
          const coords = {
            lat: position.coords.latitude,
            lng: position.coords.longitude
          }
          userCoords.value = coords
          locating.value = false
          resolve(coords)
        },
        (err) => {
          locating.value = false
          let msg
          switch (err.code) {
            case err.PERMISSION_DENIED:
              msg = 'Location permission denied. Please enable location access.'
              break
            case err.POSITION_UNAVAILABLE:
              msg = 'Location unavailable. Try again later.'
              break
            case err.TIMEOUT:
              msg = 'Location request timed out.'
              break
            default:
              msg = 'Failed to get location.'
          }
          error.value = msg
          reject(new Error(msg))
        },
        {
          enableHighAccuracy: true,
          timeout: 10000,
          maximumAge: 300000   // 5 min cache
        }
      )
    })
  }

  return {
    userCoords,
    locating,
    error,
    locateUser
  }
}
