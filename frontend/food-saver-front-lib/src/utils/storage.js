/**
 * Secure Storage Wrapper
 * Abstracts localStorage for easier testing and future encryption
 */
export const storage = {
  get(key) {
    try {
      const item = localStorage.getItem(key)
      return item ? JSON.parse(item) : null
    } catch {
      return localStorage.getItem(key)
    }
  },
  
  set(key, value) {
    localStorage.setItem(key, JSON.stringify(value))
  },
  
  remove(key) {
    localStorage.removeItem(key)
  },
  
  clear() {
    localStorage.clear()
  }
}