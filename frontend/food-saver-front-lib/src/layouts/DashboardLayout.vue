<template>
  <div class="min-h-screen bg-secondary-50 flex">
    <!-- Sidebar -->
    <AppbarSidebar 
      :is-open="sidebarOpen" 
      @toggle="sidebarOpen = !sidebarOpen"
    />
    
    <!-- Main Content -->
    <div class="flex-1 flex flex-col min-w-0 transition-all duration-300 lg:ml-64">
      <!-- Top Header -->
      <header class="bg-white border-b border-secondary-200 sticky top-0 z-30">
        <div class="flex items-center justify-between px-4 py-4 lg:px-8">
          <button 
            @click="sidebarOpen = !sidebarOpen"
            class="lg:hidden p-2 rounded-lg text-secondary-400 hover:text-secondary-500 hover:bg-secondary-100"
          >
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
            </svg>
          </button>
          
          <div class="flex items-center space-x-4">
            <NotificationDropdown />
            
            <div class="flex items-center space-x-3 pl-4 border-l border-secondary-200">
              <img 
                :src="userAvatar" 
                :alt="user?.name"
                class="w-8 h-8 rounded-full bg-secondary-200"
              />
              <div class="hidden md:block">
                <p class="text-sm font-medium text-secondary-900">{{ user?.name }}</p>
                <p class="text-xs text-secondary-500 capitalize">{{ userRole }}</p>
              </div>
            </div>
          </div>
        </div>
      </header>
      
      <!-- Page Content -->
      <main class="flex-1 p-4 lg:p-8 overflow-auto">
        <slot />
      </main>
    </div>
    
    <!-- Mobile Sidebar Overlay -->
    <div 
      v-if="sidebarOpen" 
      class="fixed inset-0 bg-secondary-900/50 z-20 lg:hidden"
      @click="sidebarOpen = false"
    />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useAuthStore } from '@/stores/auth'
import AppbarSidebar from '@/components/AppbarSidebar.vue'
import NotificationDropdown from '@/components/notifications/NotificationDropdown.vue'
import { generateAvatarUrl } from '@/utils/helpers'

const authStore = useAuthStore()
const sidebarOpen = ref(false)

const user = computed(() => authStore.user)
const userRole = computed(() => authStore.userRole)
const userAvatar = computed(() => 
  user.value?.avatar || generateAvatarUrl(user.value?.name || 'User')
)
</script>