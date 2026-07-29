import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { authService } from '@/services/auth.service'
import { storage } from '@/utils/storage'

/**
 * Authentication Store
 * Manages JWT tokens, user state, and role-based access
 */
export const useAuthStore = defineStore('auth', () => {
  // State
  const user = ref(null)
  const accessToken = ref(storage.get('access_token'))
  const refreshToken = ref(storage.get('refresh_token'))
  const initialized = ref(false)
  const loading = ref(false)
  
  // Getters
  const isAuthenticated = computed(() => !!accessToken.value && !!user.value)
  const userRole = computed(() => user.value?.role || null)
  const userPermissions = computed(() => {
    const perms = {
      ADMIN: ['manage_users', 'manage_donations', 'view_analytics', 'manage_reports'],
      DONOR: ['create_donation', 'manage_own_donations', 'view_requests'],
      RECEIVER: ['browse_donations', 'create_request', 'manage_own_requests']
    }
    return perms[userRole.value] || []
  })
  
  const hasPermission = computed(() => {
    return (permission) => userPermissions.value.includes(permission)
  })
  
  // Actions
  async function initializeAuth() {
    if (initialized.value) return
    
    const token = storage.get('access_token')
    if (token) {
      try {
        // Validate token and fetch current user
        const userData = await authService.getCurrentUser()
        user.value = userData
        accessToken.value = token
      } catch (error) {
        // Token invalid, try refresh
        await attemptRefresh()
      }
    }
    initialized.value = true
  }
  
  async function attemptRefresh() {
    const rToken = storage.get('refresh_token')
    if (!rToken) {
      logout()
      return false
    }
    
    try {
      const response = await authService.refreshToken(rToken)
      setTokens(response.accessToken, response.refreshToken)
      const userData = await authService.getCurrentUser()
      user.value = userData
      return true
    } catch {
      logout()
      return false
    }
  }
  
  async function login(credentials) {
    loading.value = true
    try {
      const response = await authService.login(credentials)
      setTokens(response.accessToken, response.refreshToken)
      user.value = response.user
      return { success: true }
    } catch (error) {
      return { 
        success: false, 
        error: error.response?.data?.message || 'Login failed' 
      }
    } finally {
      loading.value = false
    }
  }
  
  async function register(userData) {
    loading.value = true
    try {
      const response = await authService.register(userData)
      setTokens(response.accessToken, response.refreshToken)
      user.value = response.user
      return { success: true }
    } catch (error) {
      return { 
        success: false, 
        error: error.response?.data?.message || 'Registration failed' 
      }
    } finally {
      loading.value = false
    }
  }
  
  function setTokens(aToken, rToken) {
    accessToken.value = aToken
    refreshToken.value = rToken
    storage.set('access_token', aToken)
    storage.set('refresh_token', rToken)
  }
  
  function logout() {
    user.value = null
    accessToken.value = null
    refreshToken.value = null
    storage.remove('access_token')
    storage.remove('refresh_token')
    // Clear other stores if needed
    window.location.href = '/auth/login'
  }
  
  function updateUserProfile(data) {
    user.value = { ...user.value, ...data }
  }
  
  return {
    user,
    accessToken,
    refreshToken,
    initialized,
    loading,
    isAuthenticated,
    userRole,
    userPermissions,
    hasPermission,
    initializeAuth,
    login,
    register,
    logout,
    attemptRefresh,
    updateUserProfile,
    setTokens
  }
})