<template>
  <aside 
    :class="[
      'fixed inset-y-0 left-0 z-30 w-64 bg-white border-r border-secondary-200 transform transition-transform duration-300 lg:translate-x-0',
      isOpen ? 'translate-x-0' : '-translate-x-full'
    ]"
  >
    <div class="flex flex-col h-full">
      <!-- Logo -->
      <div class="flex items-center h-16 px-6 border-b border-secondary-200">
        <router-link to="/" class="flex items-center space-x-2">
          <div class="w-8 h-8 bg-primary-600 rounded-lg flex items-center justify-center text-white">
            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3.055 11H5a2 2 0 012 2v1a2 2 0 002 2 2 2 0 012 2v2.945M8 3.935V5.5A2.5 2.5 0 0010.5 8h.5a2 2 0 012 2 2 2 0 104 0 2 2 0 012-2h1.064M15 20.488V18a2 2 0 012-2h3.064M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
          </div>
          <span class="text-lg font-bold text-secondary-900">Meal Share</span>
        </router-link>
      </div>
      
      <!-- Navigation -->
      <nav class="flex-1 px-4 py-6 space-y-1 overflow-y-auto">
        <router-link
          v-for="item in menuItems"
          :key="item.name"
          :to="item.path"
          :class="[
            'sidebar-link',
            $route.path === item.path ? 'sidebar-link-active' : 'sidebar-link-inactive'
          ]"
        >
          <component :is="item.icon" class="w-5 h-5 mr-3 flex-shrink-0" />
          {{ item.label }}
        </router-link>
      </nav>
      
      <!-- User Section -->
      <div class="p-4 border-t border-secondary-200">
        <button 
          @click="logout"
          class="flex items-center w-full px-3 py-2 text-sm font-medium text-red-600 rounded-lg hover:bg-red-50 transition-colors"
        >
          <svg class="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
          </svg>
          Sign Out
        </button>
      </div>
    </div>
  </aside>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useAuth } from '@/composables/useAuth'
import { h } from 'vue'
const props = defineProps({
  isOpen: Boolean
})

defineEmits(['toggle'])

const route = useRoute()
const { logout, isAdmin, isDonor, isReceiver } = useAuth()

// Icon components as simple functional components
const DashboardIcon = () => h('svg', { class: 'w-5 h-5', fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
  h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2v-2zM14 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z' })
])

const DonationIcon = () => h('svg', { class: 'w-5 h-5', fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
  h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7' })
])

const RequestIcon = () => h('svg', { class: 'w-5 h-5', fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
  h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-3 7h3m-3 4h3m-6-4h.01M9 16h.01' })
])

const MapIcon = () => h('svg', { class: 'w-5 h-5', fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
  h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13l6-3m-6 3V7m6 10l4.553 2.276A1 1 0 0121 18.382V7.618a1 1 0 01-.447-.894L15 7m0 13V7' })
])

const ChatIcon = () => h('svg', { class: 'w-5 h-5', fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
  h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z' })
])

const AnalyticsIcon = () => h('svg', { class: 'w-5 h-5', fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
  h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z' })
])

const UsersIcon = () => h('svg', { class: 'w-5 h-5', fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
  h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z' })
])

const menuItems = computed(() => {
  const items = [
    { name: 'Dashboard', path: '/dashboard', label: 'Dashboard', icon: DashboardIcon }
  ]
  
  if (isDonor.value || isAdmin.value) {
    items.push(
      { name: 'Donations', path: '/donations', label: 'My Donations', icon: DonationIcon },
      { name: 'Create', path: '/donations/create', label: 'Create Donation', icon: DonationIcon }
    )
  }
  
  if (isReceiver.value || isAdmin.value) {
    items.push(
      { name: 'Browse', path: '/donations', label: 'Browse Food', icon: DonationIcon },
      { name: 'Requests', path: '/requests', label: 'My Requests', icon: RequestIcon }
    )
  }
  
  items.push(
    { name: 'Map', path: '/map', label: 'Food Map', icon: MapIcon },
    { name: 'Chat', path: '/chat', label: 'Messages', icon: ChatIcon }
  )
  
  if (isAdmin.value) {
    items.push(
      { name: 'Analytics', path: '/dashboard/analytics', label: 'Analytics', icon: AnalyticsIcon },
      { name: 'Users', path: '/dashboard/users', label: 'Users', icon: UsersIcon }
    )
  }
  
  return items
})
</script>