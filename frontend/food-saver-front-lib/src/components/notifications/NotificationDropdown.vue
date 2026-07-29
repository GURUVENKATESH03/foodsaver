<template>
  <div class="relative" ref="dropdownRef">
    <button 
      @click="isOpen = !isOpen"
      class="relative p-2 text-secondary-400 hover:text-secondary-500 rounded-lg hover:bg-secondary-100 transition-colors"
    >
      <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
      </svg>
      <span 
        v-if="unreadCount > 0"
        class="absolute top-1 right-1 w-4 h-4 bg-red-500 text-white text-xs rounded-full flex items-center justify-center"
      >
        {{ unreadCount > 9 ? '9+' : unreadCount }}
      </span>
    </button>
    
    <!-- Dropdown -->
    <div 
      v-if="isOpen"
      class="absolute right-0 mt-2 w-80 bg-white rounded-xl shadow-lg border border-secondary-200 overflow-hidden z-50"
    >
      <div class="px-4 py-3 border-b border-secondary-200 flex items-center justify-between">
        <h3 class="text-sm font-semibold text-secondary-900">Notifications</h3>
        <button 
          v-if="unreadCount > 0"
          @click="markAllAsRead"
          class="text-xs text-primary-600 hover:text-primary-500"
        >
          Mark all read
        </button>
      </div>
      
      <div class="max-h-96 overflow-y-auto">
        <div v-if="notifications.length === 0" class="p-8 text-center">
          <p class="text-sm text-secondary-500">No notifications yet</p>
        </div>
        
        <NotificationItem 
          v-for="notification in notifications.slice(0, 10)" 
          :key="notification.id"
          :notification="notification"
          @click="handleNotificationClick(notification)"
        />
      </div>
      
      <div class="px-4 py-2 border-t border-secondary-200 bg-secondary-50">
        <router-link 
          to="/notifications" 
          class="block text-center text-sm text-primary-600 hover:text-primary-500"
          @click="isOpen = false"
        >
          View all notifications
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { onClickOutside } from '@vueuse/core'
import { useNotificationStore } from '@/stores/notification'
import NotificationItem from './NotificationItem.vue'
import { useRouter } from 'vue-router'
const router = useRouter()
const store = useNotificationStore()
const isOpen = ref(false)
const dropdownRef = ref(null)

onClickOutside(dropdownRef, () => isOpen.value = false)

const notifications = computed(() => store.notifications)
const unreadCount = computed(() => store.unreadCount)

function markAllAsRead() {
  store.markAllAsRead()
}

function handleNotificationClick(notification) {
  store.markAsRead(notification.id)
  isOpen.value = false
  // Navigate based on notification type
  if (notification.link) {
    router.push(notification.link)
  }
}
</script>