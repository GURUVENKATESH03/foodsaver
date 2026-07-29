import axios from 'axios'
import { useToastStore } from '@/stores/toast'
import { storage } from '@/utils/storage'

/**
 * Axios Instance Configuration
 * Centralized API handling with interceptors for JWT and error management
 */
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1',
  timeout: 15000,
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  }
})

// Request Interceptor - Attach JWT Token
api.interceptors.request.use(
  (config) => {
    const token = storage.get('access_token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    
    // Add request timestamp for debugging
    config.metadata = { startTime: new Date() }
    
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response Interceptor - Handle Token Refresh & Errors
let isRefreshing = false
let failedQueue = []

const processQueue = (error, token = null) => {
  failedQueue.forEach(prom => {
    if (error) prom.reject(error)
    else prom.resolve(token)
  })
  failedQueue = []
}

api.interceptors.response.use(
  (response) => {
    // Log API timing in dev
    if (import.meta.env.DEV && response.config.metadata) {
      const duration = new Date() - response.config.metadata.startTime
      console.log(`[API] ${response.config.method?.toUpperCase()} ${response.config.url} - ${duration}ms`)
    }
    return response
  },
  async (error) => {
    const originalRequest = error.config
    const toastStore = useToastStore()
    
    // Handle 401 Unauthorized
    if (error.response?.status === 401 && !originalRequest._retry) {
      if (isRefreshing) {
        // Queue requests while refreshing
        return new Promise((resolve, reject) => {
          failedQueue.push({ resolve, reject })
        }).then(token => {
          originalRequest.headers.Authorization = `Bearer ${token}`
          return api(originalRequest)
        }).catch(err => Promise.reject(err))
      }
      
      originalRequest._retry = true
      isRefreshing = true
      
      try {
        const authStore = await import('@/stores/auth')
        const success = await authStore.attemptRefresh()
        
        if (success) {
          const newToken = storage.get('access_token')
          processQueue(null, newToken)
          originalRequest.headers.Authorization = `Bearer ${newToken}`
          return api(originalRequest)
        } else {
          processQueue(error, null)
          authStore.logout()
          return Promise.reject(error)
        }
      } catch (refreshError) {
        processQueue(refreshError, null)
        return Promise.reject(refreshError)
      } finally {
        isRefreshing = false
      }
    }
    
    // Handle 403 Forbidden
    if (error.response?.status === 403) {
      toastStore.error('You do not have permission to perform this action')
    }
    
    // Handle 500+ Server Errors
    if (error.response?.status >= 500) {
      toastStore.error('Server error. Please try again later.')
    }
    
    // Handle Network Errors
    if (!error.response) {
      toastStore.error('Network error. Please check your connection.')
    }
    
    return Promise.reject(error)
  }
)

export default api