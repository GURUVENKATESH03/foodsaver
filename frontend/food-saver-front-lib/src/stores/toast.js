import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useToastStore = defineStore('toast', () => {
  const toasts = ref([])
  let idCounter = 0

  function addToast(message, type = 'info', duration = 3000) {
    const id = ++idCounter
    toasts.value.push({ id, message, type, duration })
    if (duration > 0) setTimeout(() => removeToast(id), duration)
    return id
  }

  const success = (msg, dur) => addToast(msg, 'success', dur)
  const error = (msg, dur) => addToast(msg, 'error', dur)
  const warning = (msg, dur) => addToast(msg, 'warning', dur)
  const info = (msg, dur) => addToast(msg, 'info', dur)

  function removeToast(id) {
    const idx = toasts.value.findIndex(t => t.id === id)
    if (idx > -1) toasts.value.splice(idx, 1)
  }

  return { toasts, success, error, warning, info, removeToast }
})