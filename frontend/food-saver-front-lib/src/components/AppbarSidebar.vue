<template>
  <aside
    :class="[
      'fixed inset-y-0 left-0 z-30 w-64 bg-[var(--color-bg-deep)] transform transition-transform duration-300 lg:translate-x-0 flex flex-col',
      isOpen ? 'translate-x-0' : '-translate-x-full'
    ]"
  >
    <!-- Logo -->
    <div class="flex items-center h-16 px-6 border-b border-[var(--color-border-deep)] flex-shrink-0">
      <router-link to="/" class="flex items-center gap-2.5 group">
        <div class="w-8 h-8 rounded-lg bg-[var(--color-primary)]/15 flex items-center justify-center group-hover:bg-[var(--color-primary)]/25 transition-colors">
          <svg class="w-4.5 h-4.5 text-[var(--color-primary)]" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
          </svg>
        </div>
        <span class="font-display text-base font-bold text-[var(--color-text-light)]">Meal Share</span>
      </router-link>
    </div>

    <!-- Navigation -->
    <nav class="flex-1 px-3 py-5 overflow-y-auto space-y-1">
      <router-link
        v-for="item in menuItems"
        :key="item.name"
        :to="item.path"
        :class="[
          'flex items-center gap-3 px-3 py-2.5 rounded-xl text-sm font-medium transition-all duration-150',
          isActive(item.path)
            ? 'bg-[var(--color-primary)]/12 text-[var(--color-primary)] shadow-sm'
            : 'text-[var(--color-text-light-muted)] hover:text-[var(--color-text-light)] hover:bg-white/[0.06]'
        ]"
      >
        <component :is="item.icon" class="w-5 h-5 flex-shrink-0" />
        <span>{{ item.label }}</span>
        <span
          v-if="item.badge"
          class="ml-auto px-2 py-0.5 text-[10px] font-bold rounded-full bg-[var(--color-urgent)]/20 text-[var(--color-urgent)]"
        >
          {{ item.badge }}
        </span>
      </router-link>
    </nav>

    <!-- User section -->
    <div class="p-4 border-t border-[var(--color-border-deep)] flex-shrink-0">
      <div class="flex items-center gap-3 px-3 py-2.5 mb-2">
        <div class="w-8 h-8 rounded-full bg-[var(--color-primary)]/20 flex items-center justify-center text-sm font-bold text-[var(--color-primary)] flex-shrink-0">
          {{ userInitial }}
        </div>
        <div class="min-w-0">
          <p class="text-sm font-medium text-[var(--color-text-light)] truncate">{{ user?.name || 'User' }}</p>
          <p class="text-[11px] text-[var(--color-text-light-muted)] capitalize">{{ userRole || 'Guest' }}</p>
        </div>
      </div>

      <button
        @click="handleLogout"
        class="flex items-center gap-3 w-full px-3 py-2.5 rounded-xl text-sm font-medium text-[var(--color-text-light-muted)] hover:text-[var(--color-urgent)] hover:bg-[var(--color-urgent)]/8 transition-all duration-150"
      >
        <svg class="w-5 h-5 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
        </svg>
        Sign out
      </button>
    </div>
  </aside>
</template>

<script setup>
import { computed, h } from 'vue'
import { useRoute } from 'vue-router'
import { useAuth } from '@/composables/useAuth'

const props = defineProps({
  isOpen: Boolean
})

defineEmits(['toggle'])

const route = useRoute()
const { user, userRole, logout, isAdmin, isDonor, isReceiver } = useAuth()

const userInitial = computed(() => {
  return user.value?.name?.charAt(0)?.toUpperCase() || 'U'
})

function isActive(path) {
  if (path === '/dashboard') {
    return route.path === '/dashboard'
  }
  return route.path.startsWith(path)
}

// SVG icon components
const DashboardIcon = {
  render() {
    return h('svg', { class: 'w-5 h-5', fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
      h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2v-2zM14 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z' })
    ])
  }
}

const DonationIcon = {
  render() {
    return h('svg', { class: 'w-5 h-5', fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
      h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4' })
    ])
  }
}

const AddIcon = {
  render() {
    return h('svg', { class: 'w-5 h-5', fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
      h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M12 4v16m8-8H4' })
    ])
  }
}

const RequestIcon = {
  render() {
    return h('svg', { class: 'w-5 h-5', fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
      h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-3 7h3m-3 4h3m-6-4h.01M9 16h.01' })
    ])
  }
}

const MapIcon = {
  render() {
    return h('svg', { class: 'w-5 h-5', fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
      h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13l6-3m-6 3V7m6 10l4.553 2.276A1 1 0 0121 18.382V7.618a1 1 0 01-.447-.894L15 7m0 13V7' })
    ])
  }
}

const ChatIcon = {
  render() {
    return h('svg', { class: 'w-5 h-5', fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
      h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z' })
    ])
  }
}

const AnalyticsIcon = {
  render() {
    return h('svg', { class: 'w-5 h-5', fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
      h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z' })
    ])
  }
}

const UsersIcon = {
  render() {
    return h('svg', { class: 'w-5 h-5', fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
      h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z' })
    ])
  }
}

const menuItems = computed(() => {
  const items = [
    { name: 'Dashboard', path: '/dashboard', label: 'Dashboard', icon: DashboardIcon }
  ]

  if (isDonor.value || isAdmin.value) {
    items.push(
      { name: 'My Donations', path: '/donations', label: 'My Donations', icon: DonationIcon },
      { name: 'Create', path: '/donations/create', label: 'Share a Meal', icon: AddIcon }
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
      { name: 'Users', path: '/dashboard/users', label: 'User Management', icon: UsersIcon }
    )
  }

  return items
})

function handleLogout() {
  logout()
}
</script>
