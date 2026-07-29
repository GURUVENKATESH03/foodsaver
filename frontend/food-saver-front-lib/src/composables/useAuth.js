import { computed } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { useRouter } from 'vue-router'

export function useAuth() {
  const store = useAuthStore()
  const router = useRouter()
  
  const isAdmin = computed(() => store.userRole === 'ADMIN')
  const isDonor = computed(() => store.userRole === 'DONOR')
  const isReceiver = computed(() => store.userRole === 'RECEIVER')
  
  async function handleLogin(credentials) {
    const result = await store.login(credentials)
    if (result.success) {
      const redirect = router.currentRoute.value.query.redirect || '/dashboard'
      router.push(redirect)
    }
    return result
  }
  
  async function handleRegister(data) {
    const result = await store.register(data)
    if (result.success) {
      router.push('/dashboard')
    }
    return result
  }
  
  function handleLogout() {
    store.logout()
  }
  
  return {
    user: computed(() => store.user),
    isAuthenticated: computed(() => store.isAuthenticated),
    isLoading: computed(() => store.loading),
    userRole: computed(() => store.userRole),
    isAdmin,
    isDonor,
    isReceiver,
    hasPermission: store.hasPermission,
    login: handleLogin,
    register: handleRegister,
    logout: handleLogout,
    updateProfile: store.updateUserProfile
  }
}