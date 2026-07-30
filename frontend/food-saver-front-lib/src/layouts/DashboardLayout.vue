<template>
  <div class="min-h-screen bg-[var(--color-bg-warm)] flex">
    <!-- Sidebar -->
    <AppbarSidebar
      :is-open="sidebarOpen"
      @toggle="sidebarOpen = !sidebarOpen"
    />

    <!-- Main Content -->
    <div class="flex-1 flex flex-col min-w-0 transition-all duration-300 lg:ml-64">
      <!-- Top Header Bar -->
      <header class="bg-white border-b border-[var(--color-border)] sticky top-0 z-20">
        <div class="flex items-center justify-between px-4 py-3 lg:px-8">
          <!-- Left: mobile hamburger + breadcrumb -->
          <div class="flex items-center gap-4">
            <button
              @click="sidebarOpen = !sidebarOpen"
              class="lg:hidden p-2 rounded-xl text-[var(--color-text-muted)] hover:text-[var(--color-text-dark)] hover:bg-[var(--color-bg-warm)] transition-all"
              aria-label="Toggle sidebar"
            >
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
              </svg>
            </button>
            <div class="hidden sm:flex items-center gap-2 text-sm text-[var(--color-text-muted)]">
              <span class="font-medium text-[var(--color-text-dark)]">{{ pageTitle }}</span>
            </div>
          </div>

          <!-- Right: notifications + user -->
          <div class="flex items-center gap-4">
            <NotificationDropdown />

            <div class="flex items-center gap-3 pl-4 border-l border-[var(--color-border)]">
              <div class="w-8 h-8 rounded-full bg-[var(--color-primary)]/15 flex items-center justify-center text-sm font-bold text-[var(--color-primary)]">
                {{ userInitial }}
              </div>
              <div class="hidden md:block">
                <p class="text-sm font-medium text-[var(--color-text-dark)]">{{ user?.name || 'User' }}</p>
                <p class="text-[11px] text-[var(--color-text-muted)] capitalize">{{ userRole || 'Guest' }}</p>
              </div>
            </div>
          </div>
        </div>
      </header>

      <!-- Page Content -->
      <main class="flex-1 p-6 lg:p-8 overflow-auto">
        <slot />
      </main>
    </div>

    <!-- Mobile Sidebar Overlay -->
    <div
      v-if="sidebarOpen"
      class="fixed inset-0 bg-black/30 backdrop-blur-sm z-20 lg:hidden"
      @click="sidebarOpen = false"
    />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import { useAuth } from '@/composables/useAuth'
import AppbarSidebar from '@/components/AppbarSidebar.vue'
import NotificationDropdown from '@/components/notifications/NotificationDropdown.vue'

const route = useRoute()
const { user, userRole } = useAuth()

const sidebarOpen = ref(false)

const userInitial = computed(() => {
  return user.value?.name?.charAt(0)?.toUpperCase() || 'U'
})

const pageTitle = computed(() => {
  return route.meta?.title || 'Dashboard'
})
</script>
