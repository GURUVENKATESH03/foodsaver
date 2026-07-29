<template>
  <div v-if="hasError" class="min-h-screen flex items-center justify-center bg-gray-50 p-4">
    <div class="max-w-md w-full text-center">
      <div class="w-20 h-20 bg-red-100 rounded-full flex items-center justify-center mx-auto mb-6">
        <svg class="w-10 h-10 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-2.5L13.732 4c-.77-.833-1.964-.833-2.732 0L3.732 16.5c-.77.833.192 2.5 1.732 2.5z" />
        </svg>
      </div>
      <h2 class="text-2xl font-bold text-gray-900 mb-2">Something went wrong</h2>
      <p class="text-gray-500 mb-6">{{ error?.message || 'An unexpected error occurred.' }}</p>
      <button @click="resetError" class="px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700">
        Try Again
      </button>
    </div>
  </div>
  <slot v-else />
</template>

<script setup>
import { ref, onErrorCaptured } from 'vue'

const hasError = ref(false)
const error = ref(null)

onErrorCaptured((err) => {
  error.value = err
  hasError.value = true
  console.error('Captured error:', err)
  return false
})

function resetError() {
  hasError.value = false
  error.value = null
  window.location.reload()
}
</script>
