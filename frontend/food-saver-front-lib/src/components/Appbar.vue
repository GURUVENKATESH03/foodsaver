<template>
  <nav class="bg-white border-b border-secondary-200 fixed w-full z-50 top-0">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex justify-between h-16">
        <!-- Logo -->
        <div class="flex items-center">
          <router-link to="/" class="flex items-center space-x-2">
            <div class="w-8 h-8 bg-primary-600 rounded-lg flex items-center justify-center text-white">
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
              </svg>
            </div>
            <span class="text-xl font-bold text-secondary-900">Meal Share</span>
          </router-link>
        </div>
        
        <!-- Desktop Nav -->
        <div class="hidden md:flex items-center space-x-8">
          <template v-if="isAuthenticated">
            <router-link 
              v-for="link in navLinks" 
              :key="link.name"
              :to="link.path"
              class="text-sm font-medium text-secondary-600 hover:text-primary-600 transition-colors"
              :class="{ 'text-primary-600': $route.path === link.path }"
            >
              {{ link.label }}
            </router-link>
            
            <NotificationDropdown />
            
            <div class="relative" ref="userMenuRef">
              <button 
                @click="showUserMenu = !showUserMenu"
                class="flex items-center space-x-2 text-sm font-medium text-secondary-700 hover:text-secondary-900"
              >
                <img :src="userAvatar" class="w-8 h-8 rounded-full" />
                <span>{{ user?.name }}</span>
                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
                </svg>
              </button>
              
              <div 
                v-if="showUserMenu"
                class="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg border border-secondary-200 py-1"
              >
                <router-link to="/dashboard" class="block px-4 py-2 text-sm text-secondary-700 hover:bg-secondary-50">
                  Dashboard
                </router-link>
                <router-link to="/profile" class="block px-4 py-2 text-sm text-secondary-700 hover:bg-secondary-50">
                  Profile
                </router-link>
                <hr class="my-1 border-secondary-200" />
                <button 
                  @click="handleLogout"
                  class="block w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-red-50"
                >
                  Sign out
                </button>
              </div>
            </div>
          </template>
          
          <template v-else>
            <router-link to="/auth/login" class="text-sm font-medium text-secondary-600 hover:text-primary-600">
              Sign in
            </router-link>
            <router-link to="/auth/register" class="btn-primary">
              Get Started
            </router-link>
          </template>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, computed } from 'vue'
import { onClickOutside } from '@vueuse/core'
import { useAuth } from '@/composables/useAuth'
import NotificationDropdown from '@/components/notifications/NotificationDropdown.vue'
import { generateAvatarUrl } from '@/utils/helpers'

const { user, isAuthenticated, logout, isAdmin, isDonor } = useAuth()
const showUserMenu = ref(false)
const userMenuRef = ref(null)

onClickOutside(userMenuRef, () => showUserMenu.value = false)

const userAvatar = computed(() => 
  user.value?.avatar || generateAvatarUrl(user.value?.name || 'User')
)

const navLinks = computed(() => {
  const links = [{ name: 'Donations', path: '/donations', label: 'Browse Food' }]
  if (isDonor.value) {
    links.push({ name: 'Create', path: '/donations/create', label: 'Donate Food' })
  }
  links.push({ name: 'Map', path: '/map', label: 'Map' })
  return links
})

function handleLogout() {
  logout()
  showUserMenu.value = false
}
</script>