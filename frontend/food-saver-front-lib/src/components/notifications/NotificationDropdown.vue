<template>
  <div class="relative" ref="dropdownRef">
    <button
      @click="isOpen = !isOpen"
      class="relative p-2 rounded-xl text-[var(--color-text-muted)] hover:text-[var(--color-text-dark)] hover:bg-[var(--color-bg-warm)] transition-all"
      aria-label="Notifications"
    >
      <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
      </svg>
      <span
        v-if="unreadCount > 0"
        class="absolute top-1 right-1 w-4 h-4 bg-[var(--color-urgent)] text-white text-[10px] font-bold rounded-full flex items-center justify-center shadow-sm"
      >
        {{ unreadCount > 9 ? '9+' : unreadCount }}
      </span>
    </button>

    <!-- Dropdown -->
    <div
      v-if="isOpen"
      class="absolute right-0 mt-2 w-80 bg-white rounded-2xl shadow-lg border border-[var(--color-border)] overflow-hidden z-50"
    >
      <div class="px-5 py-3.5 border-b border-[var(--color-border)] flex items-center justify-between">
        <h3 class="text-sm font-bold text-[var(--color-text-dark)]">Notifications</h3>
        <button
          v-if="unreadCount > 0"
          @click="markAllAsRead"
          class="text-xs font-medium text-[var(--color-secondary)] hover:text-[var(--color-secondary-hover)] transition-colors"
        >
          Mark all read
        </button>
      </div>

      <div class="max-h-96 overflow-y-auto">
        <div v-if="notifications.length === 0" class="p-8 text-center">
          <div class="w-10 h-10 rounded-full bg-[var(--color-bg-warm)] flex items-center justify-center mx-auto mb-2">
            <svg class="w-5 h-5 text-[var(--color-text-muted)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
            </svg>
          </div>
          <p class="text-sm text-[var(--color-text-muted)]">No notifications yet</p>
        </div>

        <NotificationItem
          v-for="notification in notifications.slice(0, 10)"
          :key="notification.id"
          :notification="notification"
          @click="handleNotificationClick(notification)"
        />
      </div>

      <div class="px-5 py-2.5 border-t border-[var(--color-border)] bg-[var(--color-bg-warm)]">
        <router-link
          to="/notifications"
          class="block text-center text-xs font-medium text-[var(--color-secondary)] hover:text-[var(--color-secondary-hover)] transition-colors"
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

onClickOutside(dropdownRef, () => (isOpen.value = false))

const notifications = computed(() => store.notifications)
const unreadCount = computed(() => store.unreadCount)

function markAllAsRead() {
  store.markAllAsRead()
}

function handleNotificationClick(notification) {
  store.markAsRead(notification.id)
  isOpen.value = false
  if (notification.link) {
    router.push(notification.link)
  }
}
</script>
