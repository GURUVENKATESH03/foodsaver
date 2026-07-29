import { useAuthStore } from '@/stores/auth'
import { useToastStore } from '@/stores/toast'

/**
 * Navigation Guards
 * Handles authentication, authorization, and route protection
 */
export function setupRouterGuards(router) {
  // Global Before Guard
  router.beforeEach(async (to, from, next) => {
    const authStore = useAuthStore()
    const toastStore = useToastStore()
    
    // Initialize auth state if not already done
    if (!authStore.initialized) {
      await authStore.initializeAuth()
    }
    
    const isAuthenticated = authStore.isAuthenticated
    const userRole = authStore.userRole
    
    // Handle public routes
    if (to.meta.public) {
      // Redirect authenticated users away from auth pages
      if (isAuthenticated && to.path.startsWith('/auth')) {
        return next({ name: 'Dashboard' })
      }
      return next()
    }
    
    // Handle protected routes
    if (to.meta.requiresAuth && !isAuthenticated) {
      toastStore.error('Please log in to access this page')
      return next({ 
        name: 'Login', 
        query: { redirect: to.fullPath } 
      })
    }
    
    // Handle role-based access
    if (to.meta.roles && isAuthenticated) {
      if (!to.meta.roles.includes(userRole)) {
        toastStore.error('You do not have permission to access this page')
        return next({ name: 'Dashboard' })
      }
    }
    
    next()
  })
  
  // Global After Guard - Analytics/Logging
  router.afterEach((to) => {
    // Update document title
    const appName = import.meta.env.VITE_APP_NAME || 'Meal Share'
    document.title = to.meta.title 
      ? `${to.meta.title} | ${appName}` 
      : appName
  })
  
  // Error Handler
  router.onError((error) => {
    console.error('Router Error:', error)
    // Could report to error tracking service
  })
}