import { format, differenceInHours, isPast, isToday, isTomorrow } from 'date-fns'

export function formatDate(date, formatStr = 'MMM dd, yyyy') {
  if (!date) return 'N/A'
  return format(new Date(date), formatStr)
}

export function formatRelativeTime(date) {
  if (!date) return ''
  const d = new Date(date)
  if (isToday(d)) return 'Today'
  if (isTomorrow(d)) return 'Tomorrow'
  return format(d, 'MMM dd')
}

export function getExpiryStatus(expiryDate) {
  if (!expiryDate) return { label: 'Unknown', color: 'gray' }
  if (isPast(new Date(expiryDate))) return { label: 'Expired', color: 'red' }
  
  const hours = differenceInHours(new Date(expiryDate), new Date())
  if (hours <= 24) return { label: 'Expires Soon', color: 'orange' }
  if (hours <= 72) return { label: 'Expiring', color: 'yellow' }
  return { label: 'Fresh', color: 'green' }
}

export function truncateText(text, maxLength = 100) {
  if (!text || text.length <= maxLength) return text
  return text.substring(0, maxLength).trim() + '...'
}

export function debounce(fn, delay = 300) {
  let timeoutId
  return (...args) => {
    clearTimeout(timeoutId)
    timeoutId = setTimeout(() => fn(...args), delay)
  }
}

export function generateAvatarUrl(name) {
  return `https://ui-avatars.com/api/?name=${encodeURIComponent(name)}&background=random&color=fff`
}

export function calculateDistance(lat1, lng1, lat2, lng2) {
  const R = 6371 // Earth's radius in km
  const dLat = (lat2 - lat1) * Math.PI / 180
  const dLng = (lng2 - lng1) * Math.PI / 180
  const a = Math.sin(dLat/2) * Math.sin(dLat/2) +
            Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
            Math.sin(dLng/2) * Math.sin(dLng/2)
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
  return Math.round(R * c * 10) / 10
}