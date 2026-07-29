import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useNotificationStore = defineStore('notification', () => {
  const notifications = ref([])
  const unreadCount = computed(() => 
    notifications.value.filter(n => !n.read).length
  )
  
  function addNotification(notification) {
    notifications.value.unshift({
      id: Date.now(),
      timestamp: new Date().toISOString(),
      read: false,
      ...notification
    })
    // Keep max 50 notifications
    if (notifications.value.length > 50) {
      notifications.value = notifications.value.slice(0, 50)
    }
  }
  
  function markAsRead(id) {
    const notif = notifications.value.find(n => n.id === id)
    if (notif) notif.read = true
  }
  
  function markAllAsRead() {
    notifications.value.forEach(n => n.read = true)
  }
  
  function removeNotification(id) {
    notifications.value = notifications.value.filter(n => n.id !== id)
  }
  
  return {
    notifications,
    unreadCount,
    addNotification,
    markAsRead,
    markAllAsRead,
    removeNotification
  }
})